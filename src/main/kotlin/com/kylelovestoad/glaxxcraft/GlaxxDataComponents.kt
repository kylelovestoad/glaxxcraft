package com.kylelovestoad.glaxxcraft

import com.mojang.serialization.Codec
import net.fabricmc.api.ModInitializer
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.component.DataComponentType
import net.minecraft.world.item.BlockItem
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.resources.Identifier
import net.minecraft.core.UUIDUtil
import java.util.function.UnaryOperator

object GlaxxDataComponents : ModInitializer {

    // Dash Item
    val DASHES: DataComponentType<Int> = register("dashes")  { builder -> builder.persistent(Codec.INT) }
    val DASHING: DataComponentType<Boolean> = register("dashing")  { builder -> builder.persistent(Codec.BOOL) }
    val DASH_TICKS_LEFT: DataComponentType<Int> = register("dash_ticks_left")  { builder -> builder.persistent(Codec.INT) }
    val WAS_IN_FLUID: DataComponentType<Boolean> = register("was_in_fluid") { builder -> builder.persistent(Codec.BOOL)}

    val OWNER = register("owner") { builder -> builder.persistent(UUIDUtil.AUTHLIB_CODEC) }
    val OWNER_NAME = register("owner_name") { builder -> builder.persistent(ComponentSerialization.CODEC) }
    val ORIGINAL = register("original") { builder -> builder.persistent(BlockState.CODEC) }

    val BLOCK_STATE = register("block_state") { builder -> builder.persistent(BlockState.CODEC) }

    fun <T : Any> register(name: String, builderOperator: UnaryOperator<DataComponentType.Builder<T>>): DataComponentType<T> {
        return Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(name, GlaxxCraft.MOD_ID),
            builderOperator.apply(DataComponentType.builder() ).build()
        )
    }

    override fun onInitialize() {

    }
}