/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.FastColor$ARGB32
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class RootsUtil {
    private static final Random random = new Random();

    public static double randomDouble(double min, double max) {
        double range = max - min;
        double scale = random.nextDouble() * range;
        return scale + min;
    }

    public static BlockPos getRayTrace(Level levelAccessor, LivingEntity livingEntity, int reachDistance) {
        double x = livingEntity.getX();
        double y = livingEntity.getY() + (double)livingEntity.getEyeHeight();
        double z = livingEntity.getZ();
        int i = 0;
        while ((double)i < (double)reachDistance * 10.0) {
            if (levelAccessor.getBlockState(BlockPos.containing((double)(x += livingEntity.getLookAngle().x * 0.1), (double)(y += livingEntity.getLookAngle().y * 0.1), (double)(z += livingEntity.getLookAngle().z * 0.1))).getBlock() != Blocks.AIR) {
                return BlockPos.containing((double)x, (double)y, (double)z);
            }
            ++i;
        }
        return BlockPos.containing((double)x, (double)y, (double)z);
    }

    public static void addTickTracking(Entity entity) {
        if (entity.getPersistentData().contains("RMOD_trackTicks")) {
            entity.getPersistentData().putInt("RMOD_trackTicks", entity.getPersistentData().getInt("RMOD_trackTicks") + 1);
        } else {
            entity.getPersistentData().putInt("RMOD_trackTicks", 1);
        }
    }

    public static void decrementTickTracking(Entity entity) {
        if (entity.getPersistentData().contains("RMOD_trackTicks")) {
            entity.getPersistentData().putInt("RMOD_trackTicks", entity.getPersistentData().getInt("RMOD_trackTicks") - 1);
            if (entity.getPersistentData().getInt("RMOD_trackTicks") == 0) {
                entity.removeTag("RMOD_trackTicks");
            }
        }
    }

    public static Entity getRayTraceEntity(Level levelAccessor, LivingEntity livingEntity, int reachDistance) {
        double x = livingEntity.getX();
        double y = livingEntity.getY() + (double)livingEntity.getEyeHeight();
        double z = livingEntity.getZ();
        int i = 0;
        while ((double)i < (double)reachDistance * 10.0) {
            List entities;
            if ((entities = levelAccessor.getEntitiesOfClass(Entity.class, new AABB((x += livingEntity.getLookAngle().x * 0.1) - 0.1, (y += livingEntity.getLookAngle().y * 0.1) - 0.1, (z += livingEntity.getLookAngle().z * 0.1) - 0.1, x + 0.1, y + 0.1, z + 0.1))).size() > 0 && ((Entity)entities.getFirst()).getUUID() != livingEntity.getUUID()) {
                return (Entity)entities.getFirst();
            }
            ++i;
        }
        return null;
    }

    public static boolean matchesIngredients(List<ItemStack> inv, List<Ingredient> ingredients) {
        if (inv.size() != ingredients.size()) {
            return false;
        }
        ArrayList<ItemStack> available = new ArrayList<ItemStack>(inv);
        return ingredients.stream().allMatch(ingredient -> {
            Optional match = available.stream().filter(ingredient).findFirst();
            match.ifPresent(available::remove);
            return match.isPresent();
        });
    }

    public static void randomlyRepair(RandomSource rnd, ItemStack stack) {
        if (stack.isDamaged() && rnd.nextInt(80) == 0) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    public static int intColor(int r, int g, int b) {
        return FastColor.ARGB32.color((int)r, (int)g, (int)b);
    }

    public static int intColorFromHexString(String hex) {
        if (hex.isEmpty()) {
            return 0;
        }
        if (hex.charAt(0) == '#') {
            hex = hex.substring(1);
        }
        if (hex.length() != 6) {
            return 0;
        }
        return FastColor.ARGB32.color((int)Integer.parseInt(hex.substring(0, 2), 16), (int)Integer.parseInt(hex.substring(2, 4), 16), (int)Integer.parseInt(hex.substring(4, 6), 16));
    }
}

