/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap$Builder
 *  io.netty.util.collection.IntObjectHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package elucent.rootsclassic.ritual;

import com.google.common.collect.ImmutableMap;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import io.netty.util.collection.IntObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RitualPillars {
    private static final int INCENSE_RADIUS = 4;
    private static final IntObjectHashMap<Map<BlockPos, Block>> CACHE = new IntObjectHashMap();

    public static Map<BlockPos, Block> getRitualPillars(int level) {
        return (Map)CACHE.computeIfAbsent((Object)level, $ -> {
            ImmutableMap.Builder builder = new ImmutableMap.Builder();
            if (level >= 1) {
                builder.put((Object)new BlockPos(-3, 0, -3), (Object)((Block)RootsRegistry.MUNDANE_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(-3, 0, 3), (Object)((Block)RootsRegistry.MUNDANE_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(3, 0, -3), (Object)((Block)RootsRegistry.MUNDANE_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(3, 0, 3), (Object)((Block)RootsRegistry.MUNDANE_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(3, 0, 0), (Object)((Block)RootsRegistry.MUNDANE_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(-3, 0, 0), (Object)((Block)RootsRegistry.MUNDANE_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(0, 0, 3), (Object)((Block)RootsRegistry.MUNDANE_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(0, 0, -3), (Object)((Block)RootsRegistry.MUNDANE_STANDING_STONE.get()));
            }
            if (level == 2) {
                builder.put((Object)new BlockPos(5, 1, 0), (Object)((Block)RootsRegistry.ATTUNED_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(-5, 1, 0), (Object)((Block)RootsRegistry.ATTUNED_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(0, 1, 5), (Object)((Block)RootsRegistry.ATTUNED_STANDING_STONE.get()));
                builder.put((Object)new BlockPos(0, 1, -5), (Object)((Block)RootsRegistry.ATTUNED_STANDING_STONE.get()));
            }
            return builder.build();
        });
    }

    public static boolean verifyPositionBlocks(RitualRecipe recipe, Level levelAccessor, BlockPos pos) {
        Map<BlockPos, Block> pillars = RitualPillars.getRitualPillars(recipe.level);
        return pillars.entrySet().stream().allMatch(entry -> {
            BlockPos loopPos = (BlockPos)entry.getKey();
            Block loopBlock = (Block)entry.getValue();
            BlockPos loopPosOffset = pos.offset(loopPos.getX(), loopPos.getY(), loopPos.getZ());
            if (levelAccessor.getBlockState(loopPosOffset).getBlock() != loopBlock) {
                Roots.LOGGER.info("{} level recipe has Missing block {} at position {}", new Object[]{recipe.level, loopBlock, loopPosOffset});
                return false;
            }
            return true;
        });
    }

    public static List<BrazierBlockEntity> getRecipeBraziers(Level levelAccessor, BlockPos pos) {
        ArrayList<BrazierBlockEntity> links = new ArrayList<BrazierBlockEntity>();
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {
                BlockEntity tileHere;
                if (!levelAccessor.getBlockState(pos.offset(i, 0, j)).is(RootsRegistry.BRAZIER) || !((tileHere = levelAccessor.getBlockEntity(pos.offset(i, 0, j))) instanceof BrazierBlockEntity)) continue;
                BrazierBlockEntity brazier = (BrazierBlockEntity)tileHere;
                links.add(brazier);
            }
        }
        return links;
    }
}

