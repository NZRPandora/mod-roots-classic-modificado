/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.neoforged.neoforge.attachment.AttachmentType
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 */
package elucent.rootsclassic.attachment;

import elucent.rootsclassic.attachment.ManaAttachment;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class RootsAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create((Registry)NeoForgeRegistries.ATTACHMENT_TYPES, (String)"rootsclassic");
    public static final Supplier<AttachmentType<ManaAttachment>> MANA = ATTACHMENT_TYPES.register("mana", () -> AttachmentType.builder(ManaAttachment::new).build());
}

