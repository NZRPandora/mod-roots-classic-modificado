/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.ritual;

import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.ritual.RitualBaseRegistry;
import elucent.rootsclassic.util.RootsUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class RitualEffect {
    public abstract void doEffect(Level var1, BlockPos var2, Container var3, List<ItemStack> var4, CompoundTag var5);

    public ItemStack getResult(CompoundTag config, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    public MutableComponent getInfoText(CompoundTag config) {
        ResourceLocation id = RitualBaseRegistry.RITUALS.getKey((Object)this);
        if (id == null) {
            return Component.empty();
        }
        return Component.translatable((String)(id.getNamespace() + ".jei.tooltip." + id.getPath()));
    }

    public boolean incenseMatches(List<ItemStack> incensesFromNearby, RitualRecipe recipe) {
        return RootsUtil.matchesIngredients(incensesFromNearby, recipe.getIncenses());
    }
}

