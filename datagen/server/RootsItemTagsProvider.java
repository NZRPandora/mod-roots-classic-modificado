/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.tags.ItemTagsProvider
 *  net.minecraft.data.tags.TagsProvider$TagLookup
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.neoforge.common.Tags$Items
 *  net.neoforged.neoforge.common.data.ExistingFileHelper
 */
package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class RootsItemTagsProvider
extends ItemTagsProvider {
    public RootsItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, "rootsclassic", existingFileHelper);
    }

    public void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(RootsTags.BERRIES).add((Object[])new Item[]{(Item)RootsRegistry.NIGHTSHADE.get(), (Item)RootsRegistry.BLACKCURRANT.get(), (Item)RootsRegistry.REDCURRANT.get(), (Item)RootsRegistry.WHITECURRANT.get(), (Item)RootsRegistry.ELDERBERRY.get()});
        this.tag(Tags.Items.FOODS_BERRY).addTag(RootsTags.BERRIES);
        this.tag(Tags.Items.FOODS).add((Object[])new Item[]{(Item)RootsRegistry.OLD_ROOT.get(), (Item)RootsRegistry.HEALING_POULTICE.get(), (Item)RootsRegistry.FRUIT_SALAD.get()});
        this.tag(Tags.Items.FOODS_SOUP).add((Object)((Item)RootsRegistry.ROOTY_STEW.get()));
        this.addBark((Item)RootsRegistry.ACACIA_BARK.get(), "acacia");
        this.addBark((Item)RootsRegistry.BIRCH_BARK.get(), "birch");
        this.addBark((Item)RootsRegistry.DARK_OAK_BARK.get(), "dark_oak");
        this.addBark((Item)RootsRegistry.JUNGLE_BARK.get(), "jungle");
        this.addBark((Item)RootsRegistry.OAK_BARK.get(), "oak");
        this.addBark((Item)RootsRegistry.SPRUCE_BARK.get(), "spruce");
        this.tag(ItemTags.AXES).add((Object)((Item)RootsRegistry.LIVING_AXE.get()));
        this.tag(ItemTags.HOES).add((Object)((Item)RootsRegistry.LIVING_HOE.get()));
        this.tag(ItemTags.PICKAXES).add((Object)((Item)RootsRegistry.LIVING_PICKAXE.get()));
        this.tag(ItemTags.SHOVELS).add((Object)((Item)RootsRegistry.LIVING_SHOVEL.get()));
        this.tag(ItemTags.SWORDS).add((Object[])new Item[]{(Item)RootsRegistry.LIVING_SWORD.get(), (Item)RootsRegistry.ENGRAVED_BLADE.get()});
        this.tag(ItemTags.HEAD_ARMOR).add((Object[])new Item[]{(Item)RootsRegistry.SYLVAN_HOOD.get(), (Item)RootsRegistry.WILDWOOD_MASK.get()});
        this.tag(ItemTags.CHEST_ARMOR).add((Object[])new Item[]{(Item)RootsRegistry.SYLVAN_ROBE.get(), (Item)RootsRegistry.WILDWOOD_PLATE.get()});
        this.tag(ItemTags.LEG_ARMOR).add((Object[])new Item[]{(Item)RootsRegistry.SYLVAN_ROBE.get(), (Item)RootsRegistry.WILDWOOD_LEGGINGS.get()});
        this.tag(ItemTags.FOOT_ARMOR).add((Object[])new Item[]{(Item)RootsRegistry.SYLVAN_BOOTS.get(), (Item)RootsRegistry.WILDWOOD_BOOTS.get()});
    }

    private void addBark(Item item, String treeType) {
        TagKey barkTypeTag = ItemTags.create((ResourceLocation)Const.modLoc("barks/" + treeType));
        this.tag(RootsTags.BARKS).addTag(barkTypeTag);
        this.tag(barkTypeTag).add((Object)item);
    }
}

