/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.effect.MobEffectInstance
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
import elucent.rootsclassic.block.flowers.RadiantDaisyBlock;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class MutagenRadiantDaisyRecipe
extends MutagenRecipe {
    public MutagenRadiantDaisyRecipe() {
        super(Const.modLoc("radiant_daisy"), Blocks.OXEYE_DAISY.defaultBlockState(), ((RadiantDaisyBlock)((Object)RootsRegistry.RADIANT_DAISY.get())).defaultBlockState());
        this.addIngredient(new ItemStack((ItemLike)Blocks.GLOWSTONE, 1));
        this.addIngredient(new ItemStack((ItemLike)Items.PRISMARINE_CRYSTALS, 1));
    }

    @Override
    public void onCrafted(Level levelAccessor, BlockPos pos, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1200, 0));
    }

    @Override
    public boolean matches(List<ItemStack> items, Level levelAccessor, BlockPos pos, Player player) {
        if (super.matches(items, levelAccessor, pos, player)) {
            return levelAccessor.dimension() == Level.OVERWORLD && player.getEffect(MobEffects.NIGHT_VISION) != null && player.getCommandSenderWorld().getDayTime() > 5000L && player.getCommandSenderWorld().getDayTime() < 7000L;
        }
        return false;
    }
}

