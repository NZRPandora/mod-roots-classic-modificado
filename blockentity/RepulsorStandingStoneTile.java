/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RepulsorStandingStoneTile
extends BEBase {
    private static final int RADIUS = 5;
    private static final int VRADIUS = 1;
    private int ticker = 0;

    public RepulsorStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public RepulsorStandingStoneTile(BlockPos pos, BlockState state) {
        this((BlockEntityType)RootsRegistry.REPULSOR_STANDING_STONE_TILE.get(), pos, state);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RepulsorStandingStoneTile tile) {
        ++tile.ticker;
        List nearbyItems = level.getEntitiesOfClass(ItemEntity.class, new AABB((double)(pos.getX() - 5), (double)(pos.getY() - 1), (double)(pos.getZ() - 5), (double)(pos.getX() + 5), (double)(pos.getY() + 1), (double)(pos.getZ() + 5)));
        if (nearbyItems.size() > 0) {
            for (ItemEntity ei : nearbyItems) {
                if (!(Math.max(Math.abs(ei.getX() - ((double)pos.getX() + 0.5)), Math.abs(ei.getZ() - ((double)pos.getZ() + 0.5))) > 1.0)) continue;
                Vec3 v = new Vec3(ei.getX() - ((double)pos.getX() + 0.5), 0.0, ei.getZ() - ((double)pos.getZ() + 0.5));
                v.normalize();
                ei.setDeltaMovement(v.x * 0.05, ei.getDeltaMovement().y, v.z * 0.05);
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, RepulsorStandingStoneTile tile) {
        ++tile.ticker;
        if (tile.ticker % 5 == 0 && level.isClientSide) {
            for (double i = 0.0; i < 720.0; i += 45.0) {
                double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
                double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
                level.addParticle(MagicAuraParticleData.createData(255.0, 255.0, 32.0), (double)pos.getX() + 0.5 + xShift, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5 + zShift, 0.0, 0.0, 0.0);
            }
        }
    }
}

