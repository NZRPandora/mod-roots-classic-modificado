/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.neoforge.common.data.BlockTagsProvider
 *  net.neoforged.neoforge.common.data.ExistingFileHelper
 */
package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class RootsBlockTagsProvider
extends BlockTagsProvider {
    public RootsBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, "rootsclassic", existingFileHelper);
    }

    public void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(RootsTags.INCORRECT_FOR_LIVING_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
        this.tag(RootsTags.INCORRECT_FOR_ENGRAVED_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add((Object[])new Block[]{(Block)RootsRegistry.MUNDANE_STANDING_STONE.get(), (Block)RootsRegistry.ATTUNED_STANDING_STONE.get(), (Block)RootsRegistry.VACUUM_STANDING_STONE.get(), (Block)RootsRegistry.REPULSOR_STANDING_STONE.get(), (Block)RootsRegistry.ACCELERATOR_STANDING_STONE.get(), (Block)RootsRegistry.AESTHETIC_STANDING_STONE.get(), (Block)RootsRegistry.ENTANGLER_STANDING_STONE.get(), (Block)RootsRegistry.IGNITER_STANDING_STONE.get(), (Block)RootsRegistry.GROWER_STANDING_STONE.get(), (Block)RootsRegistry.ALTAR.get(), (Block)RootsRegistry.MORTAR.get()});
    }
}

