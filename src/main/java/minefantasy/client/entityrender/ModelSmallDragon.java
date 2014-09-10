package minefantasy.client.entityrender;

import minefantasy.entity.EntityDragonSmall;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSmallDragon extends ModelBase {
    //fields

    ModelRenderer footBL;
    ModelRenderer legBL;
    ModelRenderer legFR;
    ModelRenderer footBR;
    ModelRenderer legBR;
    ModelRenderer thighBR;
    ModelRenderer mouth;
    ModelRenderer tail2;
    ModelRenderer neck;
    ModelRenderer horn2;
    ModelRenderer nose;
    ModelRenderer horn1;
    ModelRenderer head;
    ModelRenderer thighBL;
    ModelRenderer legFL;
    ModelRenderer wing2L;
    ModelRenderer tail1;
    ModelRenderer body;
    ModelRenderer wing2R;
    ModelRenderer wingCR;
    ModelRenderer wingCL;
    ModelRenderer wing1L;
    ModelRenderer wing1R;

    public ModelSmallDragon() {
        textureWidth = 128;
        textureHeight = 128;

        footBL = new ModelRenderer(this, 102, 29);
        legBL = new ModelRenderer(this, 108, 16);
        legFR = new ModelRenderer(this, 86, 0);
        footBR = new ModelRenderer(this, 102, 29);
        legBR = new ModelRenderer(this, 108, 16);
        thighBR = new ModelRenderer(this, 102, 0);
        mouth = new ModelRenderer(this, 0, 64);
        tail2 = new ModelRenderer(this, 0, 106);
        neck = new ModelRenderer(this, 52, 46);
        wing1R = new ModelRenderer(this, 0, 84);
        wing1L = new ModelRenderer(this, 0, 84);
        wingCL = new ModelRenderer(this, 48, 103);
        wingCR = new ModelRenderer(this, 48, 103);
        tail1 = new ModelRenderer(this, 47, 19);
        wing2L = new ModelRenderer(this, 0, 88);
        legFL = new ModelRenderer(this, 86, 0);
        thighBL = new ModelRenderer(this, 102, 0);
        head = new ModelRenderer(this, 24, 48);
        horn1 = new ModelRenderer(this, 104, 52);
        nose = new ModelRenderer(this, 0, 74);
        horn2 = new ModelRenderer(this, 104, 52);
        wing2R = new ModelRenderer(this, 0, 88);

        footBL.addBox(-3F, 8F, -6F, 5, 1, 8);
        footBL.setRotationPoint(6F, 15F, 8F);
        footBL.setTextureSize(128, 128);
        footBL.mirror = true;
        setRotation(footBL, 0F, 0F, 0F);
        footBL.mirror = false;
        legBL.mirror = true;

        legBL.addBox(-3F, 0F, -2F, 5, 8, 5);
        legBL.setRotationPoint(6F, 15F, 8F);
        legBL.setTextureSize(128, 128);
        legBL.mirror = true;
        setRotation(legBL, 0F, 0F, 0F);
        legBL.mirror = false;

        legFR.addBox(0F, -2F, -2F, 4, 12, 4);
        legFR.setRotationPoint(-6F, 14F, -4F);
        legFR.setTextureSize(128, 128);
        legFR.mirror = true;
        setRotation(legFR, 0F, 0F, 0F);

        footBR.addBox(-2F, 8F, -6F, 5, 1, 8);
        footBR.setRotationPoint(-6F, 15F, 8F);
        footBR.setTextureSize(128, 128);
        footBR.mirror = true;
        setRotation(footBR, 0F, 0F, 0F);

        legBR.addBox(-2F, 0F, -2F, 5, 8, 5);
        legBR.setRotationPoint(-6F, 15F, 8F);
        legBR.setTextureSize(128, 128);
        legBR.mirror = true;
        setRotation(legBR, 0F, 0F, 0F);

        thighBR.addBox(-2F, -8F, -4F, 5, 8, 8);
        thighBR.setRotationPoint(-6F, 15F, 8F);
        thighBR.setTextureSize(128, 128);
        thighBR.mirror = true;
        setRotation(thighBR, 0F, 0F, 0F);

        mouth.addBox(-1.5F, 1F, -14F, 3, 1, 7);
        mouth.setRotationPoint(0F, 6F, -14F);
        mouth.setTextureSize(128, 128);
        mouth.mirror = true;
        setRotation(mouth, 0F, 0F, 0F);

        tail2.addBox(-2F, 2F, 12F, 4, 4, 18);
        tail2.setRotationPoint(0F, 13F, 14F);
        tail2.setTextureSize(128, 128);
        tail2.mirror = true;
        setRotation(tail2, -0.0174533F, 0F, 0F);

        neck.addBox(-3F, -3F, -14F, 6, 8, 10);
        neck.setRotationPoint(0F, 7F, 0F);
        neck.setTextureSize(128, 128);
        neck.mirror = true;
        setRotation(neck, -0.122173F, 0F, 0F);
        horn2.mirror = true;

        horn2.addBox(3F, -6F, -3F, 1, 1, 11);
        horn2.setRotationPoint(0F, 6F, -14F);
        horn2.setTextureSize(128, 128);
        horn2.mirror = true;
        setRotation(horn2, 0.6108652F, 0F, 0F);
        horn2.mirror = false;

        nose.addBox(-2F, 2F, -15F, 4, 2, 8);
        nose.setRotationPoint(0F, 6F, -14F);
        nose.setTextureSize(128, 128);
        nose.mirror = true;
        setRotation(nose, 0F, 0F, 0F);

        horn1.addBox(-4F, -6F, -3F, 1, 1, 11);
        horn1.setRotationPoint(0F, 6F, -14F);
        horn1.setTextureSize(128, 128);
        horn1.mirror = true;
        setRotation(horn1, 0.6108652F, 0F, 0F);

        head.addBox(-3F, -4F, -7F, 6, 8, 8);
        head.setRotationPoint(0F, 6F, -14F);
        head.setTextureSize(128, 128);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        thighBL.mirror = true;

        thighBL.addBox(-3F, -8F, -4F, 5, 8, 8);
        thighBL.setRotationPoint(6F, 15F, 8F);
        thighBL.setTextureSize(128, 128);
        thighBL.mirror = true;
        setRotation(thighBL, 0F, 0F, 0F);
        thighBL.mirror = false;
        legFL.mirror = true;

        legFL.addBox(-4F, -2F, -2F, 4, 12, 4);
        legFL.setRotationPoint(6F, 14F, -4F);
        legFL.setTextureSize(128, 128);
        legFL.mirror = true;
        setRotation(legFL, 0F, 0F, 0F);
        legFL.mirror = false;
        wing2L.mirror = true;

        wing2L.addBox(13F, -1F, 1F, 2, 2, 16);
        wing2L.setRotationPoint(5F, 4F, -2F);
        wing2L.setTextureSize(128, 128);
        wing2L.mirror = true;
        setRotation(wing2L, 0F, 0.5235988F, -0.3490659F);
        wing2L.mirror = false;

        tail1.addBox(-3F, -2F, -1F, 6, 7, 14);
        tail1.setRotationPoint(0F, 13F, 14F);
        tail1.setTextureSize(128, 128);
        tail1.mirror = true;
        setRotation(tail1, -0.1745329F, 0F, 0F);
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-5F, -4F, -5F, 10, 12, 20);
        body.setRotationPoint(0F, 7F, 0F);
        body.setTextureSize(128, 128);
        body.mirror = true;
        setRotation(body, -0.296706F, 0F, 0F);

        wing2R.addBox(-15F, -1F, 1F, 2, 2, 16);
        wing2R.setRotationPoint(-5F, 4F, -2F);
        wing2R.setTextureSize(128, 128);
        wing2R.mirror = true;
        setRotation(wing2R, 0F, -0.5235988F, 0.3490659F);

        wingCR.addBox(-13F, -1F, 1F, 16, 1, 24);
        wingCR.setRotationPoint(-5F, 4F, -2F);
        wingCR.setTextureSize(128, 128);
        wingCR.mirror = true;
        setRotation(wingCR, 0F, -0.5235988F, 0.3490659F);
        wingCL.mirror = true;

        wingCL.addBox(-3F, -1F, 1F, 16, 1, 24);
        wingCL.setRotationPoint(5F, 4F, -2F);
        wingCL.setTextureSize(128, 128);
        wingCL.mirror = true;
        setRotation(wingCL, 0F, 0.5235988F, -0.3490659F);
        wingCL.mirror = false;
        wing1L.mirror = true;

        wing1L.addBox(-1F, -1F, -1F, 16, 2, 2);
        wing1L.setRotationPoint(5F, 4F, -2F);
        wing1L.setTextureSize(128, 128);
        wing1L.mirror = true;
        setRotation(wing1L, 0F, 0.5235988F, -0.3490659F);
        wing1L.mirror = false;

        wing1R.addBox(-15F, -1F, -1F, 16, 2, 2);
        wing1R.setRotationPoint(-5F, 4F, -2F);
        wing1R.setTextureSize(128, 128);
        wing1R.mirror = true;
        setRotation(wing1R, 0F, -0.5235988F, 0.3490659F);
        mouth.setTextureOffset(0, 55).addBox(-1.5F, 2F, -14F, 3, 2, 7);
        mouth.setRotationPoint(0F, 6F, -14F);
        mouth.setTextureSize(128, 128);
        mouth.mirror = true;
        setRotation(mouth, 0F, 0F, 0F);
        nose.setTextureOffset(0, 43).addBox(-2F, -2F, -15F, 4, 4, 8);
        nose.setRotationPoint(0F, 6F, -14F);
        nose.setTextureSize(128, 128);
        nose.mirror = true;
        setRotation(nose, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles((EntityDragonSmall) entity, f, f1, f2, f3, f4, f5);
        footBL.render(f5);
        legBL.render(f5);
        legFR.render(f5);
        footBR.render(f5);
        legBR.render(f5);
        thighBR.render(f5);
        tail2.render(f5);
        neck.render(f5);
        horn2.render(f5);
        horn1.render(f5);
        head.render(f5);
        thighBL.render(f5);
        legFL.render(f5);
        wing2L.render(f5);
        tail1.render(f5);
        body.render(f5);
        wing2R.render(f5);
        wingCR.render(f5);
        wingCL.render(f5);
        wing1L.render(f5);
        wing1R.render(f5);
        mouth.render(f5);
        nose.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(EntityDragonSmall dragon, float step1, float step2, float head1, float head2, float head3, float head4) {
        super.setRotationAngles(step1, step2, head1, head2, head3, head4, dragon);
        float wingFlap = (float) Math.toRadians(dragon.wingFlap());
        float jawAngle = (float)Math.toRadians(dragon.getJawMove());
        float neckAngle = -(float)Math.toRadians(4.5F * dragon.getNeckAngle());

        this.head.rotateAngleX = head4 / (180F / (float) Math.PI) + neckAngle;
        this.head.rotateAngleY = head3 / (180F / (float) Math.PI);

        if (!dragon.isTerrestrial()) {
            if (dragon.motionX == 0 && dragon.motionZ == 0) {
                wingFlap = (float) Math.toRadians(dragon.wingFlap());
            } else {
                //  wingFlap = MathHelper.cos(step1 * 0.6662F) * 1.4F * step2;
            }
        } else {
            wingFlap = (float) Math.toRadians(-40);
            this.legBR.rotateAngleX = MathHelper.cos(step1 * 0.6662F) * 1.4F * step2;
            this.legBL.rotateAngleX = MathHelper.cos(step1 * 0.6662F + (float) Math.PI) * 1.4F * step2;
            this.legFR.rotateAngleX = MathHelper.cos(step1 * 0.6662F + (float) Math.PI) * 1.4F * step2;
            this.legFL.rotateAngleX = MathHelper.cos(step1 * 0.6662F) * 1.4F * step2;
        }
            this.wing1R.rotateAngleZ = (float) wingFlap;
            this.wing2R.rotateAngleZ = (float) wingFlap;
            this.wingCR.rotateAngleZ = (float) wingFlap;

            this.wing1L.rotateAngleZ = (float) -wingFlap;
            this.wing2L.rotateAngleZ = (float) -wingFlap;
            this.wingCL.rotateAngleZ = (float) -wingFlap;
        

        this.footBL.rotateAngleX = this.legBL.rotateAngleX;
        this.footBR.rotateAngleX = this.legBR.rotateAngleX;
        this.thighBL.rotateAngleX = this.legBL.rotateAngleX;
        this.thighBR.rotateAngleX = this.legBR.rotateAngleX;

        double wingSpread = Math.toRadians(dragon.wingAngle());
        this.wing1R.rotateAngleY = (float) -wingSpread;
        this.wing2R.rotateAngleY = (float) -wingSpread;
        this.wingCR.rotateAngleY = (float) -wingSpread;

        this.wing1L.rotateAngleY = (float) wingSpread;
        this.wing2L.rotateAngleY = (float) wingSpread;
        this.wingCL.rotateAngleY = (float) wingSpread;

        this.nose.rotateAngleX = this.head.rotateAngleX - (jawAngle/2);
        this.mouth.rotateAngleX = this.head.rotateAngleX + jawAngle;

        this.nose.rotateAngleY = this.head.rotateAngleY;
        this.mouth.rotateAngleY = this.head.rotateAngleY;

        this.horn1.rotateAngleX = this.horn2.rotateAngleX = this.head.rotateAngleX + 0.6108652F;
        this.horn1.rotateAngleY = this.horn2.rotateAngleY = this.head.rotateAngleY;
    }
}
