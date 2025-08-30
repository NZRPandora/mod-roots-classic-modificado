/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 */
package elucent.rootsclassic.mutation.mutations;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.flowers.FlareOrchidBlock;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class MutagenFlareOrchidRecipe
extends MutagenRecipe {
    public MutagenFlareOrchidRecipe() {
        super(Const.modLoc("flare_orchid"), Blocks.BLUE_ORCHID.defaultBlockState(), ((FlareOrchidBlock)((Object)RootsRegistry.FLARE_ORCHID.get())).defaultBlockState());
        this.addIngredient(new ItemStack((ItemLike)Items.BLAZE_ROD, 1));
        this.addIngredient(new ItemStack((ItemLike)Items.LAVA_BUCKET, 1));
    }

    @Override
    public void onCrafted(Level levelAccessor, BlockPos pos, Player player) {
        player.igniteForSeconds(20.0f);
    }

    @Override
    public boolean matches(List<ItemStack> items, Level levelAccessor, BlockPos pos, Player player) {
        if (super.matches(items, levelAccessor, pos, player)) {
            return levelAccessor.dimension() == Level.NETHER && player.getEffect(MobEffects.FIRE_RESISTANCE) != null && levelAccessor.getBlockState(pos.east()).is(Blocks.NETHERRACK) && levelAccessor.getBlockState(pos.west()).is(Blocks.NETHERRACK) && levelAccessor.getBlockState(pos.north()).is(Blocks.NETHERRACK) && levelAccessor.getBlockState(pos.south()).is(Blocks.NETHERRACK) && levelAccessor.getBlockState(pos.east().north()).is(Blocks.NETHERRACK) && levelAccessor.getBlockState(pos.west().south()).is(Blocks.NETHERRACK) && levelAccessor.getBlockState(pos.north().west()).is(Blocks.NETHERRACK) && levelAccessor.getBlockState(pos.south().east()).is(Blocks.NETHERRACK);
        }
        return false;
    }
}

