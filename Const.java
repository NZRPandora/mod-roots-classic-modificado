/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package elucent.rootsclassic;

import net.minecraft.resources.ResourceLocation;

public class Const {
    public static final String MODID = "rootsclassic";
    public static final ResourceLocation TABLETSMELTING = Const.modLoc("textures/gui/tabletsmelting.png");
    public static final ResourceLocation TABLETCRAFTING = Const.modLoc("textures/gui/tabletcrafting.png");
    public static final ResourceLocation TABLETGUI = Const.modLoc("textures/gui/tabletgui.png");
    public static final ResourceLocation MANA_CONTAINER = Const.modLoc("hud/mana/container");
    public static final ResourceLocation MANA_FULL = Const.modLoc("hud/mana/full");
    public static final ResourceLocation MANA_ALMOST_FULL = Const.modLoc("hud/mana/almost_full");
    public static final ResourceLocation MANA_HALF = Const.modLoc("hud/mana/half");
    public static final ResourceLocation MANA_ALMOST_EMPTY = Const.modLoc("hud/mana/almost_empty");
    public static final ResourceLocation TABLETDISPLAY = Const.modLoc("textures/gui/tabletdisplay.png");
    public static final ResourceLocation TABLETALTAR = Const.modLoc("textures/gui/tabletaltar.png");
    public static final ResourceLocation TABLETMORTAR = Const.modLoc("textures/gui/tabletmortar.png");
    public static final ResourceLocation MANA_LAYER = Const.modLoc("mana_layer");
    public static final String NBT_THORNS = "rootsclassic:RMOD_thornsDamage";
    public static final String NBT_VULN = "rootsclassic:RMOD_vuln";
    public static final String NBT_DONT_DROP = "rootsclassic:RMOD_dropItems";
    public static final String NBT_TRACK_TICKS = "RMOD_trackTicks";
    public static final String NBT_SKIP_TICKS = "rootsclassic:RMOD_skipTicks";

    public static final ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath((String)MODID, (String)path);
    }
}

