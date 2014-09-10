package minefantasy.block;

import java.util.ArrayList;

import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockMedievalStairs extends BlockStairs{
    
	public Icon hay;
    public BlockMedievalStairs(int i, Block b) {
        super(i, b, 0);
    }
    public BlockMedievalStairs(int i, Block b, int m) {
        super(i, b, m);
    }
    public BlockMedievalStairs(int i, Block b, Material m, int tex) {
        super(i, b, 0);
        if( m == Material.leaves)
        {
        	this.setBurnProperties(i, 30, 60);
        }
        if( m == Material.leaves)
        {
        	this.setBurnProperties(i, 5, 20);
        }
    }
    @Override
    public Icon getIcon(int s, int m)
    {
    	if(this == BlockListMF.hayRoof)
    		return hay;
    	return super.getIcon(s, m);
    }
    @Override
    public void addCreativeItems(ArrayList itemList) {
        itemList.add(new ItemStack(this));
    }

    public void registerIcons(IconRegister reg)
    {
		hay = reg.registerIcon("MineFantasy:Basic/Hay_Roof");
    }
}
