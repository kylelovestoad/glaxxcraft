package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.GlaxxBlocks
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlockEntity
import net.fabricmc.fabric.api.serialization.v1.view.FabricReadView
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.storage.TagValueInput
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.InteractionResult
import net.minecraft.util.ProblemReporter
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import java.util.UUID

class KeyItem(settings: Properties) : Item(settings)

{

    override fun useOn(context: UseOnContext): InteractionResult {
        val blockPos = context.clickedPos
        val world = context.level
        val blockState = world.getBlockState(blockPos)
        val player = context.player ?: return InteractionResult.FAIL

        // Other chests are incompatible for now
        if (!blockState.`is`(Blocks.CHEST) and !blockState.`is`(Blocks.TRAPPED_CHEST)) {
            return InteractionResult.FAIL
        }

        val oldChestEntity = world.getBlockEntity(blockPos) as? ChestBlockEntity ?: return InteractionResult.FAIL

        val nbt = oldChestEntity.saveWithoutMetadata(world.registryAccess())
        val readView = TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), nbt)

        oldChestEntity.clearContent()

        val oldState = world.getBlockState(blockPos)
        val facing = oldState.getValue(ChestBlock.FACING)
        val chestType = oldState.getValue(ChestBlock.TYPE)
        val waterlogged = oldState.getValue(ChestBlock.WATERLOGGED)

        world.setBlockAndUpdate(blockPos, GlaxxBlocks.LOCKED_CHEST.defaultBlockState()
            .setValue(ChestBlock.FACING, facing)
            .setValue(ChestBlock.TYPE, chestType)
            .setValue(ChestBlock.WATERLOGGED, waterlogged)
        )

        val newChestEntity = world.getBlockEntity(blockPos) as? LockedChestBlockEntity ?: return InteractionResult.FAIL

        newChestEntity.loadAdditional(readView)
        newChestEntity.owner = player.uuid
        newChestEntity.setChanged()

        return InteractionResult.SUCCESS


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