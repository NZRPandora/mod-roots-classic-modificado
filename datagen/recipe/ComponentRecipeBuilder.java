/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.Criterion
 *  net.minecraft.core.NonNullList
 *  net.minecraft.data.recipes.RecipeBuilder
 *  net.minecraft.data.recipes.RecipeOutput
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.level.ItemLike
 *  org.jetbrains.annotations.Nullable
 */
package elucent.rootsclassic.datagen.recipe;

import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.Arrays;
import java.util.Objects;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class ComponentRecipeBuilder
implements RecipeBuilder {
    private final ResourceLocation effectResult;
    private String group;
    private ItemStack output = RootsRegistry.SPELL_POWDER.toStack();
    private final NonNullList<Ingredient> materials = NonNullList.create();
    private boolean needsMixin = true;

    public ComponentRecipeBuilder(ResourceLocation effectResult) {
        this.effectResult = effectResult;
    }

    public RecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    public ComponentRecipeBuilder materials(Ingredient ... ingredients) {
        this.materials.addAll(Arrays.asList(ingredients));
        return this;
    }

    public ComponentRecipeBuilder output(ItemStack output) {
        this.output = output;
        return this;
    }

    public ComponentRecipeBuilder output(Item output) {
        this.output = new ItemStack((ItemLike)output);
        return this;
    }

    public ComponentRecipeBuilder output(Item output, int count) {
        this.output = new ItemStack((ItemLike)output, count);
        return this;
    }

    public ComponentRecipeBuilder needsMixin(boolean needsMixin) {
        this.needsMixin = needsMixin;
        return this;
    }

    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    public Item getResult() {
        return this.output != null ? this.output.getItem() : Items.AIR;
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        ComponentRecipe upgradeRecipe = new ComponentRecipe(this.effectResult, Objects.requireNonNullElse(this.group, ""), this.output, this.materials, this.needsMixin);
        recipeOutput.accept(id, (Recipe)upgradeRecipe, null);
    }
}

