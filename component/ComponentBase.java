/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.phys.Vec3
 */
package elucent.rootsclassic.component;

import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ComponentBase {
    private ItemStack itemSource = ItemStack.EMPTY;
    public Vec3 primaryColor = new Vec3(0.0, 0.0, 0.0);
    public Vec3 secondaryColor = new Vec3(0.0, 0.0, 0.0);
    private float manaCost = 0.0f;
    private ChatFormatting textColor = ChatFormatting.WHITE;

    public ComponentBase setPrimaryColor(double r, double g, double b) {
        this.primaryColor = new Vec3(r, g, b);
        return this;
    }

    public ComponentBase setSecondaryColor(double r, double g, double b) {
        this.secondaryColor = new Vec3(r, g, b);
        return this;
    }

    public ComponentBase setTextColor(ChatFormatting color) {
        this.textColor = color;
        return this;
    }

    public ComponentBase(ItemStack item, int cost) {
        this.setManaCost(cost);
        this.itemSource = item;
    }

    public ComponentBase(Item item, int cost) {
        this(new ItemStack((ItemLike)item), cost);
    }

    public ComponentBase(Block block, int cost) {
        this(new ItemStack((ItemLike)block), cost);
    }

    public MutableComponent getEffectName() {
        return Component.translatable((String)("rootsclassic.component." + String.valueOf(ComponentBaseRegistry.COMPONENTS.getKey((Object)this))));
    }

    public ChatFormatting getTextColor() {
        return this.textColor;
    }

    public ItemStack getItem() {
        return this.itemSource;
    }

    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    }

    public void doEffect(Level level, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    }

    public void castingAction(Player player, int count, int potency, int efficiency, int size) {
    }

    public float getManaCost() {
        return this.manaCost;
    }

    public void setManaCost(float manaCost) {
        this.manaCost = manaCost;
    }
}

