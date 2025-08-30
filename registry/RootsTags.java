/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 */
package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RootsTags {
    public static final TagKey<Block> INCORRECT_FOR_LIVING_TOOL = BlockTags.create((ResourceLocation)Const.modLoc("incorrect_for_living_tool"));
    public static final TagKey<Block> INCORRECT_FOR_ENGRAVED_TOOL = BlockTags.create((ResourceLocation)Const.modLoc("incorrect_for_engraved_tool"));
    public static final TagKey<Item> BARKS = ItemTags.create((ResourceLocation)Const.modLoc("barks"));
    public static final TagKey<Item> BERRIES = ItemTags.create((ResourceLocation)Const.modLoc("berries"));
}

