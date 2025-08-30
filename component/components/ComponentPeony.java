/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.DoublePlantBlock
 *  net.minecraft.world.level.block.FlowerBlock
 *  net.minecraft.world.level.block.TallFlowerBlock
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallFlowerBlock;

public class ComponentPeony
extends ComponentBase {
    private List<BlockPos> pos = new CopyOnWriteArrayList<BlockPos>();

    public ComponentPeony() {
        super(Blocks.PEONY, 24);
    }

    @Override
    public void castingAction(Player player, int count, int potency, int efficiency, int size) {
        Level levelAccessor = player.level();
        int x = player.blockPosition().getX();
        int y = player.blockPosition().getY();
        int z = player.blockPosition().getZ();
        int range = 5;
        for (int x2 = -range; x2 < range; ++x2) {
            for (int z2 = -range; z2 < range; ++z2) {
                for (int y2 = -1; y2 < 2; ++y2) {
                    BlockPos position = new BlockPos(x + x2, y + y2, z + z2);
                    if (!(levelAccessor.getBlockState(position).getBlock() instanceof TallFlowerBlock) && !(levelAccessor.getBlockState(position).getBlock() instanceof FlowerBlock) && !(levelAccessor.getBlockState(position).getBlock() instanceof DoublePlantBlock)) continue;
                    this.pos.add(position);
                }
            }
        }
        this.pos.forEach(temp -> {
            int fX = temp.getX();
            int fY = temp.getY();
            int fZ = temp.getZ();
            double factor = 0.15;
            if (levelAccessor.isClientSide && levelAccessor.random.nextInt(10 * this.pos.size()) == 0) {
                levelAccessor.addParticle(MagicAuraParticleData.createData(138.0, 42.0, 235.0), (double)fX + RootsUtil.randomDouble(0.0, 0.5), (double)fY + RootsUtil.randomDouble(0.1, 0.5), (double)fZ + RootsUtil.randomDouble(0.0, 0.5), (player.getX() - (double)fX) * factor, (player.getY() - (double)fY) * factor, (player.getZ() - (double)fZ) * factor);
                levelAccessor.addParticle(MagicAuraParticleData.createData(138.0, 42.0, 235.0), (double)fX + RootsUtil.randomDouble(0.0, 0.5), (double)fY + RootsUtil.randomDouble(0.1, 0.5), (double)fZ + RootsUtil.randomDouble(0.0, 0.5), (player.getX() - (double)fX) * factor, (player.getY() - (double)fY) * factor, (player.getZ() - (double)fZ) * factor);
            }
        });
    }

    @Override
    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL) {
            ((LivingEntity)caster).heal((float)((double)(this.pos.size() / 2) + potency * 2.0));
            this.pos.clear();
        }
    }
}

