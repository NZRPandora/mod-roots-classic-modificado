/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.server.packs.resources.ResourceManagerReloadListener
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.neoforge.event.AddReloadListenerEvent
 */
package elucent.rootsclassic.recipe;

import elucent.rootsclassic.mutation.MutagenManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

public class RootsReloadManager
implements ResourceManagerReloadListener {
    public void onResourceManagerReload(ResourceManager resourceManager) {
        MutagenManager.reload();
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener((PreparableReloadListener)this);
    }
}

