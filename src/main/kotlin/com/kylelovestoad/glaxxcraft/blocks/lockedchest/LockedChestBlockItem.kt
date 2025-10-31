package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxBlocks
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.world.World

class LockedChestBlockItem : BlockItem(GlaxxBlocks.LOCKED_CHEST, Settings()) {
    override fun onCraftByPlayer(stack: ItemStack, world: World, player: PlayerEntity) {
        super.onCraftByPlayer(stack, world, player)

        stack.set(GlaxxDataComponents.OWNER_NAME, player.name)
        stack.set(GlaxxDataComponents.OWNER, player.uuid)
    }


    override fun appendTooltip(stack: ItemStack, context: TooltipContext, tooltip: MutableList<Text>, type: TooltipType) {
        super.appendTooltip(stack, context, tooltip, type)

        val ownerName = stack.get(GlaxxDataComponents.OWNER_NAME) ?: return
        tooltip.add(Text.translatable("item.glaxxcraft.locked_chest.owner", ownerName))
    }
}