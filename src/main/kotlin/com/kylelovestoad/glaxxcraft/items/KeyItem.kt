package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.GlaxxBlocks
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlockEntity
import net.fabricmc.fabric.api.serialization.v1.view.FabricReadView
import net.minecraft.block.Blocks
import net.minecraft.block.ChestBlock
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.storage.NbtReadView
import net.minecraft.storage.ReadView
import net.minecraft.util.ActionResult
import net.minecraft.util.ErrorReporter
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.UUID

class KeyItem(settings: Settings) : Item(settings)

{

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val blockPos = context.blockPos
        val world = context.world
        val blockState = world.getBlockState(blockPos)
        val player = context.player ?: return ActionResult.FAIL

        // Other chests are incompatible for now
        if (!blockState.isOf(Blocks.CHEST) and !blockState.isOf(Blocks.TRAPPED_CHEST)) {
            return ActionResult.FAIL
        }

        val oldChestEntity = world.getBlockEntity(blockPos) as? ChestBlockEntity ?: return ActionResult.FAIL

        val nbt = oldChestEntity.createNbt(world.registryManager)
        val readView = NbtReadView.create(ErrorReporter.EMPTY, world.registryManager, nbt)

        oldChestEntity.clear()

        val oldState = world.getBlockState(blockPos)
        val facing = oldState.get(ChestBlock.FACING)
        val chestType = oldState.get(ChestBlock.CHEST_TYPE)
        val waterlogged = oldState.get(ChestBlock.WATERLOGGED)

        world.setBlockState(blockPos, GlaxxBlocks.LOCKED_CHEST.defaultState
            .with(ChestBlock.FACING, facing)
            .with(ChestBlock.CHEST_TYPE, chestType)
            .with(ChestBlock.WATERLOGGED, waterlogged)
        )

        val newChestEntity = world.getBlockEntity(blockPos) as? LockedChestBlockEntity ?: return ActionResult.FAIL

        newChestEntity.readData(readView)
        newChestEntity.owner = player.uuid
        newChestEntity.markDirty()

        return ActionResult.SUCCESS


//        val chestType = blockState.get(ChestBlock.CHEST_TYPE)

//        if (chestType == ChestType.SINGLE) {
//            return ActionResult.SUCCESS
//        }

//        val facing = blockState.get(ChestBlock.FACING)
//        val offset = when (chestType) {
//            ChestType.LEFT -> facing.rotateYClockwise()
//            ChestType.RIGHT -> facing.rotateYCounterclockwise()
//            else -> return ActionResult.PASS
//        }

//        val otherChestPos = context.blockPos.offset(offset)
    }
}