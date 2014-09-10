package minefantasy.client;

import minefantasy.block.tileentity.TileEntitySmelter;
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
 */
public class TileEntitySmelterRenderer extends TileEntitySpecialRenderer {

	public TileEntitySmelterRenderer() {
		model = new ModelBloom();
		hearth = new ModelCrucible();
	}

	public TileEntitySmelterRenderer(TileEntityRenderer render) {
		model = new ModelBloom();
		hearth = new ModelCrucible();
		this.setTileEntityRenderer(render);
	}

	public void renderAModelAt(TileEntitySmelter tile, double d, double d1,
			double d2, float f) {
		if (tile != null)
			;
		int i = 0;
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

		String type = "Bloomery";
		if (tile.getTier() == 1)
			type = "crucible";
		if (tile.getTier() == 2)
			type = "crucibleGranite";

		bindTextureByName(data_minefantasy.image("/item/" + type + ".png")); // texture
		if (tile.isBurning() && tile.getTier() == 0)
			bindTextureByName(data_minefantasy.image("/item/" + type
					+ "Active.png")); // texture

		boolean display = tile.hasSmelted();
		if (tile.getTier() >= 1)
			display = tile.isBurning();

		GL11.glPushMatrix(); // start
		float scale = 1.0F;
		if (tile.worldObj != null) {
			scale = 1.0F;
		}

		GL11.glTranslatef((float) d + 0.5F, (float) d1 + scale,
				(float) d2 + 0.5F); // size
		GL11.glRotatef(j + 180, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(scale, -scale, -scale);
		
		if (tile.getTier() == 0)
			model.renderModel(0.0625F, display);

		if (tile.getTier() >= 1) {
			hearth.renderModel(0.0625F, display, tile.willShowBase());
		}
		GL11.glPopMatrix(); // end

	}

	private void bindTextureByName(String image) {
		bindTexture(TextureHelperMF.getResource(image));
	}

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1,
			double d2, float f) {
		renderAModelAt((TileEntitySmelter) tileentity, d, d1, d2, f);
	}

	private ModelBloom model;
	private ModelCrucible hearth;
}