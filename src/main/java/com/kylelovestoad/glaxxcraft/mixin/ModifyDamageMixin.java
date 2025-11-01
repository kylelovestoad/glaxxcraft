package com.kylelovestoad.glaxxcraft.mixin;

import com.kylelovestoad.glaxxcraft.GlaxxEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class ModifyDamageMixin {
    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float modifyDamage(float amount, ServerWorld world, DamageSource source) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        StatusEffectInstance vulnerable = livingEntity.getStatusEffect(GlaxxEffects.INSTANCE.getVULNERABLE());

        if (vulnerable == null || source.isOf(DamageTypes.OUT_OF_WORLD) || source.isOf(DamageTypes.STARVE)) {
            return amount;
        }

        int level = vulnerable.getAmplifier() + 1;
        float vulnerablePercentage = 0.40f * level;
        return amount * (1.0f + vulnerablePercentage);
    }

}
