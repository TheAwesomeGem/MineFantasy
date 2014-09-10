package minefantasy.client;

import minefantasy.block.tileentity.TileEntityLantern;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

/**
 * 
 * @author Anonymous Productions
 * 
 *         Sources are provided for educational reasons. though small bits of
 *         code, or methods can be used in your own creations.
 * 
 *         Custom renderers based off render tutorial by MC_DucksAreBest
 */
public class TileEntityLanternRenderer extends TileEntitySpecialRenderer {

	public TileEntityLanternRenderer() {
		model = new ModelLantern();
	}

	public TileEntityLanternRenderer(TileEntityRenderer render) {
		this.setTileEntityRenderer(render);
		model = new ModelLantern();
	}

	public void renderAModelAt(TileEntityLantern tile, double d, double d1,
			double d2, float f) {
		if (tile != null)
			;
		bindTextureByName(data_minefantasy.image("/item/lantern.png")); // texture
		GL11.glPushMatrix(); // start
		GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1F, (float) d2 + 0.5F); // size
		GL11.glScalef(1.0F, -1F, -1F); 
		model.renderModel((TileEntityLantern) tile, 0.0625F); 
		GL11.glPopMatrix(); // end

	}

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1,
			double d2, float f) {
		renderAModelAt((TileEntityLantern) tileentity, d, d1, d2, f);
	}

	private void bindTextureByName(String image) {
		bindTexture(TextureHelperMF.getResource(image));
	}

	private ModelLantern model;
}