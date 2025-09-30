package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.particle.ParticleEffect
import net.minecraft.registry.tag.TagKey

abstract class PolymorphEffect : StatusEffect {

    constructor(
        effect: StatusEffectCategory,
        color: Int
    ) : super(effect, color)

    constructor(
        effect: StatusEffectCategory,
        color: Int,
        particleEffect: ParticleEffect,
    ) : super(effect, color, particleEffect)

    abstract fun getPolymorphedEntity(): EntityType<*>?


    fun polymorph(target: LivingEntity) {
        val targetWorld = target.world

        val targetPos = target.pos
        val yaw = target.yaw
        val pitch = target.pitch
        val headYaw = target.headYaw

        if (target.isPlayer) target.kill() else target.discard()

        val createdEntityType = getPolymorphedEntity() ?: return

        if (target.type == createdEntityType) return

        val createdEntity = createdEntityType.create(targetWorld) ?: return

        createdEntity.setPosition(targetPos)
        createdEntity.yaw = yaw
        createdEntity.pitch = pitch
        createdEntity.headYaw = headYaw

        targetWorld.spawnEntity(createdEntity)
    }

    override fun applyInstantEffect(
        source: Entity?,
        attacker: Entity?,
        target: LivingEntity?,
        amplifier: Int,
        proximity: Double
    ) {
        super.applyInstantEffect(source, attacker, target, amplifier, proximity)
        if (target == null) return

        polymorph(target)
    }
}