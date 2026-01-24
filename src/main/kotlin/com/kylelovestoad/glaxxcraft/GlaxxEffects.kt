package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.effects.ChaoticPolymorphEffect
import com.kylelovestoad.glaxxcraft.effects.GroundedEffect
import com.kylelovestoad.glaxxcraft.effects.MantleEffect
import com.kylelovestoad.glaxxcraft.effects.PigPolymorphEffect
import com.kylelovestoad.glaxxcraft.effects.VulnerableEffect
import com.kylelovestoad.glaxxcraft.events.AllowJump
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder.BuildCallback
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.alchemy.PotionBrewing
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.core.Holder
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResult
import net.minecraft.resources.Identifier

object GlaxxEffects : ModInitializer {

    val GROUNDED: Holder<MobEffect> = registerEffect("grounded", GroundedEffect())
    val GROUNDED_POTION: Potion = registerPotion(
        "grounded",
        GROUNDED,
        500, 0
    )

    val POLYMORPH: Holder<MobEffect> = registerEffect("polymorph", PigPolymorphEffect())
    val POLYMORPH_POTION: Potion = registerPotion("polymorph", POLYMORPH, 1, 0)

    val CHAOTIC_POLYMORPH: Holder<MobEffect> = registerEffect("chaotic_polymorph", ChaoticPolymorphEffect())
    val CHAOTIC_POLYMORPH_POTION: Potion = registerPotion("chaotic_polymorph",CHAOTIC_POLYMORPH, 1, 0)

    val VULNERABLE: Holder<MobEffect> = registerEffect("vulnerable", VulnerableEffect())
    val VULNERABLE_POTION: Potion = registerPotion("vulnerable",VULNERABLE, 3600, 0)
    val VULNERABLE_POTION_II: Potion = registerPotion("vulnerable_ii",VULNERABLE, 1800, 1)

    val MANTLE: Holder<MobEffect> = registerEffect("mantle", MantleEffect())

    fun registerEffect(name: String, effect: MobEffect): Holder<MobEffect> {
        return Registry.registerForHolder(
            BuiltInRegistries.MOB_EFFECT,
            Identifier.fromNamespaceAndPath(MOD_ID, name),
            effect
        )
    }

    fun registerPotion(name: String, effect: Holder<MobEffect>, duration: Int, amplifier: Int): Potion {
        return Registry.register(
            BuiltInRegistries.POTION,
            Identifier.fromNamespaceAndPath(MOD_ID, name),
            Potion(
                name,
                MobEffectInstance(effect, duration, amplifier)
            )
        )
    }


    override fun onInitialize() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(BuildCallback { builder: PotionBrewing.Builder ->
            builder.addMix(
                Potions.AWKWARD,
                Items.DIRT,
                BuiltInRegistries.POTION.wrapAsHolder(GROUNDED_POTION)
            )
        })

        AllowJump.EVENT.register { entity ->

            if (entity.hasEffect(GROUNDED)) {
                return@register InteractionResult.FAIL
            }

            return@register InteractionResult.PASS
        }

        ServerLivingEntityEvents.ALLOW_DAMAGE.register{ entity, _, _ ->

            if (entity.hasEffect(MANTLE)) {
                entity.removeEffect(MANTLE)

                if (entity is Player) {
                    entity.playSound(SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(),1f, 2f)
                }
                return@register false
            }

            return@register true
        }
    }
}