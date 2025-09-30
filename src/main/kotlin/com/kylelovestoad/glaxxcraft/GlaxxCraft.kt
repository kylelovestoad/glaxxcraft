package com.kylelovestoad.glaxxcraft

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.server.network.ServerPlayerEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object GlaxxCraft : ModInitializer {

	const val MOD_ID: String = "glaxxcraft"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("GlaxxCraft loaded!")

		GlaxxItems.onInitialize()
		GlaxxEffects.onInitialize()
		GlaxxPotions.onInitialize()
		GlaxxDataComponents.onInitialize()
		GlaxxAttachmentTypes.onInitialize()

		ServerLivingEntityEvents.ALLOW_DAMAGE.register { entity, source, _ ->
			// We only care about players receiving fall damage
			if (entity !is ServerPlayerEntity || !source.isOf(DamageTypes.FALL)) {
				return@register true
			}

			// Check if the player is holding the immunity item in their main hand
			val isHoldingItem = entity.mainHandStack.isOf(GlaxxItems.DASH)
			return@register !isHoldingItem
		}

		ServerTickEvents.END_SERVER_TICK.register { server ->

			val save = GlaxxSaveState.loadSave(server)

            val newBlocks = save.portalConsumedBlocks.toMutableList()
            newBlocks.removeIf { consumedBlock ->
                consumedBlock.ticksLeft--

                if (consumedBlock.ticksLeft <= 0) {
                    val world = server.getWorld(consumedBlock.worldRegistryKey) ?: return@removeIf true
                    world.setBlockState(consumedBlock.blockPos, consumedBlock.blockState)
                    return@removeIf true
                }
                return@removeIf false
            }

			save.portalConsumedBlocks = newBlocks
		}
	}
}
