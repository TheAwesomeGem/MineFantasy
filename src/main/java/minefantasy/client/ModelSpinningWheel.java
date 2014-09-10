package minefantasy.client;

import minefantasy.block.tileentity.TileEntityAnvil;
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
public class ModelSpinningWheel extends ModelBase {
	//fields
	ModelRenderer postR;
    ModelRenderer Base;
    ModelRenderer twine;
    ModelRenderer post;
    ModelRenderer leg3;
    ModelRenderer postL;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer back;
  
  public ModelSpinningWheel()
  {
      textureWidth = 64;
      textureHeight = 32;
    
      postR = new ModelRenderer(this, 0, 0);
      postR.addBox(1F, -3F, -1F, 2, 6, 2);
      postR.setRotationPoint(0F, 8F, 0F);
     
      back = new ModelRenderer(this, 0, 15);
      back.addBox(-4F, 2F, 2F, 8, 4, 5);
      back.setRotationPoint(0F, 8F, 0F);
     
      twine = new ModelRenderer(this, 0, 13);
      twine.addBox(-1.5F, -2F, -7F, 3, 1, 1);
      twine.setRotationPoint(0F, 8F, 0F);
     
      post = new ModelRenderer(this, 0, 0);
      post.addBox(-0.5F, -1F, -7F, 1, 4, 1);
      post.setRotationPoint(0F, 8F, 0F);
     
      leg3 = new ModelRenderer(this, 0, 0);
      leg3.addBox(-1F, 6F, -7F, 2, 6, 2);
      leg3.setRotationPoint(0F, 8F, 0F);
     
      postL = new ModelRenderer(this, 0, 0);
      postL.addBox(-3F, -3F, -1F, 2, 6, 2);
      postL.setRotationPoint(0F, 8F, 0F);
     
      leg1 = new ModelRenderer(this, 0, 0);
      leg1.addBox(-4F, 6F, 0F, 2, 6, 2);
      leg1.setRotationPoint(0F, 8F, 3F);
     
      leg2 = new ModelRenderer(this, 0, 0);
      leg2.addBox(2F, 6F, 0F, 2, 6, 2);
      leg2.setRotationPoint(0F, 8F, 3F);
     
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(-4F, 3F, -8F, 8, 3, 10);
      Base.setRotationPoint(0F, 8F, 0F);
  }

  public void renderModel(float f) 
  {
	postR.render(f);
	Base.render(f);
	twine.render(f);
	post.render(f);
	leg3.render(f);
	postL.render(f);
	leg1.render(f);
	leg2.render(f);
	back.render(f);
  }
}