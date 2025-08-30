/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.SimpleRitualEffect;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class RitualSacrifice
extends SimpleRitualEffect {
    public List<ItemStack> potentialDrops = new ArrayList<ItemStack>();

    public RitualSacrifice() {
        this.potentialDrops.add(new ItemStack((ItemLike)Items.WHEAT_SEEDS, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Items.WHEAT_SEEDS, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Items.PUMPKIN_SEEDS, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Items.PUMPKIN_SEEDS, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Items.MELON_SEEDS, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Items.MELON_SEEDS, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Items.SUGAR_CANE, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Items.SUGAR_CANE, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.VINE, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.VINE, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.POPPY, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.BLUE_ORCHID, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.ALLIUM, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.AZURE_BLUET, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.RED_TULIP, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.ORANGE_TULIP, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.WHITE_TULIP, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.PINK_TULIP, 1));
        this.potentialDrops.add(new ItemStack((ItemLike)Blocks.LILY_PAD, 1));
    }

    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
        inventory.clearContent();
        List enemies = levelAccessor.getEntitiesOfClass(LivingEntity.class, new AABB((double)(pos.getX() - 2), (double)(pos.getY() - 2), (double)(pos.getZ() - 2), (double)(pos.getX() + 3), (double)(pos.getY() + 3), (double)(pos.getZ() + 3)));
        if (enemies.size() > 0) {
            for (LivingEntity enemy : enemies) {
                if (enemy instanceof Player) continue;
                ((LivingEntity)enemies.getFirst()).setHealth(((LivingEntity)enemies.getFirst()).getHealth() - 60.0f);
                if (levelAccessor.isClientSide) continue;
                ItemEntity item = new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5, this.potentialDrops.get(levelAccessor.random.nextInt(this.potentialDrops.size())));
                levelAccessor.addFreshEntity((Entity)item);
            }
        }
    }
}

