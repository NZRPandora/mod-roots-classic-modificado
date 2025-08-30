/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.neoforged.neoforge.common.conditions.ICondition
 *  net.neoforged.neoforge.common.data.DataMapProvider
 *  net.neoforged.neoforge.common.data.DataMapProvider$Builder
 *  net.neoforged.neoforge.registries.datamaps.builtin.Compostable
 *  net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps
 */
package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.registry.RootsRegistry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class RootsDataMapProvider
extends DataMapProvider {
    public RootsDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    protected void gather() {
        float LEAVES = 0.3f;
        float FLOWER = 0.65f;
        DataMapProvider.Builder compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.add(RootsRegistry.BLACKCURRANT, (Object)new Compostable(0.3f), false, new ICondition[0]);
        compostables.add(RootsRegistry.REDCURRANT, (Object)new Compostable(0.3f), false, new ICondition[0]);
        compostables.add(RootsRegistry.WHITECURRANT, (Object)new Compostable(0.3f), false, new ICondition[0]);
        compostables.add(RootsRegistry.NIGHTSHADE, (Object)new Compostable(0.65f), false, new ICondition[0]);
        compostables.add(RootsRegistry.ELDERBERRY, (Object)new Compostable(0.65f), false, new ICondition[0]);
    }
}

