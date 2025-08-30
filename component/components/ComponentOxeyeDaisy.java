/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class ComponentOxeyeDaisy
extends ComponentBase {
    public ComponentOxeyeDaisy() {
        super(Blocks.OXEYE_DAISY, 14);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)casterEntity;
            Entity entity = RootsUtil.getRayTraceEntity(level, caster, 4 + 2 * (int)size);
            if (entity != null) {
                if (!level.isClientSide && level.getEntitiesOfClass(EntityAccelerator.class, new AABB(entity.getX() - 0.1, entity.getY() - 0.1, entity.getZ() - 0.1, entity.getX() + 0.1, entity.getY() + 0.1, entity.getZ() + 0.1)).size() == 0) {
                    EntityAccelerator a = new EntityAccelerator(level, entity, (int)potency, (int)size);
                    level.addFreshEntity((Entity)a);
                }
            } else {
                BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int)size);
                if (level.getBlockEntity(pos) != null && !level.isClientSide && level.getEntitiesOfClass(EntityTileAccelerator.class, new AABB((double)pos.getX() - 0.1, (double)pos.getY() - 0.1, (double)pos.getZ() - 0.1, (double)pos.getX() + 0.1, (double)pos.getY() + 0.1, (double)pos.getZ() + 0.1)).size() == 0) {
                    EntityTileAccelerator a = new EntityTileAccelerator(level, pos, (int)potency, (int)size);
                    a.setBEPosition(pos);
                    level.addFreshEntity((Entity)a);
                }
            }
        }
    }
}

