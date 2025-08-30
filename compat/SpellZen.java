/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.blamejared.crafttweaker.api.CraftTweakerAPI
 *  com.blamejared.crafttweaker.api.action.base.IAction
 *  com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe
 *  com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName
 *  com.blamejared.crafttweaker.api.annotation.ZenRegister
 *  com.blamejared.crafttweaker.api.item.IItemStack
 *  com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager
 *  net.minecraft.core.NonNullList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  net.minecraft.world.item.crafting.RecipeType
 *  org.openzen.zencode.java.ZenCodeType$Method
 *  org.openzen.zencode.java.ZenCodeType$Name
 */
package elucent.rootsclassic.compat;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentRegistry;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name(value="mods.rootsclassic.Spell")
public class SpellZen
implements IRecipeManager<ComponentRecipe> {
    public static final SpellZen INSTANCE = new SpellZen();

    private SpellZen() {
    }

    @ZenCodeType.Method
    public void setSpellIngredients(ResourceLocation name, IItemStack[] ingredients) {
        if (ingredients.length == 0 || ingredients.length > 4) {
            throw new IllegalArgumentException("Invalid spell ingredients, must be in range [1,4]");
        }
        RecipeHolder<ComponentRecipe> foundHolder = this.findSpellByName(name);
        ComponentRecipe recipe = (ComponentRecipe)foundHolder.value();
        NonNullList ingredientList = NonNullList.create();
        for (IItemStack ingredient : ingredients) {
            ingredientList.add((Object)ingredient.asVanillaIngredient());
        }
        ComponentRecipe newRecipe = new ComponentRecipe(recipe.getEffectResult(), recipe.getGroup(), recipe.getResultItem(), (NonNullList<Ingredient>)ingredientList, recipe.needsMixin());
        CraftTweakerAPI.apply((IAction)new ActionRemoveRecipeByName((IRecipeManager)INSTANCE, foundHolder.id()));
        CraftTweakerAPI.apply((IAction)new ActionAddRecipe((IRecipeManager)INSTANCE, new RecipeHolder(foundHolder.id(), (Recipe)newRecipe)));
    }

    @ZenCodeType.Method
    public void addMortarCrafting(String uniqueName, IItemStack[] items, IItemStack output) {
        if (items.length == 0 || items.length > 4) {
            throw new IllegalArgumentException("Invalid ingredient size, must be in range [1,4]");
        }
        NonNullList ingredients = NonNullList.create();
        for (IItemStack stack : items) {
            ingredients.add((Object)stack.asVanillaIngredient());
        }
        ComponentRecipe craftingRecipe = new ComponentRecipe(Const.modLoc("none"), "crafttweaker", output.getInternal(), (NonNullList<Ingredient>)ingredients, true);
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath((String)"crafttweaker", (String)uniqueName);
        CraftTweakerAPI.apply((IAction)new ActionAddRecipe((IRecipeManager)INSTANCE, new RecipeHolder(id, (Recipe)craftingRecipe)));
    }

    private RecipeHolder<ComponentRecipe> findSpellByName(ResourceLocation name) {
        RecipeHolder<ComponentRecipe> found = ComponentRegistry.getSpellFromName(CraftTweakerAPI.getAccessibleElementsProvider().recipeManager(), name);
        if (found == null) {
            StringBuilder names = new StringBuilder();
            for (RecipeHolder recipe : CraftTweakerAPI.getAccessibleElementsProvider().recipeManager().getAllRecipesFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get())) {
                if (!name.getNamespace().equals("rootsclassic") || name.getPath().equals("none")) continue;
                names.append(((ComponentRecipe)recipe.value()).getEffectResult()).append(", ");
            }
            throw new IllegalArgumentException("Invalid spell [" + String.valueOf(name) + "], names must be one of: " + String.valueOf(names));
        }
        return found;
    }

    public RecipeType<ComponentRecipe> getRecipeType() {
        return RootsRecipes.COMPONENT_RECIPE_TYPE.get();
    }
}

