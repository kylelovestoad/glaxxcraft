package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.items.PortalConsumedBlock
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate
import net.fabricmc.fabric.api.attachment.v1.AttachmentType
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.util.Identifier


@Suppress("UnstableApiUsage")
object GlaxxAttachmentTypes : ModInitializer {

    val PORTAL_DATA: AttachmentType<List<PortalConsumedBlock>> = AttachmentRegistry.create(
        Identifier.of(GlaxxCraft.MOD_ID,"portal_data")
    ) { builder -> builder
        .initializer { mutableListOf() }
        .persistent(PortalConsumedBlock.CODEC.listOf())
        .syncWith(
            PacketCodecs.codec(PortalConsumedBlock.CODEC.listOf()),
            AttachmentSyncPredicate.all()
        )
    }

    override fun onInitialize() {}
}