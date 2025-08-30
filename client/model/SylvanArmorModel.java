/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.world.item.ArmorItem$Type
 */
package elucent.rootsclassic.client.model;

import elucent.rootsclassic.client.model.ModelArmorBase;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.item.ArmorItem;

public class SylvanArmorModel
extends ModelArmorBase {
    public SylvanArmorModel(ModelPart root, ArmorItem.Type slot) {
        super(root, slot);
        this.armorScale = 1.1f;
    }

    public static LayerDefinition createArmorDefinition() {
        MeshDefinition meshdefinition = ModelArmorBase.createArmorMesh();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("head_1", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0f, 0.0f, 0.0f, 6.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)-9.0f, (float)-4.0f, (float)-0.2617994f, (float)0.0f, (float)0.0f));
        head.addOrReplaceChild("head_2", CubeListBuilder.create().texOffs(32, 16).addBox(-4.0f, 0.0f, 0.0f, 8.0f, 2.0f, 7.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)-8.0f, (float)-3.0f, (float)0.0f, (float)0.0f, (float)0.0f));
        head.addOrReplaceChild("head_3", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)3.5f, (float)-8.0f, (float)3.0f, (float)-0.2617994f, (float)-1.963495f, (float)0.0f));
        head.addOrReplaceChild("head_4", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-3.5f, (float)-8.0f, (float)3.0f, (float)-0.2617994f, (float)1.963495f, (float)0.0f));
        head.addOrReplaceChild("head_5", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)-8.0f, (float)4.0f, (float)-0.2617994f, (float)3.141593f, (float)0.0f));
        head.addOrReplaceChild("head_6", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0f, -4.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)-7.0f, (float)2.0f, (float)-0.7853982f, (float)-0.2617994f, (float)0.0f));
        head.addOrReplaceChild("head_7", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0f, -4.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)-7.0f, (float)2.0f, (float)-0.7853982f, (float)0.2617994f, (float)0.0f));
        head.addOrReplaceChild("head_8", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -8.0f, -1.0f, 2.0f, 8.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)-7.0f, (float)-2.0f, (float)-0.5235988f, (float)-0.5235988f, (float)0.0f));
        head.addOrReplaceChild("head_9", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -8.0f, -1.0f, 2.0f, 8.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)-7.0f, (float)-2.0f, (float)-0.5235988f, (float)0.5235988f, (float)0.0f));
        head.addOrReplaceChild("head_10", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, 0.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)-10.0f, (float)2.0f, (float)-1.047198f, (float)3.141593f, (float)0.0f));
        head.addOrReplaceChild("head_11", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, 0.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)-8.0f, (float)-3.0f, (float)-2.094395f, (float)3.141593f, (float)0.0f));
        head.addOrReplaceChild("head_12", CubeListBuilder.create().texOffs(16, 50).addBox(-2.0f, 0.0f, 0.0f, 4.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)-8.0f, (float)-4.0f, (float)-2.530727f, (float)3.141593f, (float)0.0f));
        head.addOrReplaceChild("head_13", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-4.0f, (float)-8.0f, (float)0.0f, (float)-0.2617994f, (float)1.308997f, (float)0.0f));
        head.addOrReplaceChild("head_14", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)4.0f, (float)-8.0f, (float)0.0f, (float)-0.2617994f, (float)-1.308997f, (float)0.0f));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(32, 16).addBox(-4.0f, -1.0f, -7.0f, 8.0f, 2.0f, 7.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)-1.0f, (float)1.308997f, (float)0.0f, (float)0.0f));
        body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(32, 16).addBox(-4.0f, -1.0f, -7.0f, 8.0f, 2.0f, 7.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)1.0f, (float)1.308997f, (float)-3.141593f, (float)0.0f));
        body.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0f, -1.0f, -4.0f, 6.0f, 2.0f, 4.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)6.0f, (float)-2.0f, (float)1.570796f, (float)0.0f, (float)0.0f));
        body.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0.0f, 0.0f, 4.0f, 2.0f, 4.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)12.0f, (float)-2.5f, (float)1.570796f, (float)0.0f, (float)0.0f));
        body.addOrReplaceChild("body5", CubeListBuilder.create().texOffs(48, 0).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)2.0f, (float)-0.2617994f, (float)3.141593f, (float)0.0f));
        body.addOrReplaceChild("body6", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0f, -4.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)2.0f, (float)1.5f, (float)-0.7853982f, (float)-0.2617994f, (float)0.0f));
        body.addOrReplaceChild("body7", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0f, -4.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)2.0f, (float)1.5f, (float)-0.7853982f, (float)0.2617994f, (float)0.0f));
        body.addOrReplaceChild("body8", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0f, -4.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)5.0f, (float)2.0f, (float)-1.047198f, (float)-0.2617994f, (float)0.0f));
        body.addOrReplaceChild("body9", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0f, -4.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)5.0f, (float)2.0f, (float)-1.047198f, (float)0.2617994f, (float)0.0f));
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        right_arm.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-5.0f, (float)8.0f, (float)0.0f, (float)-1.570796f, (float)1.570796f, (float)0.0f));
        right_arm.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-7.0f, (float)6.0f, (float)-1.0f, (float)3.141593f, (float)1.570796f, (float)0.2617994f));
        right_arm.addOrReplaceChild("right_arm3", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-7.0f, (float)6.0f, (float)1.0f, (float)3.141593f, (float)1.570796f, (float)-0.2617994f));
        right_arm.addOrReplaceChild("right_arm4", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-8.0f, (float)6.0f, (float)0.0f, (float)3.403392f, (float)1.570796f, (float)0.0f));
        right_arm.addOrReplaceChild("right_arm5", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0.0f, 0.0f, 4.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-4.0f, (float)-3.5f, (float)0.0f, (float)-1.308997f, (float)1.570796f, (float)0.0f));
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        left_arm.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)9.0f, (float)8.0f, (float)0.0f, (float)-1.570796f, (float)1.570796f, (float)0.0f));
        left_arm.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)8.0f, (float)6.0f, (float)0.0f, (float)2.879793f, (float)1.570796f, (float)0.0f));
        left_arm.addOrReplaceChild("left_arm3", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)7.0f, (float)6.0f, (float)-1.0f, (float)3.141593f, (float)1.570796f, (float)0.2617994f));
        left_arm.addOrReplaceChild("left_arm4", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)7.0f, (float)6.0f, (float)1.0f, (float)3.141593f, (float)1.570796f, (float)-0.2617994f));
        left_arm.addOrReplaceChild("left_arm5", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, 0.0f, 0.0f, 4.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)4.0f, (float)-3.5f, (float)0.0f, (float)-1.308997f, (float)-1.570796f, (float)0.0f));
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-12.0f, (float)0.0f));
        left_leg.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)0.0f, (float)-2.0f, (float)0.2617994f, (float)3.141593f, (float)0.0f));
        left_leg.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)0.0f, (float)2.0f, (float)-0.2617994f, (float)3.141593f, (float)0.0f));
        left_leg.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)4.0f, (float)0.0f, (float)0.0f, (float)-0.2617994f, (float)-1.570796f, (float)0.0f));
        left_leg.addOrReplaceChild("left_leg4", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0f, -4.0f, -2.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)4.0f, (float)2.0f, (float)2.0f, (float)-0.7853982f, (float)0.5235988f, (float)0.0f));
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-12.0f, (float)0.0f));
        right_leg.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-4.0f, (float)0.0f, (float)0.0f, (float)-0.2617994f, (float)1.570796f, (float)0.0f));
        right_leg.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)0.0f, (float)2.0f, (float)-0.2617994f, (float)3.141593f, (float)0.0f));
        right_leg.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 6.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)0.0f, (float)-2.0f, (float)0.2617994f, (float)3.141593f, (float)0.0f));
        right_leg.addOrReplaceChild("right_leg4", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0f, -4.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-4.0f, (float)2.0f, (float)2.0f, (float)-0.7853982f, (float)-0.5235988f, (float)0.0f));
        PartDefinition right_foot = partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-12.0f, (float)0.0f));
        right_foot.addOrReplaceChild("right_foot1", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, -2.0f, 0.0f, 4.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)10.0f, (float)-2.0f, (float)-1.570796f, (float)1.570796f, (float)0.0f));
        right_foot.addOrReplaceChild("right_foot2", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, -2.0f, 0.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)10.0f, (float)0.0f, (float)-1.570796f, (float)1.570796f, (float)0.0f));
        right_foot.addOrReplaceChild("right_foot3", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)10.0f, (float)1.0f, (float)0.2617994f, (float)0.0f, (float)3.141593f));
        right_foot.addOrReplaceChild("right_foot4", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-2.0f, (float)10.0f, (float)-2.0f, (float)-0.2617994f, (float)0.0f, (float)3.141593f));
        right_foot.addOrReplaceChild("right_foot5", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-1.0f, (float)10.0f, (float)-1.0f, (float)-0.2617994f, (float)1.570796f, (float)3.141593f));
        right_foot.addOrReplaceChild("right_foot6", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)-3.0f, (float)10.0f, (float)-1.0f, (float)0.2617994f, (float)1.570796f, (float)3.141593f));
        PartDefinition left_foot = partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-12.0f, (float)0.0f));
        left_foot.addOrReplaceChild("left_foot1", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, -2.0f, 0.0f, 4.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)10.0f, (float)-2.0f, (float)-1.570796f, (float)1.570796f, (float)0.0f));
        left_foot.addOrReplaceChild("left_foot2", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0f, -2.0f, 0.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)10.0f, (float)0.0f, (float)-1.570796f, (float)1.570796f, (float)0.0f));
        left_foot.addOrReplaceChild("left_foot3", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)1.0f, (float)10.0f, (float)-1.0f, (float)0.2617994f, (float)1.570796f, (float)3.141593f));
        left_foot.addOrReplaceChild("left_foot4", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)10.0f, (float)1.0f, (float)0.2617994f, (float)0.0f, (float)3.141593f));
        left_foot.addOrReplaceChild("left_foot5", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)2.0f, (float)10.0f, (float)-2.0f, (float)-0.2617994f, (float)0.0f, (float)3.141593f));
        left_foot.addOrReplaceChild("left_foot6", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f).mirror(), PartPose.offsetAndRotation((float)3.0f, (float)10.0f, (float)-1.0f, (float)-0.2617994f, (float)1.570796f, (float)3.141593f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)64, (int)64);
    }
}

