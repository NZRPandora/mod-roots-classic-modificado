/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.SimpleRitualEffect;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RitualLifeDrain
extends SimpleRitualEffect {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
        inventory.clearContent();
        List enemies = levelAccessor.getEntitiesOfClass(Monster.class, new AABB((double)(pos.getX() - 22), (double)(pos.getY() - 8), (double)(pos.getZ() - 22), (double)(pos.getX() + 23), (double)(pos.getY() + 9), (double)(pos.getZ() + 23)));
        float drainedHealth = 0.0f;
        if (!enemies.isEmpty()) {
            for (Monster enemy : enemies) {
                enemy.hurt(levelAccessor.damageSources().cactus(), 9.0f);
                drainedHealth += 9.0f;
            }
        }
        List players = levelAccessor.getEntitiesOfClass(Player.class, new AABB((double)(pos.getX() - 22), (double)(pos.getY() - 8), (double)(pos.getZ() - 22), (double)(pos.getX() + 23), (double)(pos.getY() + 9), (double)(pos.getZ() + 23)));
        float numPlayers = players.size();
        for (Player player : players) {
            player.heal(drainedHealth / (numPlayers * 2.5f));
        }
    }
}

