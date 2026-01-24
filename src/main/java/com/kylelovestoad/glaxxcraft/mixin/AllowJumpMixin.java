package com.kylelovestoad.glaxxcraft.mixin;


import com.kylelovestoad.glaxxcraft.GlaxxCraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.kylelovestoad.glaxxcraft.events.AllowJump;

// TODO(Ravel): can not resolve target class LivingEntity
@Mixin(LivingEntity.class)
public abstract class AllowJumpMixin {

    // TODO(Ravel): no target class
/**
     * Injects a call to {@link AllowJump} event at the beginning of the jump() method.
     */
    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    protected void allowJump(CallbackInfo ci) {

        // "this" is the instance of the class being mixed into
        LivingEntity self = (LivingEntity) (Object) this;

        // Fire the event and get the result
        InteractionResult result = AllowJump.EVENT.invoker().allowJump(self);

        if (result == InteractionResult.FAIL) {
            ci.cancel();
        }
    }
}
