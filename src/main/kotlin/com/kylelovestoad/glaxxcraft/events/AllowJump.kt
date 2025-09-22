package com.kylelovestoad.glaxxcraft.events
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.LivingEntity
import net.minecraft.util.ActionResult

fun interface AllowJump {
    /**
     * Called when a living entity jumps.
     * @param entity The entity that is jumping.
     */
    fun allowJump(entity: LivingEntity): ActionResult

    companion object {
        @JvmField
        val EVENT: Event<AllowJump> = EventFactory.createArrayBacked<AllowJump>(
            AllowJump::class.java
        ) { listeners: Array<AllowJump> ->
            AllowJump { entity: LivingEntity ->
                for (listener in listeners) {
                    var result = listener.allowJump(entity)

                    if (result != ActionResult.PASS) {
                        return@AllowJump result
                    }
                }

                return@AllowJump ActionResult.PASS
            }
        }
    }
}