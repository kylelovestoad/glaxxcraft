package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.recipes.BlorbWithBlockRecipe
import net.fabricmc.api.ModInitializer
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object GlaxxRecipeSerializers : ModInitializer {

    val BLOCK_WITH_BLORB_SERIALIZER = Registry.register(
        Registries.RECIPE_SERIALIZER,
        Identifier.of(MOD_ID, "blorb_with_block"),
        BlorbWithBlockRecipe.Serializer
    )

    override fun onInitialize() {}
}