/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ComponentAzureBluet
extends ComponentBase {
    public ComponentAzureBluet() {
        super(Blocks.AZURE_BLUET, 6);
    }

    public void destroyBlockSafe(Level levelAccessor, BlockPos pos, int potency) {
        int tier;
        Tiers usedTier;
        BlockState state = levelAccessor.getBlockState(pos);
        if (!state.is((usedTier = (tier = 2 + potency) == 3 ? Tiers.DIAMOND : (tier > 3 ? Tiers.NETHERITE : Tiers.IRON)).getIncorrectBlocksForDrops()) && state.getDestroySpeed((BlockGetter)levelAccessor, pos) != -1.0f) {
            levelAccessor.destroyBlock(pos, true);
        }
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)casterEntity;
            if (!level.isClientSide) {
                BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int)size);
                this.destroyBlockSafe(level, pos, (int)potency);
                if (level.random.nextBoolean()) {
                    this.destroyBlockSafe(level, pos.above(), (int)potency);
                }
                if (level.random.nextBoolean()) {
                    this.destroyBlockSafe(level, pos.below(), (int)potency);
                }
                if (level.random.nextBoolean()) {
                    this.destroyBlockSafe(level, pos.east(), (int)potency);
                }
                if (level.random.nextBoolean()) {
                    this.destroyBlockSafe(level, pos.west(), (int)potency);
                }
                if (level.random.nextBoolean()) {
                    this.destroyBlockSafe(level, pos.north(), (int)potency);
                }
                if (level.random.nextBoolean()) {
                    this.destroyBlockSafe(level, pos.south(), (int)potency);
                }
            }
        }
    }
}

