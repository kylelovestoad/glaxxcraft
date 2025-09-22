package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory

class GroundedEffect : StatusEffect(
    StatusEffectCategory.HARMFUL,
    0x6a339d
) {
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }
}