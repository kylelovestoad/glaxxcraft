package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxBlockEntities
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import java.util.UUID

class LockedChestBlockEntity(pos: BlockPos, state: BlockState)
    : ChestBlockEntity(GlaxxBlockEntities.LOCKED_CHEST, pos, state) {

    var keyId: UUID? = null

    var original: Block? = null

    public override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.writeNbt(nbt, registryLookup)
        keyId?.let {
            nbt.putUuid(GlaxxDataComponents.KEY_ID_COMPONENT_NAME, it)
        }

        original?.let {
            val blockId = Registries.BLOCK.getId(it)
            nbt.putString("Original", blockId.toString())
        }
    }

    public override fun readNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.readNbt(nbt, registryLookup)
        if (nbt.containsUuid(GlaxxDataComponents.KEY_ID_COMPONENT_NAME)) {
            keyId = nbt.getUuid(GlaxxDataComponents.KEY_ID_COMPONENT_NAME)
        }

        if (nbt.contains("OriginalBlock")) {
            val blockId = Identifier.of(nbt.getString("OriginalBlock"))
            original = Registries.BLOCK.get(blockId)
        }
    }
}