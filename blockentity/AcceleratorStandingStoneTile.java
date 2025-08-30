/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.blockentity;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class AcceleratorStandingStoneTile
extends BEBase {
    private static final int POTION_SECONDS = 25;
    private static final int RADIUS = 10;
    private int ticker = 0;

    public AcceleratorStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public AcceleratorStandingStoneTile(BlockPos pos, BlockState state) {
        this((BlockEntityType)RootsRegistry.ACCELERATOR_STANDING_STONE_TILE.get(), pos, state);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AcceleratorStandingStoneTile tile) {
        ++tile.ticker;
        if (tile.ticker % 20 == 0) {
            List nearbyCreatures = level.getEntitiesOfClass(LivingEntity.class, new AABB((double)(pos.getX() - 10), (double)(pos.getY() - 10), (double)(pos.getZ() - 10), (double)(pos.getX() + 10), (double)(pos.getY() + 10), (double)(pos.getZ() + 10)));
            if (nearbyCreatures.size() > 0) {
                for (LivingEntity nearbyCreature : nearbyCreatures) {
                    nearbyCreature.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500, 1));
                }
            }
            tile.ticker = 0;
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AcceleratorStandingStoneTile tile) {
        ++tile.ticker;
        if (tile.ticker % 5 == 0 && level.isClientSide) {
            for (double i = 0.0; i < 720.0; i += 45.0) {
                double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
                double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
                level.addParticle(MagicAuraParticleData.createData(32.0, 255.0, 255.0), (double)pos.getX() + 0.5 + xShift, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5 + zShift, 0.0, 0.0, 0.0);
            }
        }
    }
}

