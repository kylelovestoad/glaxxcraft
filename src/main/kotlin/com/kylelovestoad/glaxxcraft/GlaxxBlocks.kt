package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlock
import net.fabricmc.api.ModInitializer
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object GlaxxBlocks : ModInitializer {
    val LOCKED_CHEST: LockedChestBlock = register(
        "locked_chest",
        { settings ->
            LockedChestBlock(
                { GlaxxBlockEntities.LOCKED_CHEST },
                settings
            )
        }
    )

    private fun <T : Block> register(name: String, factory: (AbstractBlock.Settings) -> T): T {
        val blockKey: RegistryKey<Block> = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, name))
        val settings = AbstractBlock.Settings.create().registryKey(blockKey)
        val block = factory(settings)
        return Registry.register(Registries.BLOCK, blockKey, block)
    }

    override fun onInitialize() {}
}