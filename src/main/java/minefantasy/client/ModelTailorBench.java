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
public class ModelTailorBench extends ModelBase {
	//fields
	ModelRenderer Right;
    ModelRenderer Left;
    ModelRenderer bottomShelf;
    ModelRenderer Top;
  
  public ModelTailorBench()
  {
    textureWidth = 128;
    textureHeight = 64;
    
    Right = new ModelRenderer(this, 48, 0);
    Right.addBox(6F, 5F, -8F, 2, 15, 16);
    Right.setRotationPoint(0F, 0F, 0F);
    Right.setTextureSize(128, 64);
    
    Left = new ModelRenderer(this, 48, 0);
    Left.addBox(-8F, 5F, -8F, 2, 15, 16);
    Left.setRotationPoint(0F, 0F, 0F);
    Left.setTextureSize(128, 64);
    
    Top = new ModelRenderer(this, 0, 27);
    Top.addBox(-8F, 4F, -8F, 16, 1, 16);
    Top.setRotationPoint(0F, 0F, 0F);
    Top.setTextureSize(128, 64);
    
    bottomShelf = new ModelRenderer(this, 45, 48);
    bottomShelf.addBox(-6F, 13F, -7F, 12, 1, 15);
    bottomShelf.setRotationPoint(0F, 0F, 0F);
    bottomShelf.setTextureSize(128, 64);
  }

  public void renderModel(float f) 
  {
	  	Right.render(f);
	    Left.render(f);
	    Top.render(f);
	    bottomShelf.render(f);
  }
}