package minefantasy.block;

import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public enum EnumMFDoor {
	IRONBARK(0, true, true, Material.wood),
	STEEL(2, false, false, Material.iron),
	REINFORCED(4, true, false, Material.wood);
	
	private final boolean canHandOpen;
	private final boolean canMobSmash;
	private final Material doorMaterial;
	private Item item;
	private Block block;
	private int texture;
	
	private EnumMFDoor(int tex, boolean open, boolean smash, Material material)
	{
		canHandOpen = open;
		canMobSmash = smash;
		doorMaterial = material;
		texture = tex;
	}
	
	public boolean canBeHandOpened()
	{
		return canHandOpen;
	}
	
	public boolean canMobSmash()
	{
		return canMobSmash;
	}
	
	public Material getMaterial()
	{
		return doorMaterial;
	}

	public int getTexture() {
		return texture;
	}
}
