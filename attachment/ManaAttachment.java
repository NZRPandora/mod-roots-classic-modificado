/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.neoforged.neoforge.common.util.INBTSerializable
 */
package elucent.rootsclassic.attachment;

import elucent.rootsclassic.attachment.IMana;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class ManaAttachment
implements IMana,
INBTSerializable<CompoundTag> {
    private float maxMana = 40.0f;
    private float mana = 40.0f;

    @Override
    public float getMana() {
        return this.mana;
    }

    @Override
    public float getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMana(float mana) {
        this.mana = mana;
        if (mana < 0.0f) {
            this.mana = 0.0f;
        }
        if (mana > this.getMaxMana()) {
            this.mana = this.getMaxMana();
        }
    }

    @Override
    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return this.writeNBT(this);
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.readNBT(this, tag);
    }

    public CompoundTag writeNBT(ManaAttachment instance) {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("mana", instance.getMana());
        tag.putFloat("maxMana", instance.getMaxMana());
        return tag;
    }

    public void readNBT(ManaAttachment instance, CompoundTag tag) {
        instance.setMana(tag.getFloat("mana"));
        instance.setMaxMana(tag.getFloat("maxMana"));
    }
}

