/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package elucent.rootsclassic.ritual;

import elucent.rootsclassic.block.altar.AltarBlockEntity;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualBaseRegistry;
import elucent.rootsclassic.ritual.RitualEffect;
import elucent.rootsclassic.ritual.rituals.RitualBanishRain;
import elucent.rootsclassic.ritual.rituals.RitualCauseRain;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import elucent.rootsclassic.ritual.rituals.RitualEngravedSword;
import elucent.rootsclassic.ritual.rituals.RitualFlare;
import elucent.rootsclassic.ritual.rituals.RitualGrow;
import elucent.rootsclassic.ritual.rituals.RitualImbuer;
import elucent.rootsclassic.ritual.rituals.RitualLifeDrain;
import elucent.rootsclassic.ritual.rituals.RitualMassBreed;
import elucent.rootsclassic.ritual.rituals.RitualSacrifice;
import elucent.rootsclassic.ritual.rituals.RitualSummoning;
import elucent.rootsclassic.ritual.rituals.RitualTimeShift;
import elucent.rootsclassic.util.RootsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RitualRegistry {
    public static final DeferredRegister<RitualEffect> RITUALS = DeferredRegister.create(RitualBaseRegistry.RITUAL_KEY, (String)"rootsclassic");
    public static final DeferredHolder<RitualEffect, RitualEffect> CRAFTING = RITUALS.register("crafting", RitualCrafting::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> CAUSE_RAIN = RITUALS.register("cause_rain", RitualCauseRain::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> BANISH_RAIN = RITUALS.register("banish_rain", RitualBanishRain::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> MASS_BREEDING = RITUALS.register("mass_breeding", RitualMassBreed::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> LIFE_DRAIN = RITUALS.register("life_drain", RitualLifeDrain::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> IMBUER = RITUALS.register("imbuer", RitualImbuer::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> SUMMONING = RITUALS.register("summoning", RitualSummoning::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> SACRIFICE = RITUALS.register("sacrifice", RitualSacrifice::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> FLARE = RITUALS.register("flare", RitualFlare::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> GROW = RITUALS.register("grow", RitualGrow::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> ENGRAVED_CRAFTING = RITUALS.register("engraved_crafting", RitualEngravedSword::new);
    public static final DeferredHolder<RitualEffect, RitualEffect> TIME_SHIFT = RITUALS.register("time_shift", RitualTimeShift::new);

    public static Optional<RecipeHolder<RitualRecipe>> findMatchingByIngredients(AltarBlockEntity altar) {
        ArrayList<ItemStack> altarInv = new ArrayList<ItemStack>();
        for (int i = 0; i < altar.inventory.getSlots(); ++i) {
            ItemStack stack = altar.inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            altarInv.add(stack);
        }
        return altar.getLevel().getRecipeManager().getAllRecipesFor(RootsRecipes.RITUAL_RECIPE_TYPE.get()).stream().filter(it -> RootsUtil.matchesIngredients(altarInv, ((RitualRecipe)it.value()).getIngredients())).findFirst();
    }

    public static List<ItemStack> getIncenses(Level level, BlockPos pos) {
        ArrayList<ItemStack> test = new ArrayList<ItemStack>();
        for (int i = -4; i < 5; ++i) {
            for (int j = -4; j < 5; ++j) {
                BrazierBlockEntity brazierBlockEntity;
                BlockEntity blockEntity;
                if (!level.getBlockState(pos.offset(i, 0, j)).is(RootsRegistry.BRAZIER) || !((blockEntity = level.getBlockEntity(pos.offset(i, 0, j))) instanceof BrazierBlockEntity) || !(brazierBlockEntity = (BrazierBlockEntity)blockEntity).isBurning()) continue;
                test.add(brazierBlockEntity.getHeldItem());
            }
        }
        return test;
    }

    public static Optional<RecipeHolder<RitualRecipe>> recipeByName(RecipeManager recipeManager, ResourceLocation id) {
        return recipeManager.getAllRecipesFor(RootsRecipes.RITUAL_RECIPE_TYPE.get()).stream().filter(it -> it.id().equals((Object)id)).findFirst();
    }
}

