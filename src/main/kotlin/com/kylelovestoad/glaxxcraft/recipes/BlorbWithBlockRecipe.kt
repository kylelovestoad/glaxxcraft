package com.kylelovestoad.glaxxcraft.recipes

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import com.kylelovestoad.glaxxcraft.GlaxxItems
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.core.HolderLookup
import net.minecraft.world.level.Level


class BlorbWithBlockRecipe(category: CraftingBookCategory) : CustomRecipe(category) {

    fun createBlorbForItem(block: Block): ItemStack {
        val itemStack = ItemStack(GlaxxItems.BLORB)
        itemStack.set(GlaxxDataComponents.BLOCK_STATE, block.defaultBlockState())
        return itemStack
    }

    fun getOutput(input: CraftingInput): ItemStack {
        var blorb: ItemStack? = null
        var block: Block? = null
        for (stack in input.items()) {

            val item = stack.item
            if (item == GlaxxItems.BLORB) {

                if (blorb != null) {
                    return ItemStack.EMPTY
                }

                if (stack.get(GlaxxDataComponents.BLOCK_STATE) == null)
                    blorb = stack

            } else if (item is BlockItem) {

                if (block != null) {
                    return ItemStack.EMPTY
                }

                if (item.block != Blocks.AIR) {
                    block = item.block
                }
            } else if (!stack.isEmpty) {
                return ItemStack.EMPTY
            }
        }

        if (blorb == null || block == null) {
            return ItemStack.EMPTY
        }

        return createBlorbForItem(block)
    }

    override fun getSerializer(): RecipeSerializer<out CustomRecipe> = Serializer

    override fun matches(
        input: CraftingInput,
        world: Level
    ): Boolean = !getOutput(input).isEmpty


    override fun assemble(
        input: CraftingInput,
        registries: HolderLookup.Provider
    ): ItemStack {
        return getOutput(input)
    }

    companion object {
        val Serializer = Serializer(::BlorbWithBlockRecipe)
    }

}