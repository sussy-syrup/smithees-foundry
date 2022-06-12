package com.sussysyrup.theforge.client.model;

import com.mojang.datafixers.util.Pair;
import com.sussysyrup.theforge.blocks.PartBenchBlock;
import com.sussysyrup.theforge.client.util.RotateTransformer;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class PartBenchModel implements UnbakedModel, BakedModel, FabricBakedModel {

    private final SpriteIdentifier[] SPRITE_IDS;

    private static final Identifier DEFAULT_BLOCK_MODEL = new Identifier("minecraft:block/block");

    private ModelTransformation transformation;

    private static final List<Direction> side_directions = new ArrayList<>(){{
        add(Direction.EAST);
        add(Direction.SOUTH);
        add(Direction.WEST);
        add(Direction.NORTH);
    }};

    public PartBenchModel(String logNamespace, String logType) {
        SPRITE_IDS = new SpriteIdentifier[]{
                new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(logNamespace + ":block/" + logType + "_log")),
                new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(logNamespace + ":block/" + logType + "_planks")),
                new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(logNamespace + ":block/" + logType + "_log_top"))
        };
    }

    private Sprite[] SPRITES = new Sprite[3];

    private Mesh mesh;

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        context.pushTransform(new RotateTransformer(state.get(PartBenchBlock.FACING), 1F));
        context.meshConsumer().accept(mesh);
        context.popTransform();
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        context.meshConsumer().accept(mesh);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return null;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return true;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return SPRITES[0];
    }

    @Override
    public ModelTransformation getTransformation() {
        return transformation;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Arrays.asList(DEFAULT_BLOCK_MODEL);
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Arrays.asList(SPRITE_IDS);
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {

        JsonUnbakedModel defaultBlockModel = (JsonUnbakedModel) loader.getOrLoadModel(DEFAULT_BLOCK_MODEL);
        // Get its ModelTransformation
        transformation = defaultBlockModel.getTransformations();

        for(int i = 0; i < SPRITE_IDS.length; ++i) {
            SPRITES[i] = (textureGetter.apply(SPRITE_IDS[i]));
        }

        // Build the mesh using the Renderer API
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        //TOP

        emitter.square(Direction.UP, 0.3125F, 0.3125F, 0.6875F, 0.6875F, 0.0625F);

        emitter.spriteBake(0, SPRITES[2], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        for(Direction direction: side_directions)
        {
            emitter.square(direction, 0, 0.8125F, 1, 1, 0.001F);

            emitter.spriteBake(0, SPRITES[0], MutableQuadView.BAKE_LOCK_UV);

            emitter.spriteColor(0, -1, -1, -1, -1);

            emitter.emit();

            emitter.square(direction, 0.3125F, 0.9375F, 0.6875F, 1, 0.6875F);

            emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

            emitter.spriteColor(0, -1, -1, -1, -1);

            emitter.emit();
        }

        //BOTTOM

        emitter.square(Direction.DOWN, 0, 0, 1, 1, 0.8125F);

        emitter.spriteBake(0, SPRITES[2], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        //TOP

        emitter.square(Direction.UP, 0, 0, 0.3125F, 1, 0);

        emitter.spriteBake(0, SPRITES[2], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        emitter.square(Direction.UP, 0.6875F, 0, 1F, 1, 0);

        emitter.spriteBake(0, SPRITES[2], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        emitter.square(Direction.UP, 0.3125F, 0, 0.6875F, 0.3125F, 0);

        emitter.spriteBake(0, SPRITES[2], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        emitter.square(Direction.UP, 0.3125F, 0.6875F, 0.6875F, 1F, 0);

        emitter.spriteBake(0, SPRITES[2], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        //LEGS

        for(Direction direction : side_directions) {

            emitter.square(direction, 0.125F, 0, 0.3125F, 0.8125F, 0.125F);

            emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

            emitter.spriteColor(0, -1, -1, -1, -1);

            emitter.emit();

            emitter.square(direction, 0.6875F, 0, 0.875F, 0.8125F, 0.125F);

            emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

            emitter.spriteColor(0, -1, -1, -1, -1);

            emitter.emit();

            emitter.square(direction, 0.6875F, 0, 0.875F, 0.8125F, 0.6875F);

            emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

            emitter.spriteColor(0, -1, -1, -1, -1);

            emitter.emit();

            emitter.square(direction, 0.125F, 0, 0.3125F, 0.8125F, 0.6875F);

            emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

            emitter.spriteColor(0, -1, -1, -1, -1);

            emitter.emit();
        }

        emitter.square(Direction.DOWN, 0.125F, 0.125F, 0.3125F, 0.3125F, 0F);

        emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        emitter.square(Direction.DOWN, 0.6875F, 0.6875F, 0.875F, 0.875F, 0F);

        emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        emitter.square(Direction.DOWN, 0.125F, 0.6875F, 0.3125F, 0.875F, 0F);

        emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        emitter.square(Direction.DOWN, 0.6875F, 0.125F, 0.875F, 0.3125F, 0F);

        emitter.spriteBake(0, SPRITES[1], MutableQuadView.BAKE_LOCK_UV);

        emitter.spriteColor(0, -1, -1, -1, -1);

        emitter.emit();

        mesh = builder.build();
        return this;
    }
}
