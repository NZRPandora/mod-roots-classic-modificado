/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.Criterion
 *  net.minecraft.core.NonNullList
 *  net.minecraft.data.recipes.RecipeBuilder
 *  net.minecraft.data.recipes.RecipeOutput
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  org.jetbrains.annotations.Nullable
 */
package elucent.rootsclassic.datagen.recipe;

import elucent.rootsclassic.recipe.RitualRecipe;
import java.util.Arrays;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

public class RitualRecipeBuilder
implements RecipeBuilder {
    public final ResourceLocation effectId;
    public CompoundTag effectConfig = null;
    private final NonNullList<Ingredient> materials = NonNullList.create();
    private final NonNullList<Ingredient> incenses = NonNullList.create();
    public int level = 0;
    public String color = "";
    public String secondaryColor = "";

    public RitualRecipeBuilder(ResourceLocation effectId) {
        this.effectId = effectId;
    }

    public RitualRecipeBuilder config(CompoundTag config) {
        this.effectConfig = config;
        return this;
    }

    public RitualRecipeBuilder materials(Ingredient ... ingredients) {
        this.materials.addAll(Arrays.asList(ingredients));
        return this;
    }

    public RitualRecipeBuilder incenses(Ingredient ... ingredients) {
        this.incenses.addAll(Arrays.asList(ingredients));
        return this;
    }

    public RitualRecipeBuilder level(int level) {
        this.level = level;
        return this;
    }

    public RitualRecipeBuilder color(String color) {
        this.color = color;
        return this;
    }

    public RitualRecipeBuilder secondaryColor(String color) {
        this.secondaryColor = color;
        return this;
    }

    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    public Item getResult() {
        return Items.AIR;
    }

    public void save(RecipeOutput recipeOutput) {
        this.save(recipeOutput, this.effectId.getNamespace());
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        RitualRecipe upgradeRecipe = new RitualRecipe(this.effectId, this.effectConfig, this.materials, this.incenses, this.level, this.color, this.secondaryColor);
        recipeOutput.accept(id, (Recipe)upgradeRecipe, null);
    }
}

