package minefantasy.client.entityrender;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.client.TextureHelperMF;
import minefantasy.entity.EntityBasilisk;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBasilisk extends RenderLiving
{
    public RenderBasilisk()
    {
        super(new ModelBasilisk(), 1.0F);
        this.setRenderPassModel(new ModelBasilisk());
    }

    protected float setSpiderDeathMaxRotation(EntityBasilisk basilisk)
    {
        return 180.0F;
    }

    protected void scaleSpider(EntityBasilisk basilisk, float scale)
    {
        float f1 = basilisk.getScale();
        GL11.glScalef(f1, f1, f1);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving living, float scale)
    {
        this.scaleSpider((EntityBasilisk)living, scale);
    }

    protected float getDeathMaxRotation(EntityLiving living)
    {
        return this.setSpiderDeathMaxRotation((EntityBasilisk)living);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
		if(entity instanceof EntityBasilisk)
		{
			return TextureHelperMF.getResource(((EntityBasilisk)entity).getTexture());
		}
		return TextureHelperMF.getResource("mob/basilisk");
	}

}
