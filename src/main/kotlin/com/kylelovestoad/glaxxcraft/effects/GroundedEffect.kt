package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.util.math.Vec3d

class GroundedEffect : StatusEffect(
    StatusEffectCategory.HARMFUL,
    0x6a339d
) {
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int): Boolean {
        entity.velocity = Vec3d(entity.velocity.x, -0.5 * (1 + (amplifier * 0.5)), entity.velocity.z)
        return true
    }
}