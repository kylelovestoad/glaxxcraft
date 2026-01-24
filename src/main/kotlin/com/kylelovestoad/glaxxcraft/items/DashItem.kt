package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.SharedConstants
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import kotlin.math.round

class DashItem(settings: Properties) : Item(settings) {

    val maxDashes = 2

    private val dashSpeed = 3f
    // The time it takes to exit the dashing state
    private val dashTime = .15f
    // The time it takes for the dash to be usable again
    private val dashCooldown = .2f

    private val dashTicks = round(dashTime * SharedConstants.TICKS_PER_SECOND).toInt()
    private val dashCooldownTicks = round(dashCooldown * SharedConstants.TICKS_PER_SECOND).toInt()

    fun dash(player: Player) {
        player.deltaMovement = player.lookAngle.scale(dashSpeed.toDouble())
    }

    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResult {
        super.use(world, user, hand)

        val stack = user.getItemInHand(hand)
        val dashes = stack.get(GlaxxDataComponents.DASHES) ?: maxDashes

        if (dashes <= 0) {
            return InteractionResult.FAIL
        }

        dash(user)

        user.cooldowns.addCooldown(stack, dashCooldownTicks)
        stack.set(GlaxxDataComponents.DASHES, dashes - 1)
        stack.set(GlaxxDataComponents.DASHING, true)
        stack.set(GlaxxDataComponents.DASH_TICKS_LEFT, dashTicks)

        return InteractionResult.SUCCESS
    }

    override fun inventoryTick(stack: ItemStack, world: ServerLevel, entity: Entity, slot: EquipmentSlot?) {
        if (entity !is Player) {
            return
        }

        val dashing = stack.get(GlaxxDataComponents.DASHING) ?: false
        val dashTicksLeft = stack.get(GlaxxDataComponents.DASH_TICKS_LEFT) ?: dashTicks
        val wasInFluid = stack.get(GlaxxDataComponents.WAS_IN_FLUID) ?: false

        if (dashing) {
            if (dashTicksLeft > 0) {
                stack.set(GlaxxDataComponents.DASH_TICKS_LEFT, dashTicksLeft - 1)
            } else {
                stack.set(GlaxxDataComponents.DASHING, false)
            }
        }


        if (entity.isInLiquid) {
            // Emulating niche Celeste mechanic where leaving water while dashing lets you keep your dash
            stack.set(GlaxxDataComponents.DASHES, maxDashes + 1)
            stack.set(GlaxxDataComponents.WAS_IN_FLUID, true)
            return
        } else if (wasInFluid && !dashing) {
            // Remove the extra dash for the niche mechanic
            stack.set(GlaxxDataComponents.DASHES, maxDashes)
            stack.set(GlaxxDataComponents.WAS_IN_FLUID, false)
        }

        if (entity.onGround()) {
            stack.set(GlaxxDataComponents.DASHES, maxDashes)
        }
    }
}