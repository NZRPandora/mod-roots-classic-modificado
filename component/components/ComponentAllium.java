/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsDamageTypes;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class ComponentAllium
extends ComponentBase {
    public ComponentAllium() {
        super(Blocks.ALLIUM, 8);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)casterEntity;
            List targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
            targets.removeIf(target -> target.getUUID() == casterEntity.getUUID());
            for (LivingEntity target2 : targets) {
                if (target2 instanceof Player && ((Boolean)RootsConfig.COMMON.disablePVP.get()).booleanValue()) continue;
                target2.hurt(ComponentAllium.spellAttack(caster), (float)((int)(5.0 + 2.0 * potency)));
                target2.setLastHurtMob((Entity)caster);
                target2.setLastHurtByMob(caster);
                target2.getPersistentData().putDouble("RMOD_vuln", 1.0 + 0.5 * potency);
            }
        }
    }

    public static DamageSource spellAttack(LivingEntity attacker) {
        return attacker.damageSources().source(RootsDamageTypes.GENERIC, (Entity)attacker);
    }
}

