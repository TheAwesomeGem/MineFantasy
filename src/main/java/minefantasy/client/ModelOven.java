package minefantasy.client;

import minefantasy.block.tileentity.TileEntityTanningRack;
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
public class ModelOven extends ModelBase {
  //fields
	ModelRenderer sideRight;
    ModelRenderer Top;
    ModelRenderer leg2;
    ModelRenderer Front;
    ModelRenderer Base;
    ModelRenderer Back;
    ModelRenderer sideLeft;
    ModelRenderer leg4;
    ModelRenderer leg1;
    ModelRenderer leg3;
    ModelRenderer Heat;
  
  public ModelOven()
  {
    textureWidth = 128;
    textureHeight = 64;
	    
      Heat = new ModelRenderer(this, 40, 37);
      Heat.addBox(-4F, 1.5F, -4F, 8, 1, 8);
      Heat.setRotationPoint(0F, -3F, 0F);
	  Heat.setTextureSize(128, 64);
	  Heat.mirror = true;
    
      sideRight = new ModelRenderer(this, 0, 0);
      sideRight.addBox(5F, -11F, -7F, 2, 11, 14);
      sideRight.setRotationPoint(0F, 0F, 0F);
      sideRight.mirror = true;
      Top = new ModelRenderer(this, 0, 25);
      Top.addBox(-5F, -13F, -5F, 10, 2, 10);
      Top.setRotationPoint(0F, 0F, 0F);
      Top.mirror = true;
      leg2 = new ModelRenderer(this, 32, 13);
      leg2.addBox(3F, 0F, -6F, 3, 5, 3);
      leg2.setRotationPoint(0F, 0F, 0F);
      leg2.mirror = true;
      Front = new ModelRenderer(this, 32, 0);
      Front.addBox(-5F, -11F, -7F, 10, 3, 2);
      Front.setRotationPoint(0F, 0F, 0F);
      Front.mirror = true;
      Base = new ModelRenderer(this, 0, 25);
      Base.addBox(-5F, 0F, -5F, 10, 2, 10);
      Base.setRotationPoint(0F, 0F, 0F);
      Base.mirror = true;
      Back = new ModelRenderer(this, 32, 0);
      Back.addBox(-5F, -11F, 5F, 10, 11, 2);
      Back.setRotationPoint(0F, 0F, 0F);
      Back.mirror = true;
      sideLeft = new ModelRenderer(this, 0, 0);
      sideLeft.addBox(-7F, -11F, -7F, 2, 11, 14);
      sideLeft.setRotationPoint(0F, 0F, 0F);
      sideLeft.mirror = true;
      leg4 = new ModelRenderer(this, 32, 13);
      leg4.addBox(3F, 0F, 3F, 3, 5, 3);
      leg4.setRotationPoint(0F, 0F, 0F);
      leg4.mirror = true;
      leg1 = new ModelRenderer(this, 32, 13);
      leg1.addBox(-6F, 0F, -6F, 3, 5, 3);
      leg1.setRotationPoint(0F, 0F, 0F);
      leg1.mirror = true;
      leg3 = new ModelRenderer(this, 32, 13);
      leg3.addBox(-6F, 0F, 3F, 3, 5, 3);
      leg3.setRotationPoint(0F, 0F, 0F);
      leg3.mirror = true;
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    renderModel(false, f5);
  }
  
  /*
   * ModelRenderer Wall4t;
    ModelRenderer Wall1;
    ModelRenderer Wall2;
    ModelRenderer Top;
    ModelRenderer Wall3;
    ModelRenderer Wall4;
    ModelRenderer lava;
    ModelRenderer Base;
    ModelRenderer contents;
   */
    public void renderModel(boolean lit, float f) 
    {
    	sideRight.render(f);
        Top.render(f);
        leg2.render(f);
        Front.render(f);
        Base.render(f);
        Back.render(f);
        sideLeft.render(f);
        leg4.render(f);
        leg1.render(f);
        leg3.render(f);
        
        if(lit)
        {
        	Heat.render(f);
        }
    }
}