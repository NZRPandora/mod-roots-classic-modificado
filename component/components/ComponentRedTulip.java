/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.neoforged.neoforge.event.EventHooks
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.EventHooks;

public class ComponentRedTulip
extends ComponentBase {
    public ComponentRedTulip() {
        super(Blocks.RED_TULIP, 6);
    }

    @Override
    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL && !level.isClientSide) {
            PhantomSkeletonEntity skeleton = new PhantomSkeletonEntity(level);
            EventHooks.finalizeMobSpawn((Mob)skeleton, (ServerLevelAccessor)((ServerLevel)level), (DifficultyInstance)level.getCurrentDifficultyAt(BlockPos.containing((double)x, (double)y, (double)z)), (MobSpawnType)MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null);
            skeleton.setPos(x, y + 2.0, z);
            level.addFreshEntity((Entity)skeleton);
        }
    }
}

