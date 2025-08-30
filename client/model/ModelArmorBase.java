/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.decoration.ArmorStand
 *  net.minecraft.world.item.ArmorItem$Type
 */
package elucent.rootsclassic.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorItem;

public class ModelArmorBase
extends HumanoidModel<LivingEntity> {
    public final ArmorItem.Type slot;
    public float armorScale = 1.05f;
    public final ModelPart rightFoot;
    public final ModelPart leftFoot;

    public ModelArmorBase(ModelPart root, ArmorItem.Type armorType) {
        super(root);
        this.rightFoot = root.getChild("right_foot");
        this.leftFoot = root.getChild("left_foot");
        this.slot = armorType;
        this.young = false;
    }

    public static MeshDefinition createArmorMesh() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.ZERO);
        return meshdefinition;
    }

    public void setupAnim(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entityIn instanceof ArmorStand)) {
            super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            return;
        }
        if (entityIn instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand)entityIn;
            this.head.xRot = (float)Math.PI / 180 * armorStand.getHeadPose().getX();
            this.head.yRot = (float)Math.PI / 180 * armorStand.getHeadPose().getY();
            this.head.zRot = (float)Math.PI / 180 * armorStand.getHeadPose().getZ();
            this.head.setPos(0.0f, 1.0f, 0.0f);
            this.body.xRot = (float)Math.PI / 180 * armorStand.getBodyPose().getX();
            this.body.yRot = (float)Math.PI / 180 * armorStand.getBodyPose().getY();
            this.body.zRot = (float)Math.PI / 180 * armorStand.getBodyPose().getZ();
            this.leftArm.xRot = (float)Math.PI / 180 * armorStand.getLeftArmPose().getX();
            this.leftArm.yRot = (float)Math.PI / 180 * armorStand.getLeftArmPose().getY();
            this.leftArm.zRot = (float)Math.PI / 180 * armorStand.getLeftArmPose().getZ();
            this.rightArm.xRot = (float)Math.PI / 180 * armorStand.getRightArmPose().getX();
            this.rightArm.yRot = (float)Math.PI / 180 * armorStand.getRightArmPose().getY();
            this.rightArm.zRot = (float)Math.PI / 180 * armorStand.getRightArmPose().getZ();
            this.leftLeg.xRot = (float)Math.PI / 180 * armorStand.getLeftLegPose().getX();
            this.leftLeg.yRot = (float)Math.PI / 180 * armorStand.getLeftLegPose().getY();
            this.leftLeg.zRot = (float)Math.PI / 180 * armorStand.getLeftLegPose().getZ();
            this.leftLeg.setPos(1.9f, 11.0f, 0.0f);
            this.rightLeg.xRot = (float)Math.PI / 180 * armorStand.getRightLegPose().getX();
            this.rightLeg.yRot = (float)Math.PI / 180 * armorStand.getRightLegPose().getY();
            this.rightLeg.zRot = (float)Math.PI / 180 * armorStand.getRightLegPose().getZ();
            this.rightLeg.setPos(-1.9f, 11.0f, 0.0f);
            this.hat.copyFrom(this.head);
        }
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
        poseStack.pushPose();
        poseStack.scale(this.armorScale, this.armorScale, this.armorScale);
        this.setHeadRotation();
        this.setChestRotation();
        this.setLegsRotation();
        this.setBootRotation();
        this.head.visible = this.slot == ArmorItem.Type.HELMET;
        this.body.visible = this.slot == ArmorItem.Type.CHESTPLATE;
        this.rightArm.visible = this.slot == ArmorItem.Type.CHESTPLATE;
        this.leftArm.visible = this.slot == ArmorItem.Type.CHESTPLATE;
        this.rightLeg.visible = this.slot == ArmorItem.Type.LEGGINGS;
        this.leftLeg.visible = this.slot == ArmorItem.Type.LEGGINGS;
        this.rightFoot.visible = this.slot == ArmorItem.Type.BOOTS;
        boolean bl = this.leftFoot.visible = this.slot == ArmorItem.Type.BOOTS;
        if (this.young) {
            float f = 2.0f;
            poseStack.pushPose();
            poseStack.scale(1.5f / f, 1.5f / f, 1.5f / f);
            poseStack.translate(0.0f, 16.0f, 0.0f);
            this.head.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.scale(1.0f / f, 1.0f / f, 1.0f / f);
            poseStack.translate(0.0f, 24.0f, 0.0f);
            this.body.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
            poseStack.popPose();
        } else {
            this.head.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
            if (this.crouching) {
                poseStack.translate(0.0f, 0.2f, 0.0f);
            }
            this.body.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
            poseStack.pushPose();
            if (this.crouching) {
                poseStack.translate(0.0f, -0.15f, 0.0f);
            }
            this.rightArm.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
            this.leftArm.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
            poseStack.popPose();
        }
        poseStack.translate(0.0f, 1.25f, 0.0f);
        if (this.crouching) {
            poseStack.translate(0.0f, -0.15f, 0.05f);
        }
        this.rightLeg.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
        this.leftLeg.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
        this.rightFoot.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
        this.leftFoot.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
        poseStack.popPose();
    }

    public void setHeadRotation() {
        ModelArmorBase.setRotation(this.head, this.head.xRot, this.head.yRot, this.head.zRot);
    }

    public void setChestRotation() {
        this.body.y -= 1.0f;
        this.rightArm.x += 5.0f;
        this.rightArm.y -= 1.0f;
        this.leftArm.x -= 5.0f;
        this.leftArm.y -= 1.0f;
        ModelArmorBase.setRotation(this.body, this.body.xRot, this.body.yRot, this.body.zRot);
        ModelArmorBase.setRotation(this.rightArm, this.rightArm.xRot, this.rightArm.yRot, this.rightArm.zRot);
        ModelArmorBase.setRotation(this.leftArm, this.leftArm.xRot, this.leftArm.yRot, this.leftArm.zRot);
    }

    public void setLegsRotation() {
        this.rightLeg.x += 2.0f;
        this.rightLeg.y -= 22.0f;
        this.leftLeg.x -= 2.0f;
        this.leftLeg.y -= 22.0f;
        ModelArmorBase.setRotation(this.rightLeg, this.rightLeg.xRot, this.rightLeg.yRot, this.rightLeg.zRot);
        ModelArmorBase.setRotation(this.leftLeg, this.leftLeg.xRot, this.leftLeg.yRot, this.leftLeg.zRot);
    }

    public void setBootRotation() {
        this.rightFoot.y = this.rightLeg.y - 0.0f;
        this.rightFoot.z = this.rightLeg.z;
        this.leftFoot.y = this.leftLeg.y - 0.0f;
        this.leftFoot.z = this.leftLeg.z;
        ModelArmorBase.setRotation(this.rightFoot, this.rightLeg.xRot, this.rightLeg.yRot, this.rightLeg.zRot);
        ModelArmorBase.setRotation(this.leftFoot, this.leftLeg.xRot, this.leftLeg.yRot, this.leftLeg.zRot);
    }

    public static void setRotation(ModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }
}

