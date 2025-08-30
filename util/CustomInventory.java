/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.Container
 *  net.minecraft.world.ContainerHelper
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package elucent.rootsclassic.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CustomInventory
implements Container {
    private final NonNullList<ItemStack> itemStacks;

    public CustomInventory(int size) {
        this.itemStacks = NonNullList.withSize((int)size, (Object)ItemStack.EMPTY);
    }

    public int getContainerSize() {
        return this.itemStacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.itemStacks) {
            if (itemstack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    public ItemStack getItem(int index) {
        return (ItemStack)this.itemStacks.get(index);
    }

    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.takeItem(this.itemStacks, (int)index);
    }

    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.itemStacks, (int)index);
    }

    public void setItem(int index, ItemStack stack) {
        this.itemStacks.set(index, (Object)stack);
    }

    public void setChanged() {
    }

    public boolean stillValid(Player player) {
        return true;
    }

    public void clearContent() {
        this.itemStacks.clear();
    }
}

