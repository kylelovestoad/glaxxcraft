package com.kylelovestoad.glaxxcraft.items

import com.kylelovestoad.glaxxcraft.GlaxxDataComponents
import net.minecraft.SharedConstants
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.Rarity
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import kotlin.math.round

class DashItem : Item(Settings()
    .maxCount(1)
    .rarity(Rarity.EPIC)
) {

    val maxDashes = 2

    private val dashSpeed = 3f
    // The time it takes to exit the dashing state
    private val dashTime = .15f
    // The time it takes for the dash to be usable again
    private val dashCooldown = .2f

    private val dashTicks = round(dashTime * SharedConstants.TICKS_PER_SECOND).toInt()
    private val dashCooldownTicks = round(dashCooldown * SharedConstants.TICKS_PER_SECOND).toInt()

    fun dash(player: PlayerEntity) {
        player.velocity = player.rotationVector.multiply(dashSpeed.toDouble())
    }

    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack?>? {
        super.use(world, user, hand)

        if (user == null) {
            return TypedActionResult.fail(ItemStack.EMPTY)
        }

        val stack = user.getStackInHand(hand)
        val dashes = stack.get(GlaxxDataComponents.DASHES) ?: maxDashes

        if (dashes <= 0) {
            return TypedActionResult.fail(stack)
        }

        dash(user)

        user.itemCooldownManager.set(this, dashCooldownTicks)
        stack.set(GlaxxDataComponents.DASHES, dashes - 1)
        stack.set(GlaxxDataComponents.DASHING, true)
        stack.set(GlaxxDataComponents.DASH_TICKS_LEFT, dashTicks)

        return TypedActionResult.success(stack)
    }

    override fun inventoryTick(stack: ItemStack?, world: World?, entity: Entity?, slot: Int, selected: Boolean) {
        if (entity !is PlayerEntity) {
            return
        }

        if (stack == null) {
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


        if (entity.isInFluid) {
            // Emulating niche Celeste mechanic where leaving water while dashing lets you keep your dash
            stack.set(GlaxxDataComponents.DASHES, maxDashes + 1)
            stack.set(GlaxxDataComponents.WAS_IN_FLUID, true)
            return
        } else if (wasInFluid && !dashing) {
            // Remove the extra dash for the niche mechanic
            stack.set(GlaxxDataComponents.DASHES, maxDashes)
            stack.set(GlaxxDataComponents.WAS_IN_FLUID, false)
        }

        if (entity.isOnGround) {
            stack.set(GlaxxDataComponents.DASHES, maxDashes)
        }
    }
}