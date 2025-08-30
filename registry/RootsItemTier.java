/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.neoforged.neoforge.common.SimpleTier
 */
package elucent.rootsclassic.registry;

import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class RootsItemTier {
    public static final Tier LIVING = new SimpleTier(RootsTags.INCORRECT_FOR_LIVING_TOOL, 192, 6.0f, 2.0f, 18, () -> Ingredient.EMPTY);
    public static final Tier ENGRAVED = new SimpleTier(RootsTags.INCORRECT_FOR_ENGRAVED_TOOL, 1050, 5.0f, 8.0f, 5, () -> Ingredient.EMPTY);
}

