package minefantasy.client.entityrender;

import minefantasy.entity.EntityBasilisk;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;



public class ModelBasilisk extends ModelBase {

	ModelRenderer LFoot3;
    ModelRenderer LLeg3;
    ModelRenderer LLeg4;
    ModelRenderer LFoot4;
    ModelRenderer LFoot2;
    ModelRenderer LLeg2;
    ModelRenderer LFoot1;
    ModelRenderer LLeg1;
    ModelRenderer RFoot3;
    ModelRenderer RLeg3;
    ModelRenderer RLeg4;
    ModelRenderer RFoot4;
    ModelRenderer RFoot2;
    ModelRenderer RLeg2;
    ModelRenderer RFoot1;
    ModelRenderer RLeg1;
    ModelRenderer Body;
    ModelRenderer Back;
    ModelRenderer Belly;
    ModelRenderer Tail2;
    ModelRenderer Tail1;
    ModelRenderer Nose;
    ModelRenderer Neck;
    ModelRenderer Top;
    ModelRenderer Mouth;
    ModelRenderer TeethTop;
    ModelRenderer Head;
    ModelRenderer Jaw;
    ModelRenderer TeethBottom;

    
    public ModelBasilisk()
    {
    	textureWidth = 128;
        textureHeight = 128;
        
        LFoot3 = new ModelRenderer(this, 34, 0);
        LLeg3 = new ModelRenderer(this, 0, 0);
        LLeg4 = new ModelRenderer(this, 0, 0);
        LFoot4 = new ModelRenderer(this, 34, 0);
        LFoot2 = new ModelRenderer(this, 34, 0);
        LLeg2 = new ModelRenderer(this, 0, 0);
        LFoot1 = new ModelRenderer(this, 34, 0);
        LLeg1 = new ModelRenderer(this, 0, 0);
        RFoot3 = new ModelRenderer(this, 34, 0);
        RLeg3 = new ModelRenderer(this, 0, 0);
        RLeg4 = new ModelRenderer(this, 0, 0);
        RFoot4 = new ModelRenderer(this, 34, 0);
        RFoot2 = new ModelRenderer(this, 34, 0);
        RLeg2 = new ModelRenderer(this, 0, 0);
        RFoot1 = new ModelRenderer(this, 34, 0);
        RLeg1 = new ModelRenderer(this, 0, 0);
        Body = new ModelRenderer(this, 0, 45);
        Body.addBox(-26F, -2F, -6F, 44, 7, 12);
        Back = new ModelRenderer(this, 28, 0);
        Belly = new ModelRenderer(this, 28, 0);
        Tail2 = new ModelRenderer(this, 42, 64);
        Tail1 = new ModelRenderer(this, 0, 64);
        Nose = new ModelRenderer(this, 20, 83);
        Neck = new ModelRenderer(this, 88, 0);
        Top = new ModelRenderer(this, 80, 86);
        Mouth = new ModelRenderer(this, 0, 83);
        Head = new ModelRenderer(this, 80, 64);
        Jaw = new ModelRenderer(this, 80, 99);
        TeethBottom = new ModelRenderer(this, 0, 112);
        TeethTop = new ModelRenderer(this, 0, 112);
        
        
          LFoot3.mirror = true;
          LFoot3.addBox(-0.5F, 8F, -6F, 7, 4, 8);
          LFoot3.setRotationPoint(4F, 12F, 11F);
          LFoot3.setTextureSize(128, 128);
          LFoot3.mirror = true;
          setRotation(LFoot3, 0F, 0F, 0F);
          LFoot3.mirror = false;
          LLeg3.mirror = true;
          LLeg3.addBox(-1F, -2F, -5F, 7, 10, 7);
          LLeg3.setRotationPoint(4F, 12F, 11F);
          LLeg3.setTextureSize(128, 128);
          LLeg3.mirror = true;
          setRotation(LLeg3, 0F, 0F, 0F);
          LLeg3.mirror = false;
          LLeg4.mirror = true;
          LLeg4.addBox(-1F, -2F, -4F, 7, 10, 7);
          LLeg4.setRotationPoint(4F, 12F, 20F);
          LLeg4.setTextureSize(128, 128);
          LLeg4.mirror = true;
          setRotation(LLeg4, 0F, 0F, 0F);
          LLeg4.mirror = false;
          LFoot4.mirror = true;
          LFoot4.addBox(-0.5F, 8F, -5F, 7, 4, 8);
          LFoot4.setRotationPoint(4F, 12F, 20F);
          LFoot4.setTextureSize(128, 128);
          LFoot4.mirror = true;
          setRotation(LFoot4, 0F, 0F, 0F);
          LFoot4.mirror = false;
          LFoot2.mirror = true;
          LFoot2.addBox(-0.5F, 8F, -5F, 7, 4, 8);
          LFoot2.setRotationPoint(4F, 12F, 0F);
          LFoot2.setTextureSize(128, 128);
          LFoot2.mirror = true;
          setRotation(LFoot2, 0F, 0F, 0F);
          LFoot2.mirror = false;
          LLeg2.mirror = true;
          LLeg2.addBox(-1F, -2F, -4F, 7, 10, 7);
          LLeg2.setRotationPoint(4F, 12F, 0F);
          LLeg2.setTextureSize(128, 128);
          LLeg2.mirror = true;
          setRotation(LLeg2, 0F, 0F, 0F);
          LLeg2.mirror = false;
          LFoot1.mirror = true;
          LFoot1.addBox(-0.5F, 8F, -6F, 7, 4, 8);
          LFoot1.setRotationPoint(4F, 12F, -9F);
          LFoot1.setTextureSize(128, 128);
          LFoot1.mirror = true;
          setRotation(LFoot1, 0F, 0F, 0F);
          LFoot1.mirror = false;
          LLeg1.mirror = true;
          LLeg1.addBox(-1F, -2F, -5F, 7, 10, 7);
          LLeg1.setRotationPoint(4F, 12F, -9F);
          LLeg1.setTextureSize(128, 128);
          LLeg1.mirror = true;
          setRotation(LLeg1, 0F, 0F, 0F);
          LLeg1.mirror = false;
          RFoot3.addBox(-6.5F, 8F, -6F, 7, 4, 8);
          RFoot3.setRotationPoint(-4F, 12F, 11F);
          RFoot3.setTextureSize(128, 128);
          RFoot3.mirror = true;
          setRotation(RFoot3, 0F, 0F, 0F);
          RLeg3.addBox(-6F, -2F, -5F, 7, 10, 7);
          RLeg3.setRotationPoint(-4F, 12F, 11F);
          RLeg3.setTextureSize(128, 128);
          RLeg3.mirror = true;
          setRotation(RLeg3, 0F, 0F, 0F);
          RLeg4.addBox(-6F, -2F, -4F, 7, 10, 7);
          RLeg4.setRotationPoint(-4F, 12F, 20F);
          RLeg4.setTextureSize(128, 128);
          RLeg4.mirror = true;
          setRotation(RLeg4, 0F, 0F, 0F);
          RFoot4.addBox(-6.5F, 8F, -5F, 7, 4, 8);
          RFoot4.setRotationPoint(-4F, 12F, 20F);
          RFoot4.setTextureSize(128, 128);
          RFoot4.mirror = true;
          setRotation(RFoot4, 0F, 0F, 0F);
          RFoot2.addBox(-6.5F, 8F, -5F, 7, 4, 8);
          RFoot2.setRotationPoint(-4F, 12F, 0F);
          RFoot2.setTextureSize(128, 128);
          RFoot2.mirror = true;
          setRotation(RFoot2, 0F, 0F, 0F);
          RLeg2.addBox(-6F, -2F, -4F, 7, 10, 7);
          RLeg2.setRotationPoint(-4F, 12F, 0F);
          RLeg2.setTextureSize(128, 128);
          RLeg2.mirror = true;
          setRotation(RLeg2, 0F, 0F, 0F);
          RFoot1.addBox(-6.5F, 8F, -6F, 7, 4, 8);
          RFoot1.setRotationPoint(-4F, 12F, -9F);
          RFoot1.setTextureSize(128, 128);
          RFoot1.mirror = true;
          setRotation(RFoot1, 0F, 0F, 0F);
          RLeg1.addBox(-6F, -2F, -5F, 7, 10, 7);
          RLeg1.setRotationPoint(-4F, 12F, -9F);
          RLeg1.setTextureSize(128, 128);
          RLeg1.mirror = true;
          setRotation(RLeg1, 0F, 0F, 0F);
          Body.setRotationPoint(0F, 9F, 0F);
          Body.setTextureSize(128, 128);
          Body.mirror = true;
          setRotation(Body, 0F, 1.570796F, 0F);
          Back.addBox(-5F, -4F, -16F, 10, 3, 40);
          Back.setRotationPoint(0F, 9F, 0F);
          Back.setTextureSize(128, 128);
          Back.mirror = true;
          setRotation(Back, 0F, 0F, 0F);
          Belly.addBox(-5F, 5F, -15F, 10, 3, 40);
          Belly.setRotationPoint(0F, 9F, 0F);
          Belly.setTextureSize(128, 128);
          Belly.mirror = true;
          setRotation(Belly, 0F, 0F, 0F);
          Tail2.addBox(-3F, 5F, 9F, 6, 4, 13);
          Tail2.setRotationPoint(0F, 10F, 24F);
          Tail2.setTextureSize(128, 128);
          Tail2.mirror = true;
          setRotation(Tail2, -0.0698132F, 0F, 0F);
          Tail1.addBox(-4F, -3F, 0F, 8, 6, 13);
          Tail1.setRotationPoint(0F, 10F, 24F);
          Tail1.setTextureSize(128, 128);
          Tail1.mirror = true;
          setRotation(Tail1, -0.6457718F, 0F, 0F);
          Nose.addBox(-4F, 0F, -20F, 8, 4, 4);
          Nose.setRotationPoint(0F, 8F, -15F);
          Nose.setTextureSize(128, 128);
          Nose.mirror = true;
          setRotation(Nose, 0F, 0F, 0F);
          Neck.addBox(-5F, -1F, -8F, 10, 8, 10);
          Neck.setRotationPoint(0F, 8F, -15F);
          Neck.setTextureSize(128, 128);
          Neck.mirror = true;
          setRotation(Neck, 0F, 0F, 0F);
          Top.addBox(-5F, -5F, -16F, 10, 2, 11);
          Top.setRotationPoint(0F, 8F, -15F);
          Top.setTextureSize(128, 128);
          Top.mirror = true;
          setRotation(Top, 0F, 0F, 0F);
          Mouth.addBox(-3.5F, 4F, -19F, 7, 3, 3);
          Mouth.setRotationPoint(0F, 8F, -15F);
          Mouth.setTextureSize(128, 128);
          Mouth.mirror = true;
          setRotation(Mouth, 0F, 0F, 0F);
          TeethTop.mirror = true;
          TeethTop.addBox(-5F, 4F, -16F, 10, 2, 10);
          TeethTop.setRotationPoint(0F, 8F, -15F);
          TeethTop.setTextureSize(128, 128);
          TeethTop.mirror = true;
          setRotation(TeethTop, 0F, 0F, 0F);
          TeethTop.mirror = false;
          Head.addBox(-6F, -3F, -16F, 12, 7, 12);
          Head.setRotationPoint(0F, 8F, -15F);
          Head.setTextureSize(128, 128);
          Head.mirror = true;
          setRotation(Head, 0F, 0F, 0F);
          Jaw.addBox(-6F, 4F, -16F, 12, 4, 12);
          Jaw.setRotationPoint(0F, 8F, -15F);
          Jaw.setTextureSize(128, 128);
          Jaw.mirror = true;
          setRotation(Jaw, 0F, 0F, 0F);
          TeethBottom.addBox(-5F, 2F, -16F, 10, 2, 10);
          TeethBottom.setRotationPoint(0F, 8F, -15F);
          TeethBottom.setTextureSize(128, 128);
          TeethBottom.mirror = true;
          setRotation(TeethBottom, 0F, 0F, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     * ModelPig
     */
    @Override
    public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
        super.render(entity, f1, f2, f3, f4, f5, f6);
        setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
        LFoot3.render(f6);
        LLeg3.render(f6);
        LLeg4.render(f6);
        LFoot4.render(f6);
        LFoot2.render(f6);
        LLeg2.render(f6);
        LFoot1.render(f6);
        LLeg1.render(f6);
        RFoot3.render(f6);
        RLeg3.render(f6);
        RLeg4.render(f6);
        RFoot4.render(f6);
        RFoot2.render(f6);
        RLeg2.render(f6);
        RFoot1.render(f6);
        RLeg1.render(f6);
        Body.render(f6);
        Back.render(f6);
        Belly.render(f6);
        Tail2.render(f6);
        Tail1.render(f6);
        Nose.render(f6);
        Neck.render(f6);
        Top.render(f6);
        Mouth.render(f6);
        TeethTop.render(f6);
        Head.render(f6);
        Jaw.render(f6);
        TeethBottom.render(f6);
    }

    
    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
    {
    	EntityBasilisk basilisk = (EntityBasilisk)entity;
    	float mouth = basilisk.getMouthAngle();
    	float noseMove = -10*mouth;
    	float mouthMove = 8*mouth;
    	
        this.Top.rotateAngleX = (f5 + noseMove) / (180F / (float)Math.PI);
        this.Top.rotateAngleY = f4 / (180F / (float)Math.PI);
        this.Head.rotateAngleX = (f5 + noseMove) / (180F / (float)Math.PI);
        this.Head.rotateAngleY = f4 / (180F / (float)Math.PI);
        this.Jaw.rotateAngleX = (f5 + mouthMove) / (180F / (float)Math.PI);
        this.Jaw.rotateAngleY = f4 / (180F / (float)Math.PI);
        this.Nose.rotateAngleX = (f5 + noseMove) / (180F / (float)Math.PI);
        this.Nose.rotateAngleY = f4 / (180F / (float)Math.PI);
        this.Mouth.rotateAngleX = (f5 + mouthMove) / (180F / (float)Math.PI);
        this.Mouth.rotateAngleY = f4 / (180F / (float)Math.PI);
        this.TeethBottom.rotateAngleX = (f5 + mouthMove) / (180F / (float)Math.PI);
        this.TeethBottom.rotateAngleY = f4 / (180F / (float)Math.PI);
        this.TeethTop.rotateAngleX = (f5 + noseMove) / (180F / (float)Math.PI);
        this.TeethTop.rotateAngleY = f4 / (180F / (float)Math.PI);
        
        this.Neck.rotateAngleX = f5/2 / (180F / (float)Math.PI);
        this.Neck.rotateAngleY = f4/2 / (180F / (float)Math.PI);
        //this.Belly.rotateAngleX = ((float)Math.PI / 2F);
        //this.Back.rotateAngleX = ((float)Math.PI / 2F);
        //this.Tail1.rotateAngleX = ((float)Math.PI / 2F);
        //this.Tail2.rotateAngleX = ((float)Math.PI / 2F);
        //this.Neck.rotateAngleX = ((float)Math.PI / 2F);
        //this.Body.rotateAngleX = ((float)Math.PI / 2F);
        
        this.RLeg4.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
        this.LLeg4.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
        this.RLeg3.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
        this.LLeg3.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
        this.RLeg2.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
        this.LLeg2.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
        this.RLeg1.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
        this.LLeg1.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
        
        this.RFoot4.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
        this.LFoot4.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
        this.RFoot3.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
        this.LFoot3.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
        this.RFoot2.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
        this.LFoot2.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
        this.RFoot1.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
        this.LFoot1.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
    }

}
