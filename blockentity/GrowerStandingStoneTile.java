/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package elucent.rootsclassic.blockentity;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.item.GrowthPowderItem;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GrowerStandingStoneTile
extends BEBase {
    private static final double PCT_CHANCE_PER_BLOCK = 0.03;
    private static final int TICK_SPEED = 100;
    private static final int MAX_TICKER = 10000;
    private static final int RADIUS = 4;
    private int ticker = 0;

    public GrowerStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public GrowerStandingStoneTile(BlockPos pos, BlockState state) {
        this((BlockEntityType)RootsRegistry.GROWER_STANDING_STONE_TILE.get(), pos, state);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GrowerStandingStoneTile tile) {
        tile.updateTicker();
        if (tile.ticker % 100 == 0) {
            tile.applyGrowthToArea();
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, GrowerStandingStoneTile tile) {
        tile.updateTicker();
        tile.spawnParticles();
    }

    private void updateTicker() {
        ++this.ticker;
        if (this.ticker >= 10000) {
            this.ticker = 0;
        }
    }

    private void applyGrowthToArea() {
        ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
        for (int xp = -4; xp <= 4; ++xp) {
            for (int zp = -4; zp <= 4; ++zp) {
                positions.add(this.getBlockPos().offset(xp, -1, zp));
                positions.add(this.getBlockPos().offset(xp, -2, zp));
            }
        }
        Collections.shuffle(positions);
        for (BlockPos pos : positions) {
            if (!(this.level.random.nextDouble() < 0.03)) continue;
            GrowthPowderItem.applyGrowthHere(this.level, pos);
        }
    }

    private void spawnParticles() {
        if (this.ticker % 5 == 0 && this.level.isClientSide) {
            for (double i = 0.0; i < 720.0; i += 45.0) {
                double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
                double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
                this.level.addParticle(MagicAuraParticleData.createData(32.0, 255.0, 32.0), (double)this.worldPosition.getX() + 0.5 + xShift, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5 + zShift, 0.0, 0.0, 0.0);
            }
        }
    }
}

