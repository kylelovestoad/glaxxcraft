package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.recipes.BlorbWithBlockRecipe
import net.fabricmc.api.ModInitializer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.resources.Identifier

object GlaxxRecipeSerializers : ModInitializer {

    val BLOCK_WITH_BLORB_SERIALIZER = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        Identifier.fromNamespaceAndPath(MOD_ID, "blorb_with_block"),
        BlorbWithBlockRecipe.Serializer
    )

    override fun onInitialize() {}
}