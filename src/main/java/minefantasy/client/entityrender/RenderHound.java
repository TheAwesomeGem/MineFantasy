package minefantasy.client.entityrender;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.hound.*;
import minefantasy.client.TextureHelperMF;
import minefantasy.entity.EntityHound;
import minefantasy.entity.IGrowable;
import minefantasy.item.ItemHoundPackMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHound extends RenderLivingMF
{
	private ModelHound mail = new ModelHound(0.5F);
	private ModelHound plate = new ModelHound(0.65F);
	private ModelHoundPack pack = new ModelHoundPack();
	private static Minecraft mc = Minecraft.getMinecraft();
	
    public RenderHound(ModelBase base, float shadow)
    {
        super(base, shadow);
        this.setRenderPassModel(base);
    }
    protected float getTailRotation(EntityHound wolf, float angle)
    {
        return wolf.getTailRotation();
    }
    
    @Override
    public int getRenderPasses()
    {
    	return 10;
    }
    
    protected int renderSpecial(EntityHound wolf, int render, float f)
    {
    	this.setRenderPassModel(this.mainModel);
    	float var4;
    	this.setRenderPassModel(mainModel);

        if (render == 0 && wolf.getWolfShaking())
        {
            var4 = wolf.getBrightness(f) * wolf.getShadingWhileShaking(f);
            this.loadTexture(wolf.getTexture());
            GL11.glColor3f(var4, var4, var4);
            return 1;
        }
        else if (render == 1 && wolf.isTamed())
        {
            this.loadTexture(data_minefantasy.image("/mob/Hound/houndCollar.png"));
            var4 = 1.0F;
            int var5 = wolf.getCollarColour();
            GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
            return 1;
        }
        else if (render == 2 || render == 3) // Body Mail
        {
        	GL11.glColor3f(1.0F, 1.0F, 1.0F);
        	ItemStack stack = wolf.getStackInSlot(render-2);

            if (stack != null)
            {
                Item item = stack.getItem();

                if (item instanceof IHoundApparel)
                {
                	IHoundApparel armour = (IHoundApparel)item;
                	if(armour.getTexture() != null)
                	{
	                	this.loadTexture(armour.getTexture());
	                	ModelHound model = mail;
	                	int type = render == 2 ? 1 : 0;
	                	
	                	//HEAD
	                	model.WolfHead.showModel = type == 1;
	                	model.Nose.showModel = type == 1;
	                	model.Ear1.showModel = type == 1;
	                	model.Ear2.showModel = type == 1;
	                	model.Jaw.showModel = type == 1;
	                	
	                	//BODY
	                	model.Leg1.showModel = type == 0;
	                	model.Leg2.showModel = type == 0;
	                	model.Leg3.showModel = type == 0;
	                	model.Leg4.showModel = type == 0;
	                	model.Mane.showModel = type == 0;
	                	model.Body.showModel = type == 0;
	                	model.Tail.showModel = type == 0;
	                	
	                	
	                	this.setRenderPassModel(model);
	
	                    if (model != null)
	                    {
	                        model.onGround = this.mainModel.onGround;
	                    }
	
	                    if (model != null)
	                    {
	                        model.isRiding = this.mainModel.isRiding;
	                    }
	
	                    if (model != null)
	                    {
	                        model.isChild = this.mainModel.isChild;
	                    }
	                	
	                    
	
	                    if (stack.isItemEnchanted())
	                    {
	                        return 15;
	                    }
                	}

                    return 1;
                }
                
            }
        }
        else if (render == 4 || render == 5) // Body Plate
        {
        	GL11.glColor3f(1.0F, 1.0F, 1.0F);
        	ItemStack stack = wolf.getStackInSlot(render-4);

            if (stack != null)
            {
                Item item = stack.getItem();

                if (item instanceof IHoundApparel)
                {
                	IHoundApparel armour = (IHoundApparel)item;

                	boolean r = armour.getOverlay() != null;
                	
                	if(r)
	                	this.loadTexture(armour.getOverlay());
                	
	                	ModelHound model = r ? plate : mail;
	                	int type = render == 4 ? 1 : 0;
	                	
	                	//HEAD
	                	model.WolfHead.showModel = type == 1;
	                	model.Nose.showModel = type == 1;
	                	model.Ear1.showModel = type == 1;
	                	model.Ear2.showModel = type == 1;
	                	model.Jaw.showModel = type == 1;
	                	
	                	//BODY
	                	model.Leg1.showModel = type == 0;
	                	model.Leg2.showModel = type == 0;
	                	model.Leg3.showModel = type == 0;
	                	model.Leg4.showModel = type == 0;
	                	model.Mane.showModel = type == 0;
	                	model.Body.showModel = type == 0;
	                	model.Tail.showModel = type == 0;
	                	
	                	this.setRenderPassModel(model);
	                	
	
	                    if (model != null)
	                    {
	                        model.onGround = this.mainModel.onGround;
	                    }
	
	                    if (model != null)
	                    {
	                        model.isRiding = this.mainModel.isRiding;
	                    }
	
	                    if (model != null)
	                    {
	                        model.isChild = this.mainModel.isChild;
	                    }
                    if (stack.isItemEnchanted())
                    {
                        return 15;
                    }

                    return 1;
                }
                
            }
        }
        else if (render == 6 || render == 7) // Pack
        {
        	GL11.glColor3f(1.0F, 1.0F, 1.0F);
        	ItemStack stack = wolf.getStackInSlot(render - 6);

            if (stack != null)
            {
                Item item = stack.getItem();

                if (item instanceof IHoundPackItem)
                {
                	IHoundPackItem pack = (IHoundPackItem)item;
                	this.loadTexture(pack.getTexture());
                	ModelHoundPack model = this.pack;
                	int type = render == 6 ? 1 : 0;
                	
                	//HEAD
                	model.feedbag.showModel = type == 1;
                	
                	//BODY
                	model.bigPack.showModel = type == 0;
                	model.pack1.showModel = type == 0;
                	model.pack2.showModel = type == 0;
                	model.pack3.showModel = type == 0;
                	model.pack4.showModel = type == 0;
                	model.smlPack.showModel = type == 0;
                	model.PaxkBase.showModel = type == 0;
                	
                	float offset = (float)Math.toRadians(-90) + ((ModelHound)mainModel).Mane.rotateAngleX;
                	model.feedbag.rotateAngleX = offset;
                	model.bigPack.rotateAngleX = offset;
                	model.pack1.rotateAngleX = offset;
                	model.pack2.rotateAngleX = offset;
                	model.pack3.rotateAngleX = offset;
                	model.pack4.rotateAngleX = offset;
                	model.smlPack.rotateAngleX = offset;
                	model.PaxkBase.rotateAngleX = offset;
                	model.PackBand.rotateAngleX = offset;
                	
                	
                	this.setRenderPassModel(model);

                    if (model != null)
                    {
                        model.onGround = this.mainModel.onGround;
                    }

                    if (model != null)
                    {
                        model.isRiding = this.mainModel.isRiding;
                    }

                    if (model != null)
                    {
                        model.isChild = this.mainModel.isChild;
                    }
                	
                    

                    if (stack.isItemEnchanted())
                    {
                        return 15;
                    }

                    return 1;
                }
                
            }
        }
        else if (render == 8 && wolf.isAngry())
        {
            this.loadTexture(data_minefantasy.image("/mob/Hound/houndAnger.png"));
            return 1;
        }
        else if (render == 9 && wolf.getSpots() != null)
        {
        	this.loadTexture(data_minefantasy.image("/mob/Hound/spots" + (wolf.invertSpots == 1 ? wolf.getFurTex() : wolf.getSpots()) + ".png"));
            return 1;
        }
           
        return -1;
    }

    private void loadTexture(String image) 
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
	/**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int render, float f)
    {
        return this.renderSpecial((EntityHound)entity, render, f);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    @Override
    protected float handleRotationFloat(EntityLivingBase entity, float angle)
    {
        return this.getTailRotation((EntityHound)entity, angle);
    }
    
    @Override
    protected boolean func_110813_b(EntityLivingBase entity)
    {
    	return shouldRenderLabel((EntityHound)entity);
    }
    private boolean shouldRenderLabel(EntityHound dog)
    {
    	return dog.isTamed() && dog.hasCustomNameTag() && dog.canSeeName(mc.thePlayer);
	}
	@Override
    protected void preRenderCallback(EntityLivingBase living, float f)
    {
        this.buffAlpha((EntityHound)living, f);
    }
	private void buffAlpha(EntityHound dog, float f) {
		if(dog.isAlpha())
		{
			GL11.glScalef(1.25F, 1.2F, 1.2F);
		}
	}
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getHoundTex((EntityHound)entity);
	}
	private ResourceLocation getHoundTex(EntityHound entity)
	{
		return TextureHelperMF.getResource(entity.getTexture());
	}
	
}
