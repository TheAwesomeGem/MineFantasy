package minefantasy.client;

import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.*;
import minefantasy.system.cfg;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRendererMF implements ISimpleBlockRenderingHandler
{
	public static int renderId = cfg.renderId;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		TileEntityRenderer rend = TileEntityRenderer.instance;
		if(TileEntityRenderer.instance == null)
			return;
		if(TileEntityRenderer.instance.renderEngine == null)
			return;
		if (block == BlockListMF.lantern) {
            new TileEntityLanternRenderer(rend).renderTileEntityAt(new TileEntityLantern(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.anvil) {
            new TileEntityAnvilRenderer(metadata, rend).renderTileEntityAt(new TileEntityAnvil(metadata), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.forge) {
            new TileEntityForgeRenderer(rend).renderTileEntityAt(new TileEntityForge(metadata), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.smelter) {
            new TileEntitySmelterRenderer(rend).renderTileEntityAt(new TileEntitySmelter(metadata), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.tanner) {
            new TileEntityTanningRackRenderer(rend).renderTileEntityAt(new TileEntityTanningRack(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.weaponRack) {
            new TileEntityWeaponRackRenderer(rend).renderTileEntityAt(new TileEntityWeaponRack(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.roast) {
            new TileEntityRoastRenderer(rend).renderTileEntityAt(new TileEntityRoast(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.bellows) {
            new TileEntityBellowsRenderer(rend).renderTileEntityAt(new TileEntityBellows(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.tripHammer) {
            new TileEntityTripHammerRenderer(rend).renderTileEntityAt(new TileEntityTripHammer(metadata), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.furnace) {
            new TileEntityFurnaceRendererMF(rend).renderTileEntityAt(new TileEntityFurnaceMF(metadata), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		
		if (block == BlockListMF.foodPrep) {
            new TileEntityFoodPrepRenderer(rend).renderTileEntityAt(new TileEntityPrepBlock(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.dogbowl) {
            new TileEntityDogbowlRenderer(metadata, rend).renderTileEntityAt(new TileEntityDogBowl(metadata), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.tailor) {
            new TileEntityTailorRenderer(rend).renderTileEntityAt(new TileEntityTailor(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.spinningWheel) {
            new TileEntitySpinningWheelRenderer(rend).renderTileEntityAt(new TileEntitySpinningWheel(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.firepit) {
            new TileEntityFirepitRenderer(rend).renderTileEntityAt(new TileEntityFirepit(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
		if (block == BlockListMF.oven) {
            new TileEntityOvenRenderer(rend).renderTileEntityAt(new TileEntityOven(metadata), 0.0D, 0.0D, 0.0D, 0.0F);
        }
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderId;
	}
}