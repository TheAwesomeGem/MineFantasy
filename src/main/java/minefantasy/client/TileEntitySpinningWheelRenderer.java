package minefantasy.client;

import java.util.Random;

import minefantasy.block.tileentity.TileEntitySpinningWheel;
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
public class TileEntitySpinningWheelRenderer extends TileEntitySpecialRenderer 
{
    public TileEntitySpinningWheelRenderer() 
    {
        model = new ModelSpinningWheel();
    }
    public TileEntitySpinningWheelRenderer(TileEntityRenderer render) 
    {
    	this();
    	this.setTileEntityRenderer(render);
    }

    public void renderAModelAt(TileEntitySpinningWheel tile, double d, double d1, double d2, float f) 
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
        float p = tile.getArmValue();

        bindTextureByName(data_minefantasy.image("/item/SpinningWheel.png")); //texture
        GL11.glPushMatrix(); //start
        float yOffset = 1.25F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); //size
        GL11.glRotatef(j+180, 0.0F, 1.0F, 0.0F); //rotate based on metadata
        GL11.glScalef(1.0F, -1.0F, -1.0F); //if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, -0.0125F, 0F);
        model.renderModel(0.0625F); //renders  0.0625 is 1/16, (1 block = 1.0F)
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        renderItems(tile);
        GL11.glPopMatrix();
        
        bindTextureByName(data_minefantasy.image("/item/SpinningWheel.png")); //texture
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, pixel(5.5F), pixel(0));
        GL11.glPushMatrix();
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(p*360, 0, 0, 1F);
        renderWheel(tile, 52, 22, 10, 10, 64, 32);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    
        GL11.glPopMatrix(); //end

    }
    
    private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
    
	private void renderItems(TileEntitySpinningWheel tile) 
	{
		double xStart = -0.5F;
		double yStart = -0.5F;
		double height = 0.5F;
		
		GL11.glPushMatrix();
		GL11.glTranslated(xStart+0.5F, height+0.25F, yStart+0.05F);
		renderItem(tile.getStackInSlot(0), false);
		GL11.glPopMatrix();
		
		float offset = 1F/32F;
		
		GL11.glPushMatrix();
		GL11.glTranslated(xStart+0.2F + offset, height-0.1F, yStart+1.15F);
		GL11.glRotatef(-90, 0, 0, 1);
		renderItem(tile.getStackInSlot(1), true);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(xStart+0.8F + offset, height-0.1F, yStart+1.15F);
		GL11.glRotatef(-90, 0, 0, 1);
		renderItem(tile.getStackInSlot(2), true);
		GL11.glPopMatrix();
	}
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntitySpinningWheel) tileentity, d, d1, d2, f); //where to render
    }
    private ModelSpinningWheel model;
    private Random random = new Random();
    
    
    private void renderItem(ItemStack itemstack, boolean rotate)
    {
    	float scale = 2.0F;
    	float offset = -0.15F/16F * scale;
    	GL11.glPushMatrix();
    	GL11.glRotatef(-90, 1, 0, 0);
    	GL11.glScalef((0.3F)*scale, (0.3F)*scale, (0.3F)*scale);
    	GL11.glTranslatef(offset, 0, offset);
    	Minecraft mc = Minecraft.getMinecraft();
    	if(itemstack != null && itemstack.getItem() != null)
		{
    		if(itemstack.getItem() instanceof ItemBlock)
			{
    			GL11.glRotatef(90, 1, 0, 0);
				GL11.glTranslatef(0.0F, -0.2F, -0.3F);
				rotate = false;
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
			        float r = 180F;
			        if(rotate)
			        {
			        	r += 45F;
			        }
			        GL11.glRotatef(r, 0.0F, 0.0F, 1.0F);
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
    	if(move)
    	{
    		GL11.glTranslatef(0F, 1.25F, 0F);
    	}
    	else
    	{
    		GL11.glTranslatef(0F, 0.15F, 0F);
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
    
    
    
    
    private void renderWheel(TileEntitySpinningWheel tile, int x, int y, int w, int h, int tw, int th) {
		Minecraft mc = Minecraft.getMinecraft();
		
		float f = 0.01F / (float)tw;
        float f1 = 0.01F / (float)th;
        
		float x1 = (float)x / (float)tw + f;
		float x2 = (float)(x + w) / tw - f;
		float y1 = (float)y / th + f1;
		float y2 = (float)(y + h) / th - f1;
        
        Tessellator image = Tessellator.instance;
        float xPos = 0.5F;
        float yPos = 0.5F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-xPos, -yPos, pixel(0.5F));
        float var13 = 1F;
        GL11.glScalef(var13, var13, var13);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-1F, -1F, 0.0F);
        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, tw, th, 0.0625F);
	
	}
    public float pixel(float count)
    {
    	return count * 0.0625F;
    }
}