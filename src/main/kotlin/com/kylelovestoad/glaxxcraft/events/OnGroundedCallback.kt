package com.kylelovestoad.glaxxcraft.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.passive.SheepEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult


fun interface OnGroundedCallback {
    fun interact(player: PlayerEntity): ActionResult

    companion object {
        val EVENT: Event<OnGroundedCallback> = EventFactory.createArrayBacked(OnGroundedCallback::class.java)
        {  listeners ->
            // This is the invokerFactory lambda. It returns an instance of SheepShearCallback.
            OnGroundedCallback { player ->
                // Loop through every registered listener
                for (listener in listeners) {
                    val result = listener.interact(player)

                    // If a listener returns a result other than PASS,
                    // we stop and return that result immediately.
                    if (result != ActionResult.PASS) {
                        return@OnGroundedCallback result
                    }
                }

                // If all listeners passed, return PASS.
                ActionResult.PASS
            }
        }
    }
}
