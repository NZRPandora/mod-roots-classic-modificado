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

public record SpellData(int potency, int efficiency, int size, String effect) {
    public static final SpellData EMPTY = new SpellData(0, 0, 0, "");
    public static final Codec<SpellData> CODEC = RecordCodecBuilder.create(inst -> inst.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("potency").forGetter(SpellData::potency), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("efficiency").forGetter(SpellData::efficiency), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("size").forGetter(SpellData::size), (App)Codec.STRING.fieldOf("effect").forGetter(SpellData::effect)).apply((Applicative)inst, SpellData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellData> STREAM_CODEC = StreamCodec.composite((StreamCodec)ByteBufCodecs.INT, SpellData::potency, (StreamCodec)ByteBufCodecs.INT, SpellData::efficiency, (StreamCodec)ByteBufCodecs.INT, SpellData::size, (StreamCodec)ByteBufCodecs.STRING_UTF8, SpellData::effect, SpellData::new);
}

