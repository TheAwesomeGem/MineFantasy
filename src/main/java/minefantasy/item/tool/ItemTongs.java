package minefantasy.item.tool;

import java.awt.Color;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.MineFantasyBase;
import minefantasy.api.MineFantasyAPI;
import minefantasy.api.anvil.ITongs;
import minefantasy.api.forge.HeatableItem;
import minefantasy.api.forge.IHotItem;
import minefantasy.api.forge.TongsHelper;
import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.cfg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemTongs extends Item implements ITongs, IWeaponSpecial{

	private EnumToolMaterial material;
	public ItemTongs(int id, EnumToolMaterial mat) 
	{
		super(id);
		material = mat;
		this.setFull3D();
		setMaxDamage(mat.getMaxUses());
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean fullInfo)
	{
		super.addInformation(stack, player, list, fullInfo);
		
		ItemStack held = TongsHelper.getHeldItem(stack);
		if(held != null)
		{
			list.add("");
			list.add(held.getItem().getItemDisplayName(held));
			held.getItem().addInformation(held, player, list, fullInfo);
		}
	}
	
	public Icon getIcon(ItemStack stack, int renderPass)
    {
		ItemStack item = TongsHelper.getHeldItem(stack);
		
		if(renderPass == 0 && item != null)
		{
			return item.getItem().getIcon(item, renderPass);
		}
        return itemIcon;
    }
	
	
	
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null)
        {
            return item;
        }
        else
        {
            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k))
                {
                    return item;
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item))
                {
                    return item;
                }

                if (isWaterSource(world, i, j, k) && TongsHelper.getHeldItem(item) != null)
                {
                	ItemStack drop = TongsHelper.getHeldItem(item).copy();
                	if(TongsHelper.isCoolableItem(drop))
                	{
                		drop = TongsHelper.getHotItem(drop);
                		
                		player.playSound("random.splash", 1.0F, 1.0F);
                    	player.playSound("random.fizz", 2.0F, 0.5F);
                    	
                    	for(int a = 0; a < 5 ; a ++)
            	        {
            	        	world.spawnParticle("largesmoke", i+0.5F, j+1, k+0.5F, 0, 0.065F, 0);
            	        }
                	}
                	drop.stackSize = item.stackSize;
                	if(drop != null)
                	{
                		player.dropPlayerItem(drop);
                	}
                	
                	return TongsHelper.clearHeldItem(item, player);
                }
            }

            return item;
        }
    }

	private boolean isWaterSource(World world, int i, int j, int k) 
	{
		if(world.getBlockMaterial(i, j, k) == Material.water)
		{
			return true;
		}
		if(isCauldron(world, i, j, k))
		{
			return true;
		}
		return false;
	}
	public boolean isCauldron(World world, int x, int y, int z)
    {
    	return world.getBlockId(x, y, z) == Block.cauldron.blockID && world.getBlockMetadata(x, y, z)>0;
    }
	
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
	
	@Override
	public int getColorFromItemStack(ItemStack item, int renderPass)
    {
		if(renderPass == 1)
		{
			return Color.WHITE.getRGB();
		}
		
		ItemStack held = TongsHelper.getHeldItem(item);
    	if(held != null)
    	{
    		return held.getItem().getColorFromItemStack(held, 0);
    	}
        
        return Color.WHITE.getRGB();
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
	
	public EnumToolMaterial getMaterial() 
	{
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
