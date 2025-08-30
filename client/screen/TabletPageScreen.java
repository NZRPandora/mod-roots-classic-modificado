/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeInput
 *  net.minecraft.world.item.crafting.SingleRecipeInput
 *  net.minecraft.world.level.ItemLike
 */
package elucent.rootsclassic.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.client.screen.ScreenSlotInstance;
import elucent.rootsclassic.client.screen.ScreenTextInstance;
import elucent.rootsclassic.client.screen.TabletScreen;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.research.EnumPageType;
import elucent.rootsclassic.research.ResearchBase;
import elucent.rootsclassic.research.ResearchGroup;
import elucent.rootsclassic.research.ResearchPage;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.util.RootsUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.ItemLike;

public class TabletPageScreen
extends Screen {
    private int currentPage = 0;
    private ResearchBase research = null;
    private ResearchGroup group = null;
    private boolean showRightArrow = false;
    private boolean showLeftArrow = true;
    private final Player player;
    private int matchingStacksCurrent = 0;
    private int matchingStacksMax = 0;
    private int matchingStacks2Current = 0;
    private int matchingStacks2Max = 0;

    public TabletPageScreen(ResearchGroup g, ResearchBase r, Player player) {
        super((Component)Component.empty());
        this.player = player;
        this.group = g;
        this.research = r;
    }

    public void onClose() {
        this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
        this.minecraft.setScreen((Screen)new TabletScreen(this.player));
    }

    public boolean isPauseScreen() {
        return false;
    }

    protected void init() {
        super.init();
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float basePosX = (float)this.width / 2.0f - 96.0f;
        float basePosY = (float)this.height / 2.0f - 128.0f;
        if (this.showLeftArrow && mouseX >= (double)(basePosX + 16.0f) && mouseX < (double)(basePosX + 48.0f) && mouseY >= (double)(basePosY + 224.0f) && mouseY < (double)(basePosY + 240.0f)) {
            this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            --this.currentPage;
        }
        if (this.showRightArrow && mouseX >= (double)(basePosX + 144.0f) && mouseX < (double)(basePosX + 176.0f) && mouseY >= (double)(basePosY + 224.0f) && mouseY < (double)(basePosY + 240.0f)) {
            this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            ++this.currentPage;
        }
        List<ResearchPage> researchInfo = this.research.getInfo();
        if (this.player.level().isClientSide && mouseX >= (double)((float)this.width / 2.0f - 110.0f) && mouseX < (double)((float)this.width / 2.0f + 40.0f) && mouseY >= (double)((float)this.height / 2.0f - 138.0f) && mouseY < (double)((float)this.height / 2.0f - 40.0f)) {
            if (researchInfo.get((int)this.currentPage).recipe == EnumPageType.TYPE_MORTAR) {
                this.player.sendSystemMessage((Component)Component.translatable((String)researchInfo.get((int)this.currentPage).mortarRecipe.toString()));
            } else if (researchInfo.get((int)this.currentPage).recipe == EnumPageType.TYPE_ALTAR) {
                this.player.sendSystemMessage((Component)Component.translatable((String)researchInfo.get((int)this.currentPage).altarRecipe.toString()));
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private String makeTitle() {
        return I18n.get((String)("rootsclassic.research." + this.group.getName() + "." + this.research.getName() + ".page" + (this.currentPage + 1) + "title"), (Object[])new Object[0]);
    }

    private String makeInfo() {
        return I18n.get((String)("rootsclassic.research." + this.group.getName() + "." + this.research.getName() + ".page" + (this.currentPage + 1) + "info"), (Object[])new Object[0]);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        PoseStack ps = guiGraphics.pose();
        ps.pushPose();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        List<ResearchPage> researchInfo = this.research.getInfo();
        this.showLeftArrow = this.currentPage != 0;
        this.showRightArrow = this.currentPage != researchInfo.size() - 1;
        int basePosX = (int)((float)this.width / 2.0f - 96.0f);
        int basePosY = (int)((float)this.height / 2.0f - 128.0f);
        if (this.research == null || researchInfo == null || researchInfo.size() <= this.currentPage) {
            return;
        }
        ArrayList<ScreenSlotInstance> slots = new ArrayList<ScreenSlotInstance>();
        ArrayList<ScreenTextInstance> textLines = new ArrayList<ScreenTextInstance>();
        ResearchPage page = researchInfo.get(this.currentPage);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        switch (page.recipe) {
            case TYPE_NULL: {
                guiGraphics.blit(Const.TABLETGUI, basePosX, basePosY, 64, 0, 192, 256);
                List<String> info = page.makeLines(this.makeInfo());
                for (int i = 0; i < info.size(); ++i) {
                    textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 32 + i * 11));
                }
                Object title = this.makeTitle();
                textLines.add(new ScreenTextInstance((String)title, (float)(basePosX + 96) - (float)this.font.width((String)title) / 2.0f, basePosY + 12, RootsUtil.intColor(255, 255, 255)));
                break;
            }
            case TYPE_SMELTING: {
                guiGraphics.blit(Const.TABLETSMELTING, basePosX, basePosY, 0, 0, 192, 256);
                slots.add(new ScreenSlotInstance(page.smeltingRecipe.get(0), basePosX + 56, basePosY + 40));
                slots.add(new ScreenSlotInstance(page.smeltingRecipe.get(1), basePosX + 144, basePosY + 56));
                List<String> info = page.makeLines(this.makeInfo());
                for (int i = 0; i < info.size(); ++i) {
                    textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 104 + i * 11, RootsUtil.intColor(255, 255, 255)));
                }
                Object title = this.makeTitle();
                textLines.add(new ScreenTextInstance((String)title, (float)(basePosX + 96) - (float)this.font.width((String)title) / 2.0f, basePosY + 12, RootsUtil.intColor(255, 255, 255)));
                break;
            }
            case TYPE_DISPLAY: {
                guiGraphics.blit(Const.TABLETDISPLAY, basePosX, basePosY, 0, 0, 192, 256);
                slots.add(new ScreenSlotInstance(page.displayItem, basePosX + 88, basePosY + 48));
                List<String> info = page.makeLines(this.makeInfo());
                for (int i = 0; i < info.size(); ++i) {
                    textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 80 + i * 11, RootsUtil.intColor(255, 255, 255)));
                }
                Object title = this.makeTitle();
                textLines.add(new ScreenTextInstance((String)title, (float)(basePosX + 96) - (float)this.font.width((String)title) / 2.0f, basePosY + 12, RootsUtil.intColor(255, 255, 255)));
                break;
            }
            case TYPE_ALTAR: {
                ItemStack stack;
                int i;
                guiGraphics.blit(Const.TABLETALTAR, basePosX, basePosY, 0, 0, 192, 256);
                RitualPillars.getRitualPillars(((RitualRecipe)page.altarRecipe.value()).level).forEach((pos, block) -> {
                    RenderSystem.setShaderTexture((int)0, (ResourceLocation)Const.TABLETALTAR);
                    int u = 192;
                    int v = 240;
                    int xShift = 0;
                    int yShift = 0;
                    guiGraphics.blit(Const.TABLETALTAR, basePosX + 93, basePosY + 153, 192, 32, 16, 16);
                    if (block.equals(RootsRegistry.MUNDANE_STANDING_STONE.get())) {
                        v = 48;
                        xShift = 8 * pos.getX();
                        yShift = 8 * pos.getZ();
                    }
                    if (block.equals(RootsRegistry.ATTUNED_STANDING_STONE.get())) {
                        v = 64;
                        xShift = 8 * pos.getX();
                        yShift = 8 * pos.getZ();
                    }
                    guiGraphics.blit(Const.TABLETALTAR, basePosX + 93 + xShift, basePosY + 153 + yShift, u, v, 16, 16);
                });
                for (i = 0; i < ((RitualRecipe)page.altarRecipe.value()).getIngredients().size(); ++i) {
                    stack = ((Ingredient)((RitualRecipe)page.altarRecipe.value()).getIngredients().get(i)).getItems()[0];
                    slots.add(new ScreenSlotInstance(stack, basePosX + 64 + 24 * i, basePosY + 56));
                }
                for (i = 0; i < ((RitualRecipe)page.altarRecipe.value()).getIncenses().size(); ++i) {
                    stack = ((RitualRecipe)page.altarRecipe.value()).getIncenses().get(i).getItems()[0];
                    slots.add(new ScreenSlotInstance(stack, basePosX + 76 + 16 * i, basePosY + 88));
                }
                Object title = this.makeTitle();
                textLines.add(new ScreenTextInstance((String)title, (float)(basePosX + 96) - (float)this.font.width((String)title) / 2.0f, basePosY + 12, RootsUtil.intColor(255, 255, 255)));
                break;
            }
            case TYPE_MORTAR: {
                List<String> info;
                guiGraphics.blit(Const.TABLETMORTAR, basePosX, basePosY, 0, 0, 192, 256);
                Object title = this.makeTitle();
                if (page.mortarRecipe != null) {
                    for (int i = 0; i < ((ComponentRecipe)page.mortarRecipe.value()).getIngredients().size(); ++i) {
                        Ingredient ingredient = (Ingredient)((ComponentRecipe)page.mortarRecipe.value()).getIngredients().get(i);
                        if (ingredient.isEmpty()) {
                            slots.add(new ScreenSlotInstance(new ItemStack((ItemLike)Items.BARRIER), basePosX + 24 + i * 16, basePosY + 56));
                            continue;
                        }
                        slots.add(new ScreenSlotInstance(this.getStackFromIngredient(ingredient), basePosX + 24 + i * 16, basePosY + 56));
                    }
                    ClientLevel level = this.minecraft.level;
                    if (level != null) {
                        RegistryAccess registryAccess = level.registryAccess();
                        slots.add(new ScreenSlotInstance(((ComponentRecipe)page.mortarRecipe.value()).assemble((RecipeInput)new SingleRecipeInput(ItemStack.EMPTY), (HolderLookup.Provider)registryAccess), basePosX + 144, basePosY + 56));
                    }
                    info = page.makeLines(this.makeInfo());
                    for (int i = 0; i < info.size(); ++i) {
                        textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 96 + i * 11, RootsUtil.intColor(255, 255, 255)));
                    }
                } else {
                    title = String.valueOf(ChatFormatting.RED) + I18n.get((String)"rootsclassic.research.disabled", (Object[])new Object[0]);
                }
                textLines.add(new ScreenTextInstance((String)title, (float)(basePosX + 96) - (float)this.font.width((String)title) / 2.0f, basePosY + 12, RootsUtil.intColor(255, 255, 255)));
            }
        }
        for (ScreenSlotInstance s : slots) {
            guiGraphics.renderItem(s.getStack(), s.getX(), s.getY());
        }
        for (ScreenTextInstance line : textLines) {
            if (line.isShadow()) {
                guiGraphics.drawString(this.font, line.getLine(), line.getX(), line.getY(), line.getColor(), true);
                continue;
            }
            guiGraphics.drawString(this.font, line.getLine(), line.getX(), line.getY(), line.getColor(), false);
        }
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)Const.TABLETGUI);
        if (this.showLeftArrow) {
            if (mouseX >= basePosX + 16 && mouseX < basePosX + 48 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
                guiGraphics.blit(Const.TABLETGUI, basePosX + 16, basePosY + 224, 32, 80, 32, 16);
            } else {
                guiGraphics.blit(Const.TABLETGUI, basePosX + 16, basePosY + 224, 32, 64, 32, 16);
            }
        }
        if (this.showRightArrow) {
            if (mouseX >= basePosX + 144 && mouseX < basePosX + 176 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
                guiGraphics.blit(Const.TABLETGUI, basePosX + 144, basePosY + 224, 0, 80, 32, 16);
            } else {
                guiGraphics.blit(Const.TABLETGUI, basePosX + 144, basePosY + 224, 0, 64, 32, 16);
            }
        }
        for (ScreenSlotInstance s : slots) {
            if (!s.isMouseover(mouseX, mouseY)) continue;
            guiGraphics.renderTooltip(this.font, s.getStack(), mouseX, mouseY);
        }
        ps.popPose();
    }

    public ItemStack getStackFromIngredient(Ingredient ingredient) {
        ItemStack[] matchingStacks = ingredient.getItems();
        if (this.matchingStacksMax != matchingStacks.length) {
            this.matchingStacksMax = matchingStacks.length;
        }
        if (ClientInfo.ticksInGame % 20 == 0) {
            ++this.matchingStacksCurrent;
        }
        if (this.matchingStacksCurrent >= this.matchingStacksMax - 1) {
            this.matchingStacksCurrent = 0;
        }
        return matchingStacks[this.matchingStacksCurrent];
    }

    public ItemStack getStackFrom2ndIngredient(Ingredient ingredient) {
        ItemStack[] matchingStacks = ingredient.getItems();
        if (this.matchingStacks2Max != matchingStacks.length) {
            this.matchingStacks2Max = matchingStacks.length;
        }
        if (ClientInfo.ticksInGame % 20 == 0) {
            ++this.matchingStacks2Current;
        }
        if (this.matchingStacks2Current >= this.matchingStacks2Max - 1) {
            this.matchingStacks2Current = 0;
        }
        return matchingStacks[this.matchingStacks2Current];
    }
}

