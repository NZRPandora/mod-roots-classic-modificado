/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.level.block.Block
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ComponentFlareOrchid
extends ComponentBase {
    public ComponentFlareOrchid() {
        super((Block)RootsRegistry.FLARE_ORCHID.get(), 36);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)casterEntity;
            BlockPos pos = RootsUtil.getRayTrace(level, caster, 16 + 8 * (int)size);
            level.explode(null, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (float)(3.0 + potency), Level.ExplosionInteraction.BLOCK);
        }
    }
}

