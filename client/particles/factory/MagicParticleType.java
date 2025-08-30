/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.MapCodec
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 */
package elucent.rootsclassic.client.particles.factory;

import com.mojang.serialization.MapCodec;
import elucent.rootsclassic.client.particles.factory.MagicParticleTypeData;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class MagicParticleType
extends ParticleType<MagicParticleTypeData> {
    public MagicParticleType() {
        super(false);
    }

    public MapCodec<MagicParticleTypeData> codec() {
        return MagicParticleTypeData.CODEC;
    }

    public StreamCodec<? super RegistryFriendlyByteBuf, MagicParticleTypeData> streamCodec() {
        return MagicParticleTypeData.STREAM_CODEC;
    }
}

