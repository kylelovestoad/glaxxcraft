package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes

class MantleEffect : MobEffect(
    MobEffectCategory.BENEFICIAL,
    0x89CFF0,
    ParticleTypes.FIREWORK
) {
}