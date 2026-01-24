package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.network.protocol.status.ServerStatus
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import java.util.UUID
import java.util.function.Supplier
import kotlin.jvm.optionals.getOrNull

class LockedChestBlock(
    blockEntityTypeSupplier: Supplier<BlockEntityType<out ChestBlockEntity>>,
    settings: Properties,
) : ChestBlock(
    blockEntityTypeSupplier,
    SoundEvents.CHEST_OPEN,
    SoundEvents.CHEST_CLOSE,
    settings
) {


    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return LockedChestBlockEntity(pos, state)
    }

    fun isOwner(owner: UUID, player: Player): Boolean {
        return player.uuid == owner
    }

    override fun getDestroyProgress(
        state: BlockState,
        player: Player,
        world: BlockGetter,
        pos: BlockPos
    ): Float {

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return 0.0f
        val owner = blockEntity.owner ?: return 0.0f

        if (!isOwner(owner, player)) {
            return 0.0f
        }

        val multiplier = if (player.hasCorrectToolForDrops(state)) 30 else 100
        return player.getDestroySpeed(state) / defaultDestroyTime() / multiplier.toFloat()
    }

    override fun attack(state: BlockState, world: Level, pos: BlockPos, player: Player) {

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return
        val owner = blockEntity.owner ?: return

        if (!isOwner(owner, player)) {
            if (!world.isClientSide) {
                player.displayClientMessage(Component.translatable("container.locked_chest.failbreak"), true)
            }
            return
        }

        super.attack(state, world, pos, player)
    }

    override fun useWithoutItem(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        hit: BlockHitResult
    ): InteractionResult {

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return InteractionResult.FAIL
        val owner = blockEntity.owner ?: return InteractionResult.FAIL

        if (!isOwner(owner, player) and !player.isCreative) {
            if (!world.isClientSide) {
                player.playSound(SoundEvents.CHEST_LOCKED, 1f, 1.5f)
                player.displayClientMessage(Component.translatable("container.locked_chest.locked"), true)
            }
            return InteractionResult.FAIL
        }

        return super.useWithoutItem(state, world, pos, player, hit)
    }

    override fun setPlacedBy(
        world: Level,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        itemStack: ItemStack
    ) {
        super.setPlacedBy(world, pos, state, placer, itemStack)

        val blockEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return
        val owner = itemStack.get(GlaxxDataComponents.OWNER) ?: return
        blockEntity.owner = owner

        blockEntity.setChanged()
    }
}