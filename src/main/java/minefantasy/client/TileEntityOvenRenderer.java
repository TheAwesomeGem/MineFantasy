package minefantasy.client;

import java.util.Random;

import minefantasy.block.tileentity.TileEntityOven;
import minefantasy.block.tileentity.TileEntityPrepBlock;
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
 *         Sources are provided for educational reasons. though small bits of
 *         code, or methods can be used in your own creations.
 */
public class TileEntityOvenRenderer extends TileEntitySpecialRenderer
{

	private Random random = new Random();
	public TileEntityOvenRenderer() 
	{
		model = new ModelOven();
	}

	public TileEntityOvenRenderer(TileEntityRenderer render) 
	{
		model = new ModelOven();
		this.setTileEntityRenderer(render);
	}

	public void renderAModelAt(TileEntityOven tile, double d, double d1,
			double d2, float f) {
		if (tile != null)
			;
		int i = 1;
		if (tile.worldObj != null) {
			i = tile.direction; // this is for rotation
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

		String type = tile.getTexture();

		bindTextureByName(data_minefantasy.image("/item/" + type + ".png")); // texture

		boolean display = tile.isBurningClient;

		GL11.glPushMatrix(); // start
		float scale = 1.0F;

		float offset = (1.0F - 0.0625F);
		GL11.glTranslatef((float) d + 0.5F, (float) d1 + (4F / 16F), (float) d2 + 0.5F); // size
		GL11.glRotatef(j + 180F, 0.0F, 1.0F, 0.0F);
		
		GL11.glScalef(scale, -scale, -scale);
		model.renderModel(display, 0.0625F);

		float sc = 0.625F;
		float openPixels = 10F/15F*(float) tile.doorAngle;
		float angle = 90F / 15F * (float) tile.doorAngle;
		
		GL11.glPushMatrix();
		//DOOR
		GL11.glTranslatef(pixel(0), pixel(2), -pixel(6));
		GL11.glPushMatrix();
		GL11.glScalef(sc, sc, sc);

		GL11.glRotatef(angle, 1F, 0, 0);
		renderHatch(tile, 0, 0, 12, 8, 128, 64);

		GL11.glPopMatrix();//DOOR END
		//SHELF
		GL11.glTranslatef(pixel(0), -pixel(4.5F), -pixel(openPixels));
		GL11.glPushMatrix();
		GL11.glScalef(sc, sc, sc);

		GL11.glRotatef(90, 1F, 0, 0);
		renderRack(tile, 0, 37, 9, 9, 128, 64);

		GL11.glPopMatrix();//SHELF END
		GL11.glPopMatrix();
		
		GL11.glTranslatef(0, -pixel(3.5F), -pixel(openPixels));
        GL11.glPushMatrix();
        GL11.glRotatef(90, 1, 0, 0);
        renderItem((TileEntityOven) tile, d, d1, d2, f);
        GL11.glPopMatrix();

		GL11.glPopMatrix(); // end

	}
	
	private void bindTextureByName(String image) {
		bindTexture(TextureHelperMF.getResource(image));
	}

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1,
			double d2, float f) {
		renderAModelAt((TileEntityOven) tileentity, d, d1, d2, f); // where to
																	// render
	}

	private void renderRack(TileEntityOven tile, int x, int y, int w, int h,
			int tw, int th) {
		Minecraft mc = Minecraft.getMinecraft();

		float f = 0.01F / (float) tw;
		float f1 = 0.01F / (float) th;

		float x1 = (float) x / (float) tw + f;
		float x2 = (float) (x + w) / tw - f;
		float y1 = (float) y / th + f1;
		float y2 = (float) (y + h) / th - f1;

		Tessellator image = Tessellator.instance;
		float xPos = 0.5F;
		float yPos = 0.0F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-xPos, -yPos, pixel(0.5F));
		float var13 = 1F;
		GL11.glScalef(var13, var13, var13);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-1F, -1F, 0.0F);
		ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, tw, th, 0.0625F);

	}

	private void renderHatch(TileEntityOven tile, int x, int y, int w,
			int h, int tw, int th) {
		Minecraft mc = Minecraft.getMinecraft();

		float f = 0.01F / (float) tw;
		float f1 = 0.01F / (float) th;

		float x1 = (float) x / (float) tw + f;
		float x2 = (float) (x + w) / tw - f;
		float y1 = (float) y / th + f1;
		float y2 = (float) (y + h) / th - f1;

		Tessellator image = Tessellator.instance;
		float xPos = 0.5F;
		float yPos = 1.0F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-xPos, -yPos, pixel(0.5F));
		float var13 = 1F;
		GL11.glScalef(var13, var13, var13);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-1F, -1F, 0.0F);
		ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, tw, th, 0.0625F);

	}

	public float pixel(float count) {
		return count * 0.0625F;
	}
	
	
	private void renderItem(TileEntityOven tile, double d, double d1, double d2, float f) 
	{
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack itemstack = tile.getStackInSlot(2);
		if(itemstack == null)
		{
			itemstack = tile.getStackInSlot(0);
		}
		if(itemstack != null && itemstack.getItem() != null)
		{
			
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
				if(block.getRenderType() == cfg.renderId)
				{
					GL11.glTranslatef(-0.25F, 0.25F, -0.25F);
				}
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
		        Icon index = item.getIconFromDamage(itemstack.getItemDamage());
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
		        GL11.glTranslatef(-0.75F, -0.75F, 0.0F);
		        GL11.glScalef(0.5F, 0.5F, 0.5F);
		        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, index.getIconWidth(), index.getIconHeight(), 0.0625F);
		        
		        GL11.glPushMatrix();
		        GL11.glPopMatrix();
			}
		}
		
	}

	private ModelOven model;
}