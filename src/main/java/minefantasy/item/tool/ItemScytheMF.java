package minefantasy.item.tool;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.block.BlockListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.IPublicMaterialItem;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;
/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemScytheMF extends ItemToolMF implements IWeaponSpecial, IPublicMaterialItem
{
    private EnumToolMaterial toolMaterial;
	public ItemScytheMF(int id, EnumToolMaterial material)
    {
        super(id);
        toolMaterial = material;
        setMaxDamage(material.getMaxUses());
        this.damageVsEntity = 3F + material.getDamageVsEntity();
    }
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		
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
    public boolean onItemUse(ItemStack hoe, EntityPlayer player, World world, int x, int y, int z, int facing, float pitch, float yaw, float pan)
    {
        if (!player.canPlayerEdit(x, y, z, facing, hoe))
        {
            return false;
        }
        else
        {
            int var11 = world.getBlockId(x, y, z);
            int var12 = world.getBlockId(x, y + 1, z);

            if(Block.blocksList[var11] != null)
            {
            	Material m = world.getBlockMaterial(x, y, z);
            	float hard = Block.blocksList[var11].getBlockHardness(world, x, y, z);
            	if(this.canCutMaterial(m, hard, false))
            	{
            		if(cutGrass(world, x, y, z, 5, player, false))
            		{
            			hoe.damageItem(1, player);
            			player.swingItem();
            		}
            	}
            	else
            	if(this.canCutMaterial(m, hard, true))
            	{
            		if(cutGrass(world, x, y, z, 3, player, true))
            		{
            			hoe.damageItem(1, player);
            			player.swingItem();
            		}
            	}
            }
        }
        return false;
    }
    public EnumToolMaterial getMaterial()
    {
        return this.toolMaterial;
    }
    
    private boolean cutGrass(World world, int x, int y, int z, int r, EntityPlayer entity, boolean leaf) {
    	boolean flag = false;
		for(int x2 = -r; x2 <= r; x2 ++)
		{
			for(int y2 = -r; y2 <= r; y2 ++)
			{
				for(int z2 = -r; z2 <= r; z2 ++)
				{
					int id = world.getBlockId(x+x2, y+y2, z+z2);
					int meta = world.getBlockMetadata(x+x2, y+y2, z+z2);
					if(Block.blocksList[id] != null)
					{
						Material m = world.getBlockMaterial(x+x2, y+y2, z+z2);
						if(canCutMaterial(m, Block.blocksList[id].getBlockHardness(world, x+x2, y+y2, z+z2), leaf))
						{
							if(getDistance(x+x2, y+y2, z+z2, x, y, z) < r*1)
							{
								Block block = Block.blocksList[id];
								flag = true;
								
								ArrayList<ItemStack> items = block.getBlockDropped(world, x+x2, y+y2, z+z2, meta, 0);
								world.setBlockToAir(x+x2, y+y2, z+z2);
								world.playAuxSFX(2001, x+x2, y+y2, z+z2, Block.grass.blockID);

								if(!entity.capabilities.isCreativeMode)
								{
									for (ItemStack item : items)
						            {
						                if (world.rand.nextFloat() <= 1.0F)
						                {
						                    dropBlockAsItem_do(world, x+x2, y+y2, z+z2, item);
						                }
						            }
								}
							}
						}
					}
				}
			}
		}
		return flag;
	}
    
    private boolean canCutMaterial(Material m, float str, boolean leaf) {
    	if(!leaf)
    	{
	    	if(str <= 0.0F){
				return
				   m == Material.vine
				|| m == Material.plants
				|| m == Material.grass
				;}
	    	else return false;
    	}
    	return m == Material.leaves || m == Material.vine;
	}

	public double getDistance(double x, double y, double z, int posX, int posY, int posZ)
    {
        double var7 = posX - x;
        double var9 = posY - y;
        double var11 = posZ - z;
        return (double)MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
    }
	
	protected void dropBlockAsItem_do(World world, int x, int y, int z, ItemStack drop)
    {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float var6 = 0.7F;
            double var7 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            double var9 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            double var11 = (double)(world.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            EntityItem var13 = new EntityItem(world, (double)x + var7, (double)y + var9, (double)z + var11, drop);
            var13.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(var13);
        }
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
	@Override
	public boolean onBlockDestroyed(ItemStack item, World world, int id, int x, int y, int z, EntityLivingBase user)
    {
        Random rand = new Random();
        if(getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
        {
        	if(user instanceof EntityPlayer)
        	{
        		((EntityPlayer)user).getFoodStats().addStats(1, 0.2F);
        	}
        }
        
        return super.onBlockDestroyed(item, world, id, x, y, z, user);
    }
}
