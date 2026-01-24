package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import com.kylelovestoad.glaxxcraft.GlaxxItems
import com.kylelovestoad.glaxxcraft.entities.ThrownBlorb
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.core.component.DataComponentGetter
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.TooltipProvider
import net.minecraft.world.item.TooltipFlag
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvents
import net.minecraft.stats.Stats
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionHand
import net.minecraft.world.level.Level
import java.util.function.Consumer


class Blorb(settings: Properties): Item(settings), TooltipProvider {

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResult {
        val stack = player.getItemInHand(hand)

        world.playSound(
            null, player.x, player.y, player.z,
            SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL,
            0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        )

        if (!world.isClientSide) {
            val blockState = stack.get(GlaxxDataComponents.BLOCK_STATE)

            val itemToShow = blockState?.block?.asItem() ?: GlaxxItems.BLORB
            val blorbEntity = ThrownBlorb(world, player, itemToShow.defaultInstance)

            blorbEntity.blockState = blockState

            blorbEntity.shootFromRotation(player, player.xRot, player.yRot, 0.0f, 1.5f, 0.0f)

            world.addFreshEntity(blorbEntity)
        }


        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.abilities.instabuild) {
            stack.shrink(1)
        }

        return InteractionResult.SUCCESS
    }

    override fun addToTooltip(
        context: TooltipContext,
        textConsumer: Consumer<Component>,
        type: TooltipFlag,
        components: DataComponentGetter
    ) {
        val block = components.get(GlaxxDataComponents.BLOCK_STATE)?.block ?: return
        textConsumer.accept(Component.translatable("item.glaxxcraft.blorb.tooltip", block.name))
    }
}