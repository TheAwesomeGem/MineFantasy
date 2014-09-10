package minefantasy.client;

import minefantasy.block.tileentity.TileEntityBellows;
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
public class TileEntityBellowsRenderer extends TileEntitySpecialRenderer {

    public TileEntityBellowsRenderer() {
        model = new ModelBellows();
    }
    public TileEntityBellowsRenderer(TileEntityRenderer render) {
        model = new ModelBellows();
        this.setTileEntityRenderer(render);
    }

    public void renderAModelAt(TileEntityBellows tile, double d, double d1, double d2, float f) {
        if (tile != null);
        int i = 0;
        if (tile.worldObj != null) {
            i = tile.direction; //this is for rotation
        }

        int j = 90 * i;

        if (i == 1) {
            j = 0;
        }

        if (i == 2) {
            j = 270;
        }

        if (i == 3) {
            j = 180;
        }

        if (i == 0) {
            j = 90;
        }
        if (i == 0) {
            j = 90;
        }
        int p = tile.press;
        model.rotate(p);
        
        bindTextureByName(data_minefantasy.image("/item/Bellows.png")); //texture
        GL11.glPushMatrix(); //start
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.525F, (float) d2 + 0.5F); //size
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F); //rotate based on metadata
        GL11.glScalef(1.0F, -1F, -1F); //if you read this comment out this line and you can see what happens
        model.renderModel(p, 0.0625F); //renders and yes 0.0625 is a random number
        GL11.glPopMatrix(); //end

    }
    private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityBellows) tileentity, d, d1, d2, f); //where to render
    }
    private ModelBellows model;
}