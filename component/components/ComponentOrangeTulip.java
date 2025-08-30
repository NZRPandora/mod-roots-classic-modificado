/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ComponentOrangeTulip
extends ComponentBase {
    public ComponentOrangeTulip() {
        super(Blocks.ORANGE_TULIP, 20);
    }

    @Override
    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    }

    @Override
    public void castingAction(Player player, int count, int potency, int efficiency, int size) {
        super.castingAction(player, count, potency, efficiency, size);
        if (count % 1 == 0) {
            List entities = player.level().getEntitiesOfClass(Entity.class, new AABB(player.getX() - (1.0 + 0.5 * (double)size), player.getY() - (1.0 + 0.5 * (double)size), player.getZ() - (1.0 + 0.5 * (double)size), player.getX() + (1.0 + 0.5 * (double)size), player.getY() + (1.0 + 0.5 * (double)size), player.getZ() + (1.0 + 0.5 * (double)size)));
            entities.removeIf(entity -> entity instanceof LivingEntity || entity instanceof AbstractArrow);
            for (Entity entity2 : entities) {
                Vec3 v = new Vec3(entity2.getX() - player.getX(), entity2.getY() - (player.getY() + 1.0), entity2.getZ() - player.getZ());
                v.normalize();
                entity2.setDeltaMovement(v.x * (0.05 + 0.05 * (double)potency), v.y * (0.05 + 0.05 * (double)potency), v.z * (0.05 + 0.05 * (double)potency));
            }
        }
    }
}

