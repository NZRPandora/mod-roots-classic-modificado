/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.food.FoodProperties$Builder
 */
package elucent.rootsclassic.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class RootsFoods {
    public static final FoodProperties OLD_ROOT = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static final FoodProperties DRAGONS_EYE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties NIGHTSHADE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).effect(() -> new MobEffectInstance(MobEffects.POISON, 320), 1.0f).build();
    public static final FoodProperties BLACKCURRANT = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).build();
    public static final FoodProperties REDCURRANT = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).build();
    public static final FoodProperties WHITECURRANT = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).build();
    public static final FoodProperties ELDERBERRY = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties HEALING_POULTICE = new FoodProperties.Builder().nutrition(0).saturationModifier(0.0f).alwaysEdible().build();
    private static final float COOKED_BEEF_SATURATION = 0.8f;
    public static final FoodProperties ROOTY_STEW = new FoodProperties.Builder().nutrition(7).saturationModifier(1.0f).build();
    public static final FoodProperties FRUIT_SALAD = new FoodProperties.Builder().nutrition(8).saturationModifier(0.90000004f).build();
}

