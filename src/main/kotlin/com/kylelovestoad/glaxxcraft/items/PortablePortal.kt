package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.GlaxxSaveState
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.Rarity

class PortablePortal(settings: Settings) : Item(settings) {
    val portalWidth = 3
    val portalHeight = 3
    val portalDepth = 20
    val portalTicks = 140
    val cooldownTicks = 60

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        super.useOnBlock(context)

        val player = context.player ?: return ActionResult.FAIL

        val playerFacing = player.facing
        val playerHorizontalFacing = player.horizontalFacing

        val left = playerHorizontalFacing.rotateYCounterclockwise().vector
        val up = playerFacing.vector.crossProduct(left)

        // start position is up/left half the height/width since it is centered
        val startPos = context.blockPos
            .add(up.multiply(portalHeight / 2))
            .add(left.multiply(portalWidth / 2))

        val server = context.world.server ?: return ActionResult.FAIL
        val save = GlaxxSaveState.loadSave(server)

        for (i in 0..<portalWidth) {
            for (j in 0..<portalHeight) {
                for (k in 0..<portalDepth) {
                    val blockPos = startPos
                        .add(left.multiply(-i)) // going right from leftmost point
                        .add(up.multiply(-j)) // going down from upmost point
                        .add(playerFacing.vector.multiply(k))

                    val blockState = context.world.getBlockState(blockPos)
                    // Air cannot be consumed because it is already nothing, causes issues with intersecting portals
                    // Bedrock and other unbreakable blocks should not be consumed
                    if (blockState.isAir || blockState.block.hardness == -1f) continue

                    save.portalConsumedBlocks.add(PortalConsumedBlock(
                        blockPos,
                        context.world.getBlockState(blockPos),
                        context.world.registryKey,
                        portalTicks,
                    ))

                    save.markDirty()

                    context.world.setBlockState(blockPos, Blocks.AIR.defaultState)
                }
            }
        }

        player.itemCooldownManager.set(context.stack, cooldownTicks)

        return ActionResult.SUCCESS
    }
}
