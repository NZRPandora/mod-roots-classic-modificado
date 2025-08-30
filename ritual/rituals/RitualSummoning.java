/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SpawnEggItem
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.neoforged.neoforge.common.DeferredSpawnEggItem
 *  net.neoforged.neoforge.event.EventHooks
 */
package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualEffect;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.EventHooks;

public class RitualSummoning
extends RitualEffect {
    @Override
    public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
        if (!levelAccessor.isClientSide) {
            ResourceLocation entityId = ResourceLocation.tryParse((String)config.getString("entity"));
            if (entityId == null) {
                return;
            }
            EntityType entityType = (EntityType)BuiltInRegistries.ENTITY_TYPE.get(entityId);
            if (entityType == null) {
                return;
            }
            Entity toSpawn = entityType.create(levelAccessor);
            if (toSpawn != null) {
                if (toSpawn instanceof Mob) {
                    Mob mob = (Mob)toSpawn;
                    if (levelAccessor instanceof ServerLevel) {
                        ServerLevel sl = (ServerLevel)levelAccessor;
                        EventHooks.finalizeMobSpawn((Mob)mob, (ServerLevelAccessor)sl, (DifficultyInstance)levelAccessor.getCurrentDifficultyAt(pos), (MobSpawnType)MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null);
                    }
                }
                toSpawn.setPos((double)pos.getX() + 0.5, (double)pos.getY() + 2.0, (double)pos.getZ() + 0.5);
                inventory.clearContent();
                levelAccessor.addFreshEntity(toSpawn);
                BlockEntity tile = levelAccessor.getBlockEntity(pos);
                if (tile != null) {
                    tile.setChanged();
                }
            }
        }
    }

    @Override
    public MutableComponent getInfoText(CompoundTag config) {
        EntityType entityType = (EntityType)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse((String)config.getString("entity")));
        SpawnEggItem egg = DeferredSpawnEggItem.deferredOnlyById((EntityType)entityType);
        if (egg == null) {
            return Component.empty();
        }
        return Component.translatable((String)"rootsclassic.jei.tooltip.summoning", (Object[])new Object[]{entityType.getDescription()});
    }

    @Override
    public ItemStack getResult(CompoundTag config, HolderLookup.Provider provider) {
        EntityType entityType = (EntityType)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse((String)config.getString("entity")));
        SpawnEggItem egg = DeferredSpawnEggItem.deferredOnlyById((EntityType)entityType);
        if (egg == null) {
            return super.getResult(config, provider);
        }
        MutableComponent display = this.getInfoText(config);
        ItemStack stack = new ItemStack((ItemLike)egg);
        stack.set(DataComponents.CUSTOM_NAME, (Object)display.withStyle(Style.EMPTY.withItalic(Boolean.valueOf(false))));
        return stack;
    }
}

