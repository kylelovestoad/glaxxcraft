package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.items.PortalConsumedBlock
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.datafixer.DataFixTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtOps
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.MinecraftServer
import net.minecraft.world.PersistentState
import net.minecraft.world.PersistentStateType


data class GlaxxSaveState(
    var portalConsumedBlocks:  MutableList<PortalConsumedBlock>
) : PersistentState() {


    constructor() : this(mutableListOf())

    companion object {
        val CODEC: Codec<GlaxxSaveState> = RecordCodecBuilder.create { instance ->
            instance.group(
                PortalConsumedBlock.CODEC.listOf().fieldOf("portalConsumedBlocks").forGetter(GlaxxSaveState::portalConsumedBlocks)
            ).apply<GlaxxSaveState>(instance, ::GlaxxSaveState)
        }
        val TYPE = PersistentStateType<GlaxxSaveState>("glaxxcraft", ::GlaxxSaveState, CODEC, DataFixTypes.LEVEL)

        fun loadSave(server: MinecraftServer): GlaxxSaveState  {
            val stateManager = server.overworld.persistentStateManager
            val state = stateManager.getOrCreate(TYPE);
            return state
        }
    }
}