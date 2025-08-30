/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package elucent.rootsclassic.registry;

import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.RitualRecipe;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RootsRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create((Registry)BuiltInRegistries.RECIPE_SERIALIZER, (String)"rootsclassic");
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create((Registry)BuiltInRegistries.RECIPE_TYPE, (String)"rootsclassic");
    public static final Supplier<RecipeType<ComponentRecipe>> COMPONENT_RECIPE_TYPE = RECIPE_TYPES.register("component", () -> new RecipeType<ComponentRecipe>(){});
    public static final Supplier<ComponentRecipe.SerializeComponentRecipe> COMPONENT_SERIALIZER = RECIPE_SERIALIZERS.register("component", ComponentRecipe.SerializeComponentRecipe::new);
    public static final Supplier<RecipeType<RitualRecipe>> RITUAL_RECIPE_TYPE = RECIPE_TYPES.register("ritual", () -> new RecipeType<RitualRecipe>(){});
    public static final Supplier<RitualRecipe.SerializeRitualRecipe> RITUAL_SERIALIZER = RECIPE_SERIALIZERS.register("ritual", RitualRecipe.SerializeRitualRecipe::new);
}

