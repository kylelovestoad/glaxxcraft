package com.kylelovestoad.glaxxcraft

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.renderer.entity.EntityRenderers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.world.entity.Entity


object GlaxxCraftClient : ClientModInitializer {

	override fun onInitializeClient() {
        EntityRenderers.register(GlaxxEntities.THROWN_BLORB) { context ->
            ThrownItemRenderer(context)
        }
	}
}