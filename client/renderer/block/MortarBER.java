/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.neoforge.items.ItemStackHandler
 */
package elucent.rootsclassic.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import elucent.rootsclassic.block.mortar.MortarBlockEntity;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class MortarBER
implements BlockEntityRenderer<MortarBlockEntity> {
    public MortarBER(BlockEntityRendererProvider.Context context) {
    }

    public void render(MortarBlockEntity mortarTile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStackHandler inventory = mortarTile.inventory;
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            matrixStackIn.pushPose();
            Random random = new Random(stack.hashCode());
            matrixStackIn.translate(0.475 + (double)random.nextFloat() / 20.0, 0.05 + (double)random.nextFloat() / 20.0, 0.475 + (double)random.nextFloat() / 20.0);
            matrixStackIn.scale(0.65f, 0.65f, 0.65f);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees((float)random.nextInt(360)));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, mortarTile.getLevel(), 0);
            matrixStackIn.popPose();
        }
    }
}

