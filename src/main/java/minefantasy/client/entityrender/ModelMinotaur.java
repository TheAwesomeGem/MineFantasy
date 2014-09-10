
package minefantasy.client.entityrender;

import minefantasy.entity.EntityMinotaur;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

// Referenced classes of package net.minecraft.src:
//            ModelBiped, MathHelper, ModelRenderer
public class ModelMinotaur extends ModelBiped {

    public ModelRenderer bipedNose;
    public int swing;
    public ModelRenderer bipedTail;
    public ModelRenderer bipedRhorn;
    public ModelRenderer bipedRhorn2;
    public ModelRenderer bipedLhorn;
    public ModelRenderer bipedLhorn2;
    public ModelRenderer bipedHead;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer shoulders;
    public ModelRenderer tailEnd;
    public ModelRenderer bipedRightThigh;
    public ModelRenderer bipedLeftThigh;
    public ModelRenderer bipedRightFoot;
    public ModelRenderer bipedLeftFoot;
    
    public ModelRenderer Mane2;
    public ModelRenderer Neck;
    public ModelRenderer Mane;

    public ModelMinotaur() {
        textureWidth = 128;
        textureHeight = 128;
        
        float headPos = 6.0F;

        bipedNose = new ModelRenderer(this, 0, 54);
        bipedNose.addBox(-2F, -1F, -6F, 4, 4, 4);
        bipedNose.setRotationPoint(0F, -4F, -headPos);
        bipedNose.setTextureSize(128, 128);
        bipedNose.mirror = true;
        shoulders = new ModelRenderer(this, 86, 50);
        shoulders.addBox(-8F, -1F, -4F, 15, 7, 6);
        shoulders.setRotationPoint(0F, -8F, 1F);
        shoulders.setTextureSize(128, 128);
        shoulders.mirror = true;
        bipedLeftArm = new ModelRenderer(this, 38, 33);
        bipedLeftArm.addBox(-1F, -2F, -2F, 5, 16, 5);
        bipedLeftArm.setRotationPoint(7F, -6F, 0F);
        bipedLeftArm.setTextureSize(128, 128);
        bipedLeftArm.mirror = true;
        tailEnd = new ModelRenderer(this, 64, 0);
        tailEnd.addBox(-1F, 4F, 10F, 2, 2, 11);
        tailEnd.setRotationPoint(0F, 4F, 0F);
        tailEnd.setTextureSize(128, 128);
        tailEnd.mirror = true;
        bipedRightThigh = new ModelRenderer(this, 38, 0);
        bipedRightThigh.addBox(-3F, 0F, -3F, 5, 10, 8);
        bipedRightThigh.setRotationPoint(-3F, 4F, 0F);
        bipedRightThigh.setTextureSize(128, 128);
        bipedRightThigh.mirror = true;
        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.addBox(-2F, -3F, -2F, 4, 6, 6);
        bipedHead.setRotationPoint(0F, -4F, -headPos);
        bipedHead.setTextureSize(128, 128);
        bipedHead.mirror = true;
        bipedLhorn = new ModelRenderer(this, 20, 5);
        bipedLhorn.addBox(2F, -2F, 1F, 5, 2, 2);
        bipedLhorn.setRotationPoint(0F, -4F, -headPos);
        bipedLhorn.setTextureSize(128, 128);
        bipedLhorn.mirror = true;
        bipedLhorn2 = new ModelRenderer(this, 20, 0);
        bipedLhorn2.addBox(6F, -6F, 1F, 1, 4, 1);
        bipedLhorn2.setRotationPoint(0F, -4F, -headPos);
        bipedLhorn2.setTextureSize(128, 128);
        bipedLhorn2.mirror = true;
        bipedRhorn = new ModelRenderer(this, 20, 5);
        bipedRhorn.addBox(-7F, -2F, 1F, 5, 2, 2);
        bipedRhorn.setRotationPoint(0F, -4F, -headPos);
        bipedRhorn.setTextureSize(128, 128);
        bipedRhorn.mirror = true;
        bipedRhorn2 = new ModelRenderer(this, 20, 0);
        bipedRhorn2.addBox(-7F, -6F, 1F, 1, 4, 1);
        bipedRhorn2.setRotationPoint(0F, -4F, -headPos);
        bipedRhorn2.setTextureSize(128, 128);
        bipedRhorn2.mirror = true;
        bipedLeftLeg = new ModelRenderer(this, 20, 50);
        bipedLeftLeg.addBox(-1F, 10F, 0F, 4, 6, 5);
        bipedLeftLeg.setRotationPoint(3F, 4F, 0F);
        bipedLeftLeg.setTextureSize(128, 128);
        bipedLeftLeg.mirror = true;
        bipedLeftThigh = new ModelRenderer(this, 38, 0);
        bipedLeftThigh.addBox(-2F, 0F, -3F, 5, 10, 8);
        bipedLeftThigh.setRotationPoint(3F, 4F, 0F);
        bipedLeftThigh.setTextureSize(128, 128);
        bipedLeftThigh.mirror = true;
        bipedLeftFoot = new ModelRenderer(this, 20, 38);
        bipedLeftFoot.addBox(-1F, 13F, -2F, 4, 7, 5);
        bipedLeftFoot.setRotationPoint(3F, 4F, 0F);
        bipedLeftFoot.setTextureSize(128, 128);
        bipedLeftFoot.mirror = true;
        bipedRightLeg = new ModelRenderer(this, 20, 50);
        bipedRightLeg.addBox(-3F, 10F, 0F, 4, 6, 5);
        bipedRightLeg.setRotationPoint(-3F, 4F, 0F);
        bipedRightLeg.setTextureSize(128, 128);
        bipedRightLeg.mirror = true;
        bipedRightFoot = new ModelRenderer(this, 20, 38);
        bipedRightFoot.addBox(-3F, 13F, -2F, 4, 7, 5);
        bipedRightFoot.setRotationPoint(-3F, 4F, 0F);
        bipedRightFoot.setTextureSize(128, 128);
        bipedRightFoot.mirror = true;
        bipedBody = new ModelRenderer(this, 0, 12);
        bipedBody.addBox(-5F, 0F, -5F, 10, 14, 9);
        bipedBody.setRotationPoint(0F, -8F, 1F);
        bipedBody.setTextureSize(128, 128);
        bipedBody.mirror = true;
        bipedTail = new ModelRenderer(this, 64, 17);
        bipedTail.addBox(-2F, -3F, 2F, 4, 3, 11);
        bipedTail.setRotationPoint(0F, 4F, 0F);
        bipedTail.setTextureSize(128, 128);
        bipedTail.mirror = true;
        bipedRightArm = new ModelRenderer(this, 38, 33);
        bipedRightArm.addBox(-4F, -2F, -2F, 5, 16, 5);
        bipedRightArm.setRotationPoint(-7F, -6F, 1F);
        bipedRightArm.setTextureSize(128, 128);
        bipedRightArm.mirror = true;
        
        Mane2 = new ModelRenderer(this, 0, 88);
        Mane2.addBox(-3F, -3F, -2F, 6, 12, 8);
        Mane2.setRotationPoint(0F, -8F, 1F);
        Mane2.setTextureSize(128, 128);
        Neck = new ModelRenderer(this, 39, 64);
        Neck.addBox(-3F, -1F, -9F, 6, 8, 3);
        Neck.setRotationPoint(0F, -8F, 3F);
        Neck.setTextureSize(128, 128);
        Mane = new ModelRenderer(this, 0, 64);
        Mane.addBox(-4F, -2F, -4F, 8, 13, 9);
        Mane.setRotationPoint(0F, -8F, 1F);
        Mane.setTextureSize(128, 128);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles((EntityMinotaur) entity, f, f1, f2, f3, f4, f5);
        bipedNose.render(f5);
        tailEnd.render(f5);
        bipedLeftFoot.render(f5);
        bipedRightFoot.render(f5);
        shoulders.render(f5);
        bipedLeftThigh.render(f5);
        bipedRightThigh.render(f5);
        bipedTail.render(f5);
        bipedRhorn.render(f5);
        bipedRhorn2.render(f5);
        bipedLhorn.render(f5);
        bipedLhorn2.render(f5);
        bipedHead.render(f5);
        bipedLeftLeg.render(f5);
        bipedRightLeg.render(f5);
        bipedBody.render(f5);
        bipedRightArm.render(f5);
        bipedLeftArm.render(f5);
        bipedRightLeg.render(f5);
        bipedLeftLeg.render(f5);
        Mane.render(f5);
        Mane2.render(f5);
        Neck.render(f5);
    }

    public void setRotationAngles(EntityMinotaur mino, float f, float f1, float f2, float f3, float f4, float f5) {
        int swingAngle = 28 * mino.swing;
        double swingRad = Math.toRadians(swingAngle);
        
        int swingAngleY = 9 * mino.swing;
        double swingRadY = Math.toRadians(swingAngleY);
        bipedHead.rotateAngleY = f3 / 57.29578F;
        bipedHead.rotateAngleX = f4 / 57.29578F + (float)Math.toRadians(mino.getHeadChargeAngle());
        if (mino.swing <= 0) {
            bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
            bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
            bipedRightArm.rotateAngleY = 0.0F;
        bipedLeftArm.rotateAngleY = 0.0F;
        } else {
            bipedRightArm.rotateAngleX = (float) -swingRad;
            bipedLeftArm.rotateAngleX = (float) -swingRad;
            bipedRightArm.rotateAngleY = (float)swingRadY;
            bipedLeftArm.rotateAngleY = (float)-swingRadY;
        }
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;

        bipedTail.rotateAngleX = -1.182165F;
        tailEnd.rotateAngleX = -0.5363927F;
        bipedNose.rotateAngleX = bipedHead.rotateAngleX;
        bipedNose.rotateAngleY = bipedHead.rotateAngleY;
        bipedRhorn.rotateAngleX = bipedHead.rotateAngleX;
        bipedRhorn.rotateAngleY = bipedHead.rotateAngleY;
        bipedRhorn2.rotateAngleX = bipedHead.rotateAngleX;
        bipedRhorn2.rotateAngleY = bipedHead.rotateAngleY;
        bipedLhorn.rotateAngleX = bipedHead.rotateAngleX;
        bipedLhorn.rotateAngleY = bipedHead.rotateAngleY;
        bipedLhorn2.rotateAngleX = bipedHead.rotateAngleX;
        bipedLhorn2.rotateAngleY = bipedHead.rotateAngleY;
        
        joinBlocks(Mane, bipedBody);
        joinBlocks(Mane2, bipedBody);
        joinBlocks(Neck, bipedBody);
        
        if (onGround > -9990F) {
            float f6 = onGround;
            bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
            bipedRightArm.rotationPointZ = MathHelper.sin(bipedBody.rotateAngleY) * 5F;
            bipedRightArm.rotationPointX = -MathHelper.cos(bipedBody.rotateAngleY) * 5F;
            bipedLeftArm.rotationPointZ = -MathHelper.sin(bipedBody.rotateAngleY) * 5F;
            bipedLeftArm.rotationPointX = MathHelper.cos(bipedBody.rotateAngleY) * 5F;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
            f6 = 1.0F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f8 = MathHelper.sin(f6 * 3.141593F);
            float f10 = MathHelper.sin(onGround * 3.141593F) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            bipedRightArm.rotateAngleX -= (double) f8 * 1.2D + (double) f10;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
            bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * 3.141593F) * -0.4F;
        }
        bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
        
        bipedLeftThigh.rotateAngleX = bipedLeftFoot.rotateAngleX = bipedLeftLeg.rotateAngleX;
        bipedRightThigh.rotateAngleX = bipedRightFoot.rotateAngleX = bipedRightLeg.rotateAngleX;

    }
    private void joinBlocks(ModelRenderer model, ModelRenderer anchor)
    {
    	joinBlocks(model, anchor, 1.0F);
    }
    
    private void joinBlocks(ModelRenderer model, ModelRenderer anchor, float ratio)
    {
    	model.rotateAngleX = anchor.rotateAngleX * ratio;
    	model.rotateAngleY = anchor.rotateAngleY * ratio;
    	model.rotateAngleZ = anchor.rotateAngleZ * ratio;
    }
}
