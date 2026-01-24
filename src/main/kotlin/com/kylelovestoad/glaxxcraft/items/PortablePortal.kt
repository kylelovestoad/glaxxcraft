package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.GlaxxSaveState
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Rarity

class PortablePortal(settings: Properties) : Item(settings) {
    val portalWidth = 3
    val portalHeight = 3
    val portalDepth = 20
    val portalTicks = 140
    val cooldownTicks = 60

    override fun useOn(context: UseOnContext): InteractionResult {
        super.useOn(context)

        val player = context.player ?: return InteractionResult.FAIL

        val playerFacing = player.nearestViewDirection
        val playerHorizontalFacing = player.direction

        val left = playerHorizontalFacing.counterClockWise.unitVec3i
        val up = playerFacing.unitVec3i.cross(left)

        // start position is up/left half the height/width since it is centered
        val startPos = context.clickedPos
            .offset(up.multiply(portalHeight / 2))
            .offset(left.multiply(portalWidth / 2))

        val server = context.level.server ?: return InteractionResult.FAIL
        val save = GlaxxSaveState.loadSave(server)

        for (i in 0..<portalWidth) {
            for (j in 0..<portalHeight) {
                for (k in 0..<portalDepth) {
                    val blockPos = startPos
                        .offset(left.multiply(-i)) // going right from leftmost point
                        .offset(up.multiply(-j)) // going down from upmost point
                        .offset(playerFacing.unitVec3i.multiply(k))

                    val blockState = context.level.getBlockState(blockPos)
                    // Air cannot be consumed because it is already nothing, causes issues with intersecting portals
                    // Bedrock and other unbreakable blocks should not be consumed
                    if (blockState.isAir || blockState.block.defaultDestroyTime() == -1f) continue

                    save.portalConsumedBlocks.add(PortalConsumedBlock(
                        blockPos,
                        context.level.getBlockState(blockPos),
                        context.level.dimension(),
                        portalTicks,
                    ))

                    save.setDirty()

                    context.level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_SKIP_ALL_SIDEEFFECTS)
                }
            }
        }

        player.cooldowns.addCooldown(context.itemInHand, cooldownTicks)

        return InteractionResult.SUCCESS
    }
}
