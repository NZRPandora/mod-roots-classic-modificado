/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.tags.EntityTypeTags
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.neoforge.event.entity.living.LivingDropsEvent
 *  net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent
 *  net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
 *  net.neoforged.neoforge.event.tick.EntityTickEvent$Pre
 */
package elucent.rootsclassic.event;

import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class ComponentSpellsEvent {
    public static int TICKS_PER_MANA_REGEN = 5;

    @SubscribeEvent
    public void onLivingTick(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity entity2 = (LivingEntity)entity;
            Entity entity3 = event.getEntity();
            if (entity3 instanceof Player) {
                Player player = (Player)entity3;
                this.wildwoodArmorRegenFullset(player);
                this.tickManaRegen(entity2);
            }
            this.tickSkipMovementCurse(event, entity2);
        }
    }

    private void tickSkipMovementCurse(EntityTickEvent.Pre event, LivingEntity entity) {
        int skipTicks;
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains("RMOD_trackTicks") && persistentData.contains("rootsclassic:RMOD_skipTicks") && (skipTicks = persistentData.getInt("rootsclassic:RMOD_skipTicks")) > 0) {
            persistentData.putInt("rootsclassic:RMOD_skipTicks", skipTicks - 1);
            if (skipTicks <= 0) {
                persistentData.remove("rootsclassic:RMOD_skipTicks");
                RootsUtil.decrementTickTracking((Entity)entity);
            }
            event.setCanceled(true);
        }
    }

    private void tickManaRegen(LivingEntity entity) {
        if (entity.tickCount % TICKS_PER_MANA_REGEN == 0) {
            ManaAttachment attachment = (ManaAttachment)entity.getData(RootsAttachments.MANA);
            attachment.setMana(attachment.getMana() + 1.0f);
            entity.setData(RootsAttachments.MANA, (Object)attachment);
        }
    }

    private void wildwoodArmorRegenFullset(Player entity) {
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
        if (head.getItem() == RootsRegistry.WILDWOOD_MASK.get() && chest.getItem() == RootsRegistry.WILDWOOD_PLATE.get() && legs.getItem() == RootsRegistry.WILDWOOD_LEGGINGS.get() && feet.getItem() == RootsRegistry.WILDWOOD_BOOTS.get() && entity.level().random.nextDouble() < 0.02 && entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0f);
        }
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        CompoundTag persistentData = event.getEntity().getPersistentData();
        if (persistentData.contains("rootsclassic:RMOD_dropItems") && !persistentData.getBoolean("rootsclassic:RMOD_dropItems")) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingXP(LivingExperienceDropEvent event) {
        CompoundTag persistentData = event.getEntity().getPersistentData();
        if (persistentData.contains("rootsclassic:RMOD_dropItems") && !persistentData.getBoolean("rootsclassic:RMOD_dropItems")) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingIncomingDamageEvent event) {
        Entity sword;
        Player player;
        LivingEntity entityLiving = event.getEntity();
        CompoundTag persistentData = entityLiving.getPersistentData();
        if (persistentData.contains("rootsclassic:RMOD_vuln")) {
            event.setAmount((float)((double)event.getAmount() * (1.0 + persistentData.getDouble("rootsclassic:RMOD_vuln"))));
            persistentData.remove("rootsclassic:RMOD_vuln");
        }
        DamageSource source = event.getSource();
        if (persistentData.contains("rootsclassic:RMOD_thornsDamage") && source != null && source.getEntity() instanceof LivingEntity) {
            ((LivingEntity)event.getSource().getEntity()).hurt(entityLiving.damageSources().cactus(), persistentData.getFloat("rootsclassic:RMOD_thornsDamage"));
            persistentData.remove("rootsclassic:RMOD_thornsDamage");
            RootsUtil.decrementTickTracking((Entity)entityLiving);
        }
        if (entityLiving instanceof Player && !(player = (Player)entityLiving).getInventory().getSelected().isEmpty() && player.getInventory().getSelected().getItem() == RootsRegistry.ENGRAVED_BLADE.get() && (sword = player.getInventory().getSelected()).has(RootsComponents.SHADOWSTEP)) {
            int stepLvl = (Integer)sword.getOrDefault(RootsComponents.SHADOWSTEP, (Object)0);
            double chance = (double)stepLvl * 12.5;
            if ((double)player.getCommandSenderWorld().random.nextInt(100) < chance) {
                event.setCanceled(true);
            }
        }
        if (source != null && (sword = source.getEntity()) instanceof Player) {
            player = (Player)sword;
            if (!event.getEntity().getCommandSenderWorld().isClientSide && !player.getInventory().getSelected().isEmpty() && player.getInventory().getSelected().getItem() == RootsRegistry.ENGRAVED_BLADE.get()) {
                float currentAmount;
                sword = player.getInventory().getSelected();
                if (sword.has(RootsComponents.AQUATIC)) {
                    int aquaLvl = (Integer)sword.getOrDefault(RootsComponents.AQUATIC, (Object)0);
                    float amount = (float)aquaLvl * 0.5f;
                    event.getEntity().hurt(entityLiving.damageSources().drown(), amount);
                }
                if (sword.has(RootsComponents.HOLY) && entityLiving.getType().is(EntityTypeTags.UNDEAD)) {
                    int holyLvl = (Integer)sword.getOrDefault(RootsComponents.HOLY, (Object)0);
                    float amount = (float)holyLvl * 1.5f;
                    currentAmount = event.getAmount();
                    event.setAmount(currentAmount + amount);
                }
                if (sword.has(RootsComponents.SPIKES)) {
                    int spikeLvl = (Integer)sword.getOrDefault(RootsComponents.SPIKES, (Object)0);
                    float amount = spikeLvl;
                    currentAmount = event.getAmount();
                    event.setAmount(currentAmount + amount);
                }
            }
        }
    }
}

