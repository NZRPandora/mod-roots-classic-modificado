/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.MeshData
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.Holder
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package elucent.rootsclassic.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.client.screen.TabletPageScreen;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.research.ResearchBase;
import elucent.rootsclassic.research.ResearchGroup;
import elucent.rootsclassic.research.ResearchManager;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TabletScreen
extends Screen {
    private int currentGroup = 0;
    private final Player player;

    public TabletScreen(Player player) {
        super((Component)Component.empty());
        this.player = player;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).has(RootsComponents.CURRENT_GROUP)) {
            this.currentGroup = (Integer)player.getItemInHand(InteractionHand.MAIN_HAND).getOrDefault(RootsComponents.CURRENT_GROUP, (Object)0);
        }
    }

    public static void openScreen(Player player) {
        Minecraft.getInstance().setScreen((Screen)new TabletScreen(player));
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float basePosX = (float)this.width / 2.0f - 92.0f;
        ResearchGroup group = null;
        ResearchBase base = null;
        for (int i = 0; i < ResearchManager.globalResearches.get((int)this.currentGroup).researches.size(); ++i) {
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)Const.TABLETGUI);
            float yShift = (float)Math.floor((double)i / 6.0);
            float xShift = i % 6;
            if (!(mouseX >= (double)(basePosX + 32.0f * xShift)) || !(mouseX < (double)(basePosX + 32.0f * xShift + 24.0f)) || !(mouseY >= (double)(32.0f + 40.0f * yShift)) || !(mouseY < (double)(32.0f + 40.0f * yShift + 24.0f))) continue;
            group = ResearchManager.globalResearches.get(this.currentGroup);
            base = ResearchManager.globalResearches.get((int)this.currentGroup).researches.get(i);
        }
        if (group != null && base != null) {
            CompoundTag persistentData = this.player.getPersistentData();
            persistentData.putString("RMOD_researchGroup", group.getName());
            persistentData.putString("RMOD_researchBase", base.getName());
            ResearchBase tempResearch = ResearchManager.getResearch(persistentData.getString("RMOD_researchGroup"), persistentData.getString("RMOD_researchBase"));
            ResearchGroup tempGroup = ResearchManager.getResearchGroup(persistentData.getString("RMOD_researchGroup"));
            persistentData.remove("RMOD_researchGroup");
            persistentData.remove("RMOD_researchBase");
            if (tempResearch != null && tempGroup != null) {
                this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                this.minecraft.setScreen((Screen)new TabletPageScreen(tempGroup, tempResearch, this.player));
            }
        }
        if (mouseX >= 32.0 && mouseX < 64.0 && mouseY >= (double)(this.height - 48) && mouseY < (double)(this.height - 32)) {
            this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            --this.currentGroup;
            if (this.currentGroup < 0) {
                this.currentGroup = ResearchManager.globalResearches.size() - 1;
            }
        }
        if (mouseX >= (double)(this.width - 64) && mouseX < (double)(this.width - 32) && mouseY >= (double)(this.height - 48) && mouseY < (double)(this.height - 32)) {
            this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            ++this.currentGroup;
            if (this.currentGroup == ResearchManager.globalResearches.size()) {
                this.currentGroup = 0;
            }
        }
        ItemStack heldStack = this.player.getItemInHand(InteractionHand.MAIN_HAND);
        if (this.currentGroup != 0) {
            heldStack.set(RootsComponents.CURRENT_GROUP, (Object)this.currentGroup);
        } else if (heldStack.has(RootsComponents.CURRENT_GROUP)) {
            heldStack.remove(RootsComponents.CURRENT_GROUP);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void drawQuad(BufferBuilder bufferBuilder, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        bufferBuilder.addVertex(x4 + 0.0f, y4 + 0.0f, 0.0f).setUv((float)minU * f, (float)(minV + maxV) * f1);
        bufferBuilder.addVertex(x3 + 0.0f, y3 + 0.0f, 0.0f).setUv((float)(minU + maxU) * f, (float)(minV + maxV) * f1);
        bufferBuilder.addVertex(x2 + 0.0f, y2 + 0.0f, 0.0f).setUv((float)(minU + maxU) * f, (float)minV * f1);
        bufferBuilder.addVertex(x1 + 0.0f, y1 + 0.0f, 0.0f).setUv((float)minU * f, (float)minV * f1);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.player.releaseUsingItem();
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        float unit = (float)this.width / 32.0f;
        if (((Boolean)RootsConfig.CLIENT.showTabletWave.get()).booleanValue()) {
            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)Const.TABLETGUI);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            for (float i = 0.0f; i < (float)this.width; i += unit) {
                float height1 = 12.0f * ((float)Math.cos(((double)ClientInfo.ticksInGame / 36.0 + (double)i / ((double)this.width / 4.0)) * Math.PI) + 1.0f);
                float height2 = 12.0f * ((float)Math.cos(((double)ClientInfo.ticksInGame / 36.0 + (double)(i + unit) / ((double)this.width / 4.0)) * Math.PI) + 1.0f);
                this.drawQuad(bufferBuilder, i, (float)this.height - (24.0f + height1), i + unit, (float)this.height - (24.0f + height2), i + unit, this.height, i, this.height, 16, 96, 16, 64);
            }
            BufferUploader.drawWithShader((MeshData)bufferBuilder.buildOrThrow());
            RenderSystem.disableBlend();
        }
        int basePosX = (int)((float)this.width / 2.0f - 92.0f);
        String researchName = "rootsclassic.research." + ResearchManager.globalResearches.get(this.currentGroup).getName();
        for (int i = 0; i < ResearchManager.globalResearches.get((int)this.currentGroup).researches.size(); ++i) {
            int yShift = (int)Math.floor((double)i / 6.0);
            int xShift = i % 6;
            guiGraphics.blit(Const.TABLETGUI, basePosX + 32 * xShift, 32 + 40 * yShift, 16, 0, 24, 24);
            if (ResearchManager.globalResearches.get((int)this.currentGroup).researches.get(i).getIcon() != null) {
                guiGraphics.renderItem(ResearchManager.globalResearches.get((int)this.currentGroup).researches.get(i).getIcon(), basePosX + xShift * 32 + 4, 32 + 40 * yShift + 4);
            }
            if (mouseX < basePosX + 32 * xShift || mouseX >= basePosX + 32 * xShift + 24 || mouseY < 32 + 40 * yShift || mouseY >= 32 + 40 * yShift + 24) continue;
            String name = I18n.get((String)(researchName + "." + ResearchManager.globalResearches.get((int)this.currentGroup).researches.get(i).getName()), (Object[])new Object[0]);
            guiGraphics.drawString(this.font, name, (float)(basePosX + 32 * xShift + 12) - (float)this.font.width(name) / 2.0f, (float)(32 + 40 * yShift + 25), RootsUtil.intColor(255, 255, 255), true);
        }
        String formattedName = I18n.get((String)researchName, (Object[])new Object[0]);
        guiGraphics.drawString(this.font, formattedName, (float)this.width / 2.0f - (float)this.font.width(formattedName) / 2.0f, (float)this.height - 16.0f, RootsUtil.intColor(255, 255, 255), true);
        if (mouseX >= 32 && mouseX < 64 && mouseY >= this.height - 48 && mouseY < this.height - 32) {
            guiGraphics.blit(Const.TABLETGUI, 32, this.height - 48, 32, 80, 32, 16);
        } else {
            guiGraphics.blit(Const.TABLETGUI, 32, this.height - 48, 32, 64, 32, 16);
        }
        if (mouseX >= this.width - 64 && mouseX < this.width - 32 && mouseY >= this.height - 48 && mouseY < this.height - 32) {
            guiGraphics.blit(Const.TABLETGUI, this.width - 64, this.height - 48, 0, 80, 32, 16);
        } else {
            guiGraphics.blit(Const.TABLETGUI, this.width - 64, this.height - 48, 0, 64, 32, 16);
        }
        poseStack.popPose();
    }
}

