package com.kylelovestoad.glaxxcraft.blocks.lockedchest

import com.kylelovestoad.glaxxcraft.GlaxxBlockEntities
import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.HolderLookup
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
import net.minecraft.resources.Identifier
import net.minecraft.core.UUIDUtil
import net.minecraft.core.BlockPos
import java.rmi.registry.Registry
import java.util.*
import kotlin.jvm.optionals.getOrNull


class LockedChestBlockEntity(pos: BlockPos, state: BlockState)
    : ChestBlockEntity(GlaxxBlockEntities.LOCKED_CHEST, pos, state) {

    var owner: UUID? = null

    var original: Block? = null

    public override fun saveAdditional(view: ValueOutput) {
        super.saveAdditional(view)
        view.storeNullable("owner", UUIDUtil.AUTHLIB_CODEC, owner)
        view.storeNullable("original", BuiltInRegistries.BLOCK.byNameCodec(), original)
    }

    public override fun loadAdditional(view: ValueInput) {
        super.loadAdditional(view)
        owner = view.read("owner", UUIDUtil.AUTHLIB_CODEC).getOrNull()
        original = view.read("original", BuiltInRegistries.BLOCK.byNameCodec()).getOrNull()
    }
}