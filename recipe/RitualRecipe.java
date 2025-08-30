/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Function7
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeInput
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.Nullable
 */
package elucent.rootsclassic.recipe;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function7;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.ritual.RitualBaseRegistry;
import elucent.rootsclassic.ritual.RitualEffect;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.util.RootsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class RitualRecipe
implements Recipe<RecipeInput> {
    private final NonNullList<Ingredient> materials;
    private final NonNullList<Ingredient> incenses;
    public final int level;
    public final String color;
    private final int colorInt;
    public final String secondaryColor;
    private final int secondaryColorInt;
    public final ResourceLocation effectId;
    public final RitualEffect effect;
    @Nullable
    public final CompoundTag effectConfig;

    public RitualRecipe(ResourceLocation effectId, @Nullable CompoundTag effectConfig, NonNullList<Ingredient> materials, NonNullList<Ingredient> incenses, int level, String color, String secondaryColor) {
        this.materials = materials;
        this.incenses = incenses;
        this.level = level;
        this.effectId = effectId;
        this.effect = (RitualEffect)RitualBaseRegistry.RITUALS.get(effectId);
        this.effectConfig = effectConfig;
        this.color = color;
        this.colorInt = RootsUtil.intColorFromHexString(color);
        this.secondaryColor = secondaryColor;
        this.secondaryColorInt = RootsUtil.intColorFromHexString(secondaryColor);
    }

    public RitualRecipe(ResourceLocation effectId, Optional<CompoundTag> optionalConfig, NonNullList<Ingredient> materials, NonNullList<Ingredient> incenses, int level, String color, String secondaryColor) {
        this(effectId, (CompoundTag)optionalConfig.orElse(null), materials, incenses, level, color, secondaryColor);
    }

    public RecipeSerializer<?> getSerializer() {
        return RootsRecipes.RITUAL_SERIALIZER.get();
    }

    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return this.getResultItem(provider);
    }

    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.effect.getResult(this.effectConfig, provider).copy();
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.materials;
    }

    public RecipeType<?> getType() {
        return RootsRecipes.RITUAL_RECIPE_TYPE.get();
    }

    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    public boolean matches(RecipeInput recipeInput, Level level) {
        return false;
    }

    public List<Ingredient> getIncenses() {
        return this.incenses;
    }

    public MutableComponent getInfoText() {
        return this.effect.getInfoText(this.effectConfig);
    }

    public int getColorInt() {
        return this.colorInt;
    }

    public int getSecondaryColorInt() {
        return this.secondaryColorInt;
    }

    public boolean incenseMatches(Level levelAccessor, BlockPos pos) {
        ArrayList<ItemStack> incenseFromNearby = new ArrayList<ItemStack>();
        List<BrazierBlockEntity> braziers = RitualPillars.getRecipeBraziers(levelAccessor, pos);
        for (BrazierBlockEntity brazier : braziers) {
            if (brazier.getHeldItem().isEmpty()) continue;
            incenseFromNearby.add(brazier.getHeldItem());
        }
        return this.effect.incenseMatches(incenseFromNearby, this);
    }

    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
        this.effect.doEffect(levelAccessor, pos, inventory, incenses, this.effectConfig);
    }

    public String toString() {
        return "RitualRecipe [level=" + this.level + ", effect=" + String.valueOf(RitualBaseRegistry.RITUALS.getKey((Object)this.effect)) + ", config=" + String.valueOf(this.effectConfig) + "]";
    }

    public static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(final StreamCodec<? super B, T1> pCodec1, final Function<C, T1> pGetter1, final StreamCodec<? super B, T2> pCodec2, final Function<C, T2> pGetter2, final StreamCodec<? super B, T3> pCodec3, final Function<C, T3> pGetter3, final StreamCodec<? super B, T4> pCodec4, final Function<C, T4> pGetter4, final StreamCodec<? super B, T5> pCodec5, final Function<C, T5> pGetter5, final StreamCodec<? super B, T6> pCodec6, final Function<C, T6> pGetter6, final StreamCodec<? super B, T7> pCodec7, final Function<C, T7> pGetter7, final Function7<T1, T2, T3, T4, T5, T6, T7, C> pFactory) {
        return new StreamCodec<B, C>(){

            public C decode(B p_330310_) {
                Object t1 = pCodec1.decode(p_330310_);
                Object t2 = pCodec2.decode(p_330310_);
                Object t3 = pCodec3.decode(p_330310_);
                Object t4 = pCodec4.decode(p_330310_);
                Object t5 = pCodec5.decode(p_330310_);
                Object t6 = pCodec6.decode(p_330310_);
                Object t7 = pCodec7.decode(p_330310_);
                return pFactory.apply(t1, t2, t3, t4, t5, t6, t7);
            }

            public void encode(B p_332052_, C p_331912_) {
                pCodec1.encode(p_332052_, pGetter1.apply(p_331912_));
                pCodec2.encode(p_332052_, pGetter2.apply(p_331912_));
                pCodec3.encode(p_332052_, pGetter3.apply(p_331912_));
                pCodec4.encode(p_332052_, pGetter4.apply(p_331912_));
                pCodec5.encode(p_332052_, pGetter5.apply(p_331912_));
                pCodec6.encode(p_332052_, pGetter6.apply(p_331912_));
                pCodec7.encode(p_332052_, pGetter7.apply(p_331912_));
            }
        };
    }

    public static class SerializeRitualRecipe
    implements RecipeSerializer<RitualRecipe> {
        private static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group((App)ResourceLocation.CODEC.fieldOf("effect").forGetter(recipe -> recipe.effectId), (App)CompoundTag.CODEC.optionalFieldOf("effectConfig").forGetter(recipe -> Optional.ofNullable(recipe.effectConfig)), (App)Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(p_301021_ -> {
            Object[] aingredient = (Ingredient[])p_301021_.toArray(Ingredient[]::new);
            if (aingredient.length == 0) {
                return DataResult.error(() -> "No ingredients for ritual recipe");
            }
            return DataResult.success((Object)NonNullList.of((Object)Ingredient.EMPTY, (Object[])aingredient));
        }, DataResult::success).forGetter(recipe -> recipe.materials), (App)Ingredient.CODEC_NONEMPTY.listOf().fieldOf("incenses").flatXmap(p_301021_ -> {
            Object[] aingredient = (Ingredient[])p_301021_.toArray(Ingredient[]::new);
            if (aingredient.length > 4) {
                return DataResult.error(() -> "Too many ingredients for ritual recipe, the max is 4");
            }
            return DataResult.success((Object)NonNullList.of((Object)Ingredient.EMPTY, (Object[])aingredient));
        }, DataResult::success).forGetter(recipe -> recipe.incenses), (App)Codec.INT.fieldOf("level").validate(level -> {
            if (level < 0 || level > 2) {
                return DataResult.error(() -> "Level must be between 0 and 3, you tried " + level);
            }
            return DataResult.success((Object)level);
        }).forGetter(recipe -> recipe.level), (App)Codec.STRING.fieldOf("color").forGetter(recipe -> recipe.color), (App)Codec.STRING.optionalFieldOf("secondaryColor", (Object)"").forGetter(recipe -> recipe.secondaryColor)).apply((Applicative)instance, RitualRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> STREAM_CODEC = StreamCodec.of(SerializeRitualRecipe::toNetwork, SerializeRitualRecipe::fromNetwork);

        public MapCodec<RitualRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static RitualRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            ResourceLocation effect = buffer.readResourceLocation();
            Optional effectConfig = (Optional)ByteBufCodecs.optional((StreamCodec)ByteBufCodecs.COMPOUND_TAG).decode((Object)buffer);
            int size = buffer.readVarInt();
            NonNullList ingredients = NonNullList.withSize((int)size, (Object)Ingredient.EMPTY);
            for (int i = 0; i < size; ++i) {
                ingredients.set(i, (Object)((Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode((Object)buffer)));
            }
            size = buffer.readVarInt();
            NonNullList incenses = NonNullList.create();
            if (size > 0) {
                for (int i = 0; i < size; ++i) {
                    incenses.add((Object)((Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode((Object)buffer)));
                }
            }
            int level = buffer.readVarInt();
            String color = buffer.readUtf();
            String secondaryColor = buffer.readUtf();
            return new RitualRecipe(effect, effectConfig, (NonNullList<Ingredient>)ingredients, (NonNullList<Ingredient>)incenses, level, color, secondaryColor);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, RitualRecipe recipe) {
            int i;
            buffer.writeResourceLocation(recipe.effectId);
            ByteBufCodecs.optional((StreamCodec)ByteBufCodecs.COMPOUND_TAG).encode((Object)buffer, Optional.ofNullable(recipe.effectConfig));
            buffer.writeVarInt(recipe.materials.size());
            for (i = 0; i < recipe.materials.size(); ++i) {
                Ingredient.CONTENTS_STREAM_CODEC.encode((Object)buffer, (Object)((Ingredient)recipe.materials.get(i)));
            }
            buffer.writeVarInt(recipe.incenses.size());
            if (recipe.incenses.size() > 0) {
                for (i = 0; i < recipe.incenses.size(); ++i) {
                    Ingredient.CONTENTS_STREAM_CODEC.encode((Object)buffer, (Object)((Ingredient)recipe.incenses.get(i)));
                }
            }
            buffer.writeVarInt(recipe.level);
            buffer.writeUtf(recipe.color);
            buffer.writeUtf(recipe.secondaryColor);
        }
    }
}

