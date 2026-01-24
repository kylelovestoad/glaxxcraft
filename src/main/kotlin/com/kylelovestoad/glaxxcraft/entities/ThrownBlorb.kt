package com.kylelovestoad.glaxxcraft.entities

import com.kylelovestoad.glaxxcraft.GlaxxEntities
import com.kylelovestoad.glaxxcraft.GlaxxItems
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityReference
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.ItemStack
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionHand
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.LevelEvent


class ThrownBlorb : ThrowableItemProjectile {

    var blockState: BlockState? = null

    constructor(entityType: EntityType<out ThrowableItemProjectile>, world: Level) :
            super(entityType, world)

    constructor(world: Level, owner: LivingEntity, itemStack: ItemStack) :
            super(GlaxxEntities.THROWN_BLORB, owner, world, itemStack)

    constructor(world: Level, x: Double, y: Double, z: Double, itemStack: ItemStack) :
        super(GlaxxEntities.THROWN_BLORB, x, y, z, world, itemStack)

    override fun getDefaultItem(): Item {
        return GlaxxItems.BLORB
    }

    override fun onHitBlock(blockHitResult: BlockHitResult) {
        super.onHitBlock(blockHitResult)

        val placementPos = blockHitResult.blockPos

        val blockItem = blockState?.block?.asItem() as? BlockItem
        if (blockItem == null) {
            this.discard()

            (level() as? ServerLevel)?.sendParticles(
                ParticleTypes.POOF,
                placementPos.x.toDouble(),
                placementPos.y.toDouble(),
                placementPos.z.toDouble(),
                20,
                0.5,
                0.5,
                0.5,
                0.1
            )

            return
        }

        val player = EntityReference.getEntity(owner, level()) as? Player
        if (player == null) {
            this.discard()
            return
        }

        val context = BlockPlaceContext(
            level(),
            player,
            InteractionHand.MAIN_HAND,
            blockItem.defaultInstance,
            blockHitResult
        )

        val result = blockItem.place(context)
        if (result == InteractionResult.FAIL) {
            level().levelEvent(
                LevelEvent.PARTICLES_DESTROY_BLOCK,
                context.clickedPos,
                Block.getId(blockState)
            )
            Block.popResource(level(), context.clickedPos, blockItem.defaultInstance)
        }

        this.discard()
    }
}