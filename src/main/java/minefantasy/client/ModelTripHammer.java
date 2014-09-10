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
public class ModelTripHammer extends ModelBase
{
  //fields
	ModelRenderer Legs;
    ModelRenderer Head;
    ModelRenderer TableTip;
    ModelRenderer Table;
    ModelRenderer Wheel;
    ModelRenderer Arm;
  
  public ModelTripHammer()
  {
    textureWidth = 64;
    textureHeight = 32;
    
    Legs = new ModelRenderer(this, 22, 11);
    Legs.addBox(-6F, 1F, -4F, 2, 8, 8);
    Legs.setRotationPoint(8F, 15F, 0F);
    Legs.setTextureSize(64, 32);
    setRotation(Legs, 0F, 0F, 0F);
    Head = new ModelRenderer(this, 34, 27);
    Head.addBox(-15.5F, -1.25F, -1F, 3, 3, 2);
    Head.setRotationPoint(6F, 12F, 0F);
    Head.setTextureSize(64, 32);
    setRotation(Head, 0F, 0F, 0F);
    TableTip = new ModelRenderer(this, 0, 11);
    TableTip.addBox(-18F, -1F, -3F, 5, 10, 6);
    TableTip.setRotationPoint(8F, 15F, 0F);
    TableTip.setTextureSize(64, 32);
    setRotation(TableTip, 0F, 0F, 0F);
    Table = new ModelRenderer(this, 20, 0);
    Table.addBox(-7F, -1F, -4F, 14, 3, 8);
    Table.setRotationPoint(2F, 15F, 0F);
    Table.setTextureSize(64, 32);
    setRotation(Table, 0F, 0F, 0F);
    Wheel = new ModelRenderer(this, 0, 0);
    Wheel.addBox(2F, -6F, -2F, 6, 6, 4);
    Wheel.setRotationPoint(2F, 15F, 0F);
    Wheel.setTextureSize(64, 32);
    setRotation(Wheel, 0F, 0F, 0F);
    Arm = new ModelRenderer(this, 0, 27);
    Arm.addBox(-15F, -1F, -0.5F, 16, 2, 1);
    Arm.setRotationPoint(6F, 12F, 0F);
    Arm.setTextureSize(64, 32);
    setRotation(Arm, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    Legs.render(f5);
    Head.render(f5);
    TableTip.render(f5);
    Table.render(f5);
    Wheel.render(f5);
    Arm.render(f5);
  }
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

public void renderModel(float p, float f) {
	Wheel.render(f);
	Legs.render(f);
    Head.render(f);
    TableTip.render(f);
    Table.render(f);
    Wheel.render(f);
    Arm.render(f);
}
public void rotate(float f)
{
	int s = (int)(45F * f);
	float rot = (float)Math.toRadians(s);
	Arm.rotateAngleZ = Head.rotateAngleZ = rot;
}
}