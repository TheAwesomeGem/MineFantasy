package minefantasy.item;

import java.util.Random;

import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFoodMF extends ItemFood
{

	private boolean alwaysEdible;
	private PotionEffect effect;
	private int healAmount;
	
	public ItemFoodMF(int id, int heal, float saturation, boolean wolf) 
	{
		super(id, heal, saturation, wolf);
	}
	public ItemFoodMF(int id, int heal, float saturation, boolean wolf, PotionEffect effect) 
	{
		this(id, heal, saturation, wolf);
		this.effect = effect;
	}
	
	public ItemFoodMF(int id, int feed, float saturation, boolean wolf, int heal)
	{
		super(id, feed, saturation, wolf);
		if(heal > 0)
		this.setAlwaysEdible();
		healAmount = heal;
	}
	
	@Override
	public void onFoodEaten(ItemStack item, World world, EntityPlayer player)
    {
		if(healAmount > 0)
		{
			player.heal(healAmount);
		}
		if(effect != null)
		{
			player.addPotionEffect(effect);
		}
		super.onFoodEaten(item, world, player);
    }
	
	@Override
	public void onUsingItemTick(ItemStack item, EntityPlayer player, int count)
	{
		Random rand = player.getRNG();
		int maxUse = item.getMaxItemUseDuration();
		if(count > 2 && count < (maxUse - 30))
		{
			if(rand.nextInt(3) == 0)
				player.playSound("random.eat", 0.5F + rand.nextFloat(), 0.5F + rand.nextFloat());
		}
	}
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Food/"+name);
		return super.setUnlocalizedName(name);
    }
}
