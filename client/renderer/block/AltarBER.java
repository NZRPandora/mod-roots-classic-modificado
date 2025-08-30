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
 */
package elucent.rootsclassic.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import elucent.rootsclassic.block.altar.AltarBlockEntity;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AltarBER
implements BlockEntityRenderer<AltarBlockEntity> {
    public AltarBER(BlockEntityRendererProvider.Context context) {
    }

    public void render(AltarBlockEntity altarTile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        int i;
        ArrayList<ItemStack> renderItems = new ArrayList<ItemStack>();
        for (i = 0; i < altarTile.inventory.getSlots(); ++i) {
            renderItems.add(altarTile.inventory.getStackInSlot(i));
        }
        for (i = 0; i < altarTile.inventory.getSlots(); ++i) {
            poseStack.pushPose();
            double shifted = (double)altarTile.getTicker() + (double)i * (360.0 / (double)renderItems.size());
            poseStack.translate(0.5, 1.0 + 0.1 * Math.sin(Math.toRadians(shifted * 4.0)), 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees((float)shifted));
            poseStack.translate(-0.5, 0.0, 0.0);
            poseStack.mulPose(Axis.YP.rotationDegrees((float)shifted));
            Minecraft.getInstance().getItemRenderer().renderStatic((ItemStack)renderItems.get(i), ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, altarTile.getLevel(), 0);
            poseStack.popPose();
        }
    }
}

