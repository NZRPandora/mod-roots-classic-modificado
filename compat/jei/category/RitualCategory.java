/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.gui.drawable.IDrawableStatic
 *  mezz.jei.api.gui.ingredient.IRecipeSlotsView
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.ingredients.IIngredientType
 *  mezz.jei.api.recipe.IFocusGroup
 *  mezz.jei.api.recipe.RecipeIngredientRole
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package elucent.rootsclassic.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.jei.JEIPlugin;
import elucent.rootsclassic.compat.jei.wrapper.RitualWrapper;
import elucent.rootsclassic.registry.RootsRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class RitualCategory
implements IRecipeCategory<RitualWrapper> {
    private static final ResourceLocation backgroundLocation = Const.modLoc("textures/gui/jei/compat.png");
    private static final ResourceLocation location = Const.modLoc("textures/gui/tabletaltar.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic ingredientBackground;
    private final IDrawableStatic incenseBackground;
    private final IDrawableStatic resultBackground;
    private final IDrawableStatic grid;
    private final IDrawableStatic stone;
    private final IDrawableStatic mundaneStone;
    private final IDrawableStatic attunedStone;
    private final Component localizedName;

    public RitualCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(backgroundLocation, 0, 0, 94, 110).addPadding(0, 0, 0, 0).build();
        this.icon = guiHelper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)new ItemStack((ItemLike)RootsRegistry.ALTAR.get()));
        this.ingredientBackground = guiHelper.createDrawable(location, 61, 53, 70, 22);
        this.resultBackground = guiHelper.createDrawable(location, 61, 53, 22, 22);
        this.incenseBackground = guiHelper.createDrawable(location, 49, 85, 94, 22);
        this.grid = guiHelper.createDrawable(location, 50, 118, 93, 93);
        this.stone = guiHelper.createDrawable(location, 192, 32, 16, 16);
        this.mundaneStone = guiHelper.createDrawable(location, 192, 48, 16, 16);
        this.attunedStone = guiHelper.createDrawable(location, 192, 64, 16, 16);
        this.localizedName = Component.translatable((String)"rootsclassic.gui.jei.category.ritual");
    }

    public RecipeType<RitualWrapper> getRecipeType() {
        return JEIPlugin.RITUAL_TYPE;
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

    public void setRecipe(IRecipeLayoutBuilder builder, RitualWrapper recipe, IFocusGroup focuses) {
        Ingredient ingredient;
        int i;
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new NullPointerException("level must not be null.");
        }
        RegistryAccess registryAccess = level.registryAccess();
        for (i = 0; i < recipe.getIngredients().size(); ++i) {
            ingredient = recipe.getIngredients().get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, 15 + i * 24, 3).addIngredients(ingredient);
        }
        for (i = 0; i < recipe.getIncenses().size(); ++i) {
            ingredient = recipe.getIncenses().get(i);
            builder.addSlot(RecipeIngredientRole.CATALYST, 28 + i * 16, 27).addIngredients(ingredient);
        }
        if (!recipe.getResult((HolderLookup.Provider)registryAccess).isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 67, 67).addItemStack(recipe.getResult((HolderLookup.Provider)registryAccess));
        }
    }

    public void draw(RitualWrapper recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw((Object)recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.ingredientBackground.draw(guiGraphics, 12, 0);
        this.incenseBackground.draw(guiGraphics, 0, 24);
        this.resultBackground.draw(guiGraphics, 64, 64);
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 1.0f);
        this.grid.draw(guiGraphics, 20, 100);
        int basePosX = 63;
        int basePosY = 135;
        recipe.getPillars().forEach((pos, block) -> {
            int xShift = 0;
            int yShift = 0;
            this.stone.draw(guiGraphics, basePosX, basePosY);
            if (block.equals(RootsRegistry.MUNDANE_STANDING_STONE.get())) {
                xShift = 8 * pos.getX();
                yShift = 8 * pos.getZ();
                this.mundaneStone.draw(guiGraphics, basePosX + xShift, basePosY + yShift);
            }
            if (block.equals(RootsRegistry.ATTUNED_STANDING_STONE.get())) {
                xShift = 8 * pos.getX();
                yShift = 8 * pos.getZ();
                this.attunedStone.draw(guiGraphics, basePosX + xShift, basePosY + yShift);
            }
        });
        poseStack.popPose();
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        MutableComponent infoText = recipe.getInfoText();
        guiGraphics.drawString(font, (Component)infoText, (int)((float)(94 - font.width((FormattedText)infoText)) / 2.0f), 100, 0x8B8B8B);
    }
}

