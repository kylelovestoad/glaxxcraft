package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.events.AllowJump
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder.BuildCallback
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.Items
import net.minecraft.potion.Potion
import net.minecraft.potion.Potions
import net.minecraft.recipe.BrewingRecipeRegistry
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier

object GlaxxPotions : ModInitializer {

    val GROUNDED_POTION: Potion = Registry.register(
        Registries.POTION,
        Identifier.of(MOD_ID, "grounded"),
        Potion(
            "grounded",
            StatusEffectInstance(
                GlaxxEffects.GROUNDED,
                500,
                0
            )
        )
    )

    val POLYMORPH_POTION: Potion = Registry.register(
        Registries.POTION,
        Identifier.of(MOD_ID, "polymorph"),
        Potion(
            "polymorph",
            StatusEffectInstance(
                GlaxxEffects.POLYMORPH,
                1,
                0
            )
        )
    )

    override fun onInitialize() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(BuildCallback { builder: BrewingRecipeRegistry.Builder ->
            builder.registerPotionRecipe(
                Potions.AWKWARD, // Input potion.
                Items.DIRT,  // Ingredient
                Registries.POTION.getEntry(GROUNDED_POTION) // Output potion.
            )
        })

        AllowJump.EVENT.register { entity ->

            if (entity.hasStatusEffect(GlaxxEffects.GROUNDED)) {
                return@register ActionResult.FAIL
            }

            return@register ActionResult.PASS
        }
    }
}