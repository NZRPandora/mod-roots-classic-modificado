/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  mezz.jei.api.IModPlugin
 *  mezz.jei.api.JeiPlugin
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.helpers.IJeiHelpers
 *  mezz.jei.api.ingredients.IIngredientType
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  mezz.jei.api.registration.IRecipeCatalystRegistration
 *  mezz.jei.api.registration.IRecipeCategoryRegistration
 *  mezz.jei.api.registration.IRecipeRegistration
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.neoforged.neoforge.registries.DeferredHolder
 */
package elucent.rootsclassic.compat.jei;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.jei.category.MortarCategory;
import elucent.rootsclassic.compat.jei.category.RitualCategory;
import elucent.rootsclassic.compat.jei.wrapper.RitualWrapper;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;

@JeiPlugin
public class JEIPlugin
implements IModPlugin {
    public static final ResourceLocation PLUGIN_UID = Const.modLoc("main");
    public static final ResourceLocation MORTAR = Const.modLoc("mortar");
    public static final RecipeType<ComponentRecipe> MORTAR_TYPE = RecipeType.create((String)"rootsclassic", (String)"mortar", ComponentRecipe.class);
    public static final ResourceLocation RITUAL = Const.modLoc("ritual");
    public static final RecipeType<RitualWrapper> RITUAL_TYPE = RecipeType.create((String)"rootsclassic", (String)"ritual", RitualWrapper.class);
    @Nullable
    private IRecipeCategory<ComponentRecipe> mortarCategory;
    @Nullable
    private IRecipeCategory<RitualWrapper> ritualCategory;

    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack((ItemLike)RootsRegistry.MORTAR.get()), new RecipeType[]{MORTAR_TYPE});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)RootsRegistry.ALTAR.get()), new RecipeType[]{RITUAL_TYPE});
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        IRecipeCategory[] iRecipeCategoryArray = new IRecipeCategory[2];
        this.mortarCategory = new MortarCategory(guiHelper);
        iRecipeCategoryArray[0] = this.mortarCategory;
        this.ritualCategory = new RitualCategory(guiHelper);
        iRecipeCategoryArray[1] = this.ritualCategory;
        registration.addRecipeCategories(iRecipeCategoryArray);
    }

    public void registerRecipes(IRecipeRegistration registration) {
        for (DeferredHolder registryObject : RootsRegistry.ITEMS.getEntries()) {
            Item item = (Item)registryObject.get();
            if (item == null) continue;
            registration.addIngredientInfo((Object)new ItemStack((ItemLike)item), (IIngredientType)VanillaTypes.ITEM_STACK, new Component[]{Component.translatable((String)(item.getDescriptionId() + ".guide"))});
        }
        assert (MORTAR_TYPE != null);
        assert (RITUAL_TYPE != null);
        ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
        registration.addRecipes(MORTAR_TYPE, clientLevel.getRecipeManager().getAllRecipesFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(RITUAL_TYPE, this.getRituals((Level)clientLevel));
    }

    public List<RitualWrapper> getRituals(Level world) {
        List ritualRecipes = world.getRecipeManager().getAllRecipesFor(RootsRecipes.RITUAL_RECIPE_TYPE.get());
        return ritualRecipes.stream().map(holder -> new RitualWrapper((RitualRecipe)holder.value())).toList();
    }
}

