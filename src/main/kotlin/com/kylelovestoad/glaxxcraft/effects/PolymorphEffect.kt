package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.tags.TagKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level

abstract class PolymorphEffect : MobEffect {

    constructor(
        effect: MobEffectCategory,
        color: Int
    ) : super(effect, color)

    constructor(
        effect: MobEffectCategory,
        color: Int,
        particleEffect: ParticleOptions,
    ) : super(effect, color, particleEffect)

    abstract fun getPolymorphedEntity(): EntityType<*>?


    fun polymorph(world: ServerLevel, target: LivingEntity) {

        val targetPos = target.position()
        val yaw = target.yRot
        val pitch = target.xRot
        val headYaw = target.yHeadRot

        val createdEntityType = getPolymorphedEntity() ?: return
        if (target.type == createdEntityType) return
        if (target.isAlwaysTicking) return

        target.discard()

        val createdEntity = createdEntityType.create(
            world,
            EntitySpawnReason.CONVERSION,
        ) ?: return

        createdEntity.setPos(targetPos)
        createdEntity.setYRot(yaw)
        createdEntity.setXRot(pitch)
        createdEntity.yHeadRot = headYaw

        world.addFreshEntity(createdEntity)
    }

    override fun applyInstantenousEffect(
        world: ServerLevel,
        source: Entity?,
        attacker: Entity?,
        target: LivingEntity,
        amplifier: Int,
        proximity: Double
    ) {
        super.applyInstantenousEffect(world, source, attacker, target, amplifier, proximity)

        polymorph(world, target)
    }
}