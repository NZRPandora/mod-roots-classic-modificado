/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nonnull
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.NetherWartBlock
 *  net.minecraft.world.level.block.TallGrassBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.storage.loot.LootContext
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.level.storage.loot.predicates.LootItemCondition
 *  net.neoforged.neoforge.common.loot.IGlobalLootModifier
 *  net.neoforged.neoforge.common.loot.LootModifier
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries$Keys
 */
package elucent.rootsclassic.lootmodifiers;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DropModifier {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create((ResourceKey)NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, (String)"rootsclassic");
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ROOTSCLASSIC_DROPS = GLM.register("rootsclassic_drops", BlockDropModifier.CODEC);

    public static class BlockDropModifier
    extends LootModifier {
        public static final Supplier<MapCodec<BlockDropModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(inst -> BlockDropModifier.codecStart((RecordCodecBuilder.Instance)inst).apply((Applicative)inst, BlockDropModifier::new)));

        public BlockDropModifier(LootItemCondition[] lootConditions) {
            super(lootConditions);
        }

        @Nonnull
        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            if (context.hasParam(LootContextParams.BLOCK_STATE)) {
                BlockState state = (BlockState)context.getParamOrNull(LootContextParams.BLOCK_STATE);
                Block block = state.getBlock();
                RandomSource rand = context.getRandom();
                if (block instanceof TallGrassBlock && (Integer)RootsConfig.COMMON.oldRootDropChance.get() > 0 && rand.nextInt(((Integer)RootsConfig.COMMON.oldRootDropChance.get()).intValue()) == 0) {
                    generatedLoot.add((Object)new ItemStack((ItemLike)RootsRegistry.OLD_ROOT.get(), 1));
                }
                if ((block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES || block == Blocks.BEETROOTS) && ((CropBlock)block).isMaxAge(state) && (Integer)RootsConfig.COMMON.verdantSprigDropChance.get() > 0 && rand.nextInt(((Integer)RootsConfig.COMMON.verdantSprigDropChance.get()).intValue()) == 0) {
                    generatedLoot.add((Object)new ItemStack((ItemLike)RootsRegistry.VERDANT_SPRIG.get(), 1));
                }
                if (block == Blocks.NETHER_WART && (Integer)state.getValue((Property)NetherWartBlock.AGE) == 3 && (Integer)RootsConfig.COMMON.infernalStemDropChance.get() > 0 && rand.nextInt(((Integer)RootsConfig.COMMON.infernalStemDropChance.get()).intValue()) == 0) {
                    generatedLoot.add((Object)new ItemStack((ItemLike)RootsRegistry.INFERNAL_BULB.get(), 1));
                }
                if (block == Blocks.CHORUS_FLOWER && (Integer)RootsConfig.COMMON.dragonsEyeDropChance.get() > 0 && rand.nextInt(((Integer)RootsConfig.COMMON.dragonsEyeDropChance.get()).intValue()) == 0) {
                    generatedLoot.add((Object)new ItemStack((ItemLike)RootsRegistry.DRAGONS_EYE.get(), 1));
                }
                if (block instanceof LeavesBlock && generatedLoot.stream().noneMatch(stack -> {
                    BlockItem blockItem;
                    Item patt0$temp = stack.getItem();
                    return patt0$temp instanceof BlockItem && (blockItem = (BlockItem)patt0$temp).getBlock() == block;
                }) && (Integer)RootsConfig.COMMON.berriesDropChance.get() > 0 && rand.nextInt(((Integer)RootsConfig.COMMON.berriesDropChance.get()).intValue()) == 0) {
                    generatedLoot.add((Object)new ItemStack((Holder)BuiltInRegistries.ITEM.getTag(RootsTags.BERRIES).flatMap(tag -> tag.getRandomElement(rand)).orElse(RootsRegistry.ELDERBERRY)));
                }
            }
            return generatedLoot;
        }

        public MapCodec<? extends IGlobalLootModifier> codec() {
            return ROOTSCLASSIC_DROPS.get();
        }
    }
}

