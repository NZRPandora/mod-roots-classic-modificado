/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 */
package elucent.rootsclassic.datacomponent;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.datacomponent.SpellData;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record SpellDataList(NonNullList<SpellData> spellList) {
    public static final SpellDataList EMPTY = new SpellDataList((NonNullList<SpellData>)NonNullList.withSize((int)4, (Object)SpellData.EMPTY));
    public static final Codec<SpellDataList> CODEC = RecordCodecBuilder.create(inst -> inst.group((App)SpellData.CODEC.listOf().fieldOf("spellList").flatXmap(strings -> {
        Object[] ingredients = (SpellData[])strings.toArray(SpellData[]::new);
        if (ingredients.length == 0) {
            return DataResult.error(() -> "No spell data");
        }
        return ingredients.length > 4 ? DataResult.error(() -> "Too much spell data. The maximum amount of spells is 4") : DataResult.success((Object)NonNullList.of((Object)SpellData.EMPTY, (Object[])ingredients));
    }, DataResult::success).forGetter(SpellDataList::spellList)).apply((Applicative)inst, SpellDataList::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellDataList> STREAM_CODEC = StreamCodec.of(SpellDataList::toNetwork, SpellDataList::fromNetwork);

    private static SpellDataList fromNetwork(RegistryFriendlyByteBuf byteBuf) {
        int i = byteBuf.readVarInt();
        NonNullList spellList = NonNullList.withSize((int)i, (Object)SpellData.EMPTY);
        spellList.replaceAll(data -> (SpellData)SpellData.STREAM_CODEC.decode((Object)byteBuf));
        return new SpellDataList((NonNullList<SpellData>)spellList);
    }

    private static void toNetwork(RegistryFriendlyByteBuf byteBuf, SpellDataList data) {
        byteBuf.writeVarInt(data.spellList().size());
        for (int i = 0; i < data.spellList().size(); ++i) {
            SpellData.STREAM_CODEC.encode((Object)byteBuf, (Object)((SpellData)data.spellList().get(i)));
        }
    }
}

