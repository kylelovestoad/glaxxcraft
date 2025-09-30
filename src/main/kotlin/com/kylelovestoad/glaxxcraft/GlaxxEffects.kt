package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.effects.ChaoticPolymorphEffect
import com.kylelovestoad.glaxxcraft.effects.GroundedEffect
import com.kylelovestoad.glaxxcraft.effects.PigPolymorphEffect
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

    val POLYMORPH: RegistryEntry<StatusEffect> = Registry.registerReference(
        Registries.STATUS_EFFECT,
        Identifier.of(MOD_ID, "polymorph"),
        PigPolymorphEffect()
    )

    val CHAOTIC_POLYMORPH: RegistryEntry<StatusEffect> = Registry.registerReference(
        Registries.STATUS_EFFECT,
        Identifier.of(MOD_ID, "chaotic_polymorph"),
        ChaoticPolymorphEffect()
    )


    override fun onInitialize() {
    }
}