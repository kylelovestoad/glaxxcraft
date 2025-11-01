package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxBlockEntities
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryWrapper
import net.minecraft.storage.ReadView
import net.minecraft.storage.WriteView
import net.minecraft.util.Identifier
import net.minecraft.util.Uuids
import net.minecraft.util.math.BlockPos
import java.rmi.registry.Registry
import java.util.*
import kotlin.jvm.optionals.getOrNull


class LockedChestBlockEntity(pos: BlockPos, state: BlockState)
    : ChestBlockEntity(GlaxxBlockEntities.LOCKED_CHEST, pos, state) {

    var owner: UUID? = null

    var original: Block? = null

    public override fun writeData(view: WriteView) {
        super.writeData(view)
        view.putNullable("owner", Uuids.CODEC, owner)
        view.putNullable("original", Registries.BLOCK.codec, original)
    }

    public override fun readData(view: ReadView) {
        super.readData(view)
        owner = view.read("owner", Uuids.CODEC).getOrNull()
        original = view.read("original", Registries.BLOCK.codec).getOrNull()
    }
}