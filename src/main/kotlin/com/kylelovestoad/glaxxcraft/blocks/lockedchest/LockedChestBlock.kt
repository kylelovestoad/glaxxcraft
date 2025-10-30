package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import com.kylelovestoad.glaxxcraft.items.KeyItem
import net.minecraft.block.BlockState
import net.minecraft.block.ChestBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.function.Supplier

class LockedChestBlock(
    settings: Settings,
    blockEntityTypeSupplier: Supplier<BlockEntityType<out ChestBlockEntity>>
) : ChestBlock(settings, blockEntityTypeSupplier) {



    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return LockedChestBlockEntity(pos, state)
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hit: BlockHitResult
    ): ActionResult {
        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return ActionResult.PASS
        val keyId = blockEntity.keyId ?: return ActionResult.PASS


        val hasMatchingKey = player.inventory.main.any { stack ->
            stack.item is KeyItem && stack.get(GlaxxDataComponents.KEY_ID) == keyId
        }


        if (!hasMatchingKey) {
            if (!world.isClient) {
                player.sendMessage(Text.translatable("container.locked_chest.locked"), true)
            }
            return ActionResult.FAIL
        }

        return super.onUse(state, world, pos, player, hit)
    }
}