/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 */
package elucent.rootsclassic.mutation.mutations;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.flowers.MidnightBloomBlock;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class MutagenMidnightBloomRecipe
extends MutagenRecipe {
    public MutagenMidnightBloomRecipe() {
        super(Const.modLoc("midnight_bloom"), Blocks.POPPY.defaultBlockState(), ((MidnightBloomBlock)((Object)RootsRegistry.MIDNIGHT_BLOOM.get())).defaultBlockState());
        this.addIngredient(new ItemStack((ItemLike)Blocks.COAL_BLOCK, 1));
    }

    @Override
    public void onCrafted(Level levelAccessor, BlockPos pos, Player player) {
        player.getPersistentData().putInt("rootsclassic:RMOD_skipTicks", 200);
    }

    @Override
    public boolean matches(List<ItemStack> items, Level levelAccessor, BlockPos pos, Player player) {
        if (super.matches(items, levelAccessor, pos, player)) {
            return levelAccessor.dimension() == Level.END && levelAccessor.getBlockState(pos.below(2)).is(Blocks.OBSIDIAN) && player.getEffect(MobEffects.MOVEMENT_SLOWDOWN) != null;
        }
        return false;
    }
}

