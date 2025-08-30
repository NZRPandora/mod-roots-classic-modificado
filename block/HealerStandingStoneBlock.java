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
import elucent.rootsclassic.blockentity.HealerStandingStone;
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

public class HealerStandingStoneBlock
extends AttunedStandingStoneBlock
implements EntityBlock {
    public HealerStandingStoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue((Property)HALF) == DoubleBlockHalf.UPPER) {
            return new HealerStandingStone(pos, state);
        }
        return null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return HealerStandingStoneBlock.createStandingStoneTicker(level, entityType, (BlockEntityType<? extends HealerStandingStone>)((BlockEntityType)RootsRegistry.HEALER_STANDING_STONE_TILE.get()));
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createStandingStoneTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends HealerStandingStone> standingStoneType) {
        return level.isClientSide ? HealerStandingStoneBlock.createTickerHelper(entityType, standingStoneType, HealerStandingStone::clientTick) : HealerStandingStoneBlock.createTickerHelper(entityType, standingStoneType, HealerStandingStone::serverTick);
    }
}

