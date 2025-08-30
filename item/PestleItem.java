/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package elucent.rootsclassic.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class PestleItem
extends Item {
    public PestleItem(Item.Properties properties) {
        super(properties);
    }

    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return new ItemStack((ItemLike)this, 1);
    }

    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }
}

