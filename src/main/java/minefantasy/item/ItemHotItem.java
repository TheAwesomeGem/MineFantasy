package minefantasy.item;

import java.awt.Color;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.MineFantasyBase;
import minefantasy.api.forge.HeatableItem;
import minefantasy.api.forge.IHotItem;
import minefantasy.system.cfg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemHotItem extends Item implements IHotItem{

	public ItemHotItem(int id) {
		super(id);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	
	
	public static int getTemp(ItemStack item)
	{
		NBTTagCompound tag = getNBT(item);
		
		if(tag.hasKey("MFtemp"))return tag.getInteger("MFtemp");
		
		return 0;
	}
	
	public static ItemStack getItem(ItemStack item)
	{
		NBTTagCompound tag = getNBT(item);
		
		if(tag.hasKey("ingotID") && tag.hasKey("ingotMeta"))
		{
			return new ItemStack(tag.getInteger("ingotID"), 1, tag.getInteger("ingotMeta"));
		}
		
		return null;
	}
	
	public static void setTemp(ItemStack item, int heat)
	{
		NBTTagCompound nbt = getNBT(item);
		
		nbt.setInteger("MFtemp", heat);
	}
	public static ItemStack createHotItem(ItemStack item)
	{
		return createHotItem(item, true);
	}
	public static ItemStack createHotItem(ItemStack item, boolean display)
	{
		ItemStack out = new ItemStack(ItemListMF.hotItem);
		NBTTagCompound nbt = getNBT(out);
		
		nbt.setInteger("ingotID", item.itemID);
		nbt.setInteger("ingotMeta", item.getItemDamage());
		nbt.setBoolean("showMFTemp", display);
		
		return out;
	}
	
	@Override
    public String getItemDisplayName(ItemStack stack) {
	
		String name = "";
		
		ItemStack item = getItem(stack);
		if(item != null) name = item.getItem().getItemDisplayName(item);
		return  StatCollector.translateToLocal("item.hotItem.name") +" " + name;
	}
	 @Override
	 public EnumRarity getRarity(ItemStack stack) {
		 ItemStack item = getItem(stack);
			if(item != null) return item.getItem().getRarity(item);
			
		return EnumRarity.common;
	 }
	 public static boolean showTemp(ItemStack stack)
	    {
	    	if(stack == null)return false;
	    	
	    	NBTTagCompound nbt = getNBT(stack);
	    	
	    	if(nbt == null)return false;
	    	
			if(nbt.hasKey("showMFTemp"))
			{
				return nbt.getBoolean("showMFTemp");
			}
			return false;
	    }
	
	
	private static NBTTagCompound getNBT(ItemStack item)
	{
		if(!item.hasTagCompound())item.setTagCompound(new NBTTagCompound());
		return item.getTagCompound();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
	{
		ItemStack item = getItem(stack);
		
		if(item != null)
		{
			item.getItem().addInformation(item, player, list, b);
		}
		else
			super.addInformation(stack, player, list, b);
		
		NBTTagCompound nbt = getNBT(stack);
		if(nbt.hasKey("showMFTemp"))
		{
			if(nbt.getBoolean("showMFTemp"))
			{
				list.add(getHeatString(stack));
				if(!getWorkString(item, stack).equals(""))
				list.add(getWorkString(item, stack));
			}
		}
	}
	
	private String getHeatString(ItemStack item) {
		int heat = getTemp(item);
		String unit = "*C";
		return heat + unit;
	}
	
	private String getWorkString(ItemStack heated, ItemStack item) {
		byte stage = HeatableItem.getHeatableStage(heated, getTemp(item));
		switch(stage)
		{
		case 0:
			return "";
		case 1:
			return EnumChatFormatting.YELLOW + StatCollector.translateToLocal("state.workable");
		case 2:
			return EnumChatFormatting.RED + StatCollector.translateToLocal("state.unstable");
		}
		return "";
	}



	public Icon getIcon(ItemStack stack, int renderPass)
    {
		ItemStack item = getItem(stack);
		
		if(item != null)
		{
			return item.getItem().getIcon(item, renderPass);
		}
        return Block.fire.getBlockTextureFromSide(0);
    }

	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg)
    {
		
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

                if (isWaterSource(world, i, j, k))
                {
                	player.playSound("random.splash", 1.0F, 1.0F);
                	player.playSound("random.fizz", 2.0F, 0.5F);
                	
                	for(int a = 0; a < 5 ; a ++)
        	        {
        	        	world.spawnParticle("largesmoke", i+0.5F, j+1, k+0.5F, 0, 0.065F, 0);
        	        }
                	
                	ItemStack drop = getItem(item).copy();
                	drop.stackSize = item.stackSize;
                	if(drop != null)
                	{
                    	item.stackSize = 0;

                        if (item.stackSize <= 0)
                        {
                            return drop.copy();
                        }
                	}
                }
            }

            return item;
        }
    }

	private boolean isWaterSource(World world, int i, int j, int k) {
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



	public int getColorFromItemStack(ItemStack item, int renderPass)
    {
		if(renderPass > 1 || !cfg.renderHot)
		{
			return Color.WHITE.getRGB();
		}
    	NBTTagCompound nbt = getNBT(item);
		if(!nbt.hasKey("showMFTemp"))
		{
			return Color.RED.getRGB();
		}
		if(!nbt.getBoolean("showMFTemp"))
		{
			return Color.RED.getRGB();
		}
		int heat = getTemp(item);
		int maxHeat = cfg.maxHeat;
        double heatPer = (double)heat/(double)maxHeat*100D;
        
        int red = getRedOnHeat(heatPer);
        int green = getGreenOnHeat(heatPer);
        int blue = getBlueOnHeat(heatPer);
        
        if(heat > 0)
        {
        	return MineFantasyBase.getColourForRGB(red, green, blue);
        }
        
        return Color.WHITE.getRGB();
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    /**
     * Colour shading
     * Half 1:
     * 255,255,255 - 255,0,0
     * 
     * Half 2:
     * 255,0,0 - 255,255,0
     */
    
    private int getRedOnHeat(double percent)
    {
    	return 255;
    }
    
    private int getGreenOnHeat(double percent)
    {
    	if(percent > 100)percent = 100;
    	if(percent < 0)percent = 0;
    	
    	if(percent <= 55)
    	{
    		return (int)(255 - ((255/55) * percent));
    	}
    	else
    	{
    		return (int)((255/55) * (percent-55));
    	}
    }
    
    private int getBlueOnHeat(double percent)
    {
    	if(percent > 100)percent = 100;
    	if(percent < 0)percent = 0;
    	
    	
    	if(percent <= 55)
    	{
    		return (int)(255 - ((255/55) * percent));
    	}
    	return 0;
    }
    
    
    public boolean isCauldron(World world, int x, int y, int z)
    {
    	return world.getBlockId(x, y, z) == Block.cauldron.blockID && world.getBlockMetadata(x, y, z)>0;
    }



	@Override
	public boolean isHot(ItemStack item)
	{
		return true;
	}



	@Override
	public boolean isCoolable(ItemStack item) {
		return true;
	}
}
