/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualEffect;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RitualCrafting
extends RitualEffect {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
        ItemStack toSpawn = ItemStack.parseOptional((HolderLookup.Provider)levelAccessor.registryAccess(), (CompoundTag)config.getCompound("result"));
        if (!levelAccessor.isClientSide) {
            ItemEntity item = new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5, toSpawn);
            levelAccessor.addFreshEntity((Entity)item);
        }
        inventory.clearContent();
        BlockEntity blockEntity = levelAccessor.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.setChanged();
        }
    }

    @Override
    public ItemStack getResult(CompoundTag config, HolderLookup.Provider provider) {
        return ItemStack.parseOptional((HolderLookup.Provider)provider, (CompoundTag)config.getCompound("result"));
    }
}

