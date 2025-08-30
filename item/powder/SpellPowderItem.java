/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.RecipeInput
 */
package elucent.rootsclassic.item.powder;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.registry.RootsComponents;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeInput;

public class SpellPowderItem
extends Item {
    public SpellPowderItem(Item.Properties properties) {
        super(properties);
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
        return slotChanged;
    }

    public static void createData(ItemStack stack, ResourceLocation effect, RecipeInput recipeInput) {
        CompoundTag nbt = new CompoundTag();
        int potency = 0;
        int efficiency = 0;
        int size = 0;
        if (recipeInput != null) {
            for (int i = 0; i < recipeInput.size(); ++i) {
                ItemStack itemStack = recipeInput.getItem(i);
                if (itemStack.isEmpty()) continue;
                if (itemStack.getItem() == Items.GLOWSTONE_DUST) {
                    ++potency;
                }
                if (itemStack.getItem() == Items.REDSTONE) {
                    ++efficiency;
                }
                if (itemStack.getItem() != Items.GUNPOWDER) continue;
                ++size;
            }
        }
        stack.set(RootsComponents.SPELL, (Object)new SpellData(potency, efficiency, size, effect.toString()));
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        if (stack.has(RootsComponents.SPELL)) {
            ComponentBase comp;
            SpellData spell = (SpellData)stack.get(RootsComponents.SPELL);
            ResourceLocation compName = ResourceLocation.tryParse((String)spell.effect());
            if (compName != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(compName)) != null) {
                tooltip.add((Component)Component.translatable((String)"rootsclassic.tooltip.spelltypeheading").append(": ").withStyle(ChatFormatting.GOLD).append((Component)comp.getEffectName().withStyle(comp.getTextColor())));
            }
            tooltip.add((Component)Component.translatable((String)("  +" + spell.potency() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
            tooltip.add((Component)Component.translatable((String)("  +" + spell.efficiency() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
            tooltip.add((Component)Component.translatable((String)("  +" + spell.size() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
        }
    }
}

