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

public class ItemBloom extends Item implements IHotItem{

	public ItemBloom(int id) {
		super(id);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
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

	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IconRegister reg)
    {
		this.itemIcon = reg.registerIcon("MineFantasy:Misc/bloom");
    }
	
	public static ItemStack createBloom(ItemStack item)
	{
		ItemStack out = new ItemStack(ItemListMF.bloom);
		NBTTagCompound nbt = getNBT(out);
		
		nbt.setInteger("ingotID", item.itemID);
		nbt.setInteger("ingotMeta", item.getItemDamage());
		
		return out;
	}
	
	@Override
    public String getItemDisplayName(ItemStack stack) {
	
		return StatCollector.translateToLocal("item.bloom.name");
	}
	 @Override
	 public EnumRarity getRarity(ItemStack stack) {
		 ItemStack item = getItem(stack);
			if(item != null) return item.getItem().getRarity(item);
			
		return EnumRarity.common;
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
			list.add(item.getItem().getItemDisplayName(item));
			
			item.getItem().addInformation(item, player, list, b);
		}
		else
			super.addInformation(stack, player, list, b);
	}

	@Override
	public boolean isHot(ItemStack item) 
	{
		return true;
	}

	@Override
	public boolean isCoolable(ItemStack item) {
		return false;
	}
}
