/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.WritableRegistry
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.loot.BlockLootSubProvider
 *  net.minecraft.data.loot.EntityLootSubProvider
 *  net.minecraft.data.loot.LootTableProvider
 *  net.minecraft.data.loot.LootTableProvider$SubProviderEntry
 *  net.minecraft.util.ProblemReporter$Collector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.flag.FeatureFlags
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.properties.DoubleBlockHalf
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.LootTable$Builder
 *  net.minecraft.world.level.storage.loot.ValidationContext
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.neoforged.neoforge.registries.DeferredHolder
 */
package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

public class RootsLootsProvider
extends LootTableProvider {
    public RootsLootsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(RootsBlockLoot::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(RootsEntityLoot::new, LootContextParamSets.ENTITY)), lookupProvider);
    }

    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
        super.validate(writableregistry, validationcontext, problemreporter$collector);
    }

    private static class RootsEntityLoot
    extends EntityLootSubProvider {
        protected RootsEntityLoot(HolderLookup.Provider provider) {
            super(FeatureFlags.REGISTRY.allFlags(), provider);
        }

        public void generate() {
            this.add((EntityType)RootsEntities.PHANTOM_SKELETON.get(), LootTable.lootTable());
        }

        protected boolean canHaveLootTable(EntityType<?> entityType) {
            return entityType.getCategory() != MobCategory.MISC;
        }

        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return RootsEntities.ENTITY_TYPES.getEntries().stream().map(DeferredHolder::get);
        }
    }

    private static class RootsBlockLoot
    extends BlockLootSubProvider {
        protected RootsBlockLoot(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        public void generate() {
            this.dropSelf((Block)RootsRegistry.MORTAR.get());
            this.dropSelf((Block)RootsRegistry.ALTAR.get());
            this.dropSelf((Block)RootsRegistry.BRAZIER.get());
            this.dropSelf((Block)RootsRegistry.IMBUER.get());
            this.dropSelf((Block)RootsRegistry.MUNDANE_STANDING_STONE.get());
            this.dropSelf((Block)RootsRegistry.MIDNIGHT_BLOOM.get());
            this.dropSelf((Block)RootsRegistry.FLARE_ORCHID.get());
            this.dropSelf((Block)RootsRegistry.RADIANT_DAISY.get());
            this.add((Block)RootsRegistry.ATTUNED_STANDING_STONE.get(), this::registerStandingStone);
            this.add((Block)RootsRegistry.VACUUM_STANDING_STONE.get(), this::registerStandingStone);
            this.add((Block)RootsRegistry.REPULSOR_STANDING_STONE.get(), this::registerStandingStone);
            this.add((Block)RootsRegistry.ACCELERATOR_STANDING_STONE.get(), this::registerStandingStone);
            this.add((Block)RootsRegistry.AESTHETIC_STANDING_STONE.get(), this::registerStandingStone);
            this.add((Block)RootsRegistry.ENTANGLER_STANDING_STONE.get(), this::registerStandingStone);
            this.add((Block)RootsRegistry.IGNITER_STANDING_STONE.get(), this::registerStandingStone);
            this.add((Block)RootsRegistry.GROWER_STANDING_STONE.get(), this::registerStandingStone);
            this.add((Block)RootsRegistry.HEALER_STANDING_STONE.get(), this::registerStandingStone);
        }

        public LootTable.Builder registerStandingStone(Block standingStone) {
            return this.createSinglePropConditionTable(standingStone, (Property)AttunedStandingStoneBlock.HALF, (Comparable)DoubleBlockHalf.LOWER);
        }

        protected Iterable<Block> getKnownBlocks() {
            return RootsRegistry.BLOCKS.getEntries().stream().map(holder -> (Block)holder.value())::iterator;
        }
    }
}

