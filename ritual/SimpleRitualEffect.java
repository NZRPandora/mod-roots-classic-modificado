/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.ritual;

import elucent.rootsclassic.ritual.RitualEffect;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class SimpleRitualEffect
extends RitualEffect {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
        this.doEffect(levelAccessor, pos, inventory, incenses);
    }

    public abstract void doEffect(Level var1, BlockPos var2, Container var3, List<ItemStack> var4);
}

