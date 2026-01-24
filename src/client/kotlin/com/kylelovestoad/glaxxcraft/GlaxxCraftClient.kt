package com.kylelovestoad.glaxxcraft

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.EntityRendererFactories
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.FlyingItemEntityRenderer
import net.minecraft.entity.Entity


object GlaxxCraftClient : ClientModInitializer {

	override fun onInitializeClient() {
        EntityRendererFactories.register(GlaxxEntities.THROWN_BLORB) { context ->
            FlyingItemEntityRenderer(context)
        }
	}
}