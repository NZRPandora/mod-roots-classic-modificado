/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap$Builder
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package elucent.rootsclassic.item;

import com.google.common.collect.ImmutableMap;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class DruidKnifeItem
extends Item {
    protected static final Map<Block, Block> BLOCK_STRIPPING_MAP = new ImmutableMap.Builder().put((Object)Blocks.OAK_WOOD, (Object)Blocks.STRIPPED_OAK_WOOD).put((Object)Blocks.OAK_LOG, (Object)Blocks.STRIPPED_OAK_LOG).put((Object)Blocks.DARK_OAK_WOOD, (Object)Blocks.STRIPPED_DARK_OAK_WOOD).put((Object)Blocks.DARK_OAK_LOG, (Object)Blocks.STRIPPED_DARK_OAK_LOG).put((Object)Blocks.ACACIA_WOOD, (Object)Blocks.STRIPPED_ACACIA_WOOD).put((Object)Blocks.ACACIA_LOG, (Object)Blocks.STRIPPED_ACACIA_LOG).put((Object)Blocks.BIRCH_WOOD, (Object)Blocks.STRIPPED_BIRCH_WOOD).put((Object)Blocks.BIRCH_LOG, (Object)Blocks.STRIPPED_BIRCH_LOG).put((Object)Blocks.JUNGLE_WOOD, (Object)Blocks.STRIPPED_JUNGLE_WOOD).put((Object)Blocks.JUNGLE_LOG, (Object)Blocks.STRIPPED_JUNGLE_LOG).put((Object)Blocks.SPRUCE_WOOD, (Object)Blocks.STRIPPED_SPRUCE_WOOD).put((Object)Blocks.SPRUCE_LOG, (Object)Blocks.STRIPPED_SPRUCE_LOG).build();
    protected static final Map<Block, Supplier<Item>> BLOCK_BARK_MAP = new ImmutableMap.Builder().put((Object)Blocks.OAK_WOOD, RootsRegistry.OAK_BARK).put((Object)Blocks.OAK_LOG, RootsRegistry.OAK_BARK).put((Object)Blocks.DARK_OAK_WOOD, RootsRegistry.DARK_OAK_BARK).put((Object)Blocks.DARK_OAK_LOG, RootsRegistry.DARK_OAK_BARK).put((Object)Blocks.ACACIA_WOOD, RootsRegistry.ACACIA_BARK).put((Object)Blocks.ACACIA_LOG, RootsRegistry.ACACIA_BARK).put((Object)Blocks.BIRCH_WOOD, RootsRegistry.BIRCH_BARK).put((Object)Blocks.BIRCH_LOG, RootsRegistry.BIRCH_BARK).put((Object)Blocks.JUNGLE_WOOD, RootsRegistry.JUNGLE_BARK).put((Object)Blocks.JUNGLE_LOG, RootsRegistry.JUNGLE_BARK).put((Object)Blocks.SPRUCE_WOOD, RootsRegistry.SPRUCE_BARK).put((Object)Blocks.SPRUCE_LOG, RootsRegistry.SPRUCE_BARK).build();

    public DruidKnifeItem(Item.Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level levelAccessor = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = levelAccessor.getBlockState(pos);
        BlockState strippedState = DruidKnifeItem.getStrippingState(state);
        ItemStack barkDrop = DruidKnifeItem.getBarkDrop(state);
        if (!barkDrop.isEmpty() && strippedState != null) {
            ItemStack stack = context.getItemInHand();
            InteractionHand hand = context.getHand();
            Player playerIn = context.getPlayer();
            playerIn.spawnAtLocation(barkDrop, 1.0f);
            stack.hurtAndBreak(1, (LivingEntity)playerIn, Player.getSlotForHand((InteractionHand)hand));
            if (levelAccessor.random.nextDouble() < (Double)RootsConfig.COMMON.barkKnifeBlockStripChance.get()) {
                levelAccessor.playSound(playerIn, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!levelAccessor.isClientSide) {
                    levelAccessor.setBlock(pos, strippedState, 11);
                }
            }
            return InteractionResult.sidedSuccess((boolean)levelAccessor.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static BlockState getStrippingState(BlockState originalState) {
        Block block = BLOCK_STRIPPING_MAP.get(originalState.getBlock());
        return block != null ? (BlockState)block.defaultBlockState().setValue((Property)RotatedPillarBlock.AXIS, (Comparable)((Direction.Axis)originalState.getValue((Property)RotatedPillarBlock.AXIS))) : null;
    }

    public static ItemStack getBarkDrop(BlockState originalState) {
        Block block = originalState.getBlock();
        if (BLOCK_BARK_MAP.containsKey(block)) {
            return new ItemStack((ItemLike)BLOCK_BARK_MAP.get(block).get());
        }
        return ItemStack.EMPTY;
    }
}

