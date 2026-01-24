package com.kylelovestoad.glaxxcraft.effects

import net.minecraft.world.entity.EntityType
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.animal.pig.Pig
import net.minecraft.tags.TagKey
import net.minecraft.world.level.Level

class PigPolymorphEffect() : PolymorphEffect (
    MobEffectCategory.NEUTRAL,
    0xf5c2e7
) {

    override fun isInstantenous(): Boolean {
        return true
    }

    override fun getPolymorphedEntity(): EntityType<*> {
        return EntityType.PIG
    }
}