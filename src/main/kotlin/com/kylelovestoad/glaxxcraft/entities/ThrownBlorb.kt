package com.kylelovestoad.glaxxcraft.entities

import com.kylelovestoad.glaxxcraft.GlaxxEntities
import com.kylelovestoad.glaxxcraft.GlaxxItems
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LazyEntityReference
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.world.World
import net.minecraft.world.WorldEvents


class ThrownBlorb : ThrownItemEntity {

    var blockState: BlockState? = null

    constructor(entityType: EntityType<out ThrownItemEntity>, world: World) :
            super(entityType, world)

    constructor(world: World, owner: LivingEntity, itemStack: ItemStack) :
            super(GlaxxEntities.THROWN_BLORB, owner, world, itemStack)

    constructor(world: World, x: Double, y: Double, z: Double, itemStack: ItemStack) :
        super(GlaxxEntities.THROWN_BLORB, x, y, z, world, itemStack)

    override fun getDefaultItem(): Item {
        return GlaxxItems.BLORB
    }

    override fun onBlockHit(blockHitResult: BlockHitResult) {
        super.onBlockHit(blockHitResult)

        val placementPos = blockHitResult.blockPos

        val blockItem = blockState?.block?.asItem() as? BlockItem
        if (blockItem == null) {
            this.discard()

            (entityWorld as? ServerWorld)?.spawnParticles(
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

        val player = LazyEntityReference.getEntity(owner, entityWorld) as? PlayerEntity
        if (player == null) {
            this.discard()
            return
        }

        val context = ItemPlacementContext(
            entityWorld,
            player,
            Hand.MAIN_HAND,
            blockItem.defaultStack,
            blockHitResult
        )

        val result = blockItem.place(context)
        if (result == ActionResult.FAIL) {
            entityWorld.syncWorldEvent(
                WorldEvents.BLOCK_BROKEN,
                context.blockPos,
                Block.getRawIdFromState(blockState)
            )
            Block.dropStack(entityWorld, context.blockPos, blockItem.defaultStack)
        }

        this.discard()
    }
}