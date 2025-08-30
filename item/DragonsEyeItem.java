/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.item.RootsFoodItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DragonsEyeItem
extends RootsFoodItem {
    public DragonsEyeItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level levelAccessor, LivingEntity entityLiving) {
        super.finishUsingItem(stack, levelAccessor, entityLiving);
        if (!levelAccessor.isClientSide) {
            double d0 = entityLiving.getX();
            double d1 = entityLiving.getY();
            double d2 = entityLiving.getZ();
            for (int i = 0; i < 32; ++i) {
                double d3 = entityLiving.getX() + (entityLiving.getRandom().nextDouble() - 0.5) * 32.0;
                double d4 = Mth.clamp((double)(entityLiving.getY() + (double)(entityLiving.getRandom().nextInt(32) - 8)), (double)0.0, (double)(levelAccessor.getHeight() - 1));
                double d5 = entityLiving.getZ() + (entityLiving.getRandom().nextDouble() - 0.5) * 32.0;
                if (entityLiving.isPassenger()) {
                    entityLiving.stopRiding();
                }
                if (!entityLiving.randomTeleport(d3, d4, d5, true)) continue;
                levelAccessor.playSound((Player)null, d0, d1, d2, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
                entityLiving.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0f, 1.0f);
                break;
            }
            if (entityLiving instanceof Player) {
                ((Player)entityLiving).getCooldowns().addCooldown((Item)this, 20);
            }
        }
        return stack;
    }
}

