/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package elucent.rootsclassic.blockentity;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VacuumStandingStoneTile
extends BEBase {
    private int ticker = 0;

    public VacuumStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public VacuumStandingStoneTile(BlockPos pos, BlockState state) {
        this((BlockEntityType)RootsRegistry.VACUUM_STANDING_STONE_TILE.get(), pos, state);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, VacuumStandingStoneTile tile) {
        ++tile.ticker;
        double range = 6.0;
        List nearbyItems = level.getEntitiesOfClass(ItemEntity.class, new AABB((double)pos.getX() - range, (double)pos.getY() - range, (double)pos.getZ() - range, (double)pos.getX() + range, (double)pos.getY() + range, (double)pos.getZ() + range));
        if (nearbyItems.size() > 0) {
            for (ItemEntity nearbyItem : nearbyItems) {
                if (nearbyItem.getItem().isEmpty() || !nearbyItem.isAlive()) continue;
                float strength = 0.05f;
                Vec3 entityVector = new Vec3(nearbyItem.getX(), nearbyItem.getY() - nearbyItem.getPassengerRidingPosition((Entity)nearbyItem).y() + (double)(nearbyItem.getBbHeight() / 2.0f), nearbyItem.getZ());
                Vec3 finalVector = new Vec3((double)pos.getX(), (double)pos.getY() + 0.5, (double)pos.getZ()).subtract(entityVector);
                if (Math.sqrt(finalVector.x * finalVector.x + finalVector.y * finalVector.y + finalVector.z * finalVector.z) > 1.0) {
                    finalVector = finalVector.normalize();
                }
                nearbyItem.setDeltaMovement(finalVector.multiply((double)strength, nearbyItem.getDeltaMovement().y, (double)strength));
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, VacuumStandingStoneTile tile) {
        ++tile.ticker;
        if (tile.ticker % 5 == 0 && level.isClientSide) {
            for (double i = 0.0; i < 720.0; i += 45.0) {
                double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
                double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
                level.addParticle(MagicAuraParticleData.createData(255.0, 32.0, 160.0), (double)pos.getX() + 0.5 + xShift, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5 + zShift, 0.0, 0.0, 0.0);
            }
        }
    }
}

