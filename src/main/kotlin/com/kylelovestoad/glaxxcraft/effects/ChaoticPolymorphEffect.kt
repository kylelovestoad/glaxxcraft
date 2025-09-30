package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.command.argument.EntityArgumentType.entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.entity.mob.MobEntity
import net.minecraft.registry.Registries
import net.minecraft.world.World
import kotlin.random.Random

class ChaoticPolymorphEffect : PolymorphEffect(
    StatusEffectCategory.NEUTRAL,
    0xf38ba8
) {

    override fun isInstant(): Boolean {
        return true
    }

    override fun getPolymorphedEntity(): EntityType<*>? {
        val mobEntityTypes = Registries.ENTITY_TYPE.toList()

        return mobEntityTypes.random()
    }
}