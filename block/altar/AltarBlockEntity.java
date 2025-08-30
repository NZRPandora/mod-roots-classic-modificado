/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.ItemInteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.neoforged.neoforge.items.IItemHandler
 *  net.neoforged.neoforge.items.ItemHandlerHelper
 *  net.neoforged.neoforge.items.ItemStackHandler
 */
package elucent.rootsclassic.block.altar;

import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.ritual.RitualRegistry;
import elucent.rootsclassic.util.InventoryUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AltarBlockEntity
extends BEBase {
    private static final int RECIPE_PROGRESS_TIME = 200;
    private List<ItemStack> incenses = new ArrayList<ItemStack>();
    private int ticker = 0;
    private int progress = 0;
    @Nullable
    private RecipeHolder<RitualRecipe> currentRitual = null;
    private int clientRitualLevel;
    private Color clientRitualColor;
    private Color clientRitualSecondaryColor;
    public final ItemStackHandler inventory = new ItemStackHandler(this, 3){

        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }
    };

    public AltarBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super((BlockEntityType)RootsRegistry.ALTAR_TILE.get(), pos, state);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("InventoryHandler"));
        this.setIncenses(new ArrayList<ItemStack>());
        if (tag.contains("incenses")) {
            ListTag list = tag.getList("incenses", 10);
            for (int i = 0; i < list.size(); ++i) {
                this.getIncenses().add(ItemStack.parseOptional((HolderLookup.Provider)registries, (CompoundTag)list.getCompound(i)));
            }
        }
        if (tag.contains("progress")) {
            this.setProgress(tag.getInt("progress"));
        }
        if (this.level != null && this.level.isClientSide() && tag.contains("ritual", 10)) {
            CompoundTag ritualTag = tag.getCompound("ritual");
            this.clientRitualLevel = ritualTag.getInt("level");
            this.clientRitualColor = new Color(ritualTag.getInt("color"));
            this.clientRitualSecondaryColor = new Color(ritualTag.getInt("secondaryColor"));
        }
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("InventoryHandler", (Tag)this.inventory.serializeNBT(registries));
        if (!this.getIncenses().isEmpty()) {
            ListTag list = new ListTag();
            for (int i = 0; i < this.getIncenses().size(); ++i) {
                if (this.getIncenses().get(i).isEmpty()) continue;
                list.add((Object)this.getIncenses().get(i).saveOptional(registries));
            }
            tag.put("incenses", (Tag)list);
        }
        tag.putInt("progress", this.getProgress());
        if (this.level != null && !this.level.isClientSide() && this.currentRitual != null) {
            CompoundTag ritualTag = new CompoundTag();
            RitualRecipe ritual = (RitualRecipe)this.currentRitual.value();
            ritualTag.putInt("level", ritual.level);
            ritualTag.putInt("color", ritual.getColorInt());
            ritualTag.putInt("secondaryColor", ritual.getSecondaryColorInt());
            tag.put("ritual", (Tag)ritualTag);
        }
    }

    @Override
    public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
        for (int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack slotStack;
            if (levelAccessor.isClientSide || (slotStack = this.inventory.getStackInSlot(i)).isEmpty()) continue;
            levelAccessor.addFreshEntity((Entity)new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, slotStack));
        }
        this.setRemoved();
    }

    @Override
    public ItemInteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (heldItem.isEmpty() && !player.isShiftKeyDown() && this.getProgress() == 0) {
                if (this.inventory.getSlots() > 0) {
                    ItemStack lastStack = InventoryUtil.getLastStack((IItemHandler)this.inventory);
                    if (!lastStack.isEmpty()) {
                        if (!levelAccessor.isClientSide) {
                            levelAccessor.addFreshEntity((Entity)new ItemEntity(levelAccessor, (double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5, lastStack.copy()));
                        }
                        lastStack.shrink(1);
                        this.setChanged();
                        levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            } else {
                if (player.isShiftKeyDown() && heldItem.isEmpty() && this.getProgress() == 0) {
                    this.setCurrentRitual(null);
                    Optional<RecipeHolder<RitualRecipe>> optionalRitual = RitualRegistry.findMatchingByIngredients(this);
                    if (optionalRitual.isEmpty()) {
                        player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.error.noritual.ingredients"), true);
                        return ItemInteractionResult.FAIL;
                    }
                    RecipeHolder<RitualRecipe> recipeHolder = optionalRitual.get();
                    RitualRecipe recipe = (RitualRecipe)recipeHolder.value();
                    if (!RitualPillars.verifyPositionBlocks(recipe, levelAccessor, pos)) {
                        player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.error.noritual.stones"), true);
                        return ItemInteractionResult.FAIL;
                    }
                    if (recipe.incenseMatches(this.level, pos)) {
                        this.setCurrentRitual(recipeHolder);
                        this.setIncenses(RitualRegistry.getIncenses(levelAccessor, pos));
                        this.setProgress(200);
                        for (BrazierBlockEntity brazier : RitualPillars.getRecipeBraziers(levelAccessor, pos)) {
                            brazier.setBurning(true);
                            brazier.setHeldItem(ItemStack.EMPTY);
                        }
                        this.setChanged();
                        levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
                        player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.ritual.started"), true);
                    } else {
                        player.displayClientMessage((Component)Component.translatable((String)"rootsclassic.error.noritual.incense"), true);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
                if (!InventoryUtil.isFull((IItemHandler)this.inventory) && this.getProgress() == 0) {
                    ItemStack copyStack = heldItem.copy();
                    copyStack.setCount(1);
                    ItemStack remaining = ItemHandlerHelper.insertItem((IItemHandler)this.inventory, (ItemStack)copyStack, (boolean)false);
                    if (remaining.isEmpty()) {
                        heldItem.shrink(1);
                        this.setChanged();
                        levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
        tile.setTicker(tile.getTicker() + 3);
        if (tile.getTicker() > 360) {
            tile.setTicker(0);
        }
        if (tile.getProgress() > 0 && tile.getCurrentRitual() != null) {
            tile.setProgress(tile.getProgress() - 1);
            if (tile.getProgress() == 0 && tile.getCurrentRitual() != null) {
                ((RitualRecipe)tile.getCurrentRitual().value()).doEffect(level, pos, InventoryUtil.createIInventory((IItemHandler)tile.inventory), tile.getIncenses());
                tile.setCurrentRitual(null);
                tile.emptyAltar();
                tile.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
        if (tile.getProgress() > 0 && tile.getCurrentRitual() != null) {
            tile.setProgress(tile.getProgress() - 1);
            Map<BlockPos, Block> pillars = RitualPillars.getRitualPillars(tile.clientRitualLevel);
            List pillarPositions = pillars.keySet().stream().toList();
            Color color = tile.clientRitualColor;
            Color secondaryColor = tile.clientRitualSecondaryColor;
            if (color == null || secondaryColor == null) {
                return;
            }
            if (pillarPositions.size() > 0) {
                BlockPos particlePos = ((BlockPos)pillarPositions.get(level.random.nextInt(pillarPositions.size()))).above().offset(pos.getX(), pos.getY(), pos.getZ());
                if (level.random.nextInt(6) == 0) {
                    level.addParticle(MagicLineParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()), (double)particlePos.getX() + 0.5, (double)particlePos.getY() + 0.125, (double)particlePos.getZ() + 0.5, (double)particlePos.getX() + 0.5, (double)particlePos.getY() + 0.875, (double)particlePos.getZ() + 0.5);
                } else {
                    level.addParticle(MagicLineParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()), (double)particlePos.getX() + 0.5, (double)particlePos.getY() + 0.125, (double)particlePos.getZ() + 0.5, (double)particlePos.getX() + 0.5, (double)particlePos.getY() + 0.875, (double)particlePos.getZ() + 0.5);
                }
            }
            if (level.random.nextInt(4) == 0) {
                level.addParticle(MagicAltarParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()), (double)pos.getX() + 0.5, (double)pos.getY() + 0.875, (double)pos.getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(360.0 * (double)(tile.getProgress() % 100) / 100.0)), 0.0, 0.125 * Math.cos(Math.toRadians(360.0 * (double)(tile.getProgress() % 100) / 100.0)));
            } else {
                level.addParticle(MagicAltarParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()), (double)pos.getX() + 0.5, (double)pos.getY() + 0.875, (double)pos.getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(360.0 * (double)(tile.getProgress() % 100) / 100.0)), 0.0, 0.125 * Math.cos(Math.toRadians(360.0 * (double)(tile.getProgress() % 100) / 100.0)));
            }
            if (level.random.nextInt(4) == 0) {
                level.addParticle(MagicAltarParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()), (double)pos.getX() + 0.5, (double)pos.getY() + 0.875, (double)pos.getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)), 0.0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)));
            } else {
                level.addParticle(MagicAltarParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()), (double)pos.getX() + 0.5, (double)pos.getY() + 0.875, (double)pos.getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)), 0.0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)));
            }
            if (level.random.nextInt(4) == 0) {
                level.addParticle(MagicAltarParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()), (double)pos.getX() + 0.5, (double)pos.getY() + 0.875, (double)pos.getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)), 0.0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)));
            } else {
                level.addParticle(MagicAltarParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()), (double)pos.getX() + 0.5, (double)pos.getY() + 0.875, (double)pos.getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)), 0.0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)));
            }
            if (level.random.nextInt(4) == 0) {
                level.addParticle(MagicAltarParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()), (double)pos.getX() + 0.5, (double)pos.getY() + 0.875, (double)pos.getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)), 0.0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)));
            } else {
                level.addParticle(MagicAltarParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()), (double)pos.getX() + 0.5, (double)pos.getY() + 0.875, (double)pos.getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)), 0.0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (double)(tile.getProgress() % 100) / 100.0)));
            }
        }
    }

    public List<ItemStack> getIncenses() {
        return this.incenses;
    }

    public void setIncenses(List<ItemStack> incenses) {
        this.incenses = incenses;
    }

    public int getTicker() {
        return this.ticker;
    }

    public void setTicker(int ticker) {
        this.ticker = ticker;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Nullable
    public RecipeHolder<RitualRecipe> getCurrentRitual() {
        return this.currentRitual;
    }

    public void setCurrentRitual(RecipeHolder<RitualRecipe> currentRitual) {
        this.currentRitual = currentRitual;
        this.setChanged();
    }

    public void emptyAltar() {
        for (int i = 0; i < this.inventory.getSlots(); ++i) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}

