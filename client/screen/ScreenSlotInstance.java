/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package elucent.rootsclassic.client.screen;

import net.minecraft.world.item.ItemStack;

public class ScreenSlotInstance {
    private int x;
    private int y;
    private ItemStack stack;

    public ScreenSlotInstance(ItemStack s, int x, int y) {
        this.setIngredient(s);
        this.setX(x);
        this.setY(y);
    }

    public boolean isMouseover(int mouseX, int mouseY) {
        return this.getX() < mouseX && mouseX < this.getX() + this.getWidth() && this.getY() < mouseY && mouseY < this.getY() + this.getHeight();
    }

    private int getHeight() {
        return 18;
    }

    private int getWidth() {
        return 18;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setIngredient(ItemStack stack) {
        this.stack = stack;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

