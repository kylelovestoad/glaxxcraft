package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes

class MantleEffect : StatusEffect(
    StatusEffectCategory.BENEFICIAL,
    0x89CFF0,
    ParticleTypes.FIREWORK
) {
}