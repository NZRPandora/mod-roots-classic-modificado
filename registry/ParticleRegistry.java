/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package elucent.rootsclassic.registry;

import elucent.rootsclassic.client.particles.factory.MagicParticleType;
import elucent.rootsclassic.client.particles.factory.MagicParticleTypeData;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create((Registry)BuiltInRegistries.PARTICLE_TYPE, (String)"rootsclassic");
    public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_TYPE = PARTICLE_TYPES.register("magic", MagicParticleType::new);
    public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_AURA_TYPE = PARTICLE_TYPES.register("magic_aura", MagicParticleType::new);
    public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_TYPE = PARTICLE_TYPES.register("magic_altar", MagicParticleType::new);
    public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_LINE_TYPE = PARTICLE_TYPES.register("magic_altar_line", MagicParticleType::new);
    public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_LINE_TYPE = PARTICLE_TYPES.register("magic_line", MagicParticleType::new);
}

