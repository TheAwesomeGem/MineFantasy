package minefantasy.entity;

import net.minecraft.item.ItemStack;

public interface IThrownItem {
	ItemStack getRenderItem();
	
	int getSpin();
	int getRotate();
	float getScale();

	boolean isEnchanted();

}
