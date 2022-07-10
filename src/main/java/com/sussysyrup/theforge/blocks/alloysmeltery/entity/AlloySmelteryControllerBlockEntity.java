package com.sussysyrup.theforge.blocks.alloysmeltery.entity;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.block.ForgeAlloySmelteryRegistry;
import com.sussysyrup.theforge.api.fluid.FluidProperties;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import com.sussysyrup.theforge.api.fluid.ForgeSmelteryResourceRegistry;
import com.sussysyrup.theforge.api.fluid.SmelteryResource;
import com.sussysyrup.theforge.api.transfer.MultiStorageView;
import com.sussysyrup.theforge.api.transfer.MultiVariantStorage;
import com.sussysyrup.theforge.blocks.alloysmeltery.AlloySmelteryControllerBlock;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import com.sussysyrup.theforge.screen.AlloySmelteryInvScreenHandler;
import com.sussysyrup.theforge.util.FluidUtil;
import com.sussysyrup.theforge.util.InventoryUtil;
import com.sussysyrup.theforge.util.records.ScanResult;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class AlloySmelteryControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {

    boolean valid = false;
    int count20 = 0;
    int count5 = 0;

    public int oldHeight = 0;
    public int oldWidth = 0;
    public int oldLength = 0;

    public int widthCorrection = 0;

    public Inventory itemInventory = new SimpleInventory(1);
    Inventory oldItemInventory;
    public List<Integer> smeltTicks = new ArrayList<>();
    public int itemPageShift = 0;

    public List<BlockPos> slaves = new ArrayList<>();
    public List<BlockPos> tanks = new ArrayList<>();

    public List<Fluid> currentFuels = new ArrayList<>();

    public FluidVariant activeFuel = FluidVariant.blank();

    public MultiVariantStorage<FluidVariant> fluidStorage = new MultiVariantStorage<>(){
        @Override
        public void onFinalCommit() {
            markDirty();
        }
    };

    public AlloySmelteryControllerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlocksRegistry.ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY, blockPos, blockState);

    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("INVSIZE_KEY", itemInventory.size());
        InventoryUtil.writeNbt(nbt, itemInventory, true);
        nbt.putIntArray("SMELTTICKSLIST", smeltTicks);
        nbt.putInt("PAGESHIFTITEM", itemPageShift);

        nbt.putInt("HEIGHT", oldHeight);
        nbt.putInt("WIDTH", oldWidth);
        nbt.putInt("LENGTH", oldLength);
        nbt.putInt("WIDTHCOR", widthCorrection);

        List<Long> encode = new ArrayList<>();

        for(BlockPos pos : slaves)
        {
            encode.add(pos.asLong());
        }

        nbt.putLongArray("SLAVES", encode);

        encode = new ArrayList<>();

        for(BlockPos pos : tanks)
        {
            encode.add(pos.asLong());
        }

        nbt.putLongArray("TANKS", encode);

        nbt.put("ACTIVE", activeFuel.toNbt());

        FluidUtil.writeVariantOnly(nbt, currentFuels);

        nbt.putBoolean("VALID", valid);

        FluidUtil.writeNbt(nbt, fluidStorage);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        int size = nbt.getInt("INVSIZE_KEY");
        itemInventory = new SimpleInventory(size);
        InventoryUtil.readNbt(nbt, itemInventory);
        oldItemInventory = itemInventory;
        smeltTicks = new ArrayList<>(Arrays.stream(nbt.getIntArray("SMELTTICKSLIST")).boxed().toList());
        itemPageShift = nbt.getInt("PAGESHIFTITEM");

        oldHeight = nbt.getInt("HEIGHT");
        oldWidth = nbt.getInt("WIDTH");
        oldLength = nbt.getInt("LENGTH");
        widthCorrection = nbt.getInt("WIDTHCOR");

        slaves = new ArrayList<>();
        long[] encode = nbt.getLongArray("SLAVES");

        Arrays.stream(encode).boxed().forEach(e -> slaves.add(BlockPos.fromLong(e)));

        tanks = new ArrayList<>();
        encode = nbt.getLongArray("TANKS");

        Arrays.stream(encode).boxed().forEach(e -> tanks.add(BlockPos.fromLong(e)));

        activeFuel = FluidVariant.fromNbt(nbt.getCompound("ACTIVE"));

        currentFuels = FluidUtil.readVariantOnly(nbt);

        valid = nbt.getBoolean("VALID");

        FluidUtil.readNbt(nbt, fluidStorage);
    }

    private List<Fluid> calculateFuels() {

        List<Fluid> fluids = new ArrayList<>();

        TankBlockEntity be;

        for(BlockPos pos : tanks)
        {
            be = ((TankBlockEntity) world.getBlockEntity(pos));

            if(!fluids.contains(be.fluidStorage.getResource().getFluid()))
            {
                fluids.add(be.fluidStorage.getResource().getFluid());
            }
        }

        if(!fluids.isEmpty()) {
            activeFuel = FluidVariant.of(fluids.get(0));
        }
        else
        {
            activeFuel = FluidVariant.blank();
        }

        return fluids;
    }

    public static <E extends AlloySmelteryControllerBlockEntity> void clientTicker(World world, BlockPos blockPos, BlockState blockState, E e) {
    }

    public static <E extends AlloySmelteryControllerBlockEntity> void serverTicker(World world, BlockPos blockPos, BlockState blockState, E e) {
        e.count20++;

        if(e.count20 >= 20)
        {
            e.count20 = 0;
            e.structCheck(world, blockPos);
            e.markDirty();
        }
        world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_LISTENERS);

        if(e.isValid())
        {
            if(e.count5 >= 5) {
                blockState = blockState.with(AlloySmelteryControllerBlock.LIT, true);
                world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
                e.markDirty();
            }
            e.itemSmeltTick();
        }
        else
        {
            if(e.count5 >= 5) {
                blockState = blockState.with(AlloySmelteryControllerBlock.LIT, false);
                world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
                e.markDirty();
            }
        }

        e.count5++;
    }

    @javax.annotation.Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    protected void structCheck(World world, BlockPos pos)
    {
        currentFuels = new ArrayList<>();
        tanks = new ArrayList<>();
        slaves = new ArrayList<>();

        BlockPos scanPos = pos;

        Direction direction = world.getBlockState(pos).get(AlloySmelteryControllerBlock.FACING);

        ScanResult result;

        int distanceL = 0;
        int distanceR = 0;

        int remaining = 6;

        int sideLength1 = 0;
        int sideLength2 = 0;
        int sideLength3 = 0;
        int sideLength4 = 0;

        //Scanning front
        //right
        result = scanInitialSide(world, remaining - 1, scanPos.add(direction.rotateYClockwise().getVector()), direction.rotateYClockwise());

        if(result.endPos() == null || result.nextPos() == null)
        {
            failedScan();
            return;
        }

        remaining -= result.length();
        distanceR = result.length();
        BlockPos saveScanPos = result.nextPos();

        //left
        result = specialScanLeft(world, remaining, scanPos.add(direction.rotateYCounterclockwise().getVector()), direction.rotateYCounterclockwise());

        if(result.endPos() == null || result.nextPos() == null)
        {
            failedScan();
            return;
        }

        distanceL = result.length();

        sideLength1 = distanceL + distanceR + 1;

        //relative right side
        result = scanInitialSide(world, 7, saveScanPos, direction);

        if(result.endPos() == null || result.nextPos() == null)
        {
            failedScan();
            return;
        }

        sideLength2 = result.length();
        scanPos = result.nextPos();

        //relative opposite side
        direction = direction.rotateYCounterclockwise();

        result = scanInitialSide(world, sideLength1, scanPos, direction);

        if(result.endPos() == null || result.nextPos() == null || !(result.length() == sideLength1))
        {
            failedScan();
            return;
        }

        scanPos = result.nextPos();
        sideLength3 = result.length();

        //relative left side
        direction = direction.rotateYCounterclockwise();

        result = scanInitialSide(world, sideLength2, scanPos, direction);

        if(result.endPos() == null || result.nextPos() == null || !(result.length() == sideLength2))
        {
            failedScan();
            return;
        }

        scanPos = result.nextPos();
        sideLength4 = result.length();
        widthCorrection = distanceL;

        direction = direction.rotateYCounterclockwise().rotateYCounterclockwise();
        BlockPos floorScan = scanPos.add(direction.getVector());
        floorScan = floorScan.add(0, -1, 0);

        boolean scanResult = floorScan(world, sideLength1, sideLength2, floorScan, direction);

        if(!scanResult)
        {
            failedScan();
            return;
        }

        BlockPos heightPos = scanPos.add(0, 1, 0);
        boolean cont=true;
        int height = 1;
        while (cont)
        {
            if(height > 15)
            {
                return;
            }
            if(scanRing(world, sideLength1, sideLength2, heightPos, direction))
            {
                height++;
                heightPos = heightPos.add(0, 1, 0);
            }
            else
            {
                break;
            }
        }

        BlockPos volumeScan = scanPos.add(direction.getVector());

        if(!checkInterior(world, sideLength1, sideLength2, height, volumeScan, direction))
        {
            failedScan();
            return;
        }

        successfulScan(height, sideLength1, sideLength2);
    }

    public boolean checkInterior(World world, int width, int length, int height, BlockPos startPos, Direction direction)
    {
        boolean approve = true;
        BlockPos lengthScan;
        BlockPos widthScan;
        BlockPos heightScan = startPos;

        mainloop : for(int h = 0; h < height; h++) {
            lengthScan = heightScan;

            innerloop:
            for (int l = 0; l < length; l++) {
                widthScan = lengthScan;

                for (int w = 0; w < width; w++) {
                    approve = world.getBlockState(widthScan).isAir();

                    if (approve == false) {
                        break mainloop;
                    }

                    widthScan = widthScan.add(direction.rotateYClockwise().getVector());
                }

                lengthScan = lengthScan.add(direction.getVector());
            }
            heightScan = heightScan.add(0, 1, 0);
        }

        return approve;
    }

    public ScanResult scanInitialSide(World world, int length, BlockPos startPos, Direction direction)
    {
        boolean cont = true;
        BlockPos pos = startPos;

        boolean blockCheck;
        boolean firstScan = true;

        int lengthOut = 0;

        //Scan Blocks to the side
        while (cont)
        {
            lengthOut++;

            if(lengthOut > length)
            {
                lengthOut--;
                break;
            }

            blockCheck = slaveBlockCheck(world, pos);

            if(!blockCheck)
            {
                lengthOut--;
                break;
            }

            pos = pos.add(direction.getVector());
            firstScan = false;
        }

        if(firstScan == true)
        {
            return new ScanResult(0, null, null);
        }

        BlockPos endPos = startPos;
        endPos = endPos.add(direction.rotateYCounterclockwise().getVector());
        endPos = endPos.add(direction.getVector());

        int newLengthOut = -1;

        for(int i = 0; i < lengthOut; i++)
        {
            blockCheck = dummyBlockCheck(world, endPos);
            if(blockCheck)
            {
                newLengthOut = i + 1;
                break;
            }
            endPos = endPos.add(direction.getVector());
        }

        int removeLength = 0;
        BlockPos removePos = null;

        if(newLengthOut == -1)
        {
            endPos = null;
        }
        else
        {
            removeLength = lengthOut - newLengthOut;
            removePos = endPos.add(direction.rotateYClockwise().getVector());
            lengthOut = newLengthOut;
        }

        for(int i = 0; i < removeLength; i++)
        {
            removeSlave(world, removePos);
            removePos = removePos.add(direction.getVector());
        }

        return new ScanResult(lengthOut, pos, endPos);
    }
    public ScanResult specialScanLeft(World world, int length, BlockPos startPos, Direction direction)
    {
        boolean cont = true;
        BlockPos pos = startPos;

        boolean blockCheck;
        boolean firstScan = true;

        int lengthOut = 0;

        //Scan Blocks to the side
        while (cont)
        {
            lengthOut++;

            if(lengthOut > length)
            {
                lengthOut--;
                break;
            }

            blockCheck = slaveBlockCheck(world, pos);

            if(!blockCheck)
            {
                lengthOut--;
                break;
            }

            pos = pos.add(direction.getVector());
            firstScan = false;
        }

        if(firstScan == true)
        {
            return new ScanResult(0, null, null);
        }

        BlockPos endPos = startPos;
        endPos = endPos.add(direction.rotateYClockwise().getVector());
        endPos = endPos.add(direction.getVector());

        int newLengthOut = -1;

        for(int i = 0; i < lengthOut; i++)
        {
            blockCheck = dummyBlockCheck(world, endPos);
            if(blockCheck)
            {
                newLengthOut = i + 1;
                break;
            }
            endPos = endPos.add(direction.getVector());
        }

        int removeLength = 0;
        BlockPos removePos = null;

        if(newLengthOut == -1)
        {
            endPos = null;
        }
        else
        {
            removeLength = lengthOut - newLengthOut;
            removePos = endPos.add(direction.rotateYCounterclockwise().getVector());
            lengthOut = newLengthOut;
        }

        for(int i = 0; i < removeLength; i++)
        {
            removeSlave(world, removePos);
            removePos = removePos.add(direction.getVector());
        }

        return new ScanResult(lengthOut, pos, endPos);
    }

    public ScanResult scanSide(World world, int length, BlockPos startPos, Direction direction)
    {
        boolean cont = true;
        BlockPos pos = startPos;

        boolean blockCheck;
        boolean firstScan = true;

        int lengthOut = 0;

        //Scan Blocks to the side
        while (cont)
        {
            lengthOut++;

            if(lengthOut > length)
            {
                lengthOut--;
                break;
            }

            blockCheck = dummyBlockCheck(world, pos);

            if(!blockCheck)
            {
                lengthOut--;
                break;
            }

            pos = pos.add(direction.getVector());
            firstScan = false;
        }

        if(firstScan == true)
        {
            return new ScanResult(0, null, null);
        }

        BlockPos endPos = startPos;
        endPos = endPos.add(direction.rotateYCounterclockwise().getVector());
        endPos = endPos.add(direction.getVector());

        int newLengthOut = -1;

        for(int i = 0; i < lengthOut; i++)
        {
            blockCheck = dummyBlockCheck(world, endPos);
            if(blockCheck)
            {
                newLengthOut = i + 1;
                break;
            }
            endPos = endPos.add(direction.getVector());
        }

        int removeLength = 0;
        if(newLengthOut == -1)
        {
            endPos = null;
        }
        else
        {
            lengthOut = newLengthOut;
        }

        return new ScanResult(lengthOut, pos, endPos);
    }

    public void scanSideInclude(World world, int length, BlockPos startPos, Direction direction)
    {
        boolean cont = true;
        BlockPos pos = startPos;

        boolean blockCheck;

        int lengthOut = 0;

        //Scan Blocks to the side
        while (cont)
        {
            lengthOut++;

            if(lengthOut > length)
            {
                lengthOut--;
                break;
            }

            blockCheck = slaveBlockCheck(world, pos);

            if(!blockCheck)
            {
                lengthOut--;
                break;
            }

            pos = pos.add(direction.getVector());
        }
    }

    public boolean scanRing(World world, int sideWidth, int sideHeight, BlockPos startPos, Direction direction)
    {
        BlockPos pos = startPos;

        ScanResult scanResult = scanSide(world, sideWidth, pos, direction.rotateYClockwise());

        if(scanResult.length() != sideWidth || scanResult.endPos() == null || scanResult.nextPos() == null)
        {
            return false;
        }

        BlockPos pos1 = scanResult.nextPos();
        direction = direction.rotateYCounterclockwise();

        scanResult = scanSide(world, sideHeight, pos1, direction.rotateYClockwise());

        if(scanResult.length() != sideHeight || scanResult.endPos() == null || scanResult.nextPos() == null)
        {
            return false;
        }

        BlockPos pos2 = scanResult.nextPos();
        direction = direction.rotateYCounterclockwise();

        scanResult = scanSide(world, sideWidth, pos2, direction.rotateYClockwise());

        if(scanResult.length() != sideWidth || scanResult.endPos() == null || scanResult.nextPos() == null)
        {
            return false;
        }

        BlockPos pos3 = scanResult.nextPos();
        direction = direction.rotateYCounterclockwise();

        scanResult = scanSide(world, sideHeight, pos3, direction.rotateYClockwise());

        if(scanResult.length() != sideHeight || scanResult.endPos() == null || scanResult.nextPos() == null)
        {
            return false;
        }
        scanSideInclude(world, sideWidth, pos, direction);

        direction = direction.rotateYCounterclockwise();
        scanSideInclude(world, sideHeight, pos1, direction);

        direction = direction.rotateYCounterclockwise();
        scanSideInclude(world, sideWidth, pos2, direction);

        direction = direction.rotateYCounterclockwise();
        scanSideInclude(world, sideHeight, pos3, direction);

        return true;
    }

    public boolean floorScan(World world, int width, int height, BlockPos startPos, Direction direction)
    {
        boolean approve = true;
        BlockPos pos = startPos;
        BlockPos widthScan;

        mainloop : for(int h = 0; h < height; h++)
        {
            widthScan = pos;

            for(int w = 0; w < width; w++)
            {
                approve = dummyBlockCheck(world, widthScan);

                if(approve == false)
                {
                    break mainloop;
                }

                widthScan = widthScan.add(direction.rotateYClockwise().getVector());
            }

            pos = pos.add(direction.getVector());
        }

        return approve;
    }

    protected void removeSlave(World world, BlockPos pos)
    {
        if(ForgeAlloySmelteryRegistry.getFunctionalBlocks().contains(world.getBlockState(pos).getBlock()))
        {
            slaves.remove(pos);
        }
        if(ForgeAlloySmelteryRegistry.getTankBlocks().contains(world.getBlockState(pos).getBlock()))
        {
            if(tanks.contains(pos)) {
                tanks.remove(pos);
            }
        }
    }

    protected boolean dummyBlockCheck(World world, BlockPos pos)
    {
        boolean valid = false;

        if(ForgeAlloySmelteryRegistry.getStructureBlocks().contains(world.getBlockState(pos).getBlock())) {
            valid = true;
        }
        if(ForgeAlloySmelteryRegistry.getFunctionalBlocks().contains(world.getBlockState(pos).getBlock()))
        {
            valid = true;
        }

        if(ForgeAlloySmelteryRegistry.getTankBlocks().contains(world.getBlockState(pos).getBlock()))
        {
            valid = true;
        }

        return valid;
    }

    protected boolean slaveBlockCheck(World world, BlockPos pos)
    {
        boolean valid = false;
        Block block = world.getBlockState(pos).getBlock();

        if(ForgeAlloySmelteryRegistry.getStructureBlocks().contains(block)) {
            valid = true;
        }
        if(ForgeAlloySmelteryRegistry.getFunctionalBlocks().contains(block))
        {
            slaves.add(pos);
            valid = true;
        }
        if(ForgeAlloySmelteryRegistry.getTankBlocks().contains(block))
        {
            TankBlockEntity blockEntity = (TankBlockEntity) world.getBlockEntity(pos);

            Map<Fluid, Integer> fuelMap = ForgeAlloySmelteryRegistry.getFuelFluids();

            if(fuelMap.keySet().contains(blockEntity.fluidStorage.getResource().getFluid())) {
                if(currentFuels.contains(blockEntity.fluidStorage.getResource().getFluid())) {
                    tanks.add(pos);
                }
                else
                {
                    if(currentFuels.size() < 3)
                    {
                        tanks.add(pos);
                        currentFuels.add(blockEntity.fluidStorage.getResource().getFluid());
                    }
                }
            }
            valid = true;
        }

        return valid;
    }

    private void successfulScan(int height, int length, int width)
    {
        for(BlockPos slavePos : slaves)
        {
            if(world.getBlockEntity(slavePos) instanceof ISlave slave)
            {
                slave.addMaster(this.pos);
            }
        }

        valid = true;

        boolean recalculateInventories = oldHeight != height || oldLength != length || oldWidth != width;

        if(recalculateInventories)
        {
            //Item Inventory
            SimpleInventory newInv = new SimpleInventory(height * length * width);
            int newSize = newInv.size();
            int oldSize = itemInventory.size();

            if(oldSize > newSize) {
                for (int i = 0; i < newSize; i++) {
                    newInv.setStack(i, itemInventory.getStack(i));
                }

                BlockPos dropPos = pos.add(world.getBlockState(pos).get(AlloySmelteryControllerBlock.FACING).getOpposite().getVector());
                Inventory dropInv = new SimpleInventory(oldSize - newSize);

                int counter = 0;

                for (int i = newSize; i < oldSize; i++) {
                    dropInv.setStack(counter, itemInventory.getStack(i));
                    counter++;
                }

                ItemScatterer.spawn(world, dropPos, dropInv);
            }
            else
            {
                for(int i = 0; i < oldSize; i++)
                {
                    newInv.setStack(i, itemInventory.getStack(i));
                }
            }

            itemInventory = newInv;

            //Fluid inventory
            long maxCapacity = height * length * width * FluidConstants.BLOCK;
            if(fluidStorage.maxCapacity <= maxCapacity)
            {
                fluidStorage.maxCapacity = maxCapacity;
            }

            if(fluidStorage.maxCapacity > maxCapacity)
            {
                if(fluidStorage.getCurrentCapacity() > maxCapacity)
                {
                    long diff = fluidStorage.getCurrentCapacity() - maxCapacity;
                    MultiStorageView<FluidVariant> view;
                    for (int i = fluidStorage.views.size() - 1; i >= 0; i--)
                    {
                        view = (MultiStorageView<FluidVariant>) fluidStorage.views.get(i);
                        if(view.getAmount() > diff)
                        {
                            view.setAmount(view.getAmount() - diff);
                            break;
                        }
                        if(view.getAmount() == diff)
                        {
                            fluidStorage.views.remove(view);
                            break;
                        }
                        if(view.getAmount() < diff)
                        {
                            fluidStorage.views.remove(view);
                        }
                    }
                }
                fluidStorage.maxCapacity = maxCapacity;
            }
        }

        oldHeight = height;
        oldLength = length;
        oldWidth = width;

        if(tanks.isEmpty())
        {
            activeFuel = FluidVariant.blank();
        }
        else
        {
            boolean containsOldFuel = false;

            for(BlockPos pos : tanks)
            {
                if(activeFuel.equals(((TankBlockEntity) world.getBlockEntity(pos)).fluidStorage.variant))
                {
                    containsOldFuel = true;
                    break;
                }
            }
            if(!containsOldFuel)
            {
                activeFuel = ((TankBlockEntity) world.getBlockEntity(tanks.get(0))).fluidStorage.variant;
            }
        }
    }

    private void failedScan()
    {
        valid = false;

        for(BlockPos pos : slaves)
        {
            if(world.getBlockEntity(pos) instanceof ISlave slave)
            {
                slave.removeMaster();
            }
        }

        slaves = new ArrayList<>();
    }

    public boolean isValid()
    {
        return valid;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        if(valid)
        {
            return new AlloySmelteryInvScreenHandler(syncId, inv, itemInventory, this);
        }
        return null;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("theforge.container.alloysmeltery");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(itemInventory.size());
        buf.writeBlockPos(getPos());
        buf.writeBlockPos(getPos());
    }

    void itemSmeltTick()
    {
        //PrepareSmeltList
        if(smeltTicks == null)
        {
            smeltTicks = new ArrayList<>();
        }
        if(smeltTicks.isEmpty())
        {
            for(int i = 0; i < itemInventory.size(); i++)
            {
                smeltTicks.add(0);
            }
        }
        else
        {
            if(smeltTicks.size() > itemInventory.size())
            {
                smeltTicks = smeltTicks.subList(0, itemInventory.size() - 1);
            }
            if(smeltTicks.size() < itemInventory.size())
            {
                for(int i = smeltTicks.size(); i < itemInventory.size(); i++)
                {
                    smeltTicks.add(0);
                }
            }
        }

        if(oldItemInventory == null)
        {
            oldItemInventory = itemInventory;
        }

        FluidVariant fuel = activeFuel;

        if(fuel.isBlank())
        {
            return;
        }

        int tickStep = ForgeAlloySmelteryRegistry.getFuelValue(fuel.getFluid());

        ItemStack stack;
        Item item;
        SmelteryResource smelteryResource;
        FluidProperties fluidProperties;
        int cookTime;

        int smeltTick;

        boolean tickSmelt = false;

        //Actually tick the smelting
        for(int i = 0; i < itemInventory.size(); i++)
        {
            stack = itemInventory.getStack(i);
            if(stack.isEmpty())
            {
                smeltTicks.set(i, 0);
                continue;
            }
            item = stack.getItem();

            if(!ForgeSmelteryResourceRegistry.getSmelteryResourceMap().containsKey(item))
            {
                smeltTicks.set(i, 0);
                continue;
            }

            if(!oldItemInventory.getStack(i).getItem().equals(itemInventory.getStack(i).getItem()))
            {
                smeltTicks.set(i, 0);
            }

            smelteryResource = ForgeSmelteryResourceRegistry.getSmelteryResource(item);

            if(smelteryResource == null)
            {
                smeltTicks.set(i, 0);
                continue;
            }

            fluidProperties = ForgeMoltenFluidRegistry.getFluidProperties(smelteryResource.fluidID());
            cookTime = (int) (fluidProperties.getCookTime() * (smelteryResource.fluidValue() / FluidConstants.INGOT));

            if(smeltTicks.get(i) < cookTime) {
                smeltTicks.set(i, smeltTicks.get(i) + tickStep);
            }

            tickSmelt = true;

            smeltTick = smeltTicks.get(i);

            if(smeltTick >= cookTime && fluidStorage.getCurrentCapacity() < fluidStorage.maxCapacity)
            {
                smeltTicks.set(i, 0);
                itemInventory.setStack(i, ItemStack.EMPTY);
                meltItem(smelteryResource);
            }
        }

        if(tickSmelt)
        {
            TankBlockEntity tank;

            for(BlockPos pos : tanks)
            {
                tank = ((TankBlockEntity) world.getBlockEntity(pos));
                if(tank.fluidStorage.getResource().equals(fuel))
                {
                    try (Transaction transaction = Transaction.openOuter())
                    {
                        tank.fluidStorage.extract(fuel, 20, transaction);
                        transaction.commit();
                    }

                    calculateFuels();

                    break;
                }
            }
        }

        oldItemInventory = itemInventory;
    }

    private void meltItem(SmelteryResource smelteryResource)
    {
        try(Transaction transaction = Transaction.openOuter())
        {
            fluidStorage.insert(FluidVariant.of(Registry.FLUID.get(new Identifier(Main.MODID, smelteryResource.fluidID()))), smelteryResource.fluidValue(), transaction);
            transaction.commit();
        }
    }
}
