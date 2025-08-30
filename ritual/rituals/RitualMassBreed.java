/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.SimpleRitualEffect;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RitualMassBreed
extends SimpleRitualEffect {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
        inventory.clearContent();
        List animals = levelAccessor.getEntitiesOfClass(Animal.class, new AABB((double)(pos.getX() - 22), (double)(pos.getY() - 8), (double)(pos.getZ() - 22), (double)(pos.getX() + 23), (double)(pos.getY() + 9), (double)(pos.getZ() + 23)));
        if (animals.size() > 0) {
            for (Animal animal : animals) {
                animal.setInLove(levelAccessor.getNearestPlayer((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 5.0, false));
                animal.getPersistentData().putInt("InLove", 400);
            }
        }
    }
}

