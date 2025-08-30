/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.DoubleBlockHalf
 *  net.minecraft.world.level.block.state.properties.Property
 */
package elucent.rootsclassic.block;

import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.blockentity.AcceleratorStandingStoneTile;
import elucent.rootsclassic.registry.RootsRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;

public class AcceleratorStandingStoneBlock
extends AttunedStandingStoneBlock
implements EntityBlock {
    public AcceleratorStandingStoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue((Property)HALF) == DoubleBlockHalf.UPPER) {
            return new AcceleratorStandingStoneTile(pos, state);
        }
        return null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return AcceleratorStandingStoneBlock.createStandingStoneTicker(level, entityType, (BlockEntityType<? extends AcceleratorStandingStoneTile>)((BlockEntityType)RootsRegistry.ACCELERATOR_STANDING_STONE_TILE.get()));
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createStandingStoneTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends AcceleratorStandingStoneTile> standingStoneType) {
        return level.isClientSide ? AcceleratorStandingStoneBlock.createTickerHelper(entityType, standingStoneType, AcceleratorStandingStoneTile::clientTick) : AcceleratorStandingStoneBlock.createTickerHelper(entityType, standingStoneType, AcceleratorStandingStoneTile::serverTick);
    }
}

