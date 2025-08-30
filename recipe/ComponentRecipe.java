/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.NonNullList
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeInput
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.Level
 *  net.neoforged.neoforge.common.util.RecipeMatcher
 */
package elucent.rootsclassic.recipe;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.item.powder.SpellPowderItem;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.ArrayList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

public class ComponentRecipe
implements Recipe<RecipeInput> {
    private static final int MAX_INGREDIENTS = 4;
    private final ResourceLocation effectResult;
    private final String group;
    private final ItemStack recipeOutput;
    private final NonNullList<Ingredient> materials;
    private final boolean needsMixin;

    public ComponentRecipe(ResourceLocation effect, String group, ItemStack result, NonNullList<Ingredient> materials, boolean needsMixin) {
        this.effectResult = effect;
        this.group = group;
        this.recipeOutput = result;
        this.materials = materials;
        this.needsMixin = needsMixin;
    }

    public ResourceLocation getEffectResult() {
        return this.effectResult;
    }

    public RecipeSerializer<?> getSerializer() {
        return RootsRecipes.COMPONENT_SERIALIZER.get();
    }

    public String getGroup() {
        return this.group;
    }

    public ItemStack assemble(RecipeInput inventory, HolderLookup.Provider provider) {
        ItemStack outputStack = this.getResultItem(provider);
        if (outputStack.getItem() instanceof SpellPowderItem) {
            SpellPowderItem.createData(outputStack, this.getEffectResult(), inventory);
        }
        return outputStack;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.recipeOutput.copy();
    }

    public ItemStack getResultItem() {
        return this.recipeOutput.copy();
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.materials;
    }

    public RecipeType<?> getType() {
        return RootsRecipes.COMPONENT_RECIPE_TYPE.get();
    }

    public boolean isSpecial() {
        return true;
    }

    public boolean needsMixin() {
        return this.needsMixin;
    }

    public boolean matches(RecipeInput recipeInput, Level levelAccessor) {
        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
        int i = 0;
        for (int j = 0; j < recipeInput.size(); ++j) {
            ItemStack stack = recipeInput.getItem(j);
            if (stack.isEmpty() || this.isSupplementItem(stack, levelAccessor.registryAccess())) continue;
            ++i;
            inputs.add(stack);
        }
        return i == this.materials.size() && RecipeMatcher.findMatches(inputs, this.materials) != null;
    }

    private boolean isSupplementItem(ItemStack stack, RegistryAccess access) {
        if (this.getResultItem((HolderLookup.Provider)access).getItem() instanceof SpellPowderItem) {
            return stack.getItem() == RootsRegistry.OLD_ROOT.get() || stack.getItem() == RootsRegistry.VERDANT_SPRIG.get() || stack.getItem() == RootsRegistry.INFERNAL_BULB.get() || stack.getItem() == RootsRegistry.DRAGONS_EYE.get() || stack.getItem() == Items.GLOWSTONE_DUST || stack.getItem() == Items.REDSTONE || stack.getItem() == Items.GUNPOWDER;
        }
        return false;
    }

    public static int getModifierCapacity(RecipeInput inventory) {
        int maxCapacity = -1;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == RootsRegistry.OLD_ROOT.get() && maxCapacity < 0) {
                maxCapacity = 0;
            }
            if (stack.getItem() == RootsRegistry.VERDANT_SPRIG.get() && maxCapacity < 1) {
                maxCapacity = 1;
            }
            if (stack.getItem() == RootsRegistry.INFERNAL_BULB.get() && maxCapacity < 2) {
                maxCapacity = 2;
            }
            if (stack.getItem() != RootsRegistry.DRAGONS_EYE.get() || maxCapacity >= 10) continue;
            maxCapacity = 10;
        }
        return maxCapacity;
    }

    public static int getModifierCount(RecipeInput inventory) {
        int count = 0;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == Items.GLOWSTONE_DUST) {
                ++count;
                continue;
            }
            if (stack.getItem() == Items.REDSTONE) {
                ++count;
                continue;
            }
            if (stack.getItem() != Items.GUNPOWDER) continue;
            ++count;
        }
        return count;
    }

    public String toString() {
        return "ComponentRecipe [effectResult=" + String.valueOf(this.effectResult) + ", recipeOutput=" + String.valueOf(this.recipeOutput) + "]";
    }

    public static class SerializeComponentRecipe
    implements RecipeSerializer<ComponentRecipe> {
        private static final MapCodec<ComponentRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group((App)ResourceLocation.CODEC.fieldOf("effect").forGetter(recipe -> recipe.effectResult), (App)Codec.STRING.optionalFieldOf("group", (Object)"").forGetter(recipe -> recipe.group), (App)ItemStack.STRICT_CODEC.optionalFieldOf("result", (Object)RootsRegistry.SPELL_POWDER.toStack()).forGetter(recipe -> recipe.recipeOutput), (App)Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(p_301021_ -> {
            Object[] aingredient = (Ingredient[])p_301021_.toArray(Ingredient[]::new);
            if (aingredient.length == 0) {
                return DataResult.error(() -> "No ingredients for component recipe");
            }
            return aingredient.length > 4 ? DataResult.error(() -> "Too many ingredients for component recipe. The maximum is: %s".formatted(4)) : DataResult.success((Object)NonNullList.of((Object)Ingredient.EMPTY, (Object[])aingredient));
        }, DataResult::success).forGetter(recipe -> recipe.materials), (App)Codec.BOOL.optionalFieldOf("need_mixin", (Object)true).forGetter(recipe -> recipe.needsMixin)).apply((Applicative)instance, ComponentRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, ComponentRecipe> STREAM_CODEC = StreamCodec.of(SerializeComponentRecipe::toNetwork, SerializeComponentRecipe::fromNetwork);

        public MapCodec<ComponentRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, ComponentRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static ComponentRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            ResourceLocation effectResult = buffer.readResourceLocation();
            String s = buffer.readUtf();
            ItemStack itemstack = (ItemStack)ItemStack.STREAM_CODEC.decode((Object)buffer);
            int i = buffer.readVarInt();
            NonNullList nonnulllist = NonNullList.withSize((int)i, (Object)Ingredient.EMPTY);
            for (int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, (Object)((Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode((Object)buffer)));
            }
            boolean needsMixin = buffer.readBoolean();
            return new ComponentRecipe(effectResult, s, itemstack, (NonNullList<Ingredient>)nonnulllist, needsMixin);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, ComponentRecipe recipe) {
            buffer.writeResourceLocation(recipe.effectResult);
            buffer.writeUtf(recipe.group);
            ItemStack.STREAM_CODEC.encode((Object)buffer, (Object)recipe.recipeOutput);
            buffer.writeVarInt(recipe.materials.size());
            for (Ingredient ingredient : recipe.materials) {
                Ingredient.CONTENTS_STREAM_CODEC.encode((Object)buffer, (Object)ingredient);
            }
            buffer.writeBoolean(recipe.needsMixin);
        }
    }
}

