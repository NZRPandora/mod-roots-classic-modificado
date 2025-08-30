/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.util.RootsUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class MutatingPowderItem
extends Item {
    public MutatingPowderItem(Item.Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level levelAccessor, Player player, InteractionHand hand) {
        MutagenRecipe recipe;
        ItemStack stack = player.getItemInHand(hand);
        if (levelAccessor.isClientSide) {
            for (int i = 0; i < 40; ++i) {
                double velX = player.getLookAngle().x * 0.75 + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
                double velY = player.getLookAngle().y * 0.75 + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
                double velZ = player.getLookAngle().z * 0.75 + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
                levelAccessor.addParticle(MagicParticleData.createData(142.0, 62.0, 56.0), player.getX() + 0.5 * player.getLookAngle().x, player.getY() + 1.5 + 0.5 * player.getLookAngle().y, player.getZ() + 0.5 * player.getLookAngle().z, velX, velY, velZ);
            }
        }
        BlockPos pos = RootsUtil.getRayTrace(levelAccessor, (LivingEntity)player, 4);
        List itemEntities = levelAccessor.getEntitiesOfClass(ItemEntity.class, new AABB((double)(pos.getX() - 2), (double)(pos.getY() - 2), (double)(pos.getZ() - 2), (double)(pos.getX() + 3), (double)(pos.getY() + 3), (double)(pos.getZ() + 3)));
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        for (ItemEntity itemEntity : itemEntities) {
            items.add(itemEntity.getItem());
        }
        if (items.size() > 0 && (recipe = MutagenManager.getRecipe(items, levelAccessor, pos, player)) != null) {
            levelAccessor.setBlockAndUpdate(pos, recipe.result);
            if (levelAccessor.isClientSide) {
                for (int i = 0; i < 100; ++i) {
                    double velX = 1.5 * (levelAccessor.random.nextDouble() - 0.5);
                    double velY = 1.5 * (levelAccessor.random.nextDouble() - 0.5);
                    double velZ = 1.5 * (levelAccessor.random.nextDouble() - 0.5);
                    levelAccessor.addParticle(MagicParticleData.createData(142.0, 62.0, 56.0), (double)pos.getX() + levelAccessor.random.nextDouble(), (double)pos.getY() + levelAccessor.random.nextDouble(), (double)pos.getZ() + levelAccessor.random.nextDouble(), velX, velY, velZ);
                }
            }
            for (ItemEntity itemEntity : itemEntities) {
                itemEntity.discard();
            }
            recipe.onCrafted(levelAccessor, pos, player);
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)stack);
    }
}

