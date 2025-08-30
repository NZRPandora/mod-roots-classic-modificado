/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.util.ExtraCodecs
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package elucent.rootsclassic.registry;

import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.datacomponent.SpellDataList;
import elucent.rootsclassic.datacomponent.StaffUses;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RootsComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPE = DeferredRegister.create((ResourceKey)Registries.DATA_COMPONENT_TYPE, (String)"rootsclassic");
    public static final Supplier<DataComponentType<StaffUses>> STAFF_USES = COMPONENT_TYPE.register("staff_uses", () -> DataComponentType.builder().persistent(StaffUses.CODEC).networkSynchronized(StaffUses.STREAM_CODEC).build());
    public static final Supplier<DataComponentType<Integer>> SELECTED_SPELL = COMPONENT_TYPE.register("selected", () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final Supplier<DataComponentType<SpellData>> SPELL = COMPONENT_TYPE.register("spell", () -> DataComponentType.builder().persistent(SpellData.CODEC).networkSynchronized(SpellData.STREAM_CODEC).build());
    public static final Supplier<DataComponentType<SpellDataList>> SPELLS = COMPONENT_TYPE.register("spells", () -> DataComponentType.builder().persistent(SpellDataList.CODEC).networkSynchronized(SpellDataList.STREAM_CODEC).build());
    public static final Supplier<DataComponentType<Integer>> SPIKES = COMPONENT_TYPE.register("spikes", () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final Supplier<DataComponentType<Integer>> FORCEFUL = COMPONENT_TYPE.register("forceful", () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final Supplier<DataComponentType<Integer>> HOLY = COMPONENT_TYPE.register("holy", () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final Supplier<DataComponentType<Integer>> AQUATIC = COMPONENT_TYPE.register("aquatic", () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final Supplier<DataComponentType<Integer>> SHADOWSTEP = COMPONENT_TYPE.register("shadowstep", () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final Supplier<DataComponentType<Integer>> CURRENT_GROUP = COMPONENT_TYPE.register("current_group", () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
}

