/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ComponentNetherWart
extends ComponentBase {
    public ComponentNetherWart() {
        super(Items.NETHER_WART, 10);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL) {
            List targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
            for (LivingEntity target : targets) {
                if (target.getUUID() == casterEntity.getUUID() || !(casterEntity instanceof LivingEntity)) continue;
                LivingEntity caster = (LivingEntity)casterEntity;
                if (target instanceof Player && ((Boolean)RootsConfig.COMMON.disablePVP.get()).booleanValue()) continue;
                target.hurt(ComponentNetherWart.spellAttack(caster), (float)((int)(5.0 + 3.0 * potency)));
                target.igniteForSeconds((float)((int)(4.0 + 3.0 * potency)));
                target.setLastHurtMob((Entity)caster);
                target.setLastHurtByMob(caster);
            }
        }
    }

    public static DamageSource spellAttack(LivingEntity attacker) {
        return attacker.damageSources().source(RootsDamageTypes.FIRE, (Entity)attacker);
    }
}

