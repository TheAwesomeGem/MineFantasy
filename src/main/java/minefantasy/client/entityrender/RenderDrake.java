package minefantasy.client.entityrender;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.client.TextureHelperMF;
import minefantasy.entity.EntityDrake;
import minefantasy.entity.IGrowable;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDrake extends RenderLiving
{
    /** Scale of the model to use */
    private float scale;

    public RenderDrake(ModelBase base, float shadow)
    {
        super(base, 0F);
    }

    /**
     * Applies the scale to the transform matrix
     */
    protected void preRenderScale(IGrowable entity, float f)
    {
    	scale = entity.getTotalScale();
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * EntityLivingBase, partialTickTime
     */
    @Override
    protected void preRenderCallback(EntityLivingBase living, float f)
    {
        this.preRenderScale((IGrowable)living, f);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
		return getTexture((EntityDrake)entity);
	}
	protected ResourceLocation getTexture(EntityDrake entity) 
	{
		return TextureHelperMF.getResource(data_minefantasy.image("/mob/"+entity.getTexture()+".png"));
	}
}
