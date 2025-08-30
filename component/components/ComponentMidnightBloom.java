/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public class ComponentMidnightBloom
extends ComponentBase {
    public ComponentMidnightBloom() {
        super((Block)RootsRegistry.MIDNIGHT_BLOOM.get(), 36);
    }

    @Override
    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL) {
            List targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size * 6.0, y - size * 6.0, z - size * 6.0, x + size * 6.0, y + size * 6.0, z + size * 6.0));
            targets.removeIf(target -> target.getUUID() == caster.getUUID());
            for (LivingEntity target2 : targets) {
                CompoundTag persistentData = target2.getPersistentData();
                persistentData.putBoolean("RMOD_trackTicks", false);
                persistentData.putInt("rootsclassic:RMOD_skipTicks", 40 + 40 * (int)potency);
            }
        }
    }
}

