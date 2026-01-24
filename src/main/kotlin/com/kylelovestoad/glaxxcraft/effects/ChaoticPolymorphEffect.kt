package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.commands.arguments.EntityArgument.entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.Mob
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.Level
import kotlin.random.Random

class ChaoticPolymorphEffect : PolymorphEffect(
    MobEffectCategory.NEUTRAL,
    0xf38ba8
) {

    override fun isInstantenous(): Boolean {
        return true
    }

    override fun getPolymorphedEntity(): EntityType<*>? {
        val mobEntityTypes = BuiltInRegistries.ENTITY_TYPE.toList()

        return mobEntityTypes.random()
    }
}