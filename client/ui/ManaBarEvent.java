/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.player.LocalPlayer
 *  net.neoforged.neoforge.client.event.ClientTickEvent$Post
 *  net.neoforged.neoforge.client.event.RegisterGuiLayersEvent
 *  net.neoforged.neoforge.client.gui.VanillaGuiLayers
 */
package elucent.rootsclassic.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.attachment.IMana;
import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.item.IManaRelatedItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ManaBarEvent {
    public static void onRegisterLayer(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Const.MANA_LAYER, ManaBarEvent::onDrawManaBar);
    }

    private static void onDrawManaBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        boolean showBar = player.getMainHandItem().getItem() instanceof IManaRelatedItem || player.getOffhandItem().getItem() instanceof IManaRelatedItem;
        ManaAttachment capability = (ManaAttachment)player.getData(RootsAttachments.MANA);
        if (showBar && capability.getMaxMana() > 0.0f) {
            ManaBarEvent.drawManaBar(guiGraphics, mc, capability);
        }
    }

    private static void drawManaBar(GuiGraphics guiGraphics, Minecraft mc, IMana capability) {
        int manaBarOffset = (Integer)RootsConfig.CLIENT.manaBarOffset.get();
        int w = mc.getWindow().getGuiScaledWidth();
        int h = mc.getWindow().getGuiScaledHeight();
        int manaNumber = Math.round(capability.getMana());
        int maxManaNumber = Math.round(capability.getMaxMana());
        RenderSystem.enableBlend();
        int offsetX = 0;
        for (int i = 0; i < maxManaNumber; i += 4) {
            guiGraphics.blitSprite(Const.MANA_CONTAINER, w / 2 + 10 + offsetX, h - manaBarOffset, 9, 9);
            offsetX += 8;
        }
        offsetX = 0;
        while (manaNumber > 0) {
            if (manaNumber >= 4) {
                guiGraphics.blitSprite(Const.MANA_FULL, w / 2 + 10 + offsetX, h - manaBarOffset, 9, 9);
                manaNumber -= 4;
            } else if (manaNumber == 3) {
                guiGraphics.blitSprite(Const.MANA_ALMOST_FULL, w / 2 + 10 + offsetX, h - manaBarOffset, 9, 9);
                manaNumber = 0;
            } else if (manaNumber == 2) {
                guiGraphics.blitSprite(Const.MANA_HALF, w / 2 + 10 + offsetX, h - manaBarOffset, 9, 9);
                manaNumber = 0;
            } else if (manaNumber == 1) {
                guiGraphics.blitSprite(Const.MANA_ALMOST_EMPTY, w / 2 + 10 + offsetX, h - manaBarOffset, 9, 9);
                manaNumber = 0;
            }
            offsetX += 8;
        }
        RenderSystem.disableBlend();
    }

    public static void clientTickEnd(ClientTickEvent.Post event) {
        ++ClientInfo.ticksInGame;
    }
}

