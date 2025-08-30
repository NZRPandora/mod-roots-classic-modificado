/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.LazyLoadedValue
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.ArmorMaterial$Layer
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 */
package elucent.rootsclassic.item;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import elucent.rootsclassic.util.RootsUtil;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class SylvanArmorItem
extends ArmorItem {
    public SylvanArmorItem(Holder<ArmorMaterial> materialHolder, ArmorItem.Type type, Item.Properties builderIn) {
        super(materialHolder, type, builderIn);
    }

    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return Const.modLoc("textures/models/armor/sylvan.png");
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (slotId < 4) {
            RootsUtil.randomlyRepair(level.random, stack);
            if (level.random.nextInt(40) == 0 && entity instanceof Player) {
                Player player = (Player)entity;
                ManaAttachment mana = (ManaAttachment)player.getData(RootsAttachments.MANA);
                mana.setMana(mana.getMana() + 1.0f);
                player.setData(RootsAttachments.MANA, (Object)mana);
            }
        }
    }

    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add((Component)Component.empty());
        tooltipComponents.add((Component)Component.translatable((String)"rootsclassic.attribute.equipped").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add((Component)Component.literal((String)" ").append((Component)Component.translatable((String)"rootsclassic.attribute.increasedmanaregen")).withStyle(ChatFormatting.BLUE));
        tooltipComponents.add((Component)Component.empty());
        tooltipComponents.add((Component)Component.translatable((String)"rootsclassic.attribute.fullset").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add((Component)Component.literal((String)" +1 ").append((Component)Component.translatable((String)"rootsclassic.attribute.potency")).withStyle(ChatFormatting.BLUE));
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions(){
            private final LazyLoadedValue<HumanoidModel<?>> model = new LazyLoadedValue(() -> this.provideArmorModelForSlot(SylvanArmorItem.this.type));

            public HumanoidModel<?> provideArmorModelForSlot(ArmorItem.Type type) {
                return new SylvanArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientHandler.SYLVAN_ARMOR), type);
            }

            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return (HumanoidModel)this.model.get();
            }
        });
    }
}

