/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.NetherWartBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class ComponentLilac
extends ComponentBase {
    public ComponentLilac() {
        super(Blocks.LILAC, 14);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (!level.isClientSide && type == EnumCastType.SPELL && casterEntity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity)casterEntity;
            BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int)size);
            boolean bl = this.growBlockSafe(level, pos, (int)potency) && this.growBlockSafe(level, pos.east(), (int)potency) && this.growBlockSafe(level, pos.west(), (int)potency) && this.growBlockSafe(level, pos.north(), (int)potency) && this.growBlockSafe(level, pos.south(), (int)potency);
        }
    }

    public boolean growBlockSafe(Level levelAccessor, BlockPos pos, int potency) {
        int age;
        BlockState state = levelAccessor.getBlockState(pos);
        if (state.getBlock() instanceof BonemealableBlock && levelAccessor.random.nextInt(5 - potency) < 2) {
            ((BonemealableBlock)state.getBlock()).performBonemeal((ServerLevel)levelAccessor, levelAccessor.random, pos, state);
            return true;
        }
        if (state.is(Blocks.NETHER_WART) && levelAccessor.random.nextInt(5 - potency) < 2 && (age = ((Integer)state.getValue((Property)NetherWartBlock.AGE)).intValue()) < 3) {
            state = (BlockState)state.setValue((Property)NetherWartBlock.AGE, (Comparable)Integer.valueOf(age + 1));
            levelAccessor.setBlock(pos, state, 2);
            return true;
        }
        return false;
    }
}

