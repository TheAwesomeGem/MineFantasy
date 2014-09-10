package minefantasy.client.entityrender;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelApron extends ModelBiped
{
    public ModelApron(float scale)
    {
        this(scale, 0.0F, 64, 64);
    }

    public ModelApron(float scale, float rotate, int x, int y)
    {
        this.bipedBody = (new ModelRenderer(this)).setTextureSize(x, y);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + rotate, 0.0F);
        this.bipedBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, scale + 0.5F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedBody.render(f5);
    }
}
