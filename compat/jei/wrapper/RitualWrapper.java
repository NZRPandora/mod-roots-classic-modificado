/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.block.Block
 */
package elucent.rootsclassic.compat.jei.wrapper;

import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.ritual.RitualPillars;
import java.util.List;
import java.util.Map;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class RitualWrapper
implements IRecipeCategoryExtension<RitualRecipe> {
    private final RitualRecipe ritual;

    public Map<BlockPos, Block> getPillars() {
        return RitualPillars.getRitualPillars(this.ritual.level);
    }

    public List<Ingredient> getIngredients() {
        return this.ritual.getIngredients();
    }

    public List<Ingredient> getIncenses() {
        return this.ritual.getIncenses();
    }

    public ItemStack getResult(HolderLookup.Provider provider) {
        return this.ritual.getResultItem(provider);
    }

    public RitualWrapper(RitualRecipe ritual) {
        this.ritual = ritual;
    }

    public MutableComponent getInfoText() {
        return this.ritual.getInfoText();
    }
}

