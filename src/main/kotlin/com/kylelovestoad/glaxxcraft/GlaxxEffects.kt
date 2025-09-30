package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.effects.ChaoticPolymorphEffect
import com.kylelovestoad.glaxxcraft.effects.GroundedEffect
import com.kylelovestoad.glaxxcraft.effects.PigPolymorphEffect
import com.kylelovestoad.glaxxcraft.effects.VulnerableEffect
import com.kylelovestoad.glaxxcraft.events.AllowJump
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder.BuildCallback
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.Items
import net.minecraft.potion.Potion
import net.minecraft.potion.Potions
import net.minecraft.recipe.BrewingRecipeRegistry
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier

object GlaxxEffects : ModInitializer {

    val GROUNDED: RegistryEntry<StatusEffect> = registerEffect("grounded", GroundedEffect())
    val GROUNDED_POTION: Potion = registerPotion(
        "grounded",
        GROUNDED,
        500, 0
    )

    val POLYMORPH: RegistryEntry<StatusEffect> = registerEffect("polymorph", PigPolymorphEffect())
    val POLYMORPH_POTION: Potion = registerPotion("polymorph", POLYMORPH, 1, 0)

    val CHAOTIC_POLYMORPH: RegistryEntry<StatusEffect> = registerEffect("chaotic_polymorph", ChaoticPolymorphEffect())
    val CHAOTIC_POLYMORPH_POTION: Potion = registerPotion("chaotic_polymorph",CHAOTIC_POLYMORPH, 1, 0)

    val VULNERABLE: RegistryEntry<StatusEffect> = registerEffect("vulnerable", VulnerableEffect())
    val VULNERABLE_POTION: Potion = registerPotion("vulnerable",VULNERABLE, 3600, 0)
    val VULNERABLE_POTION_II: Potion = registerPotion("vulnerable_ii",VULNERABLE, 1800, 1)




    fun registerEffect(name: String, effect: StatusEffect): RegistryEntry<StatusEffect> {
        return Registry.registerReference(
            Registries.STATUS_EFFECT,
            Identifier.of(MOD_ID, name),
            effect
        )
    }

    fun registerPotion(name: String, effect: RegistryEntry<StatusEffect>, duration: Int, amplifier: Int): Potion {
        return Registry.register(
            Registries.POTION,
            Identifier.of(MOD_ID, name),
            Potion(
                name,
                StatusEffectInstance(effect, duration, amplifier)
            )
        )
    }


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