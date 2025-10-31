package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.datagen.GlaxxBlockTagProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object GlaxxCraftDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()

        pack.addProvider( ::GlaxxBlockTagProvider )
	}
}