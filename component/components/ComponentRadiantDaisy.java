/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.phys.AABB
 */
package elucent.rootsclassic.component.components;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsDamageTypes;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public class ComponentRadiantDaisy
extends ComponentBase {
    public ComponentRadiantDaisy() {
        super((Block)RootsRegistry.RADIANT_DAISY.get(), 24);
    }

    @Override
    public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (caster instanceof LivingEntity) {
            LivingEntity livingCaster = (LivingEntity)caster;
            double posX = livingCaster.getX() + livingCaster.getLookAngle().x * 0.5;
            double posY = livingCaster.getY() + 1.5 + livingCaster.getLookAngle().y * 0.5;
            double posZ = livingCaster.getZ() + livingCaster.getLookAngle().z * 0.5;
            double potencyMod = 0.0;
            double motionX = livingCaster.getLookAngle().x * 0.25;
            double motionY = livingCaster.getLookAngle().y * 0.25;
            double motionZ = livingCaster.getLookAngle().z * 0.25;
            int i = 0;
            while ((double)i < 200.0 + 100.0 * size) {
                boolean didHit = false;
                if (level.isClientSide) {
                    level.addParticle(MagicAuraParticleData.createData(255.0, 255.0, 255.0), posX, posY, posZ, 0.0, 0.0, 0.0);
                }
                if (!level.isClientSide) {
                    List targets;
                    if (!(targets = level.getEntitiesOfClass(LivingEntity.class, new AABB((posX += motionX) - 0.25, (posY += motionY) - 0.25, (posZ += motionZ) - 0.25, posX + 0.25, posY + 0.25, posZ + 0.25))).isEmpty()) {
                        targets.removeIf(target -> target.getUUID() == livingCaster.getUUID());
                        for (int j = 0; j < targets.size() && !didHit; ++j) {
                            if (targets.get(j) instanceof Player && ((Boolean)RootsConfig.COMMON.disablePVP.get()).booleanValue()) continue;
                            didHit = true;
                            ((LivingEntity)targets.get(j)).hurt(ComponentRadiantDaisy.spellAttack(livingCaster), (float)(12.0 + 3.0 * (potency + potencyMod)));
                            ((LivingEntity)targets.get(j)).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40 + (int)(20.0 * (potency + potencyMod)), 0));
                            ((LivingEntity)targets.get(j)).setLastHurtMob((Entity)livingCaster);
                            ((LivingEntity)targets.get(j)).setLastHurtByMob(livingCaster);
                        }
                    }
                    if (level.getBlockState(BlockPos.containing((double)posX, (double)posY, (double)posZ)).canOcclude()) {
                        if (potency - potencyMod == -1.0) {
                            didHit = true;
                        } else {
                            potencyMod -= 0.5;
                            if (!level.getBlockState(BlockPos.containing((double)(posX + 1.0), (double)posY, (double)posZ)).canOcclude() && motionX < 0.0) {
                                motionX *= -1.0;
                            }
                            if (!level.getBlockState(BlockPos.containing((double)(posX - 1.0), (double)posY, (double)posZ)).canOcclude() && motionX > 0.0) {
                                motionX *= -1.0;
                            }
                            if (!level.getBlockState(BlockPos.containing((double)posX, (double)(posY + 1.0), (double)posZ)).canOcclude() && motionY < 0.0) {
                                motionY *= -1.0;
                            }
                            if (!level.getBlockState(BlockPos.containing((double)posX, (double)(posY - 1.0), (double)posZ)).canOcclude() && motionY > 0.0) {
                                motionY *= -1.0;
                            }
                            if (!level.getBlockState(BlockPos.containing((double)posX, (double)posY, (double)(posZ + 1.0))).canOcclude() && motionZ < 0.0) {
                                motionZ *= -1.0;
                            }
                            if (!level.getBlockState(BlockPos.containing((double)posX, (double)posY, (double)(posZ - 1.0))).canOcclude() && motionZ > 0.0) {
                                motionZ *= -1.0;
                            }
                        }
                    }
                    if (didHit) {
                        return;
                    }
                }
                ++i;
            }
        }
    }

    public static DamageSource spellAttack(LivingEntity attacker) {
        return attacker.damageSources().source(RootsDamageTypes.GENERIC, (Entity)attacker);
    }
}

