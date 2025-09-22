package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.effects.GroundedEffect
import net.fabricmc.api.ModInitializer
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.Identifier

object GlaxxEffects : ModInitializer {

    val GROUNDED: RegistryEntry<StatusEffect> = Registry.registerReference(
        Registries.STATUS_EFFECT,
        Identifier.of(MOD_ID, "grounded"),
        GroundedEffect()
    )

    override fun onInitialize() {
    }
}