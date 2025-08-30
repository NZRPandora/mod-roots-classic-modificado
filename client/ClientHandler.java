/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.renderer.item.ItemProperties
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
 *  net.neoforged.neoforge.client.event.EntityRenderersEvent$RegisterLayerDefinitions
 *  net.neoforged.neoforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.neoforged.neoforge.client.event.RegisterColorHandlersEvent$Item
 *  net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent
 */
package elucent.rootsclassic.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import elucent.rootsclassic.client.model.WildwoodArmorModel;
import elucent.rootsclassic.client.particles.MagicAltarLineParticleData;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.client.renderer.block.AltarBER;
import elucent.rootsclassic.client.renderer.block.BrazierBER;
import elucent.rootsclassic.client.renderer.block.ImbuerBER;
import elucent.rootsclassic.client.renderer.block.MortarBER;
import elucent.rootsclassic.client.renderer.entity.AcceleratorRenderer;
import elucent.rootsclassic.client.renderer.entity.PhantomSkeletonRenderer;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class ClientHandler {
    public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(Const.modLoc("main"), "sylvan_armor");
    public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(Const.modLoc("main"), "wildwood_armor");

    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemProperties.register((Item)((Item)RootsRegistry.STAFF.get()), (ResourceLocation)ResourceLocation.withDefaultNamespace((String)"imbued"), (stack, world, livingEntity, unused) -> stack.has(RootsComponents.SPELL) ? 1.0f : 0.0f);
    }

    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType)RootsRegistry.MORTAR_TILE.get(), MortarBER::new);
        event.registerBlockEntityRenderer((BlockEntityType)RootsRegistry.IMBUER_TILE.get(), ImbuerBER::new);
        event.registerBlockEntityRenderer((BlockEntityType)RootsRegistry.ALTAR_TILE.get(), AltarBER::new);
        event.registerBlockEntityRenderer((BlockEntityType)RootsRegistry.BRAZIER_TILE.get(), BrazierBER::new);
        event.registerEntityRenderer((EntityType)RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonRenderer::new);
        event.registerEntityRenderer((EntityType)RootsEntities.ENTITY_ACCELERATOR.get(), AcceleratorRenderer::new);
        event.registerEntityRenderer((EntityType)RootsEntities.TILE_ACCELERATOR.get(), AcceleratorRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SYLVAN_ARMOR, SylvanArmorModel::createArmorDefinition);
        event.registerLayerDefinition(WILDWOOD_ARMOR, WildwoodArmorModel::createArmorDefinition);
    }

    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            ComponentBase comp;
            SpellData data;
            ResourceLocation compName;
            if (stack.has(RootsComponents.SPELL) && stack.getItem() instanceof StaffItem && (compName = ResourceLocation.tryParse((String)(data = (SpellData)stack.get(RootsComponents.SPELL)).effect())) != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(compName)) != null) {
                if (tintIndex == 2) {
                    return RootsUtil.intColor((int)comp.primaryColor.x, (int)comp.primaryColor.y, (int)comp.primaryColor.z);
                }
                if (tintIndex == 1) {
                    return RootsUtil.intColor((int)comp.secondaryColor.x, (int)comp.secondaryColor.y, (int)comp.secondaryColor.z);
                }
            }
            return RootsUtil.intColor(255, 255, 255);
        }, new ItemLike[]{(ItemLike)RootsRegistry.STAFF.get()});
        event.register((stack, tintIndex) -> {
            ComponentBase comp;
            ResourceLocation compName;
            SpellData selectedSpell;
            String effect;
            if (stack.getItem() instanceof CrystalStaffItem && stack.has(RootsComponents.SPELLS) && (effect = (selectedSpell = CrystalStaffItem.getSelectedSpell(stack)).effect()) != null && (compName = ResourceLocation.tryParse((String)effect)) != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(compName)) != null) {
                if (tintIndex == 2) {
                    return RootsUtil.intColor((int)comp.primaryColor.x, (int)comp.primaryColor.y, (int)comp.primaryColor.z);
                }
                if (tintIndex == 1) {
                    return RootsUtil.intColor((int)comp.secondaryColor.x, (int)comp.secondaryColor.y, (int)comp.secondaryColor.z);
                }
            }
            return RootsUtil.intColor(255, 255, 255);
        }, new ItemLike[]{(ItemLike)RootsRegistry.CRYSTAL_STAFF.get()});
    }

    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.MAGIC_TYPE.get(), MagicParticleData::new);
        event.registerSpriteSet(ParticleRegistry.MAGIC_AURA_TYPE.get(), MagicAuraParticleData::new);
        event.registerSpriteSet(ParticleRegistry.MAGIC_ALTAR_TYPE.get(), MagicAltarParticleData::new);
        event.registerSpriteSet(ParticleRegistry.MAGIC_ALTAR_LINE_TYPE.get(), MagicAltarLineParticleData::new);
        event.registerSpriteSet(ParticleRegistry.MAGIC_LINE_TYPE.get(), MagicLineParticleData::new);
    }
}

