package minefantasy.client;

import java.util.Random;

import minefantasy.block.tileentity.TileEntityPrepBlock;
import minefantasy.block.tileentity.TileEntityTailor;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 * 
 * Custom renderers based off render tutorial by MC_DucksAreBest
 */
public class TileEntityTailorRenderer extends TileEntitySpecialRenderer 
{
    public TileEntityTailorRenderer() 
    {
        model = new ModelTailorBench();
    }
    public TileEntityTailorRenderer(TileEntityRenderer render) 
    {
    	this();
    	this.setTileEntityRenderer(render);
    }

    public void renderAModelAt(TileEntityTailor tile, double d, double d1, double d2, float f) 
    {
        if (tile != null);
        int i = 0;
        if (tile.worldObj != null)
        {
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

        bindTextureByName(data_minefantasy.image("/item/TailorBench.png")); //texture
        GL11.glPushMatrix(); //start
        float yOffset = 1.25F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); //size
        GL11.glRotatef(j+180, 0.0F, 1.0F, 0.0F); //rotate based on metadata
        GL11.glScalef(1.0F, -1.0F, -1.0F); //if you read this comment out this line and you can see what happens
        model.renderModel(0.0625F); //renders  0.0625 is 1/16, (1 block = 1.0F)
        GL11.glPushMatrix();
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        renderItems(tile);
        GL11.glPopMatrix();
        GL11.glPopMatrix(); //end

    }
    
    private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
    
	private void renderItems(TileEntityTailor tile) 
	{
		double xStart = -0.5 - (0.0625)*1.0F;
		double yStart = 0.15;
		double xGap = (0.0625*4)*0.9F;
		double yGap = -(0.0625*4)*0.9F;
		
		for(int y = 0; y < 4; y ++)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(xStart, 0.325, yStart + (yGap)*y);
			renderItem(tile.getRenderItem(y));
			GL11.glPopMatrix();
		}
		
		for(int x = 0; x < 4; x ++)
		{
			for(int y = 0; y < 4; y ++)
			{
				GL11.glPushMatrix();
				GL11.glTranslated(xStart + (xGap)*(x+2), 0.325, yStart + (yGap)*y);
				renderItem(tile.getRenderItem(y*4 + x + 4));
				GL11.glPopMatrix();
			}
		}
		if(tile.getRenderItem(tile.getOutputSlot()) != null)
		{
			boolean move = tile.canCraft() && tile.canFitResult();
			renderBigItem(move, tile.getRenderItem(tile.getOutputSlot()), 0D, 1D, 0D);
		}
	}
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityTailor) tileentity, d, d1, d2, f); //where to render
    }
    private ModelTailorBench model;
    private Random random = new Random();
    
    
    private void renderItem(ItemStack itemstack)
    {
    	GL11.glPushMatrix();
    	GL11.glRotatef(-90, 1, 0, 0);
    	GL11.glScalef((0.3F)*0.75F, (0.3F)*0.75F, (0.3F)*0.75F);
    	Minecraft mc = Minecraft.getMinecraft();
    	if(itemstack != null && itemstack.getItem() != null)
		{
    		if(itemstack.getItem() instanceof ItemBlock)
			{
    			GL11.glRotatef(90, 1, 0, 0);
				GL11.glTranslatef(0.0F, -0.2F, -0.3F);
			}
    		for (int k = 0; k < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++k)
			{
    			GL11.glPushMatrix();
    			Icon index = itemstack.getItem().getIcon(itemstack, k);
				int colour = Item.itemsList[itemstack.itemID].getColorFromItemStack(itemstack, k);
				float red = (float)(colour >> 16 & 255) / 255.0F;
	            float green = (float)(colour >> 8 & 255) / 255.0F;
	            float blue = (float)(colour & 255) / 255.0F;
	            float shade = 1.0F;
	            GL11.glColor4f(red * shade, green * shade, blue * shade, 1.0F);
	            
				Block block = null;
	            if (itemstack.itemID < Block.blocksList.length)
	            {
	                block = Block.blocksList[itemstack.itemID];
	            }
	            
	            int i;
	            float f4;
	            float f5;
	            float f6;
	            
				if (itemstack.getItemSpriteNumber() == 0 && block != null)
	            {
					GL11.glRotatef(90, 1, 0, 0);
					GL11.glTranslatef(0F, (2.5F * 0.0625F), 0F);
					mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
	                float f7 = 0.5F;
	
	                GL11.glScalef(f7, f7, f7);
	
	                byte b0 = 1;
	                for (i = 0; i < b0; ++i)
	                {
	                    GL11.glPushMatrix();
	
	                    if (i > 0)
	                    {
	                        f5 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
	                        f4 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
	                        f6 = (this.random .nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
	                        GL11.glTranslatef(f5, f4, f6);
	                    }
	
	                    f5 = 1.0F;
	                    new RenderBlocks().renderBlockAsItem(block, itemstack.getItemDamage(), f5);
	                    GL11.glPopMatrix();
	                }
	            }
	            else
				{
					Item item = itemstack.getItem();
					mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
			
			        Tessellator image = Tessellator.instance;
			        float x1 = index.getMinU();
			        float x2 = index.getMaxU();
			        float y1 = index.getMinV();
			        float y2 = index.getMaxV();
			        float xPos = 0.5F;
			        float yPos = 0.5F;
			        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			        GL11.glTranslatef(-xPos, -yPos, 0.0F);
			        float var13 = 1F;
			        GL11.glScalef(var13, var13, var13);
			        //GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			        GL11.glTranslatef(-1F, -1F, 0.0F);
			        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, index.getIconWidth(), index.getIconHeight(), 0.0625F);
			        
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
				GL11.glPopMatrix();
			}
		}
    	GL11.glPopMatrix();
    }
    
    
    
    
    private void renderBigItem(boolean move, ItemStack itemstack, double d, double d1,
			double d2) {
    	GL11.glPushMatrix();
    	GL11.glScalef(0.75F, 0.75F, 0.75F);
    	GL11.glTranslatef(0F, 0.15F, 0F);
    	if(move)
    	{
    		GL11.glTranslatef(0F, 1.0F, 0F);
    	}
		Minecraft mc = Minecraft.getMinecraft();
		if(itemstack != null && itemstack.getItem() != null)
		{
			if(!(itemstack.getItem() instanceof ItemBlock))
			{
				GL11.glTranslatef(0.0F, 0.3F, 0.0F);
		    	GL11.glRotatef(-90, 1, 0, 0);
			}
			for (int k = 0; k < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++k)
			{
				GL11.glPushMatrix();
				Icon index = itemstack.getItem().getIcon(itemstack, k);
				int colour = Item.itemsList[itemstack.itemID].getColorFromItemStack(itemstack, k);
				float red = (float)(colour >> 16 & 255) / 255.0F;
	            float green = (float)(colour >> 8 & 255) / 255.0F;
	            float blue = (float)(colour & 255) / 255.0F;
	            float shade = 1.0F;
	            GL11.glColor4f(red * shade, green * shade, blue * shade, 1.0F);
				
				Block block = null;
	            if (itemstack.itemID < Block.blocksList.length)
	            {
	                block = Block.blocksList[itemstack.itemID];
	            }
	            
	            int i;
	            float f4;
	            float f5;
	            float f6;
	            
				if (itemstack.getItemSpriteNumber() == 0 && block != null)
	            {
					GL11.glTranslatef(0F, (2.5F * 0.0625F), 0F);
					mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
	                float f7 = 0.5F;
	
	                GL11.glScalef(f7, f7, f7);
	
	                byte b0 = 1;
	                for (i = 0; i < b0; ++i)
	                {
	                    GL11.glPushMatrix();
	
	                    if (i > 0)
	                    {
	                        f5 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
	                        f4 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
	                        f6 = (this.random .nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
	                        GL11.glTranslatef(f5, f4, f6);
	                    }
	
	                    f5 = 1.0F;
	                    new RenderBlocks().renderBlockAsItem(block, itemstack.getItemDamage(), f5);
	                    GL11.glPopMatrix();
	                }
	            }
	            else
				{
					Item item = itemstack.getItem();
					mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
			
			        Tessellator image = Tessellator.instance;
			        float x1 = index.getMinU();
			        float x2 = index.getMaxU();
			        float y1 = index.getMinV();
			        float y2 = index.getMaxV();
			        float xPos = 0.5F;
			        float yPos = 0.5F;
			        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			        GL11.glTranslatef(-xPos, -yPos, 0.0F);
			        float var13 = 1F;
			        GL11.glScalef(var13, var13, var13);
			        //GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			        GL11.glTranslatef(-1F, -1F, 0.0F);
			        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, index.getIconWidth(), index.getIconHeight(), 0.0625F);
			        
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
				GL11.glPopMatrix();
			}
		}
		 GL11.glPopMatrix();
	}
}