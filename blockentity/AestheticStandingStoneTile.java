/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.ItemInteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.neoforged.neoforge.common.Tags$Items
 */
package elucent.rootsclassic.blockentity;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;

public class AestheticStandingStoneTile
extends BEBase {
    private int ticker = 0;
    private int r = 0;
    private int g = 0;
    private int b = 0;

    public AestheticStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public AestheticStandingStoneTile(BlockPos pos, BlockState state) {
        this((BlockEntityType)RootsRegistry.AESTHETIC_STANDING_STONE_TILE.get(), pos, state);
    }

    @Override
    public ItemInteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
        if (heldItem != null) {
            int amount = 5;
            if (heldItem.is(Tags.Items.DYES_RED) && this.r < 255) {
                this.r += amount;
                return ItemInteractionResult.SUCCESS;
            }
            if (heldItem.is(Tags.Items.DYES_GREEN) && this.g < 255) {
                this.g += amount;
                return ItemInteractionResult.SUCCESS;
            }
            if (heldItem.is(Tags.Items.DYES_BLUE) && this.b < 255) {
                this.b += amount;
                return ItemInteractionResult.SUCCESS;
            }
            if (heldItem.is(Tags.Items.DYES_WHITE)) {
                this.r = 0;
                this.g = 0;
                this.b = 0;
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.r = tag.getInt("red");
        this.b = tag.getInt("blue");
        this.g = tag.getInt("green");
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("red", this.r);
        tag.putInt("blue", this.b);
        tag.putInt("green", this.g);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AestheticStandingStoneTile tile) {
        ++tile.ticker;
        if (tile.ticker % 5 == 0 && level.isClientSide) {
            for (double i = 0.0; i < 720.0; i += 45.0) {
                double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
                double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
                level.addParticle(MagicAuraParticleData.createData(tile.r, tile.g, tile.b), (double)pos.getX() + 0.5 + xShift, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5 + zShift, 0.0, 0.0, 0.0);
            }
        }
    }
}

