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
import net.minecraft.util.Rarity
import java.util.function.Supplier

object GlaxxItems : ModInitializer {

    val DASH: Item = register("dash") { settings -> DashItem(settings) }
    val PORTABLE_PORTAL: Item = register("portable_portal") { settings -> PortablePortal(settings) }
    val KEY: Item = register("key") { settings -> KeyItem(settings) }
    val LOCKED_CHEST: Item = register("locked_chest") { settings -> LockedChestBlockItem(settings) }

    private fun register(name: String, factory: (Item.Settings) -> Item): Item {
        val itemKey: RegistryKey<Item> = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name))
        val settings = Item.Settings().registryKey(itemKey)
        val item = factory(settings)
        return Registry.register(Registries.ITEM, itemKey, item)
    }

    override fun onInitialize() {}
}