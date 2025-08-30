/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LightningBolt
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ComponentPoisonousPotato
extends ComponentBase {
    public ComponentPoisonousPotato() {
        super(Items.POISONOUS_POTATO, 24);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (!level.isClientSide && type == EnumCastType.SPELL && casterEntity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)casterEntity;
            BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int)size);
            LightningBolt lightningBolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(level);
            if (lightningBolt != null) {
                lightningBolt.moveTo((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
                lightningBolt.setVisualOnly(false);
                level.addFreshEntity((Entity)lightningBolt);
            }
        }
    }
}

