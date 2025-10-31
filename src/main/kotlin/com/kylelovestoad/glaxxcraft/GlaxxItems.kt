package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlock
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlockItem
import com.kylelovestoad.glaxxcraft.items.DashItem
import com.kylelovestoad.glaxxcraft.items.KeyItem
import com.kylelovestoad.glaxxcraft.items.PortablePortal
import net.fabricmc.api.ModInitializer
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object GlaxxItems : ModInitializer {

    val DASH = register("dash", DashItem())
    val PORTABLE_PORTAL = register("portable_portal", PortablePortal())
    val KEY = register("key", KeyItem())

    val LOCKED_CHEST = register("locked_chest", LockedChestBlockItem())

    fun register(name: String, item: Item): Item {
        val itemKey: RegistryKey<Item> =
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name))

        val registeredItem = Registry.register(Registries.ITEM, itemKey, item)

        return registeredItem
    }

    override fun onInitialize() {}
}