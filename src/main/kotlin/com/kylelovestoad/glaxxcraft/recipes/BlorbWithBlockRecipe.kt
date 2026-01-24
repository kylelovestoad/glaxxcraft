package com.kylelovestoad.glaxxcraft.recipes

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import com.kylelovestoad.glaxxcraft.GlaxxItems
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.SpecialCraftingRecipe
import net.minecraft.recipe.book.CraftingRecipeCategory
import net.minecraft.recipe.input.CraftingRecipeInput
import net.minecraft.registry.RegistryWrapper
import net.minecraft.world.World


class BlorbWithBlockRecipe(category: CraftingRecipeCategory) : SpecialCraftingRecipe(category) {

    fun createBlorbForItem(block: Block): ItemStack {
        val itemStack = ItemStack(GlaxxItems.BLORB)
        itemStack.set(GlaxxDataComponents.BLOCK_STATE, block.defaultState)
        return itemStack
    }

    fun getOutput(input: CraftingRecipeInput): ItemStack {
        var blorb: ItemStack? = null
        var block: Block? = null
        for (stack in input.stacks) {

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

    override fun getSerializer(): RecipeSerializer<out SpecialCraftingRecipe> = Serializer

    override fun matches(
        input: CraftingRecipeInput,
        world: World
    ): Boolean = !getOutput(input).isEmpty


    override fun craft(
        input: CraftingRecipeInput,
        registries: RegistryWrapper.WrapperLookup
    ): ItemStack {
        return getOutput(input)
    }

    companion object {
        val Serializer = SpecialRecipeSerializer(::BlorbWithBlockRecipe)
    }

}