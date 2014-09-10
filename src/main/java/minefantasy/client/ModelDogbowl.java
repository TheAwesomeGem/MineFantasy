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
public class ModelDogbowl extends ModelBase {
	//fields
	ModelRenderer Wall;
	ModelRenderer Wall1;
	ModelRenderer Wall2;
	ModelRenderer Wall3;
	ModelRenderer Food;
	ModelRenderer Base;
  
  public ModelDogbowl()
  {
	  textureWidth = 64;
	    textureHeight = 32;
	    
	      Wall = new ModelRenderer(this, 18, 0);
	      Wall.addBox(3F, -4F, -4F, 1, 4, 8);
	      
	      Wall1 = new ModelRenderer(this, 0, 0);
	      Wall1.addBox(-3F, -4F, 3F, 6, 4, 1);
	      
	      Wall2 = new ModelRenderer(this, 18, 0);
	      Wall2.addBox(-4F, -4F, -4F, 1, 4, 8);
	      
	      Wall3 = new ModelRenderer(this, 0, 0);
	      Wall3.addBox(-3F, -4F, -4F, 6, 4, 1);
	      
	      Food = new ModelRenderer(this, 0, 22);
	      Food.addBox(-3F, 0.5F, -3F, 6, 0, 6);
	      Food.setRotationPoint(0F, 0F, 0F);
	      Food.setTextureSize(64, 32);
	      Food.mirror = true;
	      
	      Base = new ModelRenderer(this, 0, 12);
	      Base.addBox(-4.5F, 0F, -4.5F, 9, 1, 9);
	      Base.setRotationPoint(0F, 0F, 0F);
	      Base.setTextureSize(64, 32);
	      Base.mirror = true;
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
  }
  

public void renderModel(int food, float foodMax, float f) {
    Base.render(f);
    Wall.render(f);
    Wall1.render(f);
    Wall2.render(f);
    Wall3.render(f);
    
    float start = 0.6F;
    
    float fill = (3.5F - start) / (float)foodMax * (float)food;
    
    Food.rotationPointY = -start - (float)Math.min(fill, (4.0F - start));
    
    if(food > 0)
    {
    	Food.render(f);
    }
}
}