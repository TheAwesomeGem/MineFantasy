package minefantasy.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ModelTripHammerBack extends ModelBase
{
  //fields
	ModelRenderer Legs;
    ModelRenderer Head;
    ModelRenderer TableTip;
    ModelRenderer Table;
    ModelRenderer Wheel1B;
    ModelRenderer Axle;
    ModelRenderer Joint;
    ModelRenderer Wheel1;
  
  public ModelTripHammerBack()
  {
    textureWidth = 64;
    textureHeight = 32;
    
    Legs = new ModelRenderer(this, 22, 11);
    Legs.addBox(-5F, 1F, -4F, 2, 8, 8);
    Legs.setRotationPoint(8F, 15F, 0F);
    Legs.setTextureSize(64, 32);
    setRotation(Legs, 0F, 0F, 0F);
    Head = new ModelRenderer(this, 34, 27);
    Head.addBox(-15F, -1.25F, -1F, 3, 3, 2);
    Head.setRotationPoint(6F, 12F, 0F);
    Head.setTextureSize(64, 32);
    setRotation(Head, 0F, 0F, 0F);
    TableTip = new ModelRenderer(this, 0, 11);
    TableTip.addBox(-13F, 1F, -3F, 5, 8, 6);
    TableTip.setRotationPoint(8F, 15F, 0F);
    TableTip.setTextureSize(64, 32);
    setRotation(TableTip, 0F, 0F, 0F);
    Table = new ModelRenderer(this, 20, 0);
    Table.addBox(-10F, -1F, -4F, 14, 3, 8);
    Table.setRotationPoint(2F, 15F, 0F);
    Table.setTextureSize(64, 32);
    setRotation(Table, 0F, 0F, 0F);
    Wheel1B = new ModelRenderer(this, 42, 11);
    Wheel1B.addBox(-5F, -5F, 4F, 10, 10, 1);
    Wheel1B.setRotationPoint(-1F, 11F, 0F);
    Wheel1B.setTextureSize(64, 32);
    setRotation(Wheel1B, 0F, 0F, 0F);
    Axle = new ModelRenderer(this, 0, 27);
    Axle.addBox(-6F, -0.5F, -0.5F, 12, 1, 1);
    Axle.setRotationPoint(-1F, 11F, 0F);
    Axle.setTextureSize(64, 32);
    setRotation(Axle, 0F, 1.570796F, 0F);
    Joint = new ModelRenderer(this, 0, 0);
    Joint.addBox(-8F, -6F, -2F, 6, 6, 4);
    Joint.setRotationPoint(2F, 15F, 0F);
    Joint.setTextureSize(64, 32);
    setRotation(Joint, 0F, 0F, 0F);
    Wheel1 = new ModelRenderer(this, 42, 11);
    Wheel1.addBox(-5F, -5F, -5F, 10, 10, 1);
    Wheel1.setRotationPoint(-1F, 11F, 0F);
    Wheel1.setTextureSize(64, 32);
    setRotation(Wheel1, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    Legs.render(f5);
    Head.render(f5);
    TableTip.render(f5);
    Table.render(f5);
    Wheel1B.render(f5);
    Axle.render(f5);
    Joint.render(f5);
    Wheel1.render(f5);
  }
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

public void renderModel(float p, float f) {
	Legs.render(f);
    Head.render(f);
    TableTip.render(f);
    Table.render(f);
    //Wheel1B.render(f);
    Axle.render(f);
    Joint.render(f);
    //Wheel1.render(f);
}
}