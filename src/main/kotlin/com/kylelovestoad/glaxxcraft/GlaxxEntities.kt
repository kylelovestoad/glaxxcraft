package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.entities.ThrownBlorb
import net.fabricmc.api.ModInitializer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier

object GlaxxEntities : ModInitializer{

    val THROWN_BLORB: EntityType<ThrownBlorb> = register("thrown_blorb", { key ->
        EntityType.Builder.of(::ThrownBlorb, MobCategory.MISC)
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(key)
    })

    private fun <T : Entity> register(name: String, factory: (key: ResourceKey<EntityType<*>>) -> EntityType<T>): EntityType<T> {

        val identifier = Identifier.fromNamespaceAndPath(MOD_ID, name)
        val entityKey =
            ResourceKey.create(Registries.ENTITY_TYPE, identifier)

        return Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            identifier,
            factory(entityKey)
        )
    }

    override fun onInitialize() {}

}