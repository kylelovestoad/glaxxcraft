package com.kylelovestoad.glaxxcraft.models

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.special.SpecialModelRenderer
import net.minecraft.client.renderer.special.TridentSpecialRenderer
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.resources.Identifier
import org.joml.Vector3f
import org.joml.Vector3fc
import java.util.function.Consumer

class BlorbModel() : SpecialModelRenderer<BlockState> {
    override fun submit(
        data: BlockState?,
        displayContext: ItemDisplayContext,
        matrices: PoseStack,
        queue: SubmitNodeCollector,
        light: Int,
        overlay: Int,
        glint: Boolean,
        tintIndex: Int
    ) {
        
    }

    override fun getExtents(consumer: Consumer<Vector3fc>) {}

    override fun extractArgument(itemStack: ItemStack): BlockState? {
        return itemStack.get(GlaxxDataComponents.BLOCK_STATE)
    }
}