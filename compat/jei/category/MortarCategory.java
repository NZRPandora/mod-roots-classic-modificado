/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.gui.ingredient.IRecipeSlotsView
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.ingredients.IIngredientType
 *  mezz.jei.api.recipe.IFocusGroup
 *  mezz.jei.api.recipe.RecipeIngredientRole
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeInput
 *  net.minecraft.world.item.crafting.SingleRecipeInput
 *  net.minecraft.world.level.ItemLike
 */
package elucent.rootsclassic.compat.jei.category;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.jei.JEIPlugin;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.ItemLike;

public class MortarCategory
implements IRecipeCategory<ComponentRecipe> {
    private static final ResourceLocation backgroundLocation = Const.modLoc("textures/gui/tabletmortar.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public MortarCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(backgroundLocation, 21, 30, 142, 45).addPadding(0, 0, 0, 0).build();
        this.icon = guiHelper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)new ItemStack((ItemLike)RootsRegistry.MORTAR.get()));
        this.localizedName = Component.translatable((String)"rootsclassic.gui.jei.category.mortar");
    }

    public RecipeType<ComponentRecipe> getRecipeType() {
        return JEIPlugin.MORTAR_TYPE;
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    public Component getTitle() {
        return this.localizedName;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, ComponentRecipe recipe, IFocusGroup focuses) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new NullPointerException("level must not be null.");
        }
        RegistryAccess registryAccess = level.registryAccess();
        for (int i = 0; i < recipe.getIngredients().size(); ++i) {
            Ingredient ingredient = (Ingredient)recipe.getIngredients().get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, 3 + i * 16, 26).addIngredients(ingredient);
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 123, 26).addItemStack(recipe.assemble((RecipeInput)new SingleRecipeInput(ItemStack.EMPTY), (HolderLookup.Provider)registryAccess));
    }

    public void draw(ComponentRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw((Object)recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }
}

