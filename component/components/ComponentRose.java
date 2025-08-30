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
import elucent.rootsclassic.util.RootsUtil;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class ComponentRose
extends ComponentBase {
    public ComponentRose() {
        super(Blocks.ROSE_BUSH, 14);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)casterEntity;
            List targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
            targets.removeIf(target -> target.getUUID() == casterEntity.getUUID());
            for (LivingEntity target2 : targets) {
                if (target2 instanceof Player && ((Boolean)RootsConfig.COMMON.disablePVP.get()).booleanValue()) continue;
                target2.hurt(ComponentRose.spellAttack(caster), (float)((int)(9.0 + 2.0 * potency)));
                RootsUtil.addTickTracking((Entity)target2);
                target2.getPersistentData().putFloat("RMOD_thornsDamage", 2.0f + (float)potency);
                if (caster instanceof Player) {
                    Player player = (Player)caster;
                    target2.setLastHurtByPlayer(player);
                    continue;
                }
                target2.setLastHurtByMob(caster);
            }
        }
    }

    public static DamageSource spellAttack(LivingEntity attacker) {
        return attacker.damageSources().source(RootsDamageTypes.CACTUS, (Entity)attacker);
    }
}

