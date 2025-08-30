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
import elucent.rootsclassic.block.imbuer.ImbuerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ImbuerBER
implements BlockEntityRenderer<ImbuerBlockEntity> {
    public ImbuerBER(BlockEntityRendererProvider.Context context) {
    }

    public void render(ImbuerBlockEntity imbuerTile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStack dustStack;
        ItemStack stickStack = imbuerTile.getStick();
        if (!stickStack.isEmpty()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5, 0.3125, 0.5);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees((float)imbuerTile.spin));
            Minecraft.getInstance().getItemRenderer().renderStatic(stickStack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, imbuerTile.getLevel(), 0);
            matrixStackIn.popPose();
        }
        if (!(dustStack = imbuerTile.getSpellPowder()).isEmpty()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5, 0.125, 0.375);
            matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(dustStack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, imbuerTile.getLevel(), 0);
            matrixStackIn.popPose();
        }
    }
}

