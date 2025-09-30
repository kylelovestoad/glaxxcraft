package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.entity.EntityType
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.PigEntity
import net.minecraft.registry.tag.TagKey
import net.minecraft.world.World

class PigPolymorphEffect() : PolymorphEffect (
    StatusEffectCategory.NEUTRAL,
    0xf5c2e7
) {

    override fun isInstant(): Boolean {
        return true
    }

    override fun getPolymorphedEntity(): EntityType<*> {
        return EntityType.PIG
    }
}