/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.registry.RootsComponents;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

public class EngravedBladeItem
extends SwordItem {
    private final String[] numerals = new String[]{"0", "I", "II", "III", "IIII"};

    public EngravedBladeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes((Tier)tier, (int)attackDamageIn, (float)attackSpeedIn)));
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        if (stack.has(RootsComponents.SPIKES)) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.tooltip.spikes").append(" " + this.numerals[(Integer)stack.getOrDefault(RootsComponents.SPIKES, (Object)0)]).withStyle(ChatFormatting.WHITE));
        }
        if (stack.has(RootsComponents.FORCEFUL)) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.tooltip.forceful").append(" " + this.numerals[(Integer)stack.getOrDefault(RootsComponents.FORCEFUL, (Object)0)]).withStyle(ChatFormatting.DARK_GRAY));
        }
        if (stack.has(RootsComponents.HOLY)) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.tooltip.holy").append(" " + this.numerals[(Integer)stack.getOrDefault(RootsComponents.HOLY, (Object)0)]).withStyle(ChatFormatting.GOLD));
        }
        if (stack.has(RootsComponents.AQUATIC)) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.tooltip.aquatic").append(" " + this.numerals[(Integer)stack.getOrDefault(RootsComponents.AQUATIC, (Object)0)]).withStyle(ChatFormatting.AQUA));
        }
        if (stack.has(RootsComponents.SHADOWSTEP)) {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.tooltip.shadowstep").append(" " + this.numerals[(Integer)stack.getOrDefault(RootsComponents.SHADOWSTEP, (Object)0)]).withStyle(ChatFormatting.DARK_PURPLE));
        }
    }

    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }
}

