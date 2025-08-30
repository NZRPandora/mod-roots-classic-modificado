/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import elucent.rootsclassic.util.RootsUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class RitualTimeShift
extends SimpleRitualEffect {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
        long shiftAmount = 0L;
        ArrayList<Item> items = new ArrayList<Item>();
        for (ItemStack itemStack : incenses) {
            items.add(itemStack.getItem());
        }
        for (Item item : items) {
            if (item != Items.CLOCK) continue;
            shiftAmount += 1000L;
        }
        inventory.clearContent();
        if (!levelAccessor.isClientSide && levelAccessor.getServer() != null) {
            for (ServerLevel serverLevel : levelAccessor.getServer().getAllLevels()) {
                serverLevel.setDayTime(serverLevel.getDayTime() + shiftAmount);
            }
        }
    }

    @Override
    public boolean incenseMatches(List<ItemStack> incensesFromNearby, RitualRecipe recipe) {
        ArrayList<ItemStack> incensesWithoutClocks = new ArrayList<ItemStack>(incensesFromNearby);
        incensesWithoutClocks.removeIf(stack -> stack.is(Items.CLOCK));
        if (incensesFromNearby.size() == incensesWithoutClocks.size()) {
            return false;
        }
        incensesWithoutClocks.add(new ItemStack((ItemLike)Items.CLOCK));
        return RootsUtil.matchesIngredients(incensesWithoutClocks, recipe.getIncenses());
    }
}

