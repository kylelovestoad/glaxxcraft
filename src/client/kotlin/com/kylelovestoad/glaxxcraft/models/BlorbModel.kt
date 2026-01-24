package com.kylelovestoad.glaxxcraft.models

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.block.BlockState
import net.minecraft.client.render.command.OrderedRenderCommandQueue
import net.minecraft.client.render.item.model.special.SpecialModelRenderer
import net.minecraft.client.render.item.model.special.TridentModelRenderer
import net.minecraft.client.texture.Sprite
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemDisplayContext
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import org.joml.Vector3f

class BlorbModel() : SpecialModelRenderer<BlockState> {
    override fun render(
        data: BlockState?,
        displayContext: ItemDisplayContext,
        matrices: MatrixStack,
        queue: OrderedRenderCommandQueue,
        light: Int,
        overlay: Int,
        glint: Boolean,
        tintIndex: Int
    ) {
        
    }

    override fun collectVertices(vertices: Set<Vector3f>) {}

    override fun getData(stack: ItemStack?): BlockState? {
        return stack?.get(GlaxxDataComponents.BLOCK_STATE)
    }
}