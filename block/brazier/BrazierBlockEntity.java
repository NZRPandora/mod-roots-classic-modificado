/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
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
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.neoforged.neoforge.items.ItemStackHandler
 */
package elucent.rootsclassic.block.brazier;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.registry.RootsRegistry;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;

public class BrazierBlockEntity
extends BEBase {
    private static final int TOTAL_BURN_TIME = 2400;
    private int ticker = 0;
    private boolean burning = false;
    private int progress = 0;
    public final ItemStackHandler inventory = new ItemStackHandler(this, 1){

        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }
    };

    public BrazierBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public BrazierBlockEntity(BlockPos pos, BlockState state) {
        super((BlockEntityType)RootsRegistry.BRAZIER_TILE.get(), pos, state);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("InventoryHandler"));
        if (tag.contains("burning")) {
            this.setBurning(tag.getBoolean("burning"));
        }
        if (tag.contains("progress")) {
            this.progress = tag.getInt("progress");
        }
    }

    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("InventoryHandler", (Tag)this.inventory.serializeNBT(registries));
        tag.putBoolean("burning", this.isBurning());
        tag.putInt("progress", this.progress);
    }

    @Override
    public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
        if (this.getHeldItem() != null && !this.isBurning()) {
            this.dropContaining();
        }
        this.setRemoved();
    }

    private void dropContaining() {
        if (!this.level.isClientSide) {
            this.level.addFreshEntity((Entity)new ItemEntity(this.level, (double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 1.0, (double)this.worldPosition.getZ() + 0.5, this.getHeldItem()));
        }
        this.setHeldItem(ItemStack.EMPTY);
    }

    private void notifyUpdate(BlockState state) {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), state, this.level.getBlockState(this.worldPosition), 3);
    }

    @Override
    public ItemInteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack playerItem, BlockHitResult hit) {
        if (playerItem.isEmpty()) {
            if (!this.getHeldItem().isEmpty() && !this.isBurning()) {
                if (player.isShiftKeyDown()) {
                    player.displayClientMessage(this.getHeldItem().getHoverName(), true);
                } else {
                    this.dropContaining();
                    this.notifyUpdate(state);
                    player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.brazier.burning.empty"), true);
                }
                return ItemInteractionResult.SUCCESS;
            }
            if (this.isBurning() && player.isShiftKeyDown()) {
                player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.brazier.burning.off"), true);
                this.stopBurning();
                this.notifyUpdate(state);
                return ItemInteractionResult.SUCCESS;
            }
        } else if (playerItem.getItem() == Items.FLINT_AND_STEEL) {
            if (!this.getHeldItem().isEmpty()) {
                this.startBurning();
                player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.brazier.burning.on"), true);
                this.notifyUpdate(state);
                return ItemInteractionResult.SUCCESS;
            }
        } else if (this.getHeldItem().isEmpty()) {
            this.setHeldItem(playerItem.copyWithCount(1));
            playerItem.shrink(1);
            player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.brazier.burning.added"), true);
            this.notifyUpdate(state);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private void startBurning() {
        this.setBurning(true);
        this.progress = 2400;
    }

    private void stopBurning() {
        this.setBurning(false);
        this.progress = 0;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BrazierBlockEntity tile) {
        tile.setTicker(tile.getTicker() + (tile.isBurning() ? 12 : 3));
        if (tile.progress > 0) {
            --tile.progress;
            if (tile.progress <= 0) {
                tile.setBurning(false);
                tile.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
        if (tile.getTicker() > 360) {
            tile.setTicker(0);
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BrazierBlockEntity tile) {
        tile.setTicker(tile.getTicker() + (tile.isBurning() ? 12 : 3));
        if (tile.progress > 0) {
            --tile.progress;
            if (level.isClientSide) {
                if (tile.progress % 2 == 0) {
                    level.addParticle((ParticleOptions)ParticleTypes.SMOKE, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, level.random.nextDouble() * 0.0625 + 0.0625, 0.0);
                }
                if (tile.progress % 20 == 0) {
                    level.addParticle((ParticleOptions)ParticleTypes.FLAME, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
                }
            }
            if (tile.progress <= 0) {
                tile.setBurning(false);
                tile.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
        if (tile.getTicker() > 360) {
            tile.setTicker(0);
        }
    }

    public boolean isBurning() {
        return this.burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

    public int getTicker() {
        return this.ticker;
    }

    public void setTicker(int ticker) {
        this.ticker = ticker;
    }

    public ItemStack getHeldItem() {
        return this.inventory.getStackInSlot(0);
    }

    public void setHeldItem(ItemStack heldItem) {
        this.inventory.setStackInSlot(0, heldItem);
    }
}

