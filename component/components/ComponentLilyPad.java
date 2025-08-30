/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ComponentLilyPad
extends ComponentBase {
    public ComponentLilyPad() {
        super(Blocks.LILY_PAD, 8);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (!level.isClientSide && type == EnumCastType.SPELL && casterEntity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)casterEntity;
            BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int)size);
            if (level.getBlockState(pos.above()).canBeReplaced((Fluid)Fluids.WATER)) {
                level.setBlock(pos.above(), (BlockState)Blocks.WATER.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, (Comparable)Integer.valueOf(15)), 3);
            }
            if (level.getBlockState(pos.above().west()).canBeReplaced((Fluid)Fluids.WATER)) {
                level.setBlock(pos.above().west(), (BlockState)Blocks.WATER.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, (Comparable)Integer.valueOf(15)), 3);
            }
            if (level.getBlockState(pos.above().east()).canBeReplaced((Fluid)Fluids.WATER)) {
                level.setBlock(pos.above().east(), (BlockState)Blocks.WATER.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, (Comparable)Integer.valueOf(15)), 3);
            }
            if (level.getBlockState(pos.above().north()).canBeReplaced((Fluid)Fluids.WATER)) {
                level.setBlock(pos.above().north(), (BlockState)Blocks.WATER.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, (Comparable)Integer.valueOf(15)), 3);
            }
            if (level.getBlockState(pos.above().south()).canBeReplaced((Fluid)Fluids.WATER)) {
                level.setBlock(pos.above().south(), (BlockState)Blocks.WATER.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, (Comparable)Integer.valueOf(15)), 3);
            }
        }
    }
}

