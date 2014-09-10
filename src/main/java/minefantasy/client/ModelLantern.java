package minefantasy.client;

import minefantasy.block.tileentity.TileEntityLantern;
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
public class ModelLantern extends ModelBase {
    //fields

    ModelRenderer Lamp;
    ModelRenderer BarZP;
    ModelRenderer BarXP;
    ModelRenderer BarXM;
    ModelRenderer BarZM;

    public ModelLantern() {
        textureWidth = 64;
        textureHeight = 32;






        Lamp = new ModelRenderer(this, 32, 0);
        Lamp.setTextureOffset(32, 0).addBox(-1F, 6F, -1F, 2, 6, 2);
        Lamp.setTextureOffset(32, 0).addBox(-4F, 0F, -4F, 8, 4, 8);
        Lamp.setTextureOffset(32, 0).addBox(-4F, 12F, -4F, 8, 4, 8);
        Lamp.setTextureOffset(32, 16).addBox(-4F, 4F, -4F, 8, 8, 8);
        Lamp.setRotationPoint(0F, 0F, 0F);
        Lamp.setTextureSize(64, 32);
        Lamp.mirror = true;
        setRotation(Lamp, 0F, 0F, 0F);




        BarXP = new ModelRenderer(this, 0, 0);
        BarZP = new ModelRenderer(this, 0, 0);
        BarZM = new ModelRenderer(this, 0, 0);
        BarXM = new ModelRenderer(this, 0, 0);
        BarXM.addBox(-8F, 10F, -2F, 4, 4, 4);
        BarXM.setRotationPoint(0F, 0F, 0F);
        BarXM.setTextureSize(64, 32);
        BarXM.mirror = true;
        setRotation(BarXM, 0F, 0F, 0F);
        BarZP.addBox(-2F, 10F, 4F, 4, 4, 4);
        BarZP.setRotationPoint(0F, 0F, 0F);
        BarZP.setTextureSize(64, 32);
        BarZP.mirror = true;
        setRotation(BarZP, 0F, 0F, 0F);
        BarXP.addBox(4F, 10F, -2F, 4, 4, 4);
        BarXP.setRotationPoint(0F, 0F, 0F);
        BarXP.setTextureSize(64, 32);
        BarXP.mirror = true;
        setRotation(BarXP, 0F, 0F, 0F);

        BarXP.addBox(4F, 2F, -2F, 4, 4, 4);
        BarXP.setRotationPoint(0F, 0F, 0F);
        BarXP.setTextureSize(64, 32);
        BarXP.mirror = true;
        setRotation(BarXP, 0F, 0F, 0F);
        BarXM.addBox(-8F, 2F, -2F, 4, 4, 4);
        BarXM.setRotationPoint(0F, 0F, 0F);
        BarXM.setTextureSize(64, 32);
        BarXM.mirror = true;
        setRotation(BarXM, 0F, 0F, 0F);

        BarZP.addBox(-2F, 2F, 4F, 4, 4, 4);
        BarZP.setRotationPoint(0F, 0F, 0F);
        BarZP.setTextureSize(64, 32);
        BarZP.mirror = true;
        setRotation(BarZP, 0F, 0F, 0F);
        BarZM.addBox(-2F, 10F, -8F, 4, 4, 4);
        BarZM.setRotationPoint(0F, 0F, 0F);
        BarZM.setTextureSize(64, 32);
        BarZM.mirror = true;
        setRotation(BarZM, 0F, 0F, 0F);

        BarZM.addBox(-2F, 2F, -8F, 4, 4, 4);
        BarZM.setRotationPoint(0F, 0F, 0F);
        BarZM.setTextureSize(64, 32);
        BarZM.mirror = true;
        setRotation(BarZM, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Lamp.render(f5);
    }

    public void renderModel(TileEntityLantern lantern, float f5) {
        Lamp.render(f5);
        if (lantern != null) {
            if (lantern.XM) {
                BarXM.render(f5);
            }
            if (lantern.XP) {
                BarXP.render(f5);
            }
            if (lantern.ZM) {
                BarZM.render(f5);
            }
            if (lantern.ZP) {
                BarZP.render(f5);
            }
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}