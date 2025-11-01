package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.block.AbstractBlock
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
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.UUID
import java.util.function.Supplier
import kotlin.jvm.optionals.getOrNull

class LockedChestBlock(
    blockEntityTypeSupplier: Supplier<BlockEntityType<out ChestBlockEntity>>,
    settings: Settings,
) : ChestBlock(
    blockEntityTypeSupplier,
    SoundEvents.BLOCK_CHEST_OPEN,
    SoundEvents.BLOCK_CHEST_CLOSE,
    settings
) {


    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return LockedChestBlockEntity(pos, state)
    }

    fun isOwner(owner: UUID, player: PlayerEntity): Boolean {
        return player.uuid == owner
    }

    override fun calcBlockBreakingDelta(
        state: BlockState,
        player: PlayerEntity,
        world: BlockView,
        pos: BlockPos
    ): Float {

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return 0.0f
        val owner = blockEntity.owner ?: return 0.0f

        if (!isOwner(owner, player)) {
            return 0.0f
        }

        val multiplier = if (player.canHarvest(state)) 30 else 100
        return player.getBlockBreakingSpeed(state) / hardness / multiplier.toFloat()
    }

    override fun onBlockBreakStart(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity) {

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return
        val owner = blockEntity.owner ?: return

        if (!isOwner(owner, player)) {
            if (!world.isClient) {
                player.sendMessage(Text.translatable("container.locked_chest.failbreak"), true)
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
        val owner = blockEntity.owner ?: return ActionResult.FAIL

        if (!isOwner(owner, player) and !player.isCreative) {
            if (!world.isClient) {
                player.playSoundToPlayer(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1f, 1.5f)
                player.sendMessage(Text.translatable("container.locked_chest.locked"), true)
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