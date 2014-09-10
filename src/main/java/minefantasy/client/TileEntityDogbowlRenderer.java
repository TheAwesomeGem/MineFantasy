package minefantasy.client;

import minefantasy.block.tileentity.TileEntityDogBowl;
import minefantasy.block.tileentity.TileEntityDogBowl;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 * 
 * Custom renderers based off render tutorial by MC_DucksAreBest
 */
public class TileEntityDogbowlRenderer extends TileEntitySpecialRenderer {

	private int metadata;
    public TileEntityDogbowlRenderer() {
        model = new ModelDogbowl();
    }
    public TileEntityDogbowlRenderer(int meta, TileEntityRenderer render) {
        model = new ModelDogbowl();
        this.setTileEntityRenderer(render);
        metadata = meta;
    }

    public void renderAModelAt(TileEntityDogBowl tile, double d, double d1, double d2, float f) {
       
    	if (tile.worldObj != null) {
        	metadata = tile.getBlockMetadata();
        }
    	
    	String tex = tile.getTex(metadata);
        bindTextureByName(data_minefantasy.image("/item/dogbowl" + tex + ".png")); //texture
        GL11.glPushMatrix(); //start
        float yOffset = 0.0625F; //1/16th block
        
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); //size
        GL11.glScalef(1.0F, -1.0F, -1.0F); //if you read this comment out this line and you can see what happens
        model.renderModel(tile.food, tile.getFoodMax(), 0.0625F); //renders  0.0625 is 1/16, (1 block = 1.0F)

        GL11.glPopMatrix(); //end

    }
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityDogBowl) tileentity, d, d1, d2, f); //where to render
    }
	private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
    private ModelDogbowl model;
    
}