package minefantasy.item.tool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import minefantasy.api.anvil.ICustomRepair;
import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityRoad;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemMattock extends ItemToolMF implements IPublicMaterialItem, IWeaponSpecial, ICustomRepair
{
    private EnumToolMaterial toolMaterial;
    
    public float efficiencyOnProperMaterial = 2.0F;
    
	public ItemMattock(int id, EnumToolMaterial material)
    {
        super(id);
        toolMaterial = material;
        setMaxDamage(material.getMaxUses()*2);
        this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial()*0.5F;
        MinecraftForge.setToolClass(this,   "pickaxe", material.getHarvestLevel());
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
            int var11m = world.getBlockMetadata(x, y, z);
            int var12 = world.getBlockId(x, y + 1, z);

            if (facing == 0 || var12 != 0)
            {
                return false;
            }
            else if(var11 == Block.grass.blockID || var11 == Block.dirt.blockID || var11 == Block.sand.blockID)
            {
                Block var13 = BlockListMF.road;
                int m = 0;
                
                if(var11 == Block.sand.blockID)
                {
                	m = 1;
                }
                if(var11 == Block.grass.blockID)
                {
                	var11 = Block.dirt.blockID;
                }
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);

                if (world.isRemote)
                {
                    return true;
                }
                else
                {
                    world.setBlock(x, y, z, var13.blockID, m, 2);
                    
                    TileEntityRoad road = new TileEntityRoad();
                    road.setWorldObj(world);
                    world.setBlockTileEntity(x, y, z, road);
                    road.setSurface(var11, var11m);
                    hoe.damageItem(1, player);
                    return true;
                }
            }
            else
            {
            	return false;
            }
        }
    }
    @Override
    public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase entityHolder)
    {
        item.damageItem(4, entityHolder);
        return true;
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
        item.damageItem(1, user);
        return super.onBlockDestroyed(item, world, id, x, y, z, user);
    }
    
    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack)
    {
    	return ForgeHooks.canToolHarvestBlock(block, 0, stack) || Item.shovelIron.canHarvestBlock(block, stack);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    @Override
    public float getStrVsBlock(ItemStack item, Block block, int metadata)
    {
    	if(canHarvestBlock(block, item))
    	{
    		return this.efficiencyOnProperMaterial;
    	}
    	return super.getStrVsBlock(item, block, metadata);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return toolMaterial.getEnchantability();
    }
    
    public EnumToolMaterial getMaterial()
    {
        return this.toolMaterial;
    }


	@Override
	public float getRepairValue() 
	{
		return 2.0F;
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
