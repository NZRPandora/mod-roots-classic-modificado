/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.damagesource.DamageType
 */
package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class RootsDamageTypes {
    public static final ResourceKey<DamageType> CACTUS = RootsDamageTypes.register("cactus");
    public static final ResourceKey<DamageType> FIRE = RootsDamageTypes.register("fire");
    public static final ResourceKey<DamageType> GENERIC = RootsDamageTypes.register("generic");
    public static final ResourceKey<DamageType> WITHER = RootsDamageTypes.register("wither");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create((ResourceKey)Registries.DAMAGE_TYPE, (ResourceLocation)Const.modLoc(name));
    }
}

