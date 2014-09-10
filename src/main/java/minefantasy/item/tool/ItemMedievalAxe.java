package minefantasy.item.tool;
import java.util.List;
import java.util.Random;

import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.IPublicMaterialItem;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemMedievalAxe extends ItemAxe implements IPublicMaterialItem, IWeaponSpecial
{
    private static Block blocksEffectiveAgainst[];

    public ItemMedievalAxe(int id, EnumToolMaterial material)
    {
        super(id, material);
        toolMaterial = material;
        setCreativeTab(ItemListMF.tabTool);
    }
    @Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
    }
    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List desc, boolean flag) {
    	if(item.hasTagCompound())
    	{
    		if(item.getTagCompound().hasKey("Sharp"))
    		{
    			int sharp = item.getTagCompound().getInteger("Sharp");
    			desc.add("Sharpened: " + sharp);
    		}
    	}
        super.addInformation(item, player, desc, flag);
    }
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
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
	public EnumToolMaterial getMaterial() {
		return toolMaterial;
	}
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack item, World world, int id, int x, int y, int z, EntityLivingBase user)
    {
        Random rand = new Random();
        if(world.getBlockMaterial(x, y, z) == Material.wood && getMaterial() == ToolMaterialMedieval.DRAGONFORGE && rand.nextInt(12) == 0)
        {
        	dropItem(world, x, y, z, new ItemStack(Item.coal, 1, 1));
        }
        
        if(getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
        {
        	if(user instanceof EntityPlayer)
        	{
        		((EntityPlayer)user).getFoodStats().addStats(1, 0.2F);
        	}
        }
        
        return super.onBlockDestroyed(item, world, id, x, y, y, user);
    }
	private void dropItem(World world, int x, int y, int z, ItemStack itemStack)
	{
		EntityItem drop = new EntityItem(world, x+0.5D, y+0.5D, z+0.5D, itemStack);
		drop.delayBeforeCanPickup = 10;
		world.spawnEntityInWorld(drop);
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
