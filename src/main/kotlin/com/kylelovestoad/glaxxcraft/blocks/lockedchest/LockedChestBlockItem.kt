package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxBlocks
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack

class LockedChestBlockItem(settings: Settings) : BlockItem(GlaxxBlocks.LOCKED_CHEST, settings) {
    override fun onCraftByPlayer(stack: ItemStack, player: PlayerEntity) {
        super.onCraftByPlayer(stack, player)

        stack.set(GlaxxDataComponents.OWNER, player.uuid)
    }
}