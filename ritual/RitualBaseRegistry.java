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
package elucent.rootsclassic.ritual;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.ritual.RitualEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD, modid="rootsclassic")
public class RitualBaseRegistry {
    public static final ResourceKey<Registry<RitualEffect>> RITUAL_KEY = ResourceKey.createRegistryKey((ResourceLocation)Const.modLoc("ritual"));
    public static final Registry<RitualEffect> RITUALS = new RegistryBuilder(RITUAL_KEY).sync(true).create();

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        event.register(RITUALS);
    }
}

