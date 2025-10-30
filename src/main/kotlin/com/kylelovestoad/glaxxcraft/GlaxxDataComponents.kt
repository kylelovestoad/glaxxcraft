package com.kylelovestoad.glaxxcraft

import com.mojang.serialization.Codec
import net.fabricmc.api.ModInitializer
import net.minecraft.block.BlockState
import net.minecraft.component.ComponentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
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

    const val KEY_ID_COMPONENT_NAME = "key_id"
    val KEY_ID: ComponentType<UUID> = register(KEY_ID_COMPONENT_NAME) { builder -> builder.codec(Uuids.CODEC) }

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