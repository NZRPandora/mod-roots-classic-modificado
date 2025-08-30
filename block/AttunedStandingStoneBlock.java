/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.DoubleBlockHalf
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package elucent.rootsclassic.block;

import elucent.rootsclassic.block.BaseBEBlock;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AttunedStandingStoneBlock
extends BaseBEBlock {
    private static final VoxelShape BOTTOM_SHAPE = Block.box((double)4.0, (double)0.0, (double)4.0, (double)12.0, (double)16.0, (double)12.0);
    private static final VoxelShape TOP_SHAPE = Block.box((double)4.0, (double)0.0, (double)4.0, (double)12.0, (double)10.0, (double)12.0);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public AttunedStandingStoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, (Comparable)DoubleBlockHalf.LOWER));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HALF});
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)stateIn.getValue(HALF);
        if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
            return facingState.is((Block)this) && facingState.getValue(HALF) != doubleblockhalf ? stateIn : Blocks.AIR.defaultBlockState();
        }
        return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive((LevelReader)levelAccessor, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, levelAccessor, currentPos, facingPos);
    }

    @Override
    public BlockState playerWillDestroy(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
        if (!levelAccessor.isClientSide && player.isCreative()) {
            AttunedStandingStoneBlock.removeBottomHalf(levelAccessor, pos, state, player);
        }
        return super.playerWillDestroy(levelAccessor, pos, state, player);
    }

    public static void removeBottomHalf(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
        BlockPos blockpos;
        BlockState blockstate;
        DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)state.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER && (blockstate = levelAccessor.getBlockState(blockpos = pos.below())).is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
            levelAccessor.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
            levelAccessor.levelEvent(player, 2001, blockpos, Block.getId((BlockState)blockstate));
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
            return (BlockState)this.defaultBlockState().setValue(HALF, (Comparable)DoubleBlockHalf.LOWER);
        }
        return null;
    }

    public void setPlacedBy(Level levelAccessor, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        levelAccessor.setBlock(pos.above(), (BlockState)state.setValue(HALF, (Comparable)DoubleBlockHalf.UPPER), 3);
    }

    public boolean canSurvive(BlockState state, LevelReader levelAccessor, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = levelAccessor.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy((BlockGetter)levelAccessor, blockpos, Direction.UP) : blockstate.is((Block)this);
    }

    public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return BOTTOM_SHAPE;
        }
        return TOP_SHAPE;
    }
}

