package minefantasy.item.tool;

import java.util.List;
import java.util.Random;

import minefantasy.api.leatherwork.EnumToolType;
import minefantasy.api.leatherwork.ITanningItem;
import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.item.ToolMaterialMedieval;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class ItemShearsMF extends ItemShears implements ITanningItem, IWeaponSpecial{

	private EnumToolMaterial material;
	private float quality;
	public ItemShearsMF(int id, EnumToolMaterial material) {
		super(id);
		this.setFull3D();
		setMaxDamage(material.getMaxUses());
		quality = material.getEfficiencyOnProperMaterial();
		this.material = material;
	}
	@Override
	public float getQuality() {
		return quality;
	}
	@Override
	public EnumToolType getType() {
		return EnumToolType.CUTTER;
	}
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		
    }
	
	public boolean onBlockDestroyed(ItemStack item, World world, int id, int y, int z, int s, EntityLivingBase user)
    {
		Random rand = new Random();
        if(getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
        {
        	if(user instanceof EntityPlayer)
        	{
        		((EntityPlayer)user).getFoodStats().addStats(1, 0.2F);
        	}
        }
        
        
        if (id != Block.leaves.blockID && id != Block.web.blockID && id != Block.tallGrass.blockID && id != Block.vine.blockID && id != Block.tripWire.blockID && !(Block.blocksList[id] instanceof IShearable))
        {
            return super.onBlockDestroyed(item, world, id, y, z, s, user);
        }
        else
        {
            return true;
        }
    }
	
	@Override
	public boolean canHarvestBlock(Block block)
    {
        return block.blockMaterial == Material.leaves || block.blockID == Block.web.blockID || block.blockID == Block.redstoneWire.blockID || block.blockID == Block.tripWire.blockID;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
	@Override
    public float getStrVsBlock(ItemStack item, Block block, int meta)
    {
		if(block.blockMaterial == Material.leaves || block instanceof IShearable || ((Item.shears.getStrVsBlock(item, block)) > super.getStrVsBlock(item, block, meta)))
    	{
    		if(block.blockMaterial == Material.cloth)
    		{
    			return 5.0F;
    		}
    		return 15.0F;
    	}
		return super.getStrVsBlock(item, block, meta);
    }
	
	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE)
		{
			return rarity(itemStack, 1);
		}
		if (getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			return rarity(itemStack, 2);
		}
		return super.getRarity(itemStack);
	}
	
	public EnumToolMaterial getMaterial() {
		return material;
	}
	private EnumRarity rarity(ItemStack item, int lvl)
	{
		EnumRarity[] rarity = new EnumRarity[]{EnumRarity.common, EnumRarity.uncommon, EnumRarity.rare, EnumRarity.epic};
		if(item.isItemEnchanted())
		{
			if(lvl == 0)
			{
				lvl++;
			}
			lvl ++;
		}
		if(lvl >= rarity.length)
		{
			lvl = rarity.length-1;
		}
		return rarity[lvl];
	}
	@Override
	public void onHit(float damage, ItemStack weapon, EntityLivingBase target, EntityLivingBase attacker) 
	{
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE) 
		{
			target.setFire(20);
		}
		
		if (getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			attacker.heal(1F);
		}
	}
	
}
