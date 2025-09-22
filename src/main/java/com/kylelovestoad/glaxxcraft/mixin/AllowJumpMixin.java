package com.kylelovestoad.glaxxcraft.mixin;


import com.kylelovestoad.glaxxcraft.GlaxxCraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.kylelovestoad.glaxxcraft.events.AllowJump;

@Mixin(LivingEntity.class)
public abstract class AllowJumpMixin {

    /**
     * Injects a call to {@link AllowJump} event at the beginning of the jump() method.
     */
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    protected void allowJump(CallbackInfo ci) {

        // "this" is the instance of the class being mixed into
        LivingEntity self = (LivingEntity) (Object) this;

        // Fire the event and get the result
        ActionResult result = AllowJump.EVENT.invoker().allowJump(self);

        if (result == ActionResult.FAIL) {
            ci.cancel();
        }
    }
}
