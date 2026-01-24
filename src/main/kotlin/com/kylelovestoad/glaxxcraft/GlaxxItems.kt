package com.kylelovestoad.glaxxcraft

import com.kylelovestoad.glaxxcraft.GlaxxCraft.MOD_ID
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlock
import com.kylelovestoad.glaxxcraft.blocks.lockedchest.LockedChestBlockItem
import com.kylelovestoad.glaxxcraft.items.Blorb
import com.kylelovestoad.glaxxcraft.items.DashItem
import com.kylelovestoad.glaxxcraft.items.KeyItem
import com.kylelovestoad.glaxxcraft.items.PortablePortal
import net.fabricmc.api.ModInitializer
import net.minecraft.world.level.block.Block
import net.minecraft.world.item.Item
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Rarity
import java.util.function.Supplier

object GlaxxItems : ModInitializer {

    val DASH: Item = register("dash") { settings -> DashItem(settings) }
    val PORTABLE_PORTAL: Item = register("portable_portal") { settings -> PortablePortal(settings) }
    val KEY: Item = register("key") { settings -> KeyItem(settings) }
    val LOCKED_CHEST: Item = register("locked_chest") { settings -> LockedChestBlockItem(settings) }
    val BLORB: Item = register("blorb") { settings -> Blorb(settings) }

    private fun register(name: String, factory: (Item.Properties) -> Item): Item {
        val itemKey: ResourceKey<Item> = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, name))
        val settings = Item.Properties().setId(itemKey)
        val item = factory(settings)
        return Registry.register(BuiltInRegistries.ITEM, itemKey, item)
    }

    override fun onInitialize() {}
}