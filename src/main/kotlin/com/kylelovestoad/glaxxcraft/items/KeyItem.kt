package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlockEntity
import com.kylelovestoad.glaxxcraft.GlaxxBlocks
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.block.Blocks
import net.minecraft.block.ChestBlock
import net.minecraft.block.ChestBlock.CHEST_TYPE
import net.minecraft.block.ChestBlock.FACING
import net.minecraft.block.ChestBlock.WATERLOGGED
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.UUID

class KeyItem : Item(Settings().maxCount(1))

{

    fun convertToLockedChest(world: World, pos: BlockPos, keyId: UUID): Boolean {
        val oldChestEntity = world.getBlockEntity(pos) as? ChestBlockEntity ?: return false

        val nbt = oldChestEntity.createNbt(world.registryManager)
        oldChestEntity.clear()

        val oldState = world.getBlockState(pos)
        val facing = oldState.get(ChestBlock.FACING)
        val chestType = oldState.get(ChestBlock.CHEST_TYPE)

        world.setBlockState(pos, GlaxxBlocks.LOCKED_CHEST.defaultState
            .with(ChestBlock.FACING, facing)
            .with(ChestBlock.CHEST_TYPE, chestType)
            .with(ChestBlock.WATERLOGGED, oldState.get(ChestBlock.WATERLOGGED))
        )

        val newChestEntity = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return false

        newChestEntity.readNbt(nbt, world.registryManager)
        newChestEntity.keyId = keyId
        newChestEntity.markDirty()
        return true

    }

    fun convertToChest(world: World, pos: BlockPos): Boolean {
        val lockedChest = world.getBlockEntity(pos) as? LockedChestBlockEntity ?: return false

        val items = mutableListOf<ItemStack>()
        for (i in 0 until lockedChest.size()) {
            items.add(lockedChest.getStack(i).copy())
        }

        lockedChest.clear()

        val lockedChestState = world.getBlockState(pos)
        val facing = lockedChestState.get(FACING)
        val chestType = lockedChestState.get(CHEST_TYPE)

        val originalBlock = lockedChest.original ?: return false

        world.setBlockState(pos, originalBlock.defaultState
            .with(FACING, facing)
            .with(CHEST_TYPE, chestType)
            .with(WATERLOGGED, lockedChestState.get(WATERLOGGED))
        )

        val newChestEntity = world.getBlockEntity(pos) as? ChestBlockEntity ?: return false

        newChestEntity.markDirty()
        return true

    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val blockPos = context.blockPos
        val world = context.world
        val blockState = world.getBlockState(blockPos)
        // Other chests are incompatible for now
        if (blockState.isOf(Blocks.CHEST) or blockState.isOf(Blocks.TRAPPED_CHEST)) {

            var keyId = context.stack.get(GlaxxDataComponents.KEY_ID)
            if (keyId == null) {
                keyId = UUID.randomUUID()
                context.stack.set(GlaxxDataComponents.KEY_ID, keyId)
            }

            return if (convertToLockedChest(world, blockPos, keyId)) ActionResult.SUCCESS else ActionResult.FAIL
        }

        if (!blockState.isOf(GlaxxBlocks.LOCKED_CHEST)) {
            return ActionResult.FAIL
        }

        return if (convertToChest(world, blockPos)) ActionResult.SUCCESS else ActionResult.FAIL

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