/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ComponentDandelion
extends ComponentBase {
    public ComponentDandelion() {
        super(Blocks.DANDELION, 8);
    }

    @Override
    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL) {
            List targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
            targets.removeIf(target -> target.getUUID() == caster.getUUID());
            for (LivingEntity target2 : targets) {
                target2.setDeltaMovement(new Vec3(caster.getLookAngle().x, (double)((float)(potency == 0.0 ? 1.0 : 1.0 + 0.5 * potency)), caster.getLookAngle().z));
                if (!(target2 instanceof Player)) continue;
                Player targetPlayer = (Player)target2;
                targetPlayer.hurtMarked = true;
            }
        }
    }
}

