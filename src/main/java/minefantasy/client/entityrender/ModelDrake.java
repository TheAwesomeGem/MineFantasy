package minefantasy.client.entityrender;

import org.lwjgl.opengl.GL11;

import minefantasy.entity.EntityDrake;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelDrake extends ModelBiped
{
  //fields
    ModelRenderer Lthigh;
    ModelRenderer LToe2;
    ModelRenderer Lleg;
    ModelRenderer LFoot;
    ModelRenderer LToe1;
    ModelRenderer Rthigh;
    ModelRenderer RToe2;
    ModelRenderer Rleg;
    ModelRenderer RFoot;
    ModelRenderer RToe1;
    ModelRenderer TailEnd;
    ModelRenderer Belly;
    ModelRenderer Body;
    ModelRenderer Mouth;
    ModelRenderer Tail;
    ModelRenderer TailTip;
    ModelRenderer Neck;
    ModelRenderer Head;
    ModelRenderer Jaw;
    ModelRenderer RThumb;
    ModelRenderer RArm;
    ModelRenderer RClaw;
    ModelRenderer LArm;
    ModelRenderer LThumb;
    ModelRenderer LClaw;
    ModelRenderer Back;
  
  public ModelDrake()
  {
    textureWidth = 128;
    textureHeight = 128;
    
    Lthigh = new ModelRenderer(this, 0, 0);
    LToe2 = new ModelRenderer(this, 0, 55);
    Lleg = new ModelRenderer(this, 0, 20);
    LFoot = new ModelRenderer(this, 0, 34);
    LToe1 = new ModelRenderer(this, 0, 55);
    Rthigh = new ModelRenderer(this, 0, 0);
    RToe2 = new ModelRenderer(this, 0, 55);
    Rleg = new ModelRenderer(this, 0, 20);
    RFoot = new ModelRenderer(this, 0, 34);
    RToe1 = new ModelRenderer(this, 0, 55);
    TailEnd = new ModelRenderer(this, 70, 45);
    Belly = new ModelRenderer(this, 108, 105);
    Body = new ModelRenderer(this, 84, 0);
    Mouth = new ModelRenderer(this, 0, 77);
    Tail = new ModelRenderer(this, 102, 41);
    TailTip = new ModelRenderer(this, 82, 45);
    Neck = new ModelRenderer(this, 52, 0);
    Head = new ModelRenderer(this, 44, 21);
    Jaw = new ModelRenderer(this, 0, 64);
    RThumb = new ModelRenderer(this, 0, 102);
    RArm = new ModelRenderer(this, 12, 90);
    RClaw = new ModelRenderer(this, 0, 110);
    LArm = new ModelRenderer(this, 12, 90);
    LThumb = new ModelRenderer(this, 0, 102);
    LClaw = new ModelRenderer(this, 0, 110);
    Back = new ModelRenderer(this, 108, 64);
    
      Lthigh.mirror = true;
      
      Lthigh.addBox(-1F, -2F, -4F, 5, 11, 9);
      Lthigh.setRotationPoint(4F, 2F, 0F);
      Lthigh.setTextureSize(128, 128);
      Lthigh.mirror = true;
      setRotation(Lthigh, 0F, 0F, 0F);
      Lthigh.mirror = false;
      LToe2.mirror = true;
      
      LToe2.addBox(2.5F, 19F, -8F, 3, 3, 6);
      LToe2.setRotationPoint(4F, 2F, 0F);
      LToe2.setTextureSize(128, 128);
      LToe2.mirror = true;
      setRotation(LToe2, 0F, 0F, 0F);
      LToe2.mirror = false;
      Lleg.mirror = true;
      
      Lleg.addBox(2F, 9F, 1F, 4, 9, 5);
      Lleg.setRotationPoint(3F, 2F, 0F);
      Lleg.setTextureSize(128, 128);
      Lleg.mirror = true;
      setRotation(Lleg, 0F, 0F, 0F);
      Lleg.mirror = false;
      LFoot.mirror = true;
      
      LFoot.addBox(-0.5F, 18F, -2F, 5, 4, 8);
      LFoot.setRotationPoint(4F, 2F, 0F);
      LFoot.setTextureSize(128, 128);
      LFoot.mirror = true;
      setRotation(LFoot, 0F, 0F, 0F);
      LFoot.mirror = false;
      LToe1.mirror = true;
      
      LToe1.addBox(-1.5F, 19F, -8F, 3, 3, 6);
      LToe1.setRotationPoint(4F, 2F, 0F);
      LToe1.setTextureSize(128, 128);
      LToe1.mirror = true;
      setRotation(LToe1, 0F, 0F, 0F);
      LToe1.mirror = false;
      
      Rthigh.addBox(-5F, -2F, -4F, 5, 11, 9);
      Rthigh.setRotationPoint(-4F, 2F, 0F);
      Rthigh.setTextureSize(128, 128);
      Rthigh.mirror = true;
      setRotation(Rthigh, 0F, 0F, 0F);
      
      RToe2.addBox(-1.5F, 19F, -8F, 3, 3, 6);
      RToe2.setRotationPoint(-4F, 2F, 0F);
      RToe2.setTextureSize(128, 128);
      RToe2.mirror = true;
      setRotation(RToe2, 0F, 0F, 0F);
      
      Rleg.addBox(-4F, 9F, 1F, 4, 9, 5);
      Rleg.setRotationPoint(-6F, 2F, 0F);
      Rleg.setTextureSize(128, 128);
      Rleg.mirror = true;
      setRotation(Rleg, 0F, 0F, 0F);
      
      RFoot.addBox(-4.5F, 18F, -2F, 5, 4, 8);
      RFoot.setRotationPoint(-4F, 2F, 0F);
      RFoot.setTextureSize(128, 128);
      RFoot.mirror = true;
      setRotation(RFoot, 0F, 0F, 0F);
      
      RToe1.addBox(-5.5F, 19F, -8F, 3, 3, 6);
      RToe1.setRotationPoint(-4F, 2F, 0F);
      RToe1.setTextureSize(128, 128);
      RToe1.mirror = true;
      setRotation(RToe1, 0F, 0F, 0F);
      
      TailEnd.addBox(-1F, 30F, -18F, 2, 15, 4);
      TailEnd.setRotationPoint(0F, 2F, 0F);
      TailEnd.setTextureSize(128, 128);
      TailEnd.mirror = true;
      setRotation(TailEnd, 1.570796F, 0F, 0F);
      
      Belly.addBox(-4F, -10F, -7F, 8, 21, 2);
      Belly.setRotationPoint(0F, 2F, 0F);
      Belly.setTextureSize(128, 128);
      Belly.mirror = true;
      setRotation(Belly, 0.8726646F, 0F, 0F);
      
      Body.addBox(-5F, -12F, -5F, 10, 25, 12);
      Body.setRotationPoint(0F, 2F, 0F);
      Body.setTextureSize(128, 128);
      Body.mirror = true;
      setRotation(Body, 0.8726646F, 0F, 0F);
      
      Mouth.addBox(-3F, -2F, -14F, 6, 4, 9);
      Mouth.setRotationPoint(0F, -10F, -16F);
      Mouth.setTextureSize(128, 128);
      Mouth.mirror = true;
      setRotation(Mouth, 0F, 0F, 0F);
      
      Tail.addBox(-2F, 8F, -8F, 4, 14, 9);
      Tail.setRotationPoint(0F, 2F, 0F);
      Tail.setTextureSize(128, 128);
      Tail.mirror = true;
      setRotation(Tail, 1.22173F, 0F, 0F);
      
      TailTip.addBox(-1.5F, 22F, -8F, 3, 12, 7);
      TailTip.setRotationPoint(0F, 2F, 0F);
      TailTip.setTextureSize(128, 128);
      TailTip.mirror = true;
      setRotation(TailTip, 1.22173F, 0F, 0F);
      
      Neck.addBox(-2F, -20F, 2F, 4, 11, 8);
      Neck.setRotationPoint(0F, 2F, 0F);
      Neck.setTextureSize(128, 128);
      Neck.mirror = true;
      setRotation(Neck, 1.22173F, 0F, 0F);
      
      Head.addBox(-3F, -6F, -6F, 6, 10, 9);
      Head.setRotationPoint(0F, -10F, -16F);
      Head.setTextureSize(128, 128);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      
      Jaw.addBox(-2F, 2F, -12F, 4, 2, 7);
      Jaw.setRotationPoint(0F, -10F, -16F);
      Jaw.setTextureSize(128, 128);
      Jaw.mirror = true;
      setRotation(Jaw, 0F, 0F, 0F);
      
      RThumb.addBox(0F, 6F, -4F, 2, 5, 2);
      RThumb.setRotationPoint(-3F, 5F, -9F);
      RThumb.setTextureSize(128, 128);
      RThumb.mirror = true;
      setRotation(RThumb, 0F, 0F, 0F);
      
      RArm.addBox(-2F, -2F, -2F, 3, 9, 3);
      RArm.setRotationPoint(-3F, 5F, -9F);
      RArm.setTextureSize(128, 128);
      RArm.mirror = true;
      setRotation(RArm, -0.418879F, 0F, 0F);
      
      RClaw.addBox(-3F, 6F, -4F, 2, 6, 2);
      RClaw.setRotationPoint(-3F, 5F, -9F);
      RClaw.setTextureSize(128, 128);
      RClaw.mirror = true;
      setRotation(RClaw, 0F, 0F, 0F);
      LArm.mirror = true;
      
      LArm.addBox(-1F, -2F, -2F, 3, 9, 3);
      LArm.setRotationPoint(3F, 5F, -9F);
      LArm.setTextureSize(128, 128);
      LArm.mirror = true;
      setRotation(LArm, -0.418879F, 0F, 0F);
      LArm.mirror = false;
      LThumb.mirror = true;
      
      LThumb.addBox(-2F, 6F, -4F, 2, 5, 2);
      LThumb.setRotationPoint(3F, 5F, -9F);
      LThumb.setTextureSize(128, 128);
      LThumb.mirror = true;
      setRotation(LThumb, 0F, 0F, 0F);
      LThumb.mirror = false;
      LClaw.mirror = true;
      
      LClaw.addBox(1F, 6F, -4F, 2, 6, 2);
      LClaw.setRotationPoint(3F, 5F, -9F);
      LClaw.setTextureSize(128, 128);
      LClaw.mirror = true;
      setRotation(LClaw, 0F, 0F, 0F);
      LClaw.mirror = false;
      
      
      Back.addBox(-4F, -12F, 7F, 8, 25, 2);
      Back.setRotationPoint(0F, 2F, 0F);
      Back.setTextureSize(128, 128);
      Back.mirror = true;
      setRotation(Back, 0.8726646F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    setRotationAngles(f, f1, f2, f3, f4, f5, (EntityDrake)entity);
    Lthigh.render(f5);
    LToe2.render(f5);
    Lleg.render(f5);
    LFoot.render(f5);
    LToe1.render(f5);
    Rthigh.render(f5);
    RToe2.render(f5);
    Rleg.render(f5);
    RFoot.render(f5);
    RToe1.render(f5);
    TailEnd.render(f5);
    Belly.render(f5);
    Body.render(f5);
    Mouth.render(f5);
    Tail.render(f5);
    TailTip.render(f5);
    Neck.render(f5);
    Head.render(f5);
    Jaw.render(f5);
    RThumb.render(f5);
    RArm.render(f5);
    RClaw.render(f5);
    LArm.render(f5);
    LThumb.render(f5);
    LClaw.render(f5);
    Back.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityDrake entity)
  {
	float headX = bipedHead.rotateAngleX;
	float mouth = (float)Math.toRadians(entity.getMouthAngle());
	float tailY = (float)Math.toRadians(entity.getTailYAngle());
	float tailY1 = (float)Math.toRadians(entity.getTailYAngle());
	float tailY2 = (float)Math.toRadians(entity.getTailYAngle());
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Rthigh.rotateAngleX = Rleg.rotateAngleX = RFoot.rotateAngleX = RToe1.rotateAngleX = RToe2.rotateAngleX = bipedRightLeg.rotateAngleX;
    Lthigh.rotateAngleX = Lleg.rotateAngleX = LFoot.rotateAngleX = LToe1.rotateAngleX = LToe2.rotateAngleX = bipedLeftLeg.rotateAngleX;
    
    Head.rotateAngleX = headX;
    Mouth.rotateAngleX = headX - mouth;
    Jaw.rotateAngleX = headX + mouth;
    
    Head.rotateAngleY = Mouth.rotateAngleY = Jaw.rotateAngleY = bipedHead.rotateAngleY;
    
    if(tailY < -6)tailY = -6;if(tailY > 6)tailY = 6;
    if(tailY1 < -12)tailY1 = -12;if(tailY1 > 12)tailY1 = 12;
	    Tail.rotateAngleY = tailY;
	    TailEnd.rotateAngleY = tailY1;
	    TailTip.rotateAngleY = tailY2;
	    
	if(entity.scratchTime > 0)
	{
		float armS = 30-(float)Math.toRadians(entity.getScratchForDisplay());
		Head.rotateAngleY = Mouth.rotateAngleY = Jaw.rotateAngleY = (float)Math.toRadians(0);
		Head.rotateAngleX = Mouth.rotateAngleX = Jaw.rotateAngleX = (float)Math.toRadians(90);
		RArm.rotateAngleX = RClaw.rotateAngleX = RThumb.rotateAngleX = armS;
	}
	else
	{
		RArm.rotateAngleX = -0.418879F;
		RClaw.rotateAngleX = RThumb.rotateAngleX = 0;
	}
    
  }

}
