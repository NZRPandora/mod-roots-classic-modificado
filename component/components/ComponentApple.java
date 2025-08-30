/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import java.util.ArrayList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ComponentApple
extends ComponentBase {
    public ComponentApple() {
        super(Items.APPLE, 16);
    }

    @Override
    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL && caster instanceof Player) {
            Player player = (Player)caster;
            ArrayList effects = new ArrayList(player.getActiveEffects());
            player.removeAllEffects();
            for (MobEffectInstance effect : effects) {
                if (effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, effect.getDuration(), effect.getAmplifier() + (int)potency));
                }
                if (effect.getEffect() == MobEffects.DIG_SLOWDOWN) {
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, effect.getDuration(), effect.getAmplifier() + (int)potency));
                }
                if (effect.getEffect() == MobEffects.POISON) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, effect.getDuration(), effect.getAmplifier() + (int)potency));
                }
                if (effect.getEffect() == MobEffects.WITHER) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, effect.getDuration(), effect.getAmplifier() + (int)potency));
                }
                if (effect.getEffect() == MobEffects.BLINDNESS) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, effect.getDuration(), effect.getAmplifier() + (int)potency));
                }
                if (effect.getEffect() == MobEffects.CONFUSION) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, effect.getDuration(), effect.getAmplifier() + (int)potency));
                }
                if (effect.getEffect() == MobEffects.HUNGER) {
                    player.addEffect(new MobEffectInstance(MobEffects.SATURATION, effect.getDuration(), effect.getAmplifier() + (int)potency));
                }
                if (effect.getEffect() != MobEffects.WEAKNESS) continue;
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, effect.getDuration(), effect.getAmplifier() + (int)potency));
            }
        }
    }
}

