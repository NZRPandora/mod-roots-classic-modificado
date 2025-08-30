/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.fml.IExtensionPoint
 *  net.neoforged.fml.ModContainer
 *  net.neoforged.fml.common.Mod
 *  net.neoforged.fml.config.IConfigSpec
 *  net.neoforged.fml.config.ModConfig$Type
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.neoforge.client.gui.ConfigurationScreen
 *  net.neoforged.neoforge.client.gui.IConfigScreenFactory
 *  net.neoforged.neoforge.common.NeoForge
 *  org.slf4j.Logger
 */
package elucent.rootsclassic;

import com.mojang.logging.LogUtils;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.ui.ManaBarEvent;
import elucent.rootsclassic.component.ComponentRegistry;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.event.ComponentSpellsEvent;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.recipe.RootsReloadManager;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.research.ResearchManager;
import elucent.rootsclassic.ritual.RitualRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(value="rootsclassic")
public class Roots {
    public static final Logger LOGGER = LogUtils.getLogger();

    public Roots(IEventBus eventBus, Dist dist, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, (IConfigSpec)RootsConfig.commonSpec);
        eventBus.addListener(this::setup);
        RitualRegistry.RITUALS.register(eventBus);
        ComponentRegistry.COMPONENTS.register(eventBus);
        RootsComponents.COMPONENT_TYPE.register(eventBus);
        RootsRegistry.BLOCKS.register(eventBus);
        RootsRegistry.ITEMS.register(eventBus);
        RootsRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
        RootsRegistry.CREATIVE_MODE_TABS.register(eventBus);
        RootsEntities.ENTITY_TYPES.register(eventBus);
        RootsRecipes.RECIPE_SERIALIZERS.register(eventBus);
        RootsRecipes.RECIPE_TYPES.register(eventBus);
        DropModifier.GLM.register(eventBus);
        ParticleRegistry.PARTICLE_TYPES.register(eventBus);
        RootsAttachments.ATTACHMENT_TYPES.register(eventBus);
        NeoForge.EVENT_BUS.register((Object)new RootsReloadManager());
        NeoForge.EVENT_BUS.register((Object)new ComponentSpellsEvent());
        eventBus.addListener(RootsEntities::registerEntityAttributes);
        eventBus.addListener(RootsEntities::onSpawnPlacementRegisterEvent);
        if (dist.isClient()) {
            container.registerConfig(ModConfig.Type.CLIENT, (IConfigSpec)RootsConfig.clientSpec);
            container.registerExtensionPoint(IConfigScreenFactory.class, (IExtensionPoint)((IConfigScreenFactory)ConfigurationScreen::new));
            NeoForge.EVENT_BUS.addListener(ManaBarEvent::clientTickEnd);
            eventBus.addListener(ManaBarEvent::onRegisterLayer);
            eventBus.addListener(ClientHandler::onClientSetup);
            eventBus.addListener(ClientHandler::registerEntityRenders);
            eventBus.addListener(ClientHandler::registerLayerDefinitions);
            eventBus.addListener(ClientHandler::registerItemColors);
            eventBus.addListener(ClientHandler::registerParticleFactories);
            NeoForge.EVENT_BUS.addListener(ResearchManager::onRecipesUpdated);
        }
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(MutagenManager::reload);
    }
}

