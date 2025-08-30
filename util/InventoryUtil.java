/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.neoforge.items.IItemHandler
 *  net.neoforged.neoforge.items.wrapper.RecipeWrapper
 */
package elucent.rootsclassic.util;

import elucent.rootsclassic.util.CustomInventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class InventoryUtil {
    public static int getFirstEmptyStack(IItemHandler itemHandler) {
        if (itemHandler == null) {
            return -1;
        }
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) continue;
            return i;
        }
        return -1;
    }

    public static boolean isFull(IItemHandler itemHandler) {
        if (itemHandler == null) {
            return true;
        }
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getCount() >= stack.getMaxStackSize()) continue;
            return false;
        }
        return true;
    }

    public static boolean isEmpty(IItemHandler itemHandler) {
        if (itemHandler == null) {
            return true;
        }
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    public static void clearInventory(IItemHandler itemHandler) {
        if (itemHandler == null) {
            return;
        }
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            stack.shrink(1);
        }
    }

    public static ItemStack getLastStack(IItemHandler itemHandler) {
        if (itemHandler == null) {
            return ItemStack.EMPTY;
        }
        for (int i = itemHandler.getSlots() - 1; i >= 0; --i) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public static CustomInventory createIInventory(IItemHandler itemHandler) {
        if (itemHandler == null) {
            return null;
        }
        CustomInventory inventory = new CustomInventory(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }

    public static RecipeWrapper createWrappedInventory(IItemHandler itemHandler) {
        if (itemHandler == null) {
            return null;
        }
        return new RecipeWrapper(itemHandler);
    }
}

