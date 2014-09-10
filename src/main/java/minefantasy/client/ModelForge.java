package minefantasy.client;

import minefantasy.block.tileentity.TileEntityForge;
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
public class ModelForge extends ModelBase {
  //fields
	ModelRenderer Back;
    ModelRenderer lava;
    ModelRenderer coal;
    ModelRenderer Right;
    ModelRenderer cornerBR;
    ModelRenderer Left;
    ModelRenderer Base;
    ModelRenderer front;
    ModelRenderer cornerBL;
    ModelRenderer cornerFR;
    ModelRenderer cornerFL;
  
  public ModelForge()
  {
	  textureWidth = 64;
	    textureHeight = 64;
	    
	    Back = new ModelRenderer(this, 0, 0);
	      Back.addBox(-6F, -6F, -8F, 12, 7, 2);
	      Back.setRotationPoint(0F, 22F, 0F);
	      Back.mirror = true;
	      setRotation(Back, 0F, 1.570796F, 0F);
	      lava = new ModelRenderer(this, 32, 0);
	      
	      coal = new ModelRenderer(this, 0, 44);
	      coal.addBox(-8F, -0.1F, -8F, 16, 0, 16);
	      coal.addBox(-8F, -0.5F, -8F, 16, 0, 16);
	      coal.addBox(-8F, -1.0F, -8F, 16, 0, 16);
	      
	      lava.addBox(-8F, -1.5F, -8F, 16, 0, 16);
	      lava.setRotationPoint(0F, 22F, 0F);
	      coal.setRotationPoint(0F, 22F, 0F);
	      lava.mirror = true;
	      setRotation(lava, 0F, 0F, 0F);
	      Right = new ModelRenderer(this, 0, 0);
	      Right.addBox(-6F, -6F, 6F, 12, 7, 2);
	      Right.setRotationPoint(0F, 22F, 0F);
	      Right.mirror = true;
	      setRotation(Right, 0F, 0F, 0F);
	      cornerBR = new ModelRenderer(this, 0, 0);
	      cornerBR.addBox(-8F, -6F, -8F, 2, 7, 2);
	      cornerBR.setRotationPoint(0F, 22F, 0F);
	      cornerBR.mirror = true;
	      setRotation(cornerBR, 0F, 1.570796F, 0F);
	      Left = new ModelRenderer(this, 0, 0);
	      Left.addBox(-6F, -6F, -8F, 12, 7, 2);
	      Left.setRotationPoint(0F, 22F, 0F);
	      Left.mirror = true;
	      setRotation(Left, 0F, 0F, 0F);
	      Base = new ModelRenderer(this, 0, 15);
	      Base.addBox(-8F, 1F, -8F, 16, 1, 16);
	      Base.setRotationPoint(0F, 22F, 0F);
	      Base.mirror = true;
	      setRotation(Base, 0F, 0F, 0F);
	      front = new ModelRenderer(this, 0, 0);
	      front.addBox(-6F, -6F, 6F, 12, 7, 2);
	      front.setRotationPoint(0F, 22F, 0F);
	      front.mirror = true;
	      setRotation(front, 0F, 1.570796F, 0F);
	      cornerBL = new ModelRenderer(this, 0, 0);
	      cornerBL.addBox(6F, -6F, -8F, 2, 7, 2);
	      cornerBL.setRotationPoint(0F, 22F, 0F);
	      cornerBL.mirror = true;
	      setRotation(cornerBL, 0F, 1.570796F, 0F);
	      cornerFR = new ModelRenderer(this, 0, 0);
	      cornerFR.addBox(-8F, -6F, 6F, 2, 7, 2);
	      cornerFR.setRotationPoint(0F, 22F, 0F);
	      cornerFR.mirror = true;
	      setRotation(cornerFR, 0F, 1.570796F, 0F);
	      cornerFL = new ModelRenderer(this, 0, 0);
	      cornerFL.addBox(6F, -6F, 6F, 2, 7, 2);
	      cornerFL.setRotationPoint(0F, 22F, 0F);
	      cornerFL.mirror = true;
	      setRotation(cornerFL, 0F, 1.570796F, 0F);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    Back.render(f5);
	    lava.render(f5);
	    Right.render(f5);
	    cornerBR.render(f5);
	    Left.render(f5);
	    Base.render(f5);
	    front.render(f5);
	    cornerBL.render(f5);
	    cornerFR.render(f5);
	    cornerFL.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }

    public void renderModel(TileEntityForge forge, float scale) {
    	boolean[] sides = forge.showSides();
    	
    	if(sides[0])
    	    front.render(scale);
    	
    	if(sides[1])
    	    Left.render(scale);
    	
    	if(sides[2])
    	    Right.render(scale);
    	
    	if(sides[3])
    		Back.render(scale);
    	
    	if(sides[3] || sides[2])
    		cornerBR.render(scale);
	    
	    if(sides[3] || sides[1])
	    	cornerBL.render(scale);
	    
	    if(sides[0] || sides[2])
	    	cornerFR.render(scale);
	    
	    if(sides[0] || sides[1])
	    	cornerFL.render(scale);
	    
	    
	    Base.render(scale);
	    if(forge.fuel > 0)
	    {
	    	coal.render(scale);
	    }
	    if(forge.isBurning())
	    {
	    	lava.render(scale);
	    }
    }
}