package minefantasy.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBandage extends Item
{
	private String[] types = new String[]{"crude", "basic", "tough"};
    private Icon[] icons;

	public ItemBandage(int i) 
	{
        super(i);
        setMaxStackSize(16);
        setCreativeTab(CreativeTabs.tabTools);
        setHasSubtypes(true);
        setMaxDamage(0);
    }
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < types.length ; ++n)
        {
        	ItemStack item = new ItemStack(id, 1, n);
            list.add(item);
        }
    }

    @Override
    public Icon getIconFromDamage(int id)
    {
        return icons[id];

    }
    @Override
    public String getItemDisplayName(ItemStack item)
    {
    	int i = item.getItemDamage();
    	return StatCollector.translateToLocal("item.bandage."+ types[i] +".name");
    }
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
    	return heal(player, player, item);
    }
    
    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
    {
        if(player.isSneaking())
        {
        	heal(player, entity, itemstack);
        	return true;
        }
        return super.itemInteractionForEntity(itemstack, player, entity);
    }
	private ItemStack heal(EntityPlayer player, EntityLivingBase toHeal, ItemStack item) 
	{
		if(player.worldObj.isRemote)
		{
			return item;
		}
		
		if(toHeal != null)
        {
        	if(toHeal.getHealth() <= (toHeal.getMaxHealth()-1F) && toHeal.getActivePotionEffect(Potion.regeneration) == null && !toHeal.isBurning())
        	{
        		toHeal.worldObj.playSoundEffect(toHeal.posX, toHeal.posY, toHeal.posZ, "dig.cloth", 1.0F, 0.5F);
        		player.swingItem();
        		
        		if(!player.worldObj.isRemote)
        		{
		        	toHeal.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, item.getItemDamage(), true));
		        	
		        	if(!player.capabilities.isCreativeMode)
		        	{
		        		item.stackSize --;
		        		if(item.stackSize <= 0)
		        		{
		        			
		        		}
		        	}
        		}
        	}
        }
		 return item;
	}
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
		icons = new Icon[types.length];
        for (int i = 0; i < types.length; ++i)
        {
            this.icons[i] = reg.registerIcon("minefantasy:Misc/bandage_" + i);
        }
    }
}
