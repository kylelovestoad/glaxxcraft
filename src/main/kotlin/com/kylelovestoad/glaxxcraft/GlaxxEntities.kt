package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.entities.ThrownBlorb
import net.fabricmc.api.ModInitializer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object GlaxxEntities : ModInitializer{

    val THROWN_BLORB: EntityType<ThrownBlorb> = register("thrown_blorb", { key ->
        EntityType.Builder.create(::ThrownBlorb, SpawnGroup.MISC)
            .dimensions(0.5f, 0.5f)
            .maxTrackingRange(4)
            .trackingTickInterval(10)
            .build(key)
    })

    private fun <T : Entity> register(name: String, factory: (key: RegistryKey<EntityType<*>>) -> EntityType<T>): EntityType<T> {

        val identifier = Identifier.of(MOD_ID, name)
        val entityKey =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier)

        return Registry.register(
            Registries.ENTITY_TYPE,
            identifier,
            factory(entityKey)
        )
    }

    override fun onInitialize() {}

}