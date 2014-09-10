package minefantasy.api;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IMFCrafter 
{
	/**
	 * Determines if the metre HUD should appear
	 */
	@SideOnly(Side.CLIENT)
	public boolean shouldRenderCraftMetre();
	
	@SideOnly(Side.CLIENT)
	/**
	 * Gets the width of the progress bar
	 */
	public int getProgressBar(int i);
	
	@SideOnly(Side.CLIENT)
	/**
	 * Gets the name of the crafted Result
	 */
	public String getResultName();
	
	@SideOnly(Side.CLIENT)
	/**
	 * syncs the result
	 */
	public void setTempResult(ItemStack res);
}
