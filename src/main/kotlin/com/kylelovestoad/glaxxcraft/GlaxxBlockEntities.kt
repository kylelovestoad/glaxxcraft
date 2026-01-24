package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlockEntity
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.resources.Identifier

object GlaxxBlockEntities : ModInitializer {
    val LOCKED_CHEST: BlockEntityType<LockedChestBlockEntity> = Registry.register(
        BuiltInRegistries.BLOCK_ENTITY_TYPE,
        Identifier.fromNamespaceAndPath(GlaxxCraft.MOD_ID, "locked_chest"),
        FabricBlockEntityTypeBuilder.create(
            ::LockedChestBlockEntity,
            GlaxxBlocks.LOCKED_CHEST
        ).build()
    )

    override fun onInitialize() {}
}