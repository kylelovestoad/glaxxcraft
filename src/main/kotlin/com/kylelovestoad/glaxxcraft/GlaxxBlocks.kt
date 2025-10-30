package com.kylelovestoad.glaxxcraft

import com.jcraft.jorbis.Block
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlock
import net.fabricmc.api.ModInitializer
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object GlaxxBlocks : ModInitializer {
    val LOCKED_CHEST: LockedChestBlock = Registry.register(
        Registries.BLOCK,
        Identifier.of(GlaxxCraft.MOD_ID, "locked_chest"),
        LockedChestBlock(
            AbstractBlock.Settings.copy(Blocks.CHEST),
            { GlaxxBlockEntities.LOCKED_CHEST }
        )
    )

    override fun onInitialize() {}
}