package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.entity.mob.AbstractPiglinEntity
import net.minecraft.entity.mob.Hoglin
import net.minecraft.entity.passive.PigEntity

class PolymorphEffect : StatusEffect(
    StatusEffectCategory.NEUTRAL,
    0xf5c2e7
) {

    override fun applyInstantEffect(
        source: Entity?,
        attacker: Entity?,
        target: LivingEntity?,
        amplifier: Int,
        proximity: Double
    ) {
        super.applyInstantEffect(source, attacker, target, amplifier, proximity)
        if (target == null) return

        if (
            target is PigEntity ||
            target is AbstractPiglinEntity ||
            target is Hoglin
        ) return

        val targetWorld = target.world

        val targetPos = target.pos
        val isBaby = target.isBaby
        val yaw = target.yaw
        val pitch = target.pitch
        val headYaw = target.headYaw

        if (target.isPlayer) target.kill() else target.discard()

        val createdPig = PigEntity(EntityType.PIG, targetWorld)

        createdPig.setPosition(targetPos)
        createdPig.isBaby = isBaby
        createdPig.yaw = yaw
        createdPig.pitch = pitch
        createdPig.headYaw = headYaw

        targetWorld.spawnEntity(createdPig)

    }

    override fun isInstant(): Boolean {
        return true
    }
}