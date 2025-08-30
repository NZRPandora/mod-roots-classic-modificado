/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.AxeItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class LivingAxeItem
extends AxeItem {
    public LivingAxeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Item.Properties properties) {
        super(tier, properties.attributes(AxeItem.createAttributes((Tier)tier, (float)attackDamageIn, (float)attackSpeedIn)));
    }

    public void inventoryTick(ItemStack stack, Level levelAccessor, Entity entity, int slot, boolean selected) {
        RootsUtil.randomlyRepair(levelAccessor.random, stack);
    }

    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }
}

