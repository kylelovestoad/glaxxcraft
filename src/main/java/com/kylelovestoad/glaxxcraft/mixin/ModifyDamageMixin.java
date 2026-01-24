package com.kylelovestoad.glaxxcraft.mixin;

import com.kylelovestoad.glaxxcraft.GlaxxEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// TODO(Ravel): can not resolve target class LivingEntity
@Mixin(LivingEntity.class)
public class ModifyDamageMixin {
    // TODO(Ravel): no target class
    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true)
    private float modifyDamage(float amount, ServerLevel world, DamageSource source) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        MobEffectInstance vulnerable = livingEntity.getEffect(GlaxxEffects.INSTANCE.getVULNERABLE());

        if (vulnerable == null || source.is(DamageTypes.FELL_OUT_OF_WORLD) || source.is(DamageTypes.STARVE)) {
            return amount;
        }

        int level = vulnerable.getAmplifier() + 1;
        float vulnerablePercentage = 0.40f * level;
        return amount * (1.0f + vulnerablePercentage);
    }

}
