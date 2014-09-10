package minefantasy.block;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.item.ItemListMF;
import minefantasy.system.StatListMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockMedieval extends Block{
    private ItemStack drop = null;
    private int maxDrop = 0;
    private int minDrop = 0;
    private int damDrop = 0;
    
    public BlockMedieval(int i, Material m, int d) 
    {
    	this(i, m, new ItemStack(d, 1, 0));
    }
    public BlockMedieval(int i, Material m, ItemStack droppedItem) 
    {
        super(i, m);
        drop = droppedItem;
        maxDrop = 0;
        minDrop = 0;
        damDrop = 0;
        this.setCreativeTab(CreativeTabs.tabBlock);
        if(m == Material.wood)
        	this.setBurnProperties(i, 4, 16);
        
        if(m == Material.rock)
        {
        	MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 0);
        }
    }
    public BlockMedieval(int i, Material m) {
        this(i, m, i);
    }
    @Override
    public void addCreativeItems(ArrayList itemList) {
        itemList.add(new ItemStack(this));
    }

    @Override
    public int idDropped(int meta, Random rand, int fortune) {
        if(blockID == BlockListMF.oreIgnotumite.blockID)
        {
            return ItemListMF.misc.itemID;
        }
        return drop != null ? drop.itemID : 0;
    }
    
    @Override
    public int damageDropped(int m)
    {
    	if(blockID == BlockListMF.oreIgnotumite.blockID)
    	{
    		return ItemListMF.hunkIgnotumite;
    	}
    	return drop != null ? drop.getItemDamage() : 0;
    }
    
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float f, int side)
    {
        super.dropBlockAsItemWithChance(world, x, y, z, meta, f, side);

        if (this.idDropped(meta, world.rand, side) != this.blockID)
        {
            int var8 = 0;
            this.dropXpOnBlockBreak(world, x, y, z, var8);
        }
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata) {
        if(blockID == BlockListMF.oreIgnotumite.blockID)
        {
            player.addStat(StatListMF.ignotumite, 1);
        }
        if(blockID == BlockListMF.granite.blockID)
        {
            player.addStat(StatListMF.granite, 1);
        }
        if(blockID == BlockListMF.oreMythic.blockID && metadata == 0)
        {
            player.addStat(StatListMF.mithril, 1);
        }
        super.harvestBlock(world, player, x, y, z, metadata);
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        if(world.getBlockId(x, y, z) == BlockListMF.oreIgnotumite.blockID)
        {
            player.addStat(StatListMF.ignotumite, 1);
        }
        super.onBlockClicked(world, x, y, z, player);
    }
    
    @Override
    public Block setUnlocalizedName(String name)
    {
    	this.setTextureName("minefantasy:Basic/" + name);
    	return super.setUnlocalizedName(name);
    }
    
    @Override
    public int quantityDroppedWithBonus(int level, Random rand)
    {
        if (level > 0 && this.blockID != this.idDropped(0, rand, level))
        {
            int j = rand.nextInt(level + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(rand) * (j + 1);
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }
}
