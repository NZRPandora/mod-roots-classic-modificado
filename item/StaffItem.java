/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.stats.Stats
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
 *  net.minecraft.world.phys.Vec3
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.datacomponent.StaffUses;
import elucent.rootsclassic.item.IManaRelatedItem;
import elucent.rootsclassic.item.SylvanArmorItem;
import elucent.rootsclassic.registry.RootsComponents;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
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
import net.minecraft.world.phys.Vec3;

public class StaffItem
extends Item
implements IManaRelatedItem {
    private static final double RANGE = 3.0;
    private static final double SIZE_PER_LEVEL = 2.0;
    private static final double SIZE_BASE = 3.0;

    public StaffItem(Item.Properties properties) {
        super(properties);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public int getBarWidth(ItemStack stack) {
        if (stack.has(RootsComponents.STAFF_USES)) {
            StaffUses staffUses = (StaffUses)stack.get(RootsComponents.STAFF_USES);
            return Math.round((float)staffUses.uses() * 13.0f / (float)staffUses.maxUses());
        }
        return 1;
    }

    public boolean isBarVisible(ItemStack stack) {
        if (stack.has(RootsComponents.STAFF_USES)) {
            StaffUses staffUses = (StaffUses)stack.get(RootsComponents.STAFF_USES);
            return staffUses.uses() < staffUses.maxUses();
        }
        return false;
    }

    public void releaseUsing(ItemStack stack, Level levelAccessor, LivingEntity caster, int timeLeft) {
        StaffUses staffUses;
        if (timeLeft < 71988 && stack.has(RootsComponents.STAFF_USES) && stack.has(RootsComponents.SPELL) && caster.hasData(RootsAttachments.MANA) && (staffUses = (StaffUses)stack.get(RootsComponents.STAFF_USES)).uses() >= 0) {
            ComponentBase comp;
            staffUses = new StaffUses(staffUses.uses() - 1, staffUses.maxUses());
            stack.set(RootsComponents.STAFF_USES, (Object)staffUses);
            SpellData spellData = (SpellData)stack.get(RootsComponents.SPELL);
            ResourceLocation compName = ResourceLocation.tryParse((String)spellData.effect());
            if (compName != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(compName)) != null) {
                ManaAttachment mana;
                int potency = spellData.potency();
                int efficiency = spellData.efficiency();
                int size = spellData.size();
                Player player = (Player)caster;
                if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SylvanArmorItem && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SylvanArmorItem && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SylvanArmorItem && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SylvanArmorItem) {
                    ++potency;
                }
                if ((mana = (ManaAttachment)caster.getData(RootsAttachments.MANA)).getMana() >= comp.getManaCost() / (float)(efficiency + 1)) {
                    mana.setMana(mana.getMana() - comp.getManaCost() / (float)(efficiency + 1));
                    caster.setData(RootsAttachments.MANA, (Object)mana);
                    Vec3 lookVec = caster.getLookAngle();
                    comp.doEffect(levelAccessor, (Entity)caster, EnumCastType.SPELL, caster.getX() + 3.0 * lookVec.x, caster.getY() + 3.0 * lookVec.y, caster.getZ() + 3.0 * lookVec.z, potency, efficiency, 3.0 + 2.0 * (double)size);
                    if (levelAccessor.isClientSide) {
                        for (int i = 0; i < 90; ++i) {
                            double offX = (double)levelAccessor.random.nextFloat() * 0.5 - 0.25;
                            double offY = (double)levelAccessor.random.nextFloat() * 0.5 - 0.25;
                            double offZ = (double)levelAccessor.random.nextFloat() * 0.5 - 0.25;
                            double coeff = (offX + offY + offZ) / 1.5 + 0.5;
                            double dx = (lookVec.x + offX) * coeff;
                            double dy = (lookVec.y + offY) * coeff;
                            double dz = (lookVec.z + offZ) * coeff;
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
        if (levelAccessor.isClientSide && Minecraft.getInstance().screen != null) {
            return new InteractionResultHolder(InteractionResult.FAIL, (Object)stack);
        }
        player.startUsingItem(hand);
        return new InteractionResultHolder(InteractionResult.PASS, (Object)stack);
    }

    public void inventoryTick(ItemStack stack, Level levelAccessor, Entity entityIn, int itemSlot, boolean isSelected) {
        StaffUses staffUses;
        if (stack.has(RootsComponents.STAFF_USES) && (staffUses = (StaffUses)stack.get(RootsComponents.STAFF_USES)).uses() <= 0 && entityIn instanceof Player) {
            stack.shrink(1);
            if (entityIn instanceof Player) {
                Player player = (Player)entityIn;
                player.awardStat(Stats.ITEM_BROKEN.get((Object)stack.getItem()));
            }
        }
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
        if (oldS.has(RootsComponents.SPELL) && newS.has(RootsComponents.SPELL) && !((SpellData)oldS.get(RootsComponents.SPELL)).equals(newS.get(RootsComponents.SPELL))) {
            return true;
        }
        return slotChanged;
    }

    public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
        ComponentBase comp;
        SpellData spellData;
        ResourceLocation componentName;
        if (stack.has(RootsComponents.SPELL) && (componentName = ResourceLocation.tryParse((String)(spellData = (SpellData)stack.get(RootsComponents.SPELL)).effect())) != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(componentName)) != null) {
            int potency = spellData.potency();
            int efficiency = spellData.efficiency();
            int size = spellData.size();
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

    public static void createData(ItemStack stack, String effect, int potency, int efficiency, int size) {
        stack.set(RootsComponents.SPELL, (Object)new SpellData(potency, efficiency, size, effect));
        int uses = StaffItem.getMaxUsesBase() + StaffItem.getMaxUsesPerEfficiency() * efficiency;
        stack.set(RootsComponents.STAFF_USES, (Object)new StaffUses(uses, uses));
    }

    public static int getMaxUsesBase() {
        return (Integer)RootsConfig.COMMON.staffUsesBasic.get();
    }

    public static int getMaxUsesPerEfficiency() {
        return (Integer)RootsConfig.COMMON.staffUsesEfficiency.get();
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        if (stack.has(RootsComponents.STAFF_USES) && stack.has(RootsComponents.SPELL)) {
            ComponentBase comp;
            SpellData spellData = (SpellData)stack.get(RootsComponents.SPELL);
            StaffUses staffUses = (StaffUses)stack.get(RootsComponents.STAFF_USES);
            ResourceLocation compName = ResourceLocation.tryParse((String)spellData.effect());
            if (compName != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(compName)) != null) {
                tooltip.add((Component)Component.translatable((String)"rootsclassic.tooltip.spelltypeheading").append(": ").withStyle(ChatFormatting.GOLD).append((Component)comp.getEffectName().withStyle(comp.getTextColor())));
            }
            tooltip.add((Component)Component.translatable((String)("  +" + spellData.potency() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
            tooltip.add((Component)Component.translatable((String)("  +" + spellData.efficiency() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
            tooltip.add((Component)Component.translatable((String)("  +" + spellData.size() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
            tooltip.add((Component)Component.empty());
            tooltip.add((Component)Component.translatable((String)(staffUses.uses() + " ")).append((Component)Component.translatable((String)"rootsclassic.tooltip.usesremaining")).append(".").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.add((Component)Component.translatable((String)"rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
        }
    }
}

