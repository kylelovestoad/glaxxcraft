package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import com.kylelovestoad.glaxxcraft.GlaxxItems
import com.kylelovestoad.glaxxcraft.entities.ThrownBlorb
import net.minecraft.block.ChestBlock
import net.minecraft.component.ComponentsAccess
import net.minecraft.component.type.TooltipDisplayComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipAppender
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World
import java.util.function.Consumer


class Blorb(settings: Settings): Item(settings), TooltipAppender {

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult {
        val stack = player.getStackInHand(hand)

        world.playSound(
            null, player.x, player.y, player.z,
            SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL,
            0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        )

        if (!world.isClient) {
            val blockState = stack.get(GlaxxDataComponents.BLOCK_STATE)

            val itemToShow = blockState?.block?.asItem() ?: GlaxxItems.BLORB
            val blorbEntity = ThrownBlorb(world, player, itemToShow.defaultStack)

            blorbEntity.blockState = blockState

            blorbEntity.setVelocity(player, player.pitch, player.yaw, 0.0f, 1.5f, 0.0f)

            world.spawnEntity(blorbEntity)
        }


        player.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!player.abilities.creativeMode) {
            stack.decrement(1)
        }

        return ActionResult.SUCCESS
    }

    override fun appendTooltip(
        context: TooltipContext,
        textConsumer: Consumer<Text>,
        type: TooltipType,
        components: ComponentsAccess
    ) {
        val block = components.get(GlaxxDataComponents.BLOCK_STATE)?.block ?: return
        textConsumer.accept(Text.translatable("item.glaxxcraft.blorb.tooltip", block.name))
    }
}