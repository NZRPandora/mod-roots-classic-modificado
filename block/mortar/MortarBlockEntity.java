/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.ItemInteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  net.minecraft.world.item.crafting.RecipeInput
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.neoforged.neoforge.items.IItemHandler
 *  net.neoforged.neoforge.items.ItemHandlerHelper
 *  net.neoforged.neoforge.items.ItemStackHandler
 */
package elucent.rootsclassic.block.mortar;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.InventoryUtil;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

public class MortarBlockEntity
extends BEBase {
    public final ItemStackHandler inventory = new ItemStackHandler(8){

        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }

        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            MortarBlockEntity.this.calculateRotations();
        }
    };

    public MortarBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public MortarBlockEntity(BlockPos pos, BlockState state) {
        this((BlockEntityType)RootsRegistry.MORTAR_TILE.get(), pos, state);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("InventoryHandler"));
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("InventoryHandler", (Tag)this.inventory.serializeNBT(registries));
    }

    @Override
    public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
        this.dropAllItems(levelAccessor, pos);
        this.setRemoved();
    }

    @Override
    public ItemInteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (heldItem.isEmpty()) {
                return this.tryDropSingleItem(levelAccessor, pos, state);
            }
            if (heldItem.getItem() == RootsRegistry.PESTLE.get()) {
                return this.tryActivateRecipe(player, state);
            }
            return this.tryInsertItem(levelAccessor, pos, state, heldItem);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private ItemInteractionResult tryInsertItem(Level levelAccessor, BlockPos pos, BlockState state, ItemStack heldItem) {
        if (!heldItem.isEmpty() && !InventoryUtil.isFull((IItemHandler)this.inventory)) {
            ItemStack heldCopy = heldItem.copy();
            heldCopy.setCount(1);
            if (heldItem.getItem() == Items.GLOWSTONE_DUST || heldItem.getItem() == Items.REDSTONE || heldItem.getItem() == Items.GUNPOWDER) {
                int maxCapacity = ComponentRecipe.getModifierCapacity((RecipeInput)InventoryUtil.createWrappedInventory((IItemHandler)this.inventory));
                int modifierCount = ComponentRecipe.getModifierCount((RecipeInput)InventoryUtil.createWrappedInventory((IItemHandler)this.inventory));
                if (modifierCount < maxCapacity) {
                    ItemStack restStack = ItemHandlerHelper.insertItem((IItemHandler)this.inventory, (ItemStack)heldCopy, (boolean)false);
                    if (restStack.isEmpty()) {
                        heldItem.shrink(1);
                        this.setChanged();
                        levelAccessor.sendBlockUpdated(this.getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
                        return ItemInteractionResult.SUCCESS;
                    }
                    return ItemInteractionResult.FAIL;
                }
            } else {
                ItemStack restStack = ItemHandlerHelper.insertItem((IItemHandler)this.inventory, (ItemStack)heldCopy, (boolean)false);
                if (restStack.isEmpty()) {
                    heldItem.shrink(1);
                    this.setChanged();
                    levelAccessor.sendBlockUpdated(this.getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
                    return ItemInteractionResult.SUCCESS;
                }
                return ItemInteractionResult.FAIL;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private ItemInteractionResult tryDropSingleItem(Level levelAccessor, BlockPos pos, BlockState state) {
        if (!InventoryUtil.isEmpty((IItemHandler)this.inventory)) {
            ItemStack lastStack = InventoryUtil.getLastStack((IItemHandler)this.inventory);
            if (!lastStack.isEmpty()) {
                this.dropItem(lastStack, 0.5f);
                lastStack.shrink(1);
            }
            this.setChanged();
            levelAccessor.sendBlockUpdated(this.getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private ItemInteractionResult tryActivateRecipe(Player player, BlockState state) {
        RecipeHolder recipeHolder = this.level.getRecipeManager().getRecipeFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get(), (RecipeInput)InventoryUtil.createWrappedInventory((IItemHandler)this.inventory), this.level).orElse(null);
        if (recipeHolder == null) {
            player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.mortar.invalid"), true);
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (((ComponentRecipe)recipeHolder.value()).needsMixin() && ComponentRecipe.getModifierCapacity((RecipeInput)InventoryUtil.createWrappedInventory((IItemHandler)this.inventory)) < 0) {
            player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.mortar.mixin"), true);
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!this.level.isClientSide) {
            this.level.addFreshEntity((Entity)new ItemEntity(this.level, (double)this.getBlockPos().getX() + 0.5, (double)this.getBlockPos().getY() + 0.5, (double)this.getBlockPos().getZ() + 0.5, ((ComponentRecipe)recipeHolder.value()).assemble((RecipeInput)InventoryUtil.createWrappedInventory((IItemHandler)this.inventory), (HolderLookup.Provider)this.level.registryAccess())));
        }
        InventoryUtil.clearInventory((IItemHandler)this.inventory);
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), state, this.level.getBlockState(this.worldPosition), 3);
        return ItemInteractionResult.SUCCESS;
    }

    public void dropItem(ItemStack stack, float offsetY) {
        ItemStack copyStack = stack.copy();
        if (copyStack.isEmpty() || this.level.isClientSide) {
            return;
        }
        BlockPos pos = this.getBlockPos();
        ItemEntity itementity = new ItemEntity(this.level, (double)pos.getX(), (double)pos.getY() + (double)offsetY, (double)this.worldPosition.getZ(), copyStack);
        itementity.setDefaultPickUpDelay();
        this.level.addFreshEntity((Entity)itementity);
    }

    private void dropAllItems(Level levelAccessor, BlockPos pos) {
        for (int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            this.dropItem(stack, 0.0f);
        }
    }

    private void calculateRotations() {
        for (int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;
        }
    }
}

