/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.entity;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsEntities;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityAccelerator
extends Entity {
    private Entity entity;
    private final Random random = new Random();
    private int lifetime = 0;
    private int potency = 1;

    public EntityAccelerator(EntityType<? extends EntityAccelerator> type, Level levelAccessor) {
        super(type, levelAccessor);
    }

    public EntityAccelerator(Level levelAccessor, Entity entity, int potency, int size) {
        this((EntityType<? extends EntityAccelerator>)((EntityType)RootsEntities.ENTITY_ACCELERATOR.get()), levelAccessor);
        this.entity = entity;
        this.potency = potency + 2;
        this.lifetime = 200 + 200 * size;
        this.setPosRaw(entity.getX(), entity.getY(), entity.getZ());
    }

    public void tick() {
        int i;
        super.tick();
        if (this.entity == null) {
            this.level().broadcastEntityEvent((Entity)this, (byte)3);
            this.discard();
        } else {
            this.setPosRaw((this.entity.getBoundingBox().maxX + this.entity.getBoundingBox().minX) / 2.0 - 0.5, (this.entity.getBoundingBox().maxY + this.entity.getBoundingBox().minY) / 2.0 - 0.5, (this.entity.getBoundingBox().maxZ + this.entity.getBoundingBox().minZ) / 2.0 - 0.5);
            for (i = 0; i < this.potency; ++i) {
                this.entity.tick();
                this.entity.baseTick();
            }
        }
        if (this.level().isClientSide) {
            for (i = 0; i < 2; ++i) {
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
        this.lifetime = compound.getInt("lifetime");
        this.potency = compound.getInt("potency");
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("lifetime", this.lifetime);
        compound.putInt("potency", this.potency);
    }
}

