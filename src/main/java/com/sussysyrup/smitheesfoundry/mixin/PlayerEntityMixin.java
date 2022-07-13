package com.sussysyrup.smitheesfoundry.mixin;

import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    private boolean smitheesfoundry$sweepBool;

    @Inject(method="attack",
            at = @At(shift = At.Shift.BEFORE,
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"))
    public void attack(Entity target, CallbackInfo ci) {

        this.smitheesfoundry$sweepBool = false;

        if (((PlayerEntity) ((Object) this)).getStackInHand(Hand.MAIN_HAND).getItem() instanceof ToolItem item) {
            if (ApiToolRegistry.getSweepWeapons().contains(item.getToolType())) {
                smitheesfoundry$sweepBool = true;
            }
        }

    }

    //The way this is formed should not break compatibility with other mods unless they break compatibility with this one from their side (hence preventing sweep)
    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 3)
    private boolean injected(boolean x) {
        if(smitheesfoundry$sweepBool == true) {
            return true;
        }
        else
        {
            return  x;
        }
    }

}
