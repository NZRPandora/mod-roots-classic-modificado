/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 */
package elucent.rootsclassic.client.particles.factory;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.client.particles.ParticleColor;
import elucent.rootsclassic.registry.ParticleRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class MagicParticleTypeData
implements ParticleOptions {
    private ParticleType<MagicParticleTypeData> type;
    public static final MapCodec<MagicParticleTypeData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group((App)ParticleColor.CODEC.fieldOf("color").forGetter(p_253371_ -> p_253371_.color)).apply((Applicative)instance, MagicParticleTypeData::new));
    public ParticleColor color;
    public static final StreamCodec<RegistryFriendlyByteBuf, MagicParticleTypeData> STREAM_CODEC = StreamCodec.composite(ParticleColor.STREAM_CODEC, p_319429_ -> p_319429_.color, MagicParticleTypeData::new);

    public MagicParticleTypeData(ParticleType<MagicParticleTypeData> particleTypeData, ParticleColor color) {
        this.type = particleTypeData;
        this.color = color;
    }

    public MagicParticleTypeData(float r, float g, float b, float a) {
        this(ParticleRegistry.MAGIC_TYPE.get(), new ParticleColor(r, g, b, a));
    }

    public MagicParticleTypeData(ParticleColor vector3f) {
        this(ParticleRegistry.MAGIC_TYPE.get(), vector3f);
    }

    public ParticleType<?> getType() {
        return this.type;
    }
}

