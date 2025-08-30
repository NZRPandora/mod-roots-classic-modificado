/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import elucent.rootsclassic.util.RootsUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class RitualImbuer
extends SimpleRitualEffect {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
        ItemStack toSpawn = new ItemStack((ItemLike)RootsRegistry.CRYSTAL_STAFF.get(), 1);
        for (int i = 0; i < incenses.size() && i < 4; ++i) {
            ItemStack incense = incenses.get(i);
            if (incense == null || incense.isEmpty() || incense.getItem() != RootsRegistry.SPELL_POWDER.get() || !incense.has(RootsComponents.SPELL)) continue;
            SpellData spellData = (SpellData)incense.get(RootsComponents.SPELL);
            ResourceLocation effect = ResourceLocation.tryParse((String)spellData.effect());
            CrystalStaffItem.addEffect(toSpawn, i + 1, effect.toString(), spellData.potency(), spellData.efficiency(), spellData.size());
        }
        if (!levelAccessor.isClientSide) {
            ItemEntity item = new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5, toSpawn);
            levelAccessor.addFreshEntity((Entity)item);
        }
        inventory.clearContent();
        levelAccessor.getBlockEntity(pos).setChanged();
    }

    @Override
    public boolean incenseMatches(List<ItemStack> incensesFromNearby, RitualRecipe recipe) {
        ArrayList<ItemStack> incensesWithoutPowders = new ArrayList<ItemStack>(incensesFromNearby);
        incensesWithoutPowders.removeIf(stack -> stack.is((Item)RootsRegistry.SPELL_POWDER.get()));
        if (incensesWithoutPowders.size() == incensesFromNearby.size()) {
            return false;
        }
        return RootsUtil.matchesIngredients(incensesWithoutPowders, recipe.getIncenses());
    }
}

