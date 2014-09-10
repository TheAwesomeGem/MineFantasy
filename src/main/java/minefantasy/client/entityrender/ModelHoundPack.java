package minefantasy.client.entityrender;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class ModelHoundPack extends ModelBase
{
  //fields
    ModelRenderer smlPack;
    ModelRenderer PackBand;
    ModelRenderer PaxkBase;
    ModelRenderer pack2;
    ModelRenderer bigPack;
    ModelRenderer pack4;
    ModelRenderer pack1;
    ModelRenderer feedbag;
    ModelRenderer pack3;
  
  public ModelHoundPack()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      smlPack = new ModelRenderer(this, 28, 23);
      PackBand = new ModelRenderer(this, 0, 9);
      PaxkBase = new ModelRenderer(this, 0, 0);
      pack2 = new ModelRenderer(this, 46, 0);
      bigPack = new ModelRenderer(this, 0, 21);
      pack1 = new ModelRenderer(this, 28, 0);
      pack4 = new ModelRenderer(this, 46, 11);
      feedbag = new ModelRenderer(this, 50, 20);
      pack3 = new ModelRenderer(this, 32, 11);
      
      smlPack.addBox(-4.5F, -8F, 3.5F, 7, 4, 5);
      smlPack.setRotationPoint(0F, 14F, -3F);
      smlPack.setTextureSize(64, 32);
      smlPack.mirror = true;
      setRotation(smlPack, 0F, 0F, 0F);
      
      PackBand.addBox(-5.5F, -4F, -2.5F, 9, 9, 2);
      PackBand.setRotationPoint(0F, 14F, -3F);
      PackBand.setTextureSize(64, 32);
      PackBand.mirror = true;
      setRotation(PackBand, 0F, 0F, 0F);
      
      PaxkBase.addBox(-4F, -4F, -0.5F, 6, 2, 7);
      PaxkBase.setRotationPoint(0F, 14F, -3F);
      PaxkBase.setTextureSize(64, 32);
      PaxkBase.mirror = true;
      setRotation(PaxkBase, 0F, 0F, 0F);
      
      pack2.addBox(-8F, -8F, -1.5F, 4, 6, 5);
      pack2.setRotationPoint(0F, 14F, -3F);
      pack2.setTextureSize(64, 32);
      pack2.mirror = true;
      setRotation(pack2, 0F, 0F, 0F);
      
      bigPack.addBox(-5F, -9F, -2.5F, 8, 5, 6);
      bigPack.setRotationPoint(0F, 14F, -3F);
      bigPack.setTextureSize(64, 32);
      bigPack.mirror = true;
      setRotation(bigPack, 0F, 0F, 0F);
      
      pack4.addBox(-7F, -7F, -1.5F, 3, 5, 4);
      pack4.setRotationPoint(0F, 14F, 3F);
      pack4.setTextureSize(64, 32);
      pack4.mirror = true;
      setRotation(pack4, 0F, 0F, 0F);
      
      pack1.addBox(2F, -8F, -1.5F, 4, 6, 5);
      pack1.setRotationPoint(0F, 14F, -3F);
      pack1.setTextureSize(64, 32);
      pack1.mirror = true;
      setRotation(pack1, 0F, 0F, 0F);
      
      feedbag.addBox(-3F, 4F, -10.5F, 4, 4, 3);
      feedbag.setRotationPoint(0F, 14F, 3F);
      feedbag.setTextureSize(64, 32);
      feedbag.mirror = true;
      setRotation(feedbag, 0F, 0F, 0F);
      
      pack3.addBox(2F, -7F, -1.5F, 3, 5, 4);
      pack3.setRotationPoint(0F, 14F, 3F);
      pack3.setTextureSize(64, 32);
      pack3.mirror = true;
      setRotation(pack3, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    smlPack.render(f5);
    PackBand.render(f5);
    PaxkBase.render(f5);
    pack2.render(f5);
    bigPack.render(f5);
    pack4.render(f5);
    pack1.render(f5);
    feedbag.render(f5);
    pack3.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
	  super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
}
