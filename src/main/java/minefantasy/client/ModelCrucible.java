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
public class ModelCrucible extends ModelBase {
  //fields
	ModelRenderer sides3;
    ModelRenderer sides;
    ModelRenderer sides2;
    ModelRenderer sides1;
    ModelRenderer contents;
    ModelRenderer base;
    ModelRenderer armR;
    ModelRenderer armL;
    ModelRenderer armLBase;
    ModelRenderer armRBase;
  
  public ModelCrucible()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      sides3 = new ModelRenderer(this, 0, 0);
      sides3.addBox(-6F, 0F, -7F, 12, 10, 1);
      sides3.setRotationPoint(0F, 0F, 0F);
      sides3.setTextureSize(64, 64);
      sides3.mirror = true;
      setRotation(sides3, 0F, 1.570796F, 0F);
      sides = new ModelRenderer(this, 0, 0);
      sides.addBox(-6F, 0F, 6F, 12, 10, 1);
      sides.setRotationPoint(0F, 0F, 0F);
      sides.setTextureSize(64, 64);
      sides.mirror = true;
      setRotation(sides, 0F, 0F, 0F);
      sides2 = new ModelRenderer(this, 0, 0);
      sides2.addBox(-6F, 0F, -7F, 12, 10, 1);
      sides2.setRotationPoint(0F, 0F, 0F);
      sides2.setTextureSize(64, 64);
      sides2.mirror = true;
      setRotation(sides2, 0F, 0F, 0F);
      sides1 = new ModelRenderer(this, 0, 0);
      sides1.addBox(-6F, 0F, 6F, 12, 10, 1);
      sides1.setRotationPoint(0F, 0F, 0F);
      sides1.setTextureSize(64, 64);
      sides1.mirror = true;
      setRotation(sides1, 0F, 1.570796F, 0F);
      contents = new ModelRenderer(this, -12, 18);
      contents.addBox(-6F, 2F, -6F, 12, 0, 12);
      contents.setRotationPoint(0F, 0F, 0F);
      contents.setTextureSize(64, 64);
      contents.mirror = true;
      setRotation(contents, 0F, 0F, 0F);
      base = new ModelRenderer(this, 14, 0);
      base.addBox(-6F, 10F, -6F, 12, 6, 12);
      base.setRotationPoint(0F, 0F, 0F);
      base.setTextureSize(64, 64);
      base.mirror = true;
      setRotation(base, 0F, 0F, 0F);
      
      armR = new ModelRenderer(this, 0, 30);
      armR.addBox(7F, 1F, 0F, 2, 19, 2);
      armR.setRotationPoint(0F, 0F, 0F);
      armR.setTextureSize(64, 64);
      armR.mirror = true;
      setRotation(armR, 0F, 0F, 0F);
      
      armL = new ModelRenderer(this, 0, 30);
      armL.addBox(-9F, 1F, 0F, 2, 19, 2);
      armL.setRotationPoint(0F, 0F, 0F);
      armL.setTextureSize(64, 64);
      armL.mirror = true;
      setRotation(armL, 0F, 0F, 0F);
      
      armLBase = new ModelRenderer(this, 8, 30);
      armLBase.addBox(-9F, 20F, 0F, 2, 12, 2);
      armLBase.setRotationPoint(0F, 0F, 0F);
      armLBase.setTextureSize(64, 64);
      armLBase.mirror = true;
      setRotation(armLBase, 0F, 0F, 0F);
      
      armRBase = new ModelRenderer(this, 8, 30);
      armRBase.addBox(7F, 20F, 0F, 2, 12, 2);
      armRBase.setRotationPoint(0F, 0F, 0F);
      armRBase.setTextureSize(64, 64);
      armRBase.mirror = true;
      setRotation(armRBase, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    sides.render(f5);
    sides1.render(f5);
    sides2.render(f5);
    sides3.render(f5);
    contents.render(f5);
    base.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  public void renderModel(float scale, boolean lit, boolean stand)
  {
	  sides.render(scale);
	  sides1.render(scale);
	  sides2.render(scale);
	  sides3.render(scale);
	  base.render(scale);
	  
	  if(lit)
		  contents.render(scale);
	  
	  if(stand)
	    {
    		armL.render(scale);
    		armR.render(scale);
    		armLBase.render(scale);
    		armRBase.render(scale);
	    }
  }
}