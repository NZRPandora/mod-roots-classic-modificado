/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.util.ExtraCodecs
 */
package elucent.rootsclassic.datacomponent;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record StaffUses(int uses, int maxUses) {
    public static final Codec<StaffUses> CODEC = RecordCodecBuilder.create(inst -> inst.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("uses").forGetter(StaffUses::uses), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("maxUses").forGetter(StaffUses::maxUses)).apply((Applicative)inst, StaffUses::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, StaffUses> STREAM_CODEC = StreamCodec.composite((StreamCodec)ByteBufCodecs.INT, StaffUses::uses, (StreamCodec)ByteBufCodecs.INT, StaffUses::maxUses, StaffUses::new);
}

