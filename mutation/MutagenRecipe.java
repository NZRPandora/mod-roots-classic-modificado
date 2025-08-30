/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package elucent.rootsclassic.mutation;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MutagenRecipe {
    final List<ItemStack> inputs = new ArrayList<ItemStack>();
    final ResourceLocation name;
    final BlockState plantBlock;
    public final BlockState result;

    public MutagenRecipe(ResourceLocation name, BlockState state, BlockState resultState) {
        this.name = name;
        this.plantBlock = state;
        this.result = resultState;
    }

    public void onCrafted(Level levelAccessor, BlockPos pos, Player player) {
    }

    public void addIngredient(ItemStack stack) {
        this.inputs.add(stack);
    }

    public boolean matches(List<ItemStack> items, Level levelAccessor, BlockPos pos, Player player) {
        if (levelAccessor.getBlockState(pos).is(this.plantBlock.getBlock())) {
            ArrayList<ItemStack> tempItems = new ArrayList<ItemStack>(items);
            for (ItemStack input : this.inputs) {
                boolean endIteration = false;
                for (int j = 0; j < tempItems.size() && !endIteration; ++j) {
                    if (!ItemStack.isSameItem((ItemStack)input, (ItemStack)((ItemStack)tempItems.get(j)))) continue;
                    tempItems.remove(j);
                    endIteration = true;
                }
            }
            return tempItems.size() == 0;
        }
        return false;
    }
}

