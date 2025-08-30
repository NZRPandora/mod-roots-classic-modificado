/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class ComponentPoppy
extends ComponentBase {
    public ComponentPoppy() {
        super(Blocks.POPPY, 8);
    }

    @Override
    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL) {
            List targets = level.getEntitiesOfClass(Monster.class, new AABB(x - size * 2.4, y - size * 2.4, z - size * 2.4, x + size * 2.4, y + size * 2.4, z + size * 2.4));
            for (int i = 0; i < targets.size(); ++i) {
                Monster target = (Monster)targets.get(i);
                target.setTarget(null);
                int j = level.random.nextInt(targets.size());
                if (j == i || !(level.random.nextDouble() >= 1.0 / (potency + 1.0))) continue;
                target.setTarget((LivingEntity)targets.get(j));
            }
        }
    }
}

