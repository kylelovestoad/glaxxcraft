package com.kylelovestoad.glaxxcraft.items

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.resources.ResourceKey
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level


data class PortalConsumedBlock (
    var blockPos: BlockPos,
    var blockState: BlockState,
    var worldRegistryKey: ResourceKey<Level>,
    var ticksLeft: Int,
) {


    companion object {
        val CODEC: Codec<PortalConsumedBlock> = RecordCodecBuilder.create { instance ->
            instance.group(
                BlockPos.CODEC.fieldOf("blockPos").forGetter(PortalConsumedBlock::blockPos),
                BlockState.CODEC.fieldOf("blockState").forGetter(PortalConsumedBlock::blockState),
                Level.RESOURCE_KEY_CODEC.fieldOf("worldRegistryKey").forGetter(PortalConsumedBlock::worldRegistryKey),
                Codec.INT.fieldOf("ticksLeft").forGetter(PortalConsumedBlock::ticksLeft),
            ).apply<PortalConsumedBlock>(instance, ::PortalConsumedBlock)
        }

        val PACKET_CODEC: StreamCodec<ByteBuf, PortalConsumedBlock> = ByteBufCodecs.fromCodec(CODEC)

    }
}