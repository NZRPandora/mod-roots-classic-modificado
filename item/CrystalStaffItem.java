/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.datacomponent.SpellDataList;
import elucent.rootsclassic.item.IManaRelatedItem;
import elucent.rootsclassic.item.SylvanArmorItem;
import elucent.rootsclassic.registry.RootsComponents;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CrystalStaffItem
extends Item
implements IManaRelatedItem {
    public CrystalStaffItem(Item.Properties properties) {
        super(properties.component(RootsComponents.SPELLS, (Object)SpellDataList.EMPTY).component(RootsComponents.SELECTED_SPELL, (Object)1));
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level levelAccessor, LivingEntity caster, int timeLeft) {
        SpellData selectedSpell = CrystalStaffItem.getSelectedSpell(stack);
        if (timeLeft < 71988 && selectedSpell != null) {
            Player player = (Player)caster;
            ResourceLocation compName = ResourceLocation.tryParse((String)selectedSpell.effect());
            if (compName != null) {
                ManaAttachment manaCap;
                ComponentBase comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(compName);
                if (comp == null || !caster.hasData(RootsAttachments.MANA)) {
                    return;
                }
                int potency = selectedSpell.potency() + 1;
                int efficiency = selectedSpell.efficiency();
                int size = selectedSpell.size();
                if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SylvanArmorItem && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SylvanArmorItem && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SylvanArmorItem && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SylvanArmorItem) {
                    ++potency;
                }
                if ((manaCap = (ManaAttachment)player.getData(RootsAttachments.MANA)).getMana() >= comp.getManaCost() / (float)(efficiency + 1)) {
                    manaCap.setMana(manaCap.getMana() - comp.getManaCost() / (float)(efficiency + 1));
                    comp.doEffect(levelAccessor, (Entity)caster, EnumCastType.SPELL, caster.getX() + 3.0 * caster.getLookAngle().x, caster.getY() + 3.0 * caster.getLookAngle().y, caster.getZ() + 3.0 * caster.getLookAngle().z, potency, efficiency, 3.0 + 2.0 * (double)size);
                    if (levelAccessor.isClientSide) {
                        for (int i = 0; i < 90; ++i) {
                            double offX = (double)levelAccessor.random.nextFloat() * 0.5 - 0.25;
                            double offY = (double)levelAccessor.random.nextFloat() * 0.5 - 0.25;
                            double offZ = (double)levelAccessor.random.nextFloat() * 0.5 - 0.25;
                            double coeff = (offX + offY + offZ) / 1.5 + 0.5;
                            double dx = (caster.getLookAngle().x + offX) * coeff;
                            double dy = (caster.getLookAngle().y + offY) * coeff;
                            double dz = (caster.getLookAngle().z + offZ) * coeff;
                            if (levelAccessor.random.nextBoolean()) {
                                levelAccessor.addParticle(MagicParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z), caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
                                continue;
                            }
                            levelAccessor.addParticle(MagicParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z), caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
                        }
                    }
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level levelAccessor, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.has(RootsComponents.SELECTED_SPELL)) {
            if (!player.isShiftKeyDown()) {
                if (levelAccessor.isClientSide && Minecraft.getInstance().screen != null) {
                    return new InteractionResultHolder(InteractionResult.FAIL, (Object)stack);
                }
                player.startUsingItem(hand);
                return new InteractionResultHolder(InteractionResult.PASS, (Object)stack);
            }
            int selected = (Integer)stack.get(RootsComponents.SELECTED_SPELL) + 1;
            if (selected > 4) {
                selected = 1;
            }
            stack.set(RootsComponents.SELECTED_SPELL, (Object)selected);
            return new InteractionResultHolder(InteractionResult.FAIL, (Object)stack);
        }
        return new InteractionResultHolder(InteractionResult.FAIL, (Object)stack);
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
        SpellData oldSpell = CrystalStaffItem.getSelectedSpell(oldS);
        SpellData newSpell = CrystalStaffItem.getSelectedSpell(newS);
        if (oldSpell != null && newSpell != null && (!oldSpell.effect().equals(newSpell.effect()) || oldS.get(RootsComponents.SELECTED_SPELL) != newS.get(RootsComponents.SELECTED_SPELL) || slotChanged)) {
            return true;
        }
        return slotChanged;
    }

    public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
        ComponentBase comp;
        ResourceLocation componentName;
        SpellData spell;
        if (stack.has(RootsComponents.SPELLS) && (spell = CrystalStaffItem.getSelectedSpell(stack)) != null && (componentName = ResourceLocation.tryParse((String)spell.effect())) != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(componentName)) != null) {
            int potency = spell.potency();
            int efficiency = spell.efficiency();
            int size = spell.size();
            comp.castingAction((Player)player, count, potency, efficiency, size);
            if (player.getCommandSenderWorld().isClientSide) {
                if (player.getRandom().nextBoolean()) {
                    player.getCommandSenderWorld().addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z), player.getX() + 2.0 * ((double)player.getRandom().nextFloat() - 0.5), player.getY() + 2.0 * ((double)player.getRandom().nextFloat() - 0.5) + 1.0, player.getZ() + 2.0 * ((double)player.getRandom().nextFloat() - 0.5), player.getX(), player.getY() + 1.0, player.getZ());
                } else {
                    player.getCommandSenderWorld().addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z), player.getX() + 2.0 * ((double)player.getRandom().nextFloat() - 0.5), player.getY() + 2.0 * ((double)player.getRandom().nextFloat() - 0.5) + 1.0, player.getZ() + 2.0 * ((double)player.getRandom().nextFloat() - 0.5), player.getX(), player.getY() + 1.0, player.getZ());
                }
            }
        }
    }

    public static void addEffect(ItemStack stack, int slot, String effect, int potency, int efficiency, int size) {
        SpellData data = new SpellData(potency, efficiency, size, effect);
        SpellDataList spells = (SpellDataList)stack.getOrDefault(RootsComponents.SPELLS, (Object)SpellDataList.EMPTY);
        NonNullList newSpellList = NonNullList.createWithCapacity((int)spells.spellList().size());
        if (slot < 1 || slot > spells.spellList().size()) {
            throw new IndexOutOfBoundsException("Slot " + slot + " is out of bounds for spell list of size " + spells.spellList().size());
        }
        for (int i = 0; i < spells.spellList().size(); ++i) {
            if (i == slot - 1) {
                newSpellList.add((Object)data);
                continue;
            }
            newSpellList.add((Object)((SpellData)spells.spellList().get(i)));
        }
        stack.set(RootsComponents.SPELLS, (Object)new SpellDataList((NonNullList<SpellData>)newSpellList));
    }

    public static SpellData getSelectedSpell(ItemStack stack) {
        if (stack.has(RootsComponents.SPELLS) && stack.has(RootsComponents.SELECTED_SPELL)) {
            SpellDataList spells = (SpellDataList)stack.get(RootsComponents.SPELLS);
            if (spells == null) {
                return null;
            }
            int selected = (Integer)stack.getOrDefault(RootsComponents.SELECTED_SPELL, (Object)1);
            return (SpellData)spells.spellList().get(selected - 1);
        }
        return null;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        SpellData selectedSpell = CrystalStaffItem.getSelectedSpell(stack);
        if (selectedSpell != null) {
            ComponentBase comp;
            String effect = selectedSpell.effect();
            ResourceLocation compName = ResourceLocation.tryParse((String)effect);
            if (compName != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(compName)) != null) {
                tooltip.add((Component)Component.translatable((String)"rootsclassic.tooltip.spelltypeheading").append(": ").withStyle(ChatFormatting.GOLD).append((Component)comp.getEffectName().withStyle(comp.getTextColor())));
            }
            tooltip.add((Component)Component.translatable((String)("  +" + selectedSpell.potency() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
            tooltip.add((Component)Component.translatable((String)("  +" + selectedSpell.efficiency() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
            tooltip.add((Component)Component.translatable((String)("  +" + selectedSpell.size() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
        }
    }
}

