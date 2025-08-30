/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Cloner$Factory
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.RegistryAccess$Frozen
 *  net.minecraft.core.RegistrySetBuilder
 *  net.minecraft.core.RegistrySetBuilder$PatchedRegistries
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.DataProvider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.registries.VanillaRegistries
 *  net.minecraft.world.damagesource.DamageEffects
 *  net.minecraft.world.damagesource.DamageType
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.common.EventBusSubscriber$Bus
 *  net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
 *  net.neoforged.neoforge.common.data.ExistingFileHelper
 *  net.neoforged.neoforge.data.event.GatherDataEvent
 *  net.neoforged.neoforge.registries.DataPackRegistriesHooks
 */
package elucent.rootsclassic.datagen;

import elucent.rootsclassic.datagen.server.RootsBlockTagsProvider;
import elucent.rootsclassic.datagen.server.RootsDataMapProvider;
import elucent.rootsclassic.datagen.server.RootsGLMProvider;
import elucent.rootsclassic.datagen.server.RootsItemTagsProvider;
import elucent.rootsclassic.datagen.server.RootsLootsProvider;
import elucent.rootsclassic.datagen.server.RootsRecipeProvider;
import elucent.rootsclassic.registry.RootsDamageTypes;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Cloner;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistriesHooks;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD)
public class RootsDataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeServer()) {
            generator.addProvider(true, (DataProvider)new RootsLootsProvider(packOutput, lookupProvider));
            generator.addProvider(true, (DataProvider)new RootsRecipeProvider(packOutput, lookupProvider));
            generator.addProvider(true, (DataProvider)new RootsGLMProvider(packOutput, lookupProvider));
            RootsBlockTagsProvider provider = new RootsBlockTagsProvider(packOutput, lookupProvider, helper);
            generator.addProvider(true, (DataProvider)provider);
            generator.addProvider(true, (DataProvider)new RootsItemTagsProvider(packOutput, lookupProvider, provider.contentsGetter(), helper));
            generator.addProvider(true, (DataProvider)new RootsDataMapProvider(packOutput, lookupProvider));
            generator.addProvider(true, (DataProvider)new DatapackBuiltinEntriesProvider(packOutput, CompletableFuture.supplyAsync(RootsDataGen::getProvider), Set.of("rootsclassic")));
        }
    }

    private static RegistrySetBuilder.PatchedRegistries getProvider() {
        RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        registryBuilder.add(Registries.DAMAGE_TYPE, context -> {
            context.register(RootsDamageTypes.GENERIC, (Object)new DamageType("rootsclassic.generic", 0.0f));
            context.register(RootsDamageTypes.FIRE, (Object)new DamageType("rootsclassic.fire", 0.1f, DamageEffects.BURNING));
            context.register(RootsDamageTypes.WITHER, (Object)new DamageType("rootsclassic.wither", 0.0f));
            context.register(RootsDamageTypes.CACTUS, (Object)new DamageType("rootsclassic.cactus", 0.1f));
        });
        RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries((Registry)BuiltInRegistries.REGISTRY);
        Cloner.Factory cloner$factory = new Cloner.Factory();
        DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments((arg_0, arg_1) -> ((Cloner.Factory)cloner$factory).addCodec(arg_0, arg_1)));
        return registryBuilder.buildPatch((RegistryAccess)regAccess, VanillaRegistries.createLookup(), cloner$factory);
    }
}

