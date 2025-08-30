/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.state.BlockState
 */
package elucent.rootsclassic.entity;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsEntities;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class EntityTileAccelerator
extends Entity {
    private BlockPos bePosition;
    private final Random random = new Random();
    private int lifetime = 0;
    private int potency = 1;

    public EntityTileAccelerator(EntityType<? extends EntityTileAccelerator> type, Level levelAccessor) {
        super(type, levelAccessor);
    }

    public EntityTileAccelerator(Level levelAccessor, BlockPos bePosition, int potency, int size) {
        this((EntityType<? extends EntityTileAccelerator>)((EntityType)RootsEntities.TILE_ACCELERATOR.get()), levelAccessor);
        this.setBEPosition(bePosition);
        this.potency = potency + 2;
        this.lifetime = 200 + 200 * size;
        this.setPosRaw(bePosition.getX(), bePosition.getY(), bePosition.getZ());
    }

    public void setBEPosition(BlockPos pos) {
        this.bePosition = pos;
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.bePosition == null || this.level().getBlockState(this.bePosition).isAir()) {
                if (this.tickCount > 20 && !this.level().isClientSide) {
                    this.level().broadcastEntityEvent((Entity)this, (byte)3);
                    this.discard();
                }
                return;
            }
            BlockEntity blockEntity = this.level().getBlockEntity(this.bePosition);
            if (blockEntity != null) {
                BlockState state = this.level().getBlockState(this.bePosition);
                for (int i = 0; i < this.potency; ++i) {
                    BlockEntityTicker ticker = state.getTicker(this.level(), blockEntity.getType());
                    if (ticker == null) continue;
                    ticker.tick(this.level(), this.bePosition, blockEntity.getBlockState(), blockEntity);
                }
            }
        } else if (this.level().isClientSide) {
            for (int i = 0; i < 2; ++i) {
                int side = this.random.nextInt(6);
                if (side == 0) {
                    this.level().addParticle(MagicAuraParticleData.createData(255.0, 255.0, 255.0), this.getX(), this.getY() + this.random.nextDouble(), this.getZ() + this.random.nextDouble(), 0.0, 0.0, 0.0);
                }
                if (side == 1) {
                    this.level().addParticle(MagicAuraParticleData.createData(255.0, 255.0, 255.0), this.getX() + 1.0, this.getY() + this.random.nextDouble(), this.getZ() + this.random.nextDouble(), 0.0, 0.0, 0.0);
                }
                if (side == 2) {
                    this.level().addParticle(MagicAuraParticleData.createData(255.0, 255.0, 255.0), this.getX() + this.random.nextDouble(), this.getY(), this.getZ() + this.random.nextDouble(), 0.0, 0.0, 0.0);
                }
                if (side == 3) {
                    this.level().addParticle(MagicAuraParticleData.createData(255.0, 255.0, 255.0), this.getX() + this.random.nextDouble(), this.getY() + 1.0, this.getZ() + this.random.nextDouble(), 0.0, 0.0, 0.0);
                }
                if (side == 4) {
                    this.level().addParticle(MagicAuraParticleData.createData(255.0, 255.0, 255.0), this.getX() + this.random.nextDouble(), this.getY() + this.random.nextDouble(), this.getZ(), 0.0, 0.0, 0.0);
                }
                if (side != 5) continue;
                this.level().addParticle(MagicAuraParticleData.createData(255.0, 255.0, 255.0), this.getX() + this.random.nextDouble(), this.getY() + this.random.nextDouble(), this.getZ() + 1.0, 0.0, 0.0, 0.0);
            }
        }
        if (!this.level().isClientSide) {
            --this.lifetime;
            if (this.lifetime <= 0) {
                this.level().broadcastEntityEvent((Entity)this, (byte)3);
                this.discard();
            }
        }
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        this.bePosition = new BlockPos(compound.getInt("posX"), compound.getInt("posY"), compound.getInt("posZ"));
        this.lifetime = compound.getInt("lifetime");
        this.potency = compound.getInt("potency");
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("posX", this.bePosition.getX());
        compound.putInt("posY", this.bePosition.getY());
        compound.putInt("posZ", this.bePosition.getZ());
        compound.putInt("lifetime", this.lifetime);
        compound.putInt("potency", this.potency);
    }
}

