/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.critereon.ItemPredicate$Builder
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition
 *  net.minecraft.world.level.storage.loot.predicates.LootItemCondition
 *  net.minecraft.world.level.storage.loot.predicates.LootItemCondition$Builder
 *  net.minecraft.world.level.storage.loot.predicates.MatchTool
 *  net.neoforged.neoforge.common.Tags$Items
 *  net.neoforged.neoforge.common.conditions.ICondition
 *  net.neoforged.neoforge.common.data.GlobalLootModifierProvider
 *  net.neoforged.neoforge.common.loot.IGlobalLootModifier
 */
package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.lootmodifiers.DropModifier;
import java.util.concurrent.CompletableFuture;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

public class RootsGLMProvider
extends GlobalLootModifierProvider {
    public RootsGLMProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, "rootsclassic");
    }

    protected void start() {
        this.add("rootsclassic_drops", (IGlobalLootModifier)new DropModifier.BlockDropModifier(new LootItemCondition[]{InvertedLootItemCondition.invert((LootItemCondition.Builder)MatchTool.toolMatches((ItemPredicate.Builder)ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR))).build()}), new ICondition[0]);
    }
}

