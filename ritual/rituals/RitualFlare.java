/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.SimpleRitualEffect;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RitualFlare
extends SimpleRitualEffect {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack;
            if (inventory.getItem(i).isEmpty() || (stack = inventory.getItem(i)).getItem() != Items.FLINT_AND_STEEL || stack.getDamageValue() >= stack.getDamageValue() - 1 || levelAccessor.isClientSide) continue;
            ItemStack flintStack = new ItemStack((ItemLike)Items.FLINT_AND_STEEL, 1);
            flintStack.setDamageValue(stack.getDamageValue() + 1);
            ItemEntity item = new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5, flintStack);
            levelAccessor.addFreshEntity((Entity)item);
        }
        inventory.clearContent();
        List enemies = levelAccessor.getEntitiesOfClass(LivingEntity.class, new AABB((double)(pos.getX() - 22), (double)(pos.getY() - 8), (double)(pos.getZ() - 22), (double)(pos.getX() + 23), (double)(pos.getY() + 9), (double)(pos.getZ() + 23)));
        if (enemies.size() > 0) {
            for (LivingEntity enemy : enemies) {
                enemy.igniteForSeconds((float)(levelAccessor.random.nextInt(5) + 14));
            }
        }
    }
}

