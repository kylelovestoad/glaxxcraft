package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlock
import net.fabricmc.api.ModInitializer
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier

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

    private fun <T : Block> register(name: String, factory: (BlockBehaviour.Properties) -> T): T {
        val blockKey: ResourceKey<Block> = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, name))
        val settings = BlockBehaviour.Properties.of().setId(blockKey)
        val block = factory(settings)
        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block)
    }

    override fun onInitialize() {}
}