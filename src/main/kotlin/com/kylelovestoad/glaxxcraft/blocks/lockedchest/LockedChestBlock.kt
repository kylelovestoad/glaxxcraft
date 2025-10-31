package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ChestBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.ServerMetadata
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.function.Supplier

class LockedChestBlock(
    settings: Settings,
    blockEntityTypeSupplier: Supplier<BlockEntityType<out ChestBlockEntity>>
) : ChestBlock(settings, blockEntityTypeSupplier) {


    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return LockedChestBlockEntity(pos, state)
    }

    fun isOwner(blockEntity: LockedChestBlockEntity, player: PlayerEntity): Boolean {
        val owner = blockEntity.owner ?: return false
        return player.uuid == owner
    }

    override fun calcBlockBreakingDelta(
        state: BlockState,
        player: PlayerEntity,
        world: BlockView,
        pos: BlockPos
    ): Float {

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return 0.0f

        if (!isOwner(blockEntity, player)) {
            return 0.0f
        }

        val multiplier = if (player.canHarvest(state)) 30 else 100
        return player.getBlockBreakingSpeed(state) / hardness / multiplier.toFloat()
    }

    override fun onBlockBreakStart(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity) {

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return

        if (!isOwner(blockEntity, player)) {
            if (!world.isClient) {
                player.sendMessage(Text.translatable("container.locked_chest.failbreak", blockEntity.owner), true)
            }
            return
        }

        super.onBlockBreakStart(state, world, pos, player)
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hit: BlockHitResult
    ): ActionResult {

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return ActionResult.FAIL

        if (!isOwner(blockEntity, player) and !player.isCreative) {
            if (!world.isClient) {
                println("TEST")
                player.sendMessage(Text.translatable("container.locked_chest.locked", blockEntity.owner), true)
            }
            return ActionResult.FAIL
        }


        return super.onUse(state, world, pos, player, hit)
    }

    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        itemStack: ItemStack
    ) {
        super.onPlaced(world, pos, state, placer, itemStack)

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return
        val owner = itemStack.get(GlaxxDataComponents.OWNER) ?: return
        blockEntity.owner = owner

        blockEntity.markDirty()
    }
}