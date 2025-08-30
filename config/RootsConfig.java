/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.event.config.ModConfigEvent$Loading
 *  net.neoforged.fml.event.config.ModConfigEvent$Reloading
 *  net.neoforged.neoforge.common.ModConfigSpec
 *  net.neoforged.neoforge.common.ModConfigSpec$BooleanValue
 *  net.neoforged.neoforge.common.ModConfigSpec$Builder
 *  net.neoforged.neoforge.common.ModConfigSpec$DoubleValue
 *  net.neoforged.neoforge.common.ModConfigSpec$IntValue
 *  org.apache.commons.lang3.tuple.Pair
 */
package elucent.rootsclassic.config;

import elucent.rootsclassic.Roots;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class RootsConfig {
    public static final ModConfigSpec clientSpec;
    public static final Client CLIENT;
    public static final ModConfigSpec commonSpec;
    public static final Common COMMON;

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading configEvent) {
        Roots.LOGGER.debug("Loaded Roots Classic's config file {}", (Object)configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(ModConfigEvent.Reloading configEvent) {
        Roots.LOGGER.warn("Roots Classic's config just got changed on the file system!");
    }

    static {
        Pair specPair = new ModConfigSpec.Builder().configure(Client::new);
        clientSpec = (ModConfigSpec)specPair.getRight();
        CLIENT = (Client)specPair.getLeft();
        specPair = new ModConfigSpec.Builder().configure(Common::new);
        commonSpec = (ModConfigSpec)specPair.getRight();
        COMMON = (Common)specPair.getLeft();
    }

    public static class Client {
        public final ModConfigSpec.BooleanValue showTabletWave;
        public final ModConfigSpec.IntValue manaBarOffset;

        Client(ModConfigSpec.Builder builder) {
            builder.comment("Client settings").push("client");
            this.showTabletWave = builder.comment("Toggles the wave effect in the Runic Tablet GUI").define("showTabletWave", true);
            this.manaBarOffset = builder.comment("The number of pixels above the bottom of the screen that the mana bar should be rendered. If it's conflicting with a bar from another mod, raising it by 10 will normally position it right").defineInRange("manaBarOffset", 49, 0, Short.MAX_VALUE);
            builder.pop();
        }
    }

    public static class Common {
        public final ModConfigSpec.IntValue oldRootDropChance;
        public final ModConfigSpec.IntValue verdantSprigDropChance;
        public final ModConfigSpec.IntValue infernalStemDropChance;
        public final ModConfigSpec.IntValue dragonsEyeDropChance;
        public final ModConfigSpec.IntValue berriesDropChance;
        public final ModConfigSpec.DoubleValue barkKnifeBlockStripChance;
        public final ModConfigSpec.IntValue chargeTicks;
        public final ModConfigSpec.IntValue staffUses;
        public final ModConfigSpec.IntValue efficiencyBonus;
        public final ModConfigSpec.BooleanValue disablePVP;
        public final ModConfigSpec.IntValue ticksPerManaRegen;
        public final ModConfigSpec.IntValue staffUsesBasic;
        public final ModConfigSpec.IntValue staffUsesEfficiency;

        Common(ModConfigSpec.Builder builder) {
            builder.comment("Settings related to actual gameplay-affecting features").push("items");
            this.oldRootDropChance = builder.comment("Old Roots will drop from tall grass with a 1/oldRootDropChance probability").defineInRange("oldRootDropChance", 40, 0, Short.MAX_VALUE);
            this.verdantSprigDropChance = builder.comment("Verdant Sprigs will drop from grown crops with a 1/verdantSprigDropChance probability").defineInRange("verdantSprigDropChance", 30, 0, Short.MAX_VALUE);
            this.infernalStemDropChance = builder.comment("Infernal Bulbs will drop from nether wart with a 1/infernalBulbDropChance probability").defineInRange("infernalStemDropChance", 20, 0, Short.MAX_VALUE);
            this.dragonsEyeDropChance = builder.comment("Dragon's Eyes will drop from chorus flowers with a 1/dragonsEyeDropChance probability").defineInRange("dragonsEyeDropChance", 10, 0, Short.MAX_VALUE);
            this.berriesDropChance = builder.comment("Berries will drop from oak leaves with a 1/berriesDropChance probability").defineInRange("berriesDropChance", 25, 0, Short.MAX_VALUE);
            this.barkKnifeBlockStripChance = builder.comment("Chance that the bark knife will strip the log, 1 is always strip on first harvest").defineInRange("barkKnifeBlockStripChance", 0.3, 0.1, 1.0);
            builder.pop();
            builder.comment("Settings related to actual gameplay-affecting features").push("magic");
            this.chargeTicks = builder.comment("The number of ticks required to prepare a spell with a staff").defineInRange("staffChargeTicks", 20, 1, Short.MAX_VALUE);
            this.staffUses = builder.comment("The number of uses an unmodified staff will have upon being crafted").defineInRange("staffUses", 65, 0, Short.MAX_VALUE);
            this.efficiencyBonus = builder.comment("The number of additional uses each efficiency modifier gives").defineInRange("efficiencyBonusUses", 32, 0, Short.MAX_VALUE);
            this.disablePVP = builder.comment("Whether or not damaging spells can affect players").define("disablePVP", false);
            this.ticksPerManaRegen = builder.comment("Number of ticks between each mana regeneration (20 ticks = 1 second)").defineInRange("ticksPerManaRegen", 15, 1, 100);
            this.staffUsesBasic = builder.comment("Number of basic uses for one spell staff").defineInRange("staffUsesBasic", 15, 1, 100);
            this.staffUsesEfficiency = builder.comment("Number of uses added by each efficiency level on a spell").defineInRange("staffUsesEfficiency", 15, 1, 100);
            builder.pop();
        }
    }
}

