package minefantasy.item;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.system.data_minefantasy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemFoodIngreedient extends ItemFood
{

	private Icon[] icons;

	public ItemFoodIngreedient(int id) {
		super(id, 1, 0.1F, false);
		setHasSubtypes(true);
        setMaxDamage(0);
	}
	
	
	public ItemStack onEaten(ItemStack item, World world, EntityPlayer player)
    {
		int m = item.getItemDamage();
        --item.stackSize;
        player.getFoodStats().addStats(getHealAmount(m), getSaturation(m));
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(item, world, player);
        return item;
    }

    private float getSaturation(int m)
    {
		return (Float) (getStats()[m][2]);
	}


	private int getHealAmount(int m) 
	{
		return (Integer) (getStats()[m][1]);
	}


	protected void onFoodEaten(ItemStack item, World world, EntityPlayer player)
    {
		int m = item.getItemDamage();
        if(isPoison(item.getItemDamage()) && getStats()[m].length >= 11)
        {
			int potionId = (Integer) getStats()[m][7];
			int potionDuration = (Integer) getStats()[m][8];
			int potionAmplifier = (Integer) getStats()[m][9];
			float potionEffectProbability = (Float) getStats()[m][10];
			
			if (!world.isRemote && potionId > 0 && world.rand.nextFloat() < potionEffectProbability)
            {
				player.addPotionEffect(new PotionEffect(potionId, potionDuration * 20, potionAmplifier));
            }
        }
    }

	private boolean isPoison(int m) {
		return (Boolean) (getStats()[m][4]);
	}


	public int getMaxItemUseDuration(ItemStack item)
    {
        return (Integer) getStats()[item.getItemDamage()][6];
    }
	
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < getStats().length ; ++n)
        {
        	ItemStack item = new ItemStack(id, 1, n);
        	if(!getItemDisplayName(item).endsWith("Unused"))
            list.add(item);
        }
    }
	
	@Override
    public Icon getIconFromDamage(int id) {
        return icons[id];
    }
    @Override
    public String getItemDisplayName(ItemStack item) 
    {
        int type = item.getItemDamage();
        
        String n = "ingreedient.mf." + (String)(getStats()[type][0]);
        
        return StatCollector.translateToLocal(n);
    }
	/**
	 * 0: Name, 1: Heal, 2: Saturation, 3: Meat, 4: Poison, 5: Icon, 6: EatTime, 7: Effect, 8: Dura, 9: Potency, 10: Chance
	 */
	private Object[][] getStats()
	{
		return new Object[][]
		{
				/*
			new Object[]{"breadslice", 1, 0.1F, false, false, "breadSlice", 16},//0
				
			new Object[]{"stripsBeef", 1, 0.1F, true, false, "stripsBeef", 16},//1	
			new Object[]{"stripsBeefCooked", 3, 0.3F, true, false, "stripsBeef_cooked", 16},//2	
			
			new Object[]{"bacon", 1, 0.1F, true, false, "baconRaw", 16},//3
			new Object[]{"baconCooked", 3, 0.3F, true, false, "baconCooked", 16},//4	
			
			new Object[]{"chicken", 1, 0.1F, true, true, "filletChicken", 16, Potion.hunger.id, 30, 0, 0.3F},//5	
			new Object[]{"chickenCooked", 2, 0.2F, true, false, "filletChicken_cooked", 16},//6
			
			new Object[]{"sandwitchBeef", 10, 10.0F, false, true, "sandwitch", 40, Potion.regeneration.id, 2, 0, 1.0F},//7
			new Object[]{"sandwitchBacon", 10, 10.0F, false, true, "sandwitch", 40, Potion.regeneration.id, 2, 0, 1.0F},//8
			new Object[]{"sandwitchChicken", 8, 8.0F, false, true, "sandwitch", 40, Potion.regeneration.id, 2, 0, 1.0F},//9
			new Object[]{"sandwitchFish", 6, 6.0F, false, true, "sandwitch", 40, Potion.regeneration.id, 2, 0, 1.0F},//10
			
			new Object[]{"mushroomBrown", 1, 0.1F, false, false, "mushChunks", 16},//11
			new Object[]{"mushroomRed", 1, 0.1F, false, false, "mushChunksRed", 16},//12
			new Object[]{"pumpkin", 2, 0.2F, false, false, "pumpkinSlice", 16},//13
			new Object[]{"fish", 1, 0.1F, false, false, "fishHunkRaw", 16},//14
			new Object[]{"fishCooked", 3, 0.3F, true, false, "fishHunkCooked", 16},//15
			
			new Object[]{"jerkyBeef", 4, 1.0F, false, true, "jerkyBeef", 8, Potion.regeneration.id, 1, 0, 1.0F},//16
			
			new Object[]{"stripsBasilisk", 1, 0.1F, true, true, "stripsBasilisk", 16, Potion.field_76444_x.id, 30, 1, 1.0F},//17
			new Object[]{"stripsBasiliskCooked", 2, 0.75F, true, true, "stripsBasilisk_cooked", 16, Potion.field_76444_x.id, 120, 1, 1.0F},//18
			
			new Object[]{"potato", 1, 0.1F, false, false, "potatoHunk", 16},//19
			new Object[]{"potatoCooked", 2, 0.2F, false, false, "potatoHunkCooked", 16},//20
			
			new Object[]{"drake", 2, 0.2F, true, true, "chunkDrake", 32, Potion.field_76443_y.id, 30, 1, 1.0F},//21
			new Object[]{"drakeCooked", 3, 1.25F, true, true, "chunkDrake_cooked", 32, Potion.field_76443_y.id, 120, 1, 1.0F},//22
			
			new Object[]{"jerkyBasilisk", 5, 1.5F, true, true, "jerkyBasilisk", 8, Potion.regeneration.id, 3, 3, 1.0F},//23
			
			new Object[]{"carrots", 1, 0.1F, false, false, "carrotsChopped", 16},//24
			
			new Object[]{"muttonHunkRaw", 1, 0.1F, true, false, "muttonHunkRaw", 16},//25	
			new Object[]{"muttonHunkCooked", 3, 0.3F, true, false, "muttonHunkCooked", 16},//26	
			new Object[]{"sandwitchMutton", 10, 10.0F, false, true, "sandwitch", 40, Potion.regeneration.id, 2, 0, 1.0F},//27
		*/
		};
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
		int length = getStats().length;
        this.icons = new Icon[length];
        
        for (int i = 0; i < length; ++i)
        {
        	
        	String name = (String)(getStats()[i][5]);
            this.icons[i] = reg.registerIcon("MineFantasy:Food/" + name);
        }
    }
}
