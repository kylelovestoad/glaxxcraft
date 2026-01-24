package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.Vec3

class GroundedEffect : MobEffect(
    MobEffectCategory.HARMFUL,
    0x6a339d
) {
    override fun shouldApplyEffectTickThisTick(duration: Int, amplifier: Int): Boolean {
        return true
    }

    override fun applyEffectTick(world: ServerLevel, entity: LivingEntity, amplifier: Int): Boolean {
        entity.setDeltaMovement(Vec3(entity.deltaMovement.x, -0.5 * (1 + (amplifier * 0.5)), entity.deltaMovement.z))
        return true
    }
}