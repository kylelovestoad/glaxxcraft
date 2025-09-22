package com.kylelovestoad.glaxxcraft.items

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.block.BlockState
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.registry.RegistryKey
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


data class PortalConsumedBlock (
    var blockPos: BlockPos,
    var blockState: BlockState,
    var worldRegistryKey: RegistryKey<World>,
    var ticksLeft: Int,
) {


    companion object {
        val CODEC: Codec<PortalConsumedBlock> = RecordCodecBuilder.create { instance ->
            instance.group(
                BlockPos.CODEC.fieldOf("blockPos").forGetter(PortalConsumedBlock::blockPos),
                BlockState.CODEC.fieldOf("blockState").forGetter(PortalConsumedBlock::blockState),
                World.CODEC.fieldOf("worldRegistryKey").forGetter(PortalConsumedBlock::worldRegistryKey),
                Codec.INT.fieldOf("ticksLeft").forGetter(PortalConsumedBlock::ticksLeft),
            ).apply<PortalConsumedBlock>(instance, ::PortalConsumedBlock)
        }

        val PACKET_CODEC: PacketCodec<ByteBuf, PortalConsumedBlock> = PacketCodecs.codec(CODEC)

    }
}