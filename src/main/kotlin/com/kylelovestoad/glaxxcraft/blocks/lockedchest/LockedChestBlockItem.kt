package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxBlocks
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack

class LockedChestBlockItem(settings: Properties) : BlockItem(GlaxxBlocks.LOCKED_CHEST, settings) {
    override fun onCraftedBy(stack: ItemStack, player: Player) {
        super.onCraftedBy(stack, player)

        stack.set(GlaxxDataComponents.OWNER, player.uuid)
    }
}