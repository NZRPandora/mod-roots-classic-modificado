/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.BooleanOp
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package elucent.rootsclassic.block.altar;

import elucent.rootsclassic.block.BaseBEBlock;
import elucent.rootsclassic.block.altar.AltarBlockEntity;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AltarBlock
extends BaseBEBlock
implements EntityBlock {
    private static final VoxelShape SHAPE = Stream.of(Block.box((double)0.0, (double)8.0, (double)0.0, (double)16.0, (double)12.0, (double)16.0), Block.box((double)4.0, (double)0.0, (double)4.0, (double)12.0, (double)8.0, (double)12.0), Block.box((double)2.0, (double)4.0, (double)2.0, (double)6.0, (double)8.0, (double)6.0), Block.box((double)10.0, (double)4.0, (double)2.0, (double)14.0, (double)8.0, (double)6.0), Block.box((double)10.0, (double)4.0, (double)10.0, (double)14.0, (double)8.0, (double)14.0), Block.box((double)2.0, (double)4.0, (double)10.0, (double)6.0, (double)8.0, (double)14.0)).reduce((v1, v2) -> Shapes.join((VoxelShape)v1, (VoxelShape)v2, (BooleanOp)BooleanOp.OR)).get();

    public AltarBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AltarBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return AltarBlock.createStandingStoneTicker(level, entityType, (BlockEntityType<? extends AltarBlockEntity>)((BlockEntityType)RootsRegistry.ALTAR_TILE.get()));
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createStandingStoneTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends AltarBlockEntity> standingStoneType) {
        return level.isClientSide ? AltarBlock.createTickerHelper(entityType, standingStoneType, AltarBlockEntity::clientTick) : AltarBlock.createTickerHelper(entityType, standingStoneType, AltarBlockEntity::serverTick);
    }
}

