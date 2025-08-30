/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.entity.SpawnPlacementTypes
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent$Operation
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package elucent.rootsclassic.registry;

import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RootsEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create((Registry)BuiltInRegistries.ENTITY_TYPE, (String)"rootsclassic");
    public static final DeferredHolder<EntityType<?>, EntityType<PhantomSkeletonEntity>> PHANTOM_SKELETON = ENTITY_TYPES.register("phantom_skeleton", () -> RootsEntities.register("phantom_skeleton", EntityType.Builder.of(PhantomSkeletonEntity::new, (MobCategory)MobCategory.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(6)));
    public static final DeferredHolder<EntityType<?>, EntityType<EntityAccelerator>> ENTITY_ACCELERATOR = ENTITY_TYPES.register("entity_accelerator", () -> RootsEntities.register("entity_accelerator", EntityType.Builder.of(EntityAccelerator::new, (MobCategory)MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(64).updateInterval(20).setShouldReceiveVelocityUpdates(true)));
    public static final DeferredHolder<EntityType<?>, EntityType<EntityTileAccelerator>> TILE_ACCELERATOR = ENTITY_TYPES.register("tile_accelerator", () -> RootsEntities.register("tile_accelerator", EntityType.Builder.of(EntityTileAccelerator::new, (MobCategory)MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(64).updateInterval(20).setShouldReceiveVelocityUpdates(true)));

    @SubscribeEvent
    public static void onSpawnPlacementRegisterEvent(RegisterSpawnPlacementsEvent event) {
        event.register((EntityType)PHANTOM_SKELETON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType)PHANTOM_SKELETON.get(), PhantomSkeletonEntity.registerAttributes().build());
    }

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return builder.build(id);
    }
}

