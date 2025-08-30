/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import elucent.rootsclassic.util.RootsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RitualEngravedSword
extends RitualCrafting {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
        ArrayList<Item> items = new ArrayList<Item>();
        for (ItemStack i : incenses) {
            items.add(i.getItem());
        }
        ItemStack toSpawn = ItemStack.parseOptional((HolderLookup.Provider)levelAccessor.registryAccess(), (CompoundTag)config.getCompound("result"));
        if (!levelAccessor.isClientSide) {
            int mods = 0;
            ItemEntity item = new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5, toSpawn);
            ItemStack stack = item.getItem();
            for (Item i : items) {
                if (i == RootsRegistry.ACACIA_BARK.get() && mods < 4) {
                    this.addMod(stack, RootsComponents.SPIKES);
                    ++mods;
                }
                if (i == RootsRegistry.SPRUCE_BARK.get() && mods < 4) {
                    this.addMod(stack, RootsComponents.FORCEFUL);
                    ++mods;
                }
                if (i == RootsRegistry.BIRCH_BARK.get() && mods < 4) {
                    this.addMod(stack, RootsComponents.HOLY);
                    ++mods;
                }
                if (i == RootsRegistry.JUNGLE_BARK.get() && mods < 4) {
                    this.addMod(stack, RootsComponents.AQUATIC);
                    ++mods;
                }
                if (i != RootsRegistry.DARK_OAK_BARK.get() || mods >= 4) continue;
                this.addMod(stack, RootsComponents.SHADOWSTEP);
                ++mods;
            }
            levelAccessor.addFreshEntity((Entity)item);
        }
        inventory.clearContent();
        BlockEntity tile = levelAccessor.getBlockEntity(pos);
        if (tile != null) {
            tile.setChanged();
        }
    }

    public void addMod(ItemStack stack, Supplier<DataComponentType<Integer>> componentTypeSupplier) {
        if (stack.has(componentTypeSupplier)) {
            stack.set(componentTypeSupplier, (Object)((Integer)stack.getOrDefault(componentTypeSupplier, (Object)0) + 1));
        } else {
            stack.set(componentTypeSupplier, (Object)1);
        }
    }

    @Override
    public boolean incenseMatches(List<ItemStack> incensesFromNearby, RitualRecipe recipe) {
        ArrayList<ItemStack> incensesWithoutBarks = new ArrayList<ItemStack>(incensesFromNearby);
        incensesWithoutBarks.removeIf(stack -> stack.is(RootsTags.BARKS));
        if (incensesFromNearby.size() - incensesWithoutBarks.size() > 4) {
            return false;
        }
        return RootsUtil.matchesIngredients(incensesWithoutBarks, recipe.getIncenses());
    }
}

