package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlockEntity
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object GlaxxBlockEntities : ModInitializer {
    val LOCKED_CHEST: BlockEntityType<LockedChestBlockEntity> = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier.of(GlaxxCraft.MOD_ID, "locked_chest"),
        FabricBlockEntityTypeBuilder.create(
            ::LockedChestBlockEntity,
            GlaxxBlocks.LOCKED_CHEST
        ).build()
    )

    override fun onInitialize() {}
}