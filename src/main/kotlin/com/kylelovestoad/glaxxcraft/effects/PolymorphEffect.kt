package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.particle.ParticleEffect
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World

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


    fun polymorph(world: ServerWorld, target: LivingEntity) {

        val targetPos = target.entityPos
        val yaw = target.yaw
        val pitch = target.pitch
        val headYaw = target.headYaw

        val createdEntityType = getPolymorphedEntity() ?: return
        if (target.type == createdEntityType) return
        if (target.isPlayer) return

        target.discard()

        val createdEntity = createdEntityType.create(
            world,
            SpawnReason.CONVERSION,
        ) ?: return

        createdEntity.setPosition(targetPos)
        createdEntity.yaw = yaw
        createdEntity.pitch = pitch
        createdEntity.headYaw = headYaw

        world.spawnEntity(createdEntity)
    }

    override fun applyInstantEffect(
        world: ServerWorld,
        source: Entity?,
        attacker: Entity?,
        target: LivingEntity,
        amplifier: Int,
        proximity: Double
    ) {
        super.applyInstantEffect(world, source, attacker, target, amplifier, proximity)

        polymorph(world, target)
    }
}