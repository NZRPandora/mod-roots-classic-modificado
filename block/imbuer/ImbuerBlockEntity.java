/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.ItemInteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.neoforged.neoforge.common.Tags$Items
 *  net.neoforged.neoforge.items.ItemStackHandler
 */
package elucent.rootsclassic.block.imbuer;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.item.powder.SpellPowderItem;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ImbuerBlockEntity
extends BEBase {
    private static final int STICK = 0;
    private static final int DUST = 1;
    public int progress = 0;
    public int spin = 0;
    public final ItemStackHandler inventory = new ItemStackHandler(this, 2){

        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }

        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot == 0) {
                return stack.is(Tags.Items.RODS_WOODEN);
            }
            return stack.getItem() instanceof SpellPowderItem;
        }
    };

    public ImbuerBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public ImbuerBlockEntity(BlockPos pos, BlockState state) {
        super((BlockEntityType)RootsRegistry.IMBUER_TILE.get(), pos, state);
    }

    public ItemStack getStick() {
        return this.inventory.getStackInSlot(0);
    }

    public ItemStack getSpellPowder() {
        return this.inventory.getStackInSlot(1);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("InventoryHandler"));
    }

    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("InventoryHandler", (Tag)this.inventory.serializeNBT(registries));
    }

    @Override
    public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
        for (int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (stack.isEmpty() || levelAccessor.isClientSide) continue;
            levelAccessor.addFreshEntity((Entity)new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, stack));
        }
        this.setRemoved();
    }

    @Override
    public ItemInteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
        if (this.progress == 0 && hand == InteractionHand.MAIN_HAND) {
            if (heldItem.isEmpty()) {
                if (!this.getStick().isEmpty()) {
                    if (!levelAccessor.isClientSide) {
                        levelAccessor.addFreshEntity((Entity)new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5, this.getStick()));
                    }
                    this.inventory.setStackInSlot(0, ItemStack.EMPTY);
                    this.setChanged();
                    levelAccessor.sendBlockUpdated(this.getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
                    return ItemInteractionResult.SUCCESS;
                }
                if (!this.getSpellPowder().isEmpty()) {
                    if (!levelAccessor.isClientSide) {
                        levelAccessor.addFreshEntity((Entity)new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5, this.getSpellPowder()));
                    }
                    this.inventory.setStackInSlot(1, ItemStack.EMPTY);
                    this.setChanged();
                    levelAccessor.sendBlockUpdated(this.getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (heldItem.is(Tags.Items.RODS_WOODEN)) {
                if (this.getStick().isEmpty()) {
                    ItemStack copyStack = heldItem.copy();
                    copyStack.setCount(1);
                    this.inventory.setStackInSlot(0, copyStack);
                    heldItem.shrink(1);
                    this.setChanged();
                    levelAccessor.sendBlockUpdated(this.getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (heldItem.getItem() == RootsRegistry.SPELL_POWDER.get() && this.getSpellPowder().isEmpty()) {
                ItemStack copyStack = heldItem.copy();
                copyStack.setCount(1);
                this.inventory.setStackInSlot(1, copyStack);
                heldItem.shrink(1);
                this.setChanged();
                levelAccessor.sendBlockUpdated(this.getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ImbuerBlockEntity tile) {
        tile.spin = tile.progress == 0 ? (tile.spin += 4) : (tile.spin += 12);
        ItemStack dustStack = tile.getSpellPowder();
        ItemStack stickStack = tile.getStick();
        if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
            ++tile.progress;
        }
        if (tile.progress != 0 && tile.progress % 1 == 0 && tile.progress > 40) {
            tile.progress = 0;
            if (!dustStack.isEmpty() && !stickStack.isEmpty() && dustStack.has(RootsComponents.SPELL)) {
                SpellData data = (SpellData)dustStack.get(RootsComponents.SPELL);
                ItemStack staff = new ItemStack((ItemLike)RootsRegistry.STAFF.get(), 1);
                String effectName = data.effect();
                int potency = data.potency();
                int efficiency = data.efficiency();
                int size = data.size();
                StaffItem.createData(staff, effectName, potency, efficiency, size);
                if (!level.isClientSide) {
                    level.addFreshEntity((Entity)new ItemEntity(level, (double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5, staff));
                }
                tile.inventory.setStackInSlot(0, ItemStack.EMPTY);
                tile.inventory.setStackInSlot(1, ItemStack.EMPTY);
                tile.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ImbuerBlockEntity tile) {
        tile.spin = tile.progress == 0 ? (tile.spin += 4) : (tile.spin += 12);
        ItemStack dustStack = tile.getSpellPowder();
        ItemStack stickStack = tile.getStick();
        if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
            ++tile.progress;
        }
        if (tile.progress != 0 && tile.progress % 1 == 0) {
            ComponentBase comp;
            SpellData data;
            ResourceLocation compName;
            int chance = level.random.nextInt(4);
            if (dustStack.has(RootsComponents.SPELL) && (compName = ResourceLocation.tryParse((String)(data = (SpellData)dustStack.get(RootsComponents.SPELL)).effect())) != null && (comp = (ComponentBase)ComponentBaseRegistry.COMPONENTS.get(compName)) != null && level.isClientSide) {
                if (chance == 0) {
                    if (level.random.nextBoolean()) {
                        level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z), (double)pos.getX() + 0.125, (double)pos.getY() + 0.125, (double)pos.getZ() + 0.125, (double)pos.getX() + 0.5, (double)pos.getY() + 0.625, (double)pos.getZ() + 0.5);
                    } else {
                        level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z), (double)pos.getX() + 0.125, (double)pos.getY() + 0.125, (double)pos.getZ() + 0.125, (double)pos.getX() + 0.5, (double)pos.getY() + 0.625, (double)pos.getZ() + 0.5);
                    }
                }
                if (chance == 1) {
                    if (level.random.nextBoolean()) {
                        level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z), (double)pos.getX() + 0.875, (double)pos.getY() + 0.125, (double)pos.getZ() + 0.125, (double)pos.getX() + 0.5, (double)pos.getY() + 0.625, (double)pos.getZ() + 0.5);
                    } else {
                        level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z), (double)pos.getX() + 0.875, (double)pos.getY() + 0.125, (double)pos.getZ() + 0.125, (double)pos.getX() + 0.5, (double)pos.getY() + 0.625, (double)pos.getZ() + 0.5);
                    }
                }
                if (chance == 2) {
                    if (level.random.nextBoolean()) {
                        level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z), (double)pos.getX() + 0.875, (double)pos.getY() + 0.125, (double)pos.getZ() + 0.875, (double)pos.getX() + 0.5, (double)pos.getY() + 0.625, (double)pos.getZ() + 0.5);
                    } else {
                        level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z), (double)pos.getX() + 0.875, (double)pos.getY() + 0.125, (double)pos.getZ() + 0.875, (double)pos.getX() + 0.5, (double)pos.getY() + 0.625, (double)pos.getZ() + 0.5);
                    }
                }
                if (chance == 3) {
                    if (level.random.nextBoolean()) {
                        level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z), (double)pos.getX() + 0.125, (double)pos.getY() + 0.125, (double)pos.getZ() + 0.875, (double)pos.getX() + 0.5, (double)pos.getY() + 0.625, (double)pos.getZ() + 0.5);
                    } else {
                        level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z), (double)pos.getX() + 0.125, (double)pos.getY() + 0.125, (double)pos.getZ() + 0.875, (double)pos.getX() + 0.5, (double)pos.getY() + 0.625, (double)pos.getZ() + 0.5);
                    }
                }
            }
            if (tile.progress > 40) {
                tile.progress = 0;
            }
        }
    }
}

