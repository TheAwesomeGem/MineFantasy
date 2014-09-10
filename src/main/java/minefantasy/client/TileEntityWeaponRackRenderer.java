package minefantasy.client;


import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.EQUIPPED_BLOCK;
import minefantasy.MineFantasyBase;
import minefantasy.api.aesthetic.IWeaponrackHangable;
import minefantasy.block.tileentity.TileEntityWeaponRack;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import mods.battlegear2.api.shield.IShield;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.Color;

public class TileEntityWeaponRackRenderer extends TileEntitySpecialRenderer 
{
	private static Minecraft mc = Minecraft.getMinecraft();
    public TileEntityWeaponRackRenderer() 
    {
        model = new ModelWeaponRack();
    }
    
    public TileEntityWeaponRackRenderer(TileEntityRenderer render)
    {
        model = new ModelWeaponRack();
        this.setTileEntityRenderer(render);
    }

    public void renderAModelAt(TileEntityWeaponRack tile, double d, double d1, double d2, float f) {
        if (tile != null);
        int i = 1;
        if (tile.worldObj != null) {
            i = tile.direction; //this is for rotation
        }

        int j = 90 * i;

        if (i == 0) {
            j = 0;
        }

        if (i == 1) {
            j = 270;
        }

        if (i == 2) {
            j = 180;
        }

        if (i == 3) {
            j = 90;
        }
        if (i == 4) {
            j = 90;
        }
        bindTextureByName(data_minefantasy.image("/item/Rack.png"));
        
        GL11.glPushMatrix();//Start all
        GL11.glTranslatef((float) d + 0.5F, (float) d1+1F, (float) d2 + 0.5F);
        GL11.glRotatef(j+180, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        float itemsStart = -(3F/16F);
        float itemsGap = 4F/16F;
        model.renderModel(0.0625F);
        for(int a = 0; a < 4; a ++)
        {
        	GL11.glPushMatrix();//Start Individual Items
        	ItemStack itemstack = tile.getStackInSlot(a);
    		if(itemstack != null)
    		{
    			float x = itemsStart + (a*itemsGap);
    			float y = 0.3F;
    			float z = a % 2 == 0 ? 0.4F : 0.45F;
    			z -= 2F/16F;
    			float r = getRotationForItem(itemstack.getItem());
    			float scale = 1.0F;
    			
    			GL11.glTranslatef(x, y, z);
    			
    			GL11.glPushMatrix();
    			GL11.glRotatef(r, 0, 0, 1);
    			GL11.glScalef(scale, scale, 1);

    			for(int layer = 0; layer < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); layer ++)
				{
	    			Icon icon = itemstack.getItem().getIcon(itemstack, layer);
	    			if(icon != null)
	    			{
	    				GL11.glPushMatrix();
	    	            //GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
	    	            //GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
	    	            
	    				renderItem(tile, itemstack, icon, a, layer);
	    				GL11.glPopMatrix();
	    			}
				}
    			GL11.glPopMatrix();
    		}
    		
    		GL11.glPopMatrix();//END individual item placement
        }
        GL11.glPopMatrix(); //end all
        

    }

	private float getRotationForItem(Item item) {
		String classname = item.getClass().getName();
		if(classname.endsWith("ItemCrossbow") || classname.endsWith("ItemBlunderbuss") || classname.endsWith("ItemBlowgun") || classname.endsWith("ItemMusket"))
		{
			return 45F;
		}
		return -45F;
	}

	private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
	
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityWeaponRack) tileentity, d, d1, d2, f); //where to render
    }
	
	
	private void renderItem(TileEntityWeaponRack tile, ItemStack itemstack, Icon icon, int slot, int layer) 
	{
		GL11.glPushMatrix();
        TextureManager texturemanager = this.mc.getTextureManager();
        GL11.glScalef(0.85F, 0.85F, 0.85F);
        
        IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED);
        if (isCustom(itemstack) && customRenderer != null)
        {
    		texturemanager.bindTexture(texturemanager.getResourceLocation(itemstack.getItemSpriteNumber()));
    		renderEquippedItem(ItemRenderType.EQUIPPED, customRenderer, new RenderBlocks(), itemstack);
        }
        else
        {
            if (icon == null)
            {
                GL11.glPopMatrix();
                return;
            }

            texturemanager.bindTexture(texturemanager.getResourceLocation(itemstack.getItemSpriteNumber()));
            Tessellator tessellator = Tessellator.instance;
            float f = icon.getMinU();
            float f1 = icon.getMaxU();
            float f2 = icon.getMinV();
            float f3 = icon.getMaxV();
            float f4 = 0.0F;
            float f5 = 0.3F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glTranslatef(-f4, -f5, 0.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);

            if (itemstack.hasEffect(layer))
            {
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glDisable(GL11.GL_LIGHTING);
                texturemanager.bindTexture(TextureHelperMF.ITEM_GLINT);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                float f7 = 0.76F;
                GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glPushMatrix();
                float f8 = 0.125F;
                GL11.glScalef(f8, f8, f8);
                float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                GL11.glTranslatef(f9, 0.0F, 0.0F);
                GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(f8, f8, f8);
                f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                GL11.glTranslatef(-f9, 0.0F, 0.0F);
                GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }

        GL11.glPopMatrix();
	}
	
	private boolean isCustom(ItemStack itemstack)
	{
		if(itemstack != null)
		{
			if(itemstack.getItem() instanceof IWeaponrackHangable)
			{
				return ((IWeaponrackHangable)itemstack.getItem()).canUseRenderer(itemstack);
			}
			if(itemstack.getItem() instanceof IShield)
			{
				return true;
			}
			return cfg.canRenderHung(itemstack.itemID);
		}
		return false;
	}

	@Deprecated
	private void renderItem2(TileEntityWeaponRack tile, ItemStack itemstack, Icon icon, int slot, int layer) 
	{
		Minecraft mc = Minecraft.getMinecraft();
	    	mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
	
	    	float f8 = 1.0F;
	    	
	    	int i = Item.itemsList[itemstack.itemID].getColorFromItemStack(itemstack, layer);
            float f5 = (float)(i >> 16 & 255) / 255.0F;
            float f4 = (float)(i >> 8 & 255) / 255.0F;
            float f6 = (float)(i & 255) / 255.0F;
            GL11.glColor4f(f5 * f8, f4 * f8, f6 * f8, 1.0F);
            
	        Tessellator image = Tessellator.instance;
	        float x1 = icon.getMinU();
	        float x2 = icon.getMaxU();
	        float y1 = icon.getMinV();
	        float y2 = icon.getMaxV();
	        float xPos = 0.3F;
	        float yPos = 0.3F;
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        GL11.glTranslatef(-xPos, -yPos, 0.0F);
	        float scale = 0.6F;
	        GL11.glScalef(scale, scale, scale);
	        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		
	        
	        GL11.glPushMatrix();
	        if (itemstack != null && itemstack.isItemEnchanted())
            {
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glDisable(GL11.GL_LIGHTING);
                mc.renderEngine.bindTexture(TextureHelperMF.ITEM_GLINT);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                float f13 = 0.76F;
                GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glPushMatrix();
                float f14 = 0.125F;
                GL11.glScalef(f14, f14, f14);
                float f15 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                GL11.glTranslatef(f15, 0.0F, 0.0F);
                GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(image, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, 0.0625F);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(f14, f14, f14);
                f15 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                GL11.glTranslatef(-f15, 0.0F, 0.0F);
                GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(image, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, 0.0625F);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
            }
	        GL11.glPopMatrix();
	}
	
	public static void renderEquippedItem(ItemRenderType type, IItemRenderer customRenderer, RenderBlocks renderBlocks, ItemStack item)
    {
        if (customRenderer.shouldUseRenderHelper(type, item, EQUIPPED_BLOCK))
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            customRenderer.renderItem(type, item, renderBlocks);
            GL11.glPopMatrix();
        }
        else
        {
            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glTranslatef(0.0F, -0.3F, 0.0F);
            
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            customRenderer.renderItem(type, item, renderBlocks);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }
	
    private ModelWeaponRack model;
}