package minefantasy.client;

import java.util.Random;

import minefantasy.api.anvil.ITongs;
import minefantasy.block.tileentity.TileEntityAnvil;
import minefantasy.item.ItemListMF;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
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
public class TileEntityAnvilRenderer extends TileEntitySpecialRenderer {

	private int metadata;
    public TileEntityAnvilRenderer() {
        model = new ModelAnvil();
    }
    public TileEntityAnvilRenderer(int meta, TileEntityRenderer render) {
        model = new ModelAnvil();
        this.setTileEntityRenderer(render);
        metadata = meta;
    }

    public void renderAModelAt(TileEntityAnvil tile, double d, double d1, double d2, float f) 
    {
    	for(int a = 0; a < 2; a ++)
    	{
    		if(shouldRender(tile, a))
    		{
    			this.renderAModelAt(tile, d, d1, d2, f, a);
    		}
    	}
    }
    
	public void renderAModelAt(TileEntityAnvil tile, double d, double d1, double d2, float f, int renderPass) 
    {
        if (tile != null);
        int i = 0;
        if (tile.worldObj != null) {
        	metadata = tile.getBlockMetadata();
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

        if(renderPass == 1)
        {
        	String s = tile.isBig() ? "big" : "sml";
        	bindTextureByName(data_minefantasy.image("/item/anvilGrid_" + s + ".png")); //texture
        }
        else
        {
        	bindTextureByName(data_minefantasy.image("/item/" + getTexture(metadata) + ".png")); //texture
        }
        
        GL11.glPushMatrix(); //start
        float scale = 1.0F;
        float yOffset = 0F;
        if(!tile.isBig())
        {
        	scale = 0.75F;
        	yOffset = 0.5F + (1F / 16F * 2);
        }
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + (0.3F*scale)+yOffset, (float) d2 + 0.5F); //size
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F); //rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); //if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        if(!tile.isBig())
        {
        	GL11.glTranslatef(0F, 0.55F, 0F);
        }
        model.renderModel(tile.isBig(), 0.0625F); 
        if(renderPass == 0)
        {
        	renderItems(tile);
        }
        
        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        if(renderPass == 0 && !tile.isBig())
        {
        	float scale2 = 1.0F;
        	GL11.glScalef(scale2, scale2*0.55F, scale2);
        	GL11.glTranslatef(0F, 17*0.0625F, 0F);
        	bindTextureByName(data_minefantasy.image("/item/anvilBase.png")); //texture
        	model.renderLog(0.0625F);
        }
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix(); //end

    }
    private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
	private String getTexture(int meta) {
		switch(meta)
		{
			case 0:
				return "anvil_stone";
			case 1:
				return "anvil_bronze";
			case 2:
				return "anvil_bronze";
			case 3:
				return "anvil_iron";
			case 4:
				return "anvil_iron";
			case 5:
				return "anvil_steel";
			case 6:
				return "anvil_steel";
			case 7:
				return "anvil_deep";
			case 8:
				return "anvil_deep";
		}
		return "anvil_iron";
	}
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityAnvil) tileentity, d, d1, d2, f); //where to render
    }
	
	private void renderItems(TileEntityAnvil tile) 
	{
		GL11.glRotatef(180, 0F, 1F, 0F);
		int mX = tile.gridSize()[0];
		int mY = tile.gridSize()[1];
		double xStart = (tile.isBig() ? -4.5F : -4.0F)*0.0625;
		double yStart = (2.5F)*0.0625;
		float scaleBase = 0.5F;
		
		double xGap = ((0.0625*5)*scaleBase) * 4 / mX;
		double yGap = -((0.0625*3)*scaleBase) * 4 / mY;
		double yPos = -0.5D;
		float scale = scaleBase * 4F / (float)Math.max(mX, mY);
		for(int x = 0; x < mX; x ++)
		{
			for(int y = 0; y < mY; y ++)
			{
				GL11.glPushMatrix();
				GL11.glTranslated(xStart + (xGap)*x, yPos, yStart + (yGap)*y);
				renderItem(tile.getStackInSlot(y*mX + x), scale);
				GL11.glPopMatrix();
			}
		}
		if(tile.getStackInSlot(tile.getGridSize()) != null)
		{
			boolean move = tile.canCraft() && tile.canFitResult();
			renderBigItem(tile, move, tile.getStackInSlot(tile.getGridSize()), 0D, 1D, 0D);
		}
	}
	
	
    private ModelAnvil model;
    private Random random = new Random();
    
    private void renderItem(ItemStack itemstack, float scale)
    {
    	GL11.glPushMatrix();
    	GL11.glRotatef(-90, 1, 0, 0);
    	GL11.glScalef((0.3F)*scale, (0.3F)*scale, (0.3F)*scale);
    	Minecraft mc = Minecraft.getMinecraft();
    	if(itemstack != null && itemstack.getItem() != null)
		{
    		if(itemstack.getItem() instanceof ItemBlock)
			{
				GL11.glTranslatef(0.0F, -0.1F, 0.0F);
		    	GL11.glRotatef(-180, 1, 0, 0);
		    	
		    	try
		    	{
			    	Block bl = Block.blocksList[itemstack.itemID];
			    	if(bl != null && bl.getRenderType() == cfg.renderId)
			    	{
			    		GL11.glTranslatef(-0.25F, -0F, -0.25F);
			    	}
		    	}catch(Exception e){}
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
    
    
    
    
    private void renderBigItem(TileEntityAnvil tile, boolean move, ItemStack itemstack, double d, double d1, double d2)
    {
    	GL11.glPushMatrix();
    	GL11.glTranslatef(0F, -0.7F, 0F);
    	GL11.glScalef(0.75F, 0.75F, 0.75F);
    	if(move)
    	{
    		GL11.glTranslatef(0F, 0.5F, -0.7F);
    		GL11.glRotatef(65, 1, 0, 0);
    	}
		Minecraft mc = Minecraft.getMinecraft();
		if(itemstack != null && itemstack.getItem() != null)
		{
			if(tile.isItem3D(itemstack))
			{
				GL11.glRotatef(tile.getItemRotate(itemstack), 0, 1, 0);
			}
			if(!(itemstack.getItem() instanceof ItemBlock))
			{
				GL11.glTranslatef(0.0F, 0.3F, 0.0F);
		    	GL11.glRotatef(-90, 1, 0, 0);
			}
			else
			{
				GL11.glRotatef(180, 1, 0, 0);
		    	try
		    	{
			    	Block bl = Block.blocksList[itemstack.itemID];
			    	if(bl != null && bl.getRenderType() == cfg.renderId)
			    	{
			    		GL11.glTranslatef(-0.25F, -0.4F, -0.25F);
			    	}
		    	}catch(Exception e){}
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
    
    private boolean shouldRender(TileEntityAnvil tile, int p) 
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	EntityPlayer sp = mc.thePlayer;
    	if(p == 1)//GRID
    	{
    		return tile.worldObj != null && (tile.gridTime > 0 || sp.getHeldItem() != null && sp.getHeldItem().getItem() instanceof ITongs);
    	}
    	return true;
	}
}