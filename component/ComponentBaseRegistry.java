/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.common.EventBusSubscriber$Bus
 *  net.neoforged.neoforge.registries.NewRegistryEvent
 *  net.neoforged.neoforge.registries.RegistryBuilder
 */
package elucent.rootsclassic.component;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD, modid="rootsclassic")
public class ComponentBaseRegistry {
    public static final ResourceKey<Registry<ComponentBase>> COMPONENTS_KEY = ResourceKey.createRegistryKey((ResourceLocation)Const.modLoc("component"));
    public static final Registry<ComponentBase> COMPONENTS = new RegistryBuilder(COMPONENTS_KEY).create();

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        event.register(COMPONENTS);
    }
}

