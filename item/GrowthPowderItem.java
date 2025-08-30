/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.client.particles.MagicParticleData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GrowthPowderItem
extends Item {
    public GrowthPowderItem(Item.Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level levelAccessor = context.getLevel();
        Player player = context.getPlayer();
        if (player != null) {
            boolean anySuccess;
            BlockPos pos = context.getClickedPos();
            InteractionHand hand = context.getHand();
            if (levelAccessor.isClientSide) {
                GrowthPowderItem.spawnGrowthParticle(levelAccessor, player);
            }
            if ((anySuccess = GrowthPowderItem.applyGrowthHere(levelAccessor, pos)) && !player.getAbilities().instabuild) {
                player.getItemInHand(hand).shrink(1);
            }
        }
        return InteractionResult.PASS;
    }

    public static boolean applyGrowthHere(Level levelAccessor, BlockPos pos) {
        BlockState state = levelAccessor.getBlockState(pos);
        if (state.is(Blocks.DIRT)) {
            levelAccessor.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
            return true;
        }
        if (state.is(Blocks.WATER) && levelAccessor.isEmptyBlock(pos.above())) {
            levelAccessor.setBlockAndUpdate(pos.above(), Blocks.LILY_PAD.defaultBlockState());
        } else {
            Block block = state.getBlock();
            if (block instanceof BonemealableBlock) {
                BonemealableBlock igrowable = (BonemealableBlock)block;
                if (!igrowable.isValidBonemealTarget((LevelReader)levelAccessor, pos, state)) {
                    return false;
                }
                Block block2 = state.getBlock();
                levelAccessor.blockUpdated(pos, block2);
                if (!levelAccessor.isClientSide) {
                    state.randomTick((ServerLevel)levelAccessor, pos, levelAccessor.random);
                }
                return true;
            }
        }
        return false;
    }

    public static void spawnGrowthParticle(Level levelAccessor, Player player) {
        Vec3 lookVec = player.getLookAngle();
        for (int i = 0; i < 40; ++i) {
            double velX = lookVec.x * 0.75 + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
            double velY = lookVec.y * 0.75 + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
            double velZ = lookVec.z * 0.75 + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
            levelAccessor.addParticle(MagicParticleData.createData(39.0, 232.0, 55.0), player.getX() + 0.5 * lookVec.x, player.getY() + 1.5 + 0.5 * lookVec.y, player.getZ() + 0.5 * lookVec.z, velX, velY, velZ);
        }
    }
}

