package com.kylelovestoad.glaxxcraft

import com.mojang.serialization.Codec
import net.fabricmc.api.ModInitializer
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.component.ComponentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.text.TextCodecs
import net.minecraft.util.Identifier
import net.minecraft.util.Uuids
import java.util.UUID
import java.util.function.UnaryOperator

object GlaxxDataComponents : ModInitializer {

    // Dash Item
    val DASHES: ComponentType<Int> = register("dashes")  { builder -> builder.codec(Codec.INT) }
    val DASHING: ComponentType<Boolean> = register("dashing")  { builder -> builder.codec(Codec.BOOL) }
    val DASH_TICKS_LEFT: ComponentType<Int> = register("dash_ticks_left")  { builder -> builder.codec(Codec.INT) }
    val WAS_IN_FLUID: ComponentType<Boolean> = register("was_in_fluid") { builder -> builder.codec(Codec.BOOL)}

    val OWNER = register("owner") { builder -> builder.codec(Uuids.CODEC) }
    val OWNER_NAME = register("owner_name") { builder -> builder.codec(TextCodecs.CODEC) }
    val ORIGINAL = register("original") { builder -> builder.codec(BlockState.CODEC) }

    fun <T> register(name: String, builderOperator: UnaryOperator<ComponentType.Builder<T>>): ComponentType<T> {
        return Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(name, GlaxxCraft.MOD_ID),
            builderOperator.apply(ComponentType.builder<T>() ).build()
        )
    }

    override fun onInitialize() {

    }
}