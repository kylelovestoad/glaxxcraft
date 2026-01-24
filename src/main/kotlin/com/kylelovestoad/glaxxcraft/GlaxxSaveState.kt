package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.items.PortalConsumedBlock
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.datafix.DataFixTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.core.HolderLookup
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.saveddata.SavedData
import net.minecraft.world.level.saveddata.SavedDataType


data class GlaxxSaveState(
    var portalConsumedBlocks:  MutableList<PortalConsumedBlock>
) : SavedData() {


    constructor() : this(mutableListOf())

    companion object {
        val CODEC: Codec<GlaxxSaveState> = RecordCodecBuilder.create { instance ->
            instance.group(
                PortalConsumedBlock.CODEC.listOf().fieldOf("portalConsumedBlocks").forGetter(GlaxxSaveState::portalConsumedBlocks)
            ).apply<GlaxxSaveState>(instance, ::GlaxxSaveState)
        }
        val TYPE = SavedDataType<GlaxxSaveState>("glaxxcraft", ::GlaxxSaveState, CODEC, DataFixTypes.LEVEL)

        fun loadSave(server: MinecraftServer): GlaxxSaveState  {
            val stateManager = server.overworld().dataStorage
            val state = stateManager.computeIfAbsent(TYPE);
            return state
        }
    }
}