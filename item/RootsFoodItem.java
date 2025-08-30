/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class RootsFoodItem
extends Item {
    private static final int HEAL_LARGE = 5;
    private static final int HEAL_SMALL = 2;

    public RootsFoodItem(Item.Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level levelAccessor, LivingEntity entityLiving) {
        Item item = stack.getItem();
        super.finishUsingItem(stack, levelAccessor, entityLiving);
        if (item == RootsRegistry.REDCURRANT.get()) {
            entityLiving.heal(2.0f);
        }
        if (item == RootsRegistry.ELDERBERRY.get()) {
            entityLiving.removeAllEffects();
        }
        if (item == RootsRegistry.HEALING_POULTICE.get()) {
            entityLiving.heal(5.0f);
        }
        return stack;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        if (stack.is((Item)RootsRegistry.REDCURRANT.get())) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.healingitem.tooltip").withStyle(ChatFormatting.GRAY));
        }
        if (stack.is((Item)RootsRegistry.ELDERBERRY.get())) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.clearpotionsitem.tooltip").withStyle(ChatFormatting.GRAY));
        }
        if (stack.is((Item)RootsRegistry.HEALING_POULTICE.get())) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.healingitem.tooltip").withStyle(ChatFormatting.GRAY));
        }
        if (stack.is((Item)RootsRegistry.NIGHTSHADE.get())) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.poisonitem.tooltip").withStyle(ChatFormatting.GRAY));
        }
    }
}

