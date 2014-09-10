package minefantasy.client.entityrender;

import minefantasy.entity.EntitySkeletalKnight;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ModelSkeletalKnight extends ModelBiped
{

    public ModelSkeletalKnight() 
    {
        textureWidth = 128;
        textureHeight = 64;

        bipedHead = new ModelRenderer(this, 46, 0);
        bipedBody = new ModelRenderer(this, 0, 46);
        bipedRightArm = new ModelRenderer(this, 28, 32);
        bipedRightLeg = new ModelRenderer(this, 28, 51);
        bipedLeftArm = new ModelRenderer(this, 40, 16);
        bipedLeftLeg = new ModelRenderer(this, 28, 51);

        bipedHead.setTextureOffset(46, 0).addBox(-4.5F, -8.4F, -4.4F, 9, 9, 9);
        bipedHead.setRotationPoint(0F, 0F, 0F);
        bipedHead.setTextureSize(128, 64);
        bipedHead.mirror = true;
        setRotation(bipedHead, 0F, 0F, 0F);

        bipedBody.setTextureOffset(0, 46).addBox(-4F, 0F, -3F, 8, 12, 6);
        bipedBody.setRotationPoint(0F, 0F, 0F);
        bipedBody.setTextureSize(128, 64);
        bipedBody.mirror = true;
        setRotation(bipedBody, 0F, 0F, 0F);

        bipedRightArm.setTextureOffset(28, 32).addBox(-1.5F, -3F, -1.5F, 3, 3, 3);
        bipedRightArm.setRotationPoint(-5F, 2F, 0F);
        bipedRightArm.setTextureSize(128, 64);
        bipedRightArm.mirror = true;

        bipedRightLeg.setTextureOffset(28, 51).addBox(-1.5F, 2F, -1.5F, 3, 10, 3);
        bipedRightLeg.setRotationPoint(-2F, 12F, 0F);
        bipedRightLeg.setTextureSize(128, 64);
        bipedRightLeg.mirror = true;
        setRotation(bipedRightLeg, 0F, 0F, 0F);

        bipedLeftArm.setTextureOffset(40, 16).addBox(-1F, -2F, -1F, 2, 12, 2);
        bipedLeftArm.setRotationPoint(5F, 2F, 0F);
        bipedLeftArm.setTextureSize(128, 64);
        bipedLeftArm.mirror = true;
        setRotation(bipedLeftArm, 0F, 0F, 0F);

        bipedLeftLeg.setTextureOffset(28, 51).addBox(-1.5F, 2F, -1.5F, 3, 10, 3);
        bipedLeftLeg.setRotationPoint(2F, 12F, 0F);
        bipedLeftLeg.setTextureSize(128, 64);
        bipedLeftLeg.mirror = true;
        setRotation(bipedLeftLeg, 0F, 0F, 0F);
        bipedLeftArm.setTextureOffset(12, 32).addBox(-1.5F, -3.5F, -1.5F, 4, 5, 4);
        bipedLeftArm.setRotationPoint(5F, 2F, 0F);
        bipedLeftArm.setTextureSize(128, 64);
        bipedLeftArm.mirror = true;
        setRotation(bipedLeftArm, 0F, 0F, 0F);
        bipedLeftArm.setTextureOffset(0, 30).addBox(-1.5F, 4.5F, -1.5F, 3, 6, 3);
        bipedLeftArm.setRotationPoint(5F, 2F, 0F);
        bipedLeftArm.setTextureSize(128, 64);
        bipedLeftArm.mirror = true;
        setRotation(bipedLeftArm, 0F, 0F, 0F);
        bipedRightArm.setRotationPoint(-5F, 2F, 0F);
        bipedRightArm.setTextureSize(128, 64);
        bipedRightArm.mirror = true;
        setRotation(bipedRightArm, 0F, 0F, 0F);
        bipedRightArm.setTextureOffset(40, 30).addBox(-1.5F, 1F, -1.5F, 3, 7, 3);
        bipedRightArm.setRotationPoint(-5F, 2F, 0F);
        bipedRightArm.setTextureSize(128, 64);
        bipedRightArm.mirror = true;
        bipedBody.setTextureOffset(16, 16).addBox(-4F, 0F, -2F, 8, 12, 4);
        bipedBody.setRotationPoint(0F, 0F, 0F);
        bipedBody.setTextureSize(128, 64);
        bipedBody.mirror = true;
        setRotation(bipedBody, 0F, 0F, 0F);
        bipedRightLeg.setTextureOffset(0, 16).addBox(-1F, 0F, -1F, 2, 12, 2);
        bipedRightLeg.setRotationPoint(-2F, 12F, 0F);
        bipedRightLeg.setTextureSize(128, 64);
        bipedRightLeg.mirror = true;
        setRotation(bipedRightLeg, 0F, 0F, 0F);
        bipedLeftLeg.setTextureOffset(0, 16).addBox(-1F, 0F, -1F, 2, 12, 2);
        bipedLeftLeg.setRotationPoint(2F, 12F, 0F);
        bipedLeftLeg.setTextureSize(128, 64);
        bipedLeftLeg.mirror = true;
        setRotation(bipedLeftLeg, 0F, 0F, 0F);
        bipedHead.setTextureOffset(0, 0).addBox(-4F, -8F, -4F, 8, 8, 8);
        bipedHead.setRotationPoint(0F, 0F, 0F);
        bipedHead.setTextureSize(128, 64);
        bipedHead.mirror = true;
        setRotation(bipedHead, 0F, 0F, 0F);
    }
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	super.render(entity, f, f1, f2, f3, f4, f5);
    	
    	if(entity instanceof EntitySkeletalKnight)
    	{
    		this.aimedBow = ((EntitySkeletalKnight)entity).useRanged();
    	}
    }
    

    public void setRotationAngles(EntitySkeletalKnight skele, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, skele);
        
    }
}