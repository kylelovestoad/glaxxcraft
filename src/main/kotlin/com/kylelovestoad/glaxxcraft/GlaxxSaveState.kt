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
        val TYPE: Type<GlaxxSaveState> = Type(::GlaxxSaveState, GlaxxSaveState::readFromNbt, DataFixTypes.LEVEL)

        fun readFromNbt(save: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup): GlaxxSaveState? {
            val result = CODEC.decode(NbtOps.INSTANCE, save.get("glaxx_data")).result()
            if (result.isPresent) { // If we successfully loaded the PersistentState
                return result.get().getFirst();
            }

            return null;
        }

        fun loadSave(server: MinecraftServer): GlaxxSaveState  {
            val stateManager = server.overworld.persistentStateManager
            val state = stateManager.getOrCreate(TYPE, "glaxx");
            return state
        }
    }

    override fun writeNbt(
        nbt: NbtCompound,
        registryLookup: RegistryWrapper.WrapperLookup
    ): NbtCompound {
        nbt.put("glaxx_data", CODEC.encode(this, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow())
        return nbt
    }
}