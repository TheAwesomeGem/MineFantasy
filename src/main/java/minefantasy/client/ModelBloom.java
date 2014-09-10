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
public class ModelBloom extends ModelBase {
  //fields
	ModelRenderer chimney;
    ModelRenderer Base;
    ModelRenderer Furnace;
    ModelRenderer top;
    ModelRenderer ingot;
    ModelRenderer mould;
  
  public ModelBloom()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      chimney = new ModelRenderer(this, 84, 43);
      chimney.addBox(-5.5F, 0F, -5.5F, 11, 2, 11);
      chimney.setRotationPoint(0F, 0F, 0F);
      chimney.setTextureSize(128, 64);
      chimney.mirror = true;
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(-9F, 14F, -9F, 18, 2, 18);
      Base.setRotationPoint(0F, 0F, 0F);
      Base.setTextureSize(128, 64);
      Base.mirror = true;
      Furnace = new ModelRenderer(this, 64, 11);
      Furnace.addBox(-8F, 5F, -8F, 16, 9, 16);
      Furnace.setRotationPoint(0F, 0F, 0F);
      Furnace.setTextureSize(128, 64);
      Furnace.mirror = true;
      top = new ModelRenderer(this, 0, 46);
      top.addBox(-7F, 2F, -7F, 14, 3, 14);
      top.setRotationPoint(0F, 0F, 0F);
      top.setTextureSize(128, 64);
      top.mirror = true;
      
      ingot = new ModelRenderer(this, 0, 30);
      ingot.addBox(8.5F, 11.5F, -2.5F, 2, 2, 5);
      ingot.setRotationPoint(0F, 0F, 0F);
      ingot.setTextureSize(128, 64);
      ingot.mirror = true;
      
      mould = new ModelRenderer(this, 0, 20);
      mould.addBox(8F, 12F, -3F, 3, 3, 6);
      mould.setRotationPoint(0F, 0F, 0F);
      mould.setTextureSize(128, 64);
      mould.mirror = true;
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    chimney.render(f5);
    Base.render(f5);
    Furnace.render(f5);
    top.render(f5);
  }
  public void renderModel(float f5, boolean result)
  {
    chimney.render(f5);
    Base.render(f5);
    Furnace.render(f5);
    top.render(f5);
    mould.render(f5);
    if(result)
    	ingot.render(f5);
  }
}