/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  net.minecraft.world.item.crafting.RecipeInput
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.level.Level
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package elucent.rootsclassic.component;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.component.components.ComponentAllium;
import elucent.rootsclassic.component.components.ComponentApple;
import elucent.rootsclassic.component.components.ComponentAzureBluet;
import elucent.rootsclassic.component.components.ComponentBlueOrchid;
import elucent.rootsclassic.component.components.ComponentChorus;
import elucent.rootsclassic.component.components.ComponentDandelion;
import elucent.rootsclassic.component.components.ComponentFlareOrchid;
import elucent.rootsclassic.component.components.ComponentLilac;
import elucent.rootsclassic.component.components.ComponentLilyPad;
import elucent.rootsclassic.component.components.ComponentMidnightBloom;
import elucent.rootsclassic.component.components.ComponentNetherWart;
import elucent.rootsclassic.component.components.ComponentOrangeTulip;
import elucent.rootsclassic.component.components.ComponentOxeyeDaisy;
import elucent.rootsclassic.component.components.ComponentPeony;
import elucent.rootsclassic.component.components.ComponentPinkTulip;
import elucent.rootsclassic.component.components.ComponentPoisonousPotato;
import elucent.rootsclassic.component.components.ComponentPoppy;
import elucent.rootsclassic.component.components.ComponentRadiantDaisy;
import elucent.rootsclassic.component.components.ComponentRedTulip;
import elucent.rootsclassic.component.components.ComponentRose;
import elucent.rootsclassic.component.components.ComponentSunflower;
import elucent.rootsclassic.component.components.ComponentWhiteTulip;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ComponentRegistry {
    public static final DeferredRegister<ComponentBase> COMPONENTS = DeferredRegister.create(ComponentBaseRegistry.COMPONENTS_KEY, (String)"rootsclassic");
    public static final DeferredHolder<ComponentBase, ComponentBase> ROSE_BUSH = COMPONENTS.register("rose_bush", () -> new ComponentRose().setPrimaryColor(192.0, 0.0, 72.0).setSecondaryColor(0.0, 200.0, 48.0).setTextColor(ChatFormatting.GREEN));
    public static final DeferredHolder<ComponentBase, ComponentBase> DANDELION = COMPONENTS.register("dandelion", () -> new ComponentDandelion().setPrimaryColor(255.0, 217.0, 102.0).setSecondaryColor(240.0, 159.0, 10.0).setTextColor(ChatFormatting.YELLOW));
    public static final DeferredHolder<ComponentBase, ComponentBase> CHORUS = COMPONENTS.register("chorus", () -> new ComponentChorus().setPrimaryColor(95.0, 57.0, 95.0).setSecondaryColor(225.0, 215.0, 225.0).setTextColor(ChatFormatting.DARK_PURPLE));
    public static final DeferredHolder<ComponentBase, ComponentBase> NETHER_WART = COMPONENTS.register("nether_wart", () -> new ComponentNetherWart().setPrimaryColor(255.0, 76.0, 36.0).setSecondaryColor(255.0, 174.0, 0.0).setTextColor(ChatFormatting.GOLD));
    public static final DeferredHolder<ComponentBase, ComponentBase> PEONY = COMPONENTS.register("peony", () -> new ComponentPeony().setPrimaryColor(255.0, 102.0, 178.0).setSecondaryColor(255.0, 51.0, 153.0).setTextColor(ChatFormatting.LIGHT_PURPLE));
    public static final DeferredHolder<ComponentBase, ComponentBase> SUNFLOWER = COMPONENTS.register("sunflower", () -> new ComponentSunflower().setPrimaryColor(255.0, 255.0, 128.0).setSecondaryColor(255.0, 255.0, 255.0).setTextColor(ChatFormatting.WHITE));
    public static final DeferredHolder<ComponentBase, ComponentBase> LILAC = COMPONENTS.register("lilac", () -> new ComponentLilac().setPrimaryColor(112.0, 80.0, 112.0).setSecondaryColor(0.0, 112.0, 24.0).setTextColor(ChatFormatting.GREEN));
    public static final DeferredHolder<ComponentBase, ComponentBase> AZURE_BLUET = COMPONENTS.register("azure_bluet", () -> new ComponentAzureBluet().setPrimaryColor(240.0, 240.0, 255.0).setSecondaryColor(86.0, 86.0, 96.0).setTextColor(ChatFormatting.GRAY));
    public static final DeferredHolder<ComponentBase, ComponentBase> ALLIUM = COMPONENTS.register("allium", () -> new ComponentAllium().setPrimaryColor(255.0, 202.0, 255.0).setSecondaryColor(51.0, 30.0, 50.0).setTextColor(ChatFormatting.DARK_PURPLE));
    public static final DeferredHolder<ComponentBase, ComponentBase> WHITE_TULIP = COMPONENTS.register("white_tulip", () -> new ComponentWhiteTulip().setPrimaryColor(255.0, 255.0, 255.0).setSecondaryColor(136.0, 252.0, 255.0).setTextColor(ChatFormatting.AQUA));
    public static final DeferredHolder<ComponentBase, ComponentBase> RED_TULIP = COMPONENTS.register("red_tulip", () -> new ComponentRedTulip().setPrimaryColor(128.0, 16.0, 16.0).setSecondaryColor(128.0, 16.0, 64.0).setTextColor(ChatFormatting.DARK_RED));
    public static final DeferredHolder<ComponentBase, ComponentBase> POPPY = COMPONENTS.register("poppy", () -> new ComponentPoppy().setPrimaryColor(255.0, 0.0, 0.0).setSecondaryColor(50.0, 50.0, 50.0).setTextColor(ChatFormatting.RED));
    public static final DeferredHolder<ComponentBase, ComponentBase> BLUE_ORCHID = COMPONENTS.register("blue_orchid", () -> new ComponentBlueOrchid().setPrimaryColor(68.0, 39.0, 26.0).setSecondaryColor(162.0, 153.0, 150.0).setTextColor(ChatFormatting.GRAY));
    public static final DeferredHolder<ComponentBase, ComponentBase> POISONOUS_POTATO = COMPONENTS.register("poisonous_potato", () -> new ComponentPoisonousPotato().setPrimaryColor(172.0, 255.0, 81.0).setSecondaryColor(81.0, 181.0, 255.0).setTextColor(ChatFormatting.YELLOW));
    public static final DeferredHolder<ComponentBase, ComponentBase> ORANGE_TULIP = COMPONENTS.register("orange_tulip", () -> new ComponentOrangeTulip().setPrimaryColor(255.0, 181.0, 70.0).setSecondaryColor(255.0, 255.0, 0.0).setTextColor(ChatFormatting.GOLD));
    public static final DeferredHolder<ComponentBase, ComponentBase> PINK_TULIP = COMPONENTS.register("pink_tulip", () -> new ComponentPinkTulip().setPrimaryColor(255.0, 0.0, 51.0).setSecondaryColor(255.0, 0.0, 249.0).setTextColor(ChatFormatting.LIGHT_PURPLE));
    public static final DeferredHolder<ComponentBase, ComponentBase> OXEYE_DAISY = COMPONENTS.register("oxeye_daisy", () -> new ComponentOxeyeDaisy().setPrimaryColor(255.0, 254.0, 206.0).setSecondaryColor(52.0, 0.0, 74.0).setTextColor(ChatFormatting.WHITE));
    public static final DeferredHolder<ComponentBase, ComponentBase> LILY_PAD = COMPONENTS.register("lily_pad", () -> new ComponentLilyPad().setPrimaryColor(36.0, 255.0, 167.0).setSecondaryColor(8.0, 0.0, 255.0).setTextColor(ChatFormatting.BLUE));
    public static final DeferredHolder<ComponentBase, ComponentBase> APPLE = COMPONENTS.register("apple", () -> new ComponentApple().setPrimaryColor(255.0, 43.0, 89.0).setSecondaryColor(255.0, 43.0, 43.0).setTextColor(ChatFormatting.DARK_RED));
    public static final DeferredHolder<ComponentBase, ComponentBase> MIDNIGHT_BLOOM = COMPONENTS.register("midnight_bloom", () -> new ComponentMidnightBloom().setPrimaryColor(12.0, 6.0, 36.0).setSecondaryColor(18.0, 18.0, 18.0).setTextColor(ChatFormatting.DARK_PURPLE));
    public static final DeferredHolder<ComponentBase, ComponentBase> FLARE_ORCHID = COMPONENTS.register("flare_orchid", () -> new ComponentFlareOrchid().setPrimaryColor(255.0, 60.0, 18.0).setSecondaryColor(255.0, 60.0, 18.0).setTextColor(ChatFormatting.RED));
    public static final DeferredHolder<ComponentBase, ComponentBase> RADIANT_DAISY = COMPONENTS.register("radiant_daisy", () -> new ComponentRadiantDaisy().setPrimaryColor(255.0, 255.0, 255.0).setSecondaryColor(255.0, 255.0, 255.0).setTextColor(ChatFormatting.WHITE));

    public static RecipeHolder<ComponentRecipe> getSpellFromName(RecipeManager mgr, ResourceLocation name) {
        if (name.getNamespace().equals("rootsclassic") && name.getPath().equals("none")) {
            return null;
        }
        for (RecipeHolder recipe : mgr.getAllRecipesFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get())) {
            if (!((ComponentRecipe)recipe.value()).getEffectResult().equals((Object)name)) continue;
            return recipe;
        }
        return null;
    }

    public static RecipeHolder<ComponentRecipe> getRecipeFromInput(Level levelAccessor, RecipeInput inventory) {
        Optional recipe = levelAccessor.getRecipeManager().getRecipeFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get(), inventory, levelAccessor);
        return recipe.orElse(null);
    }
}

