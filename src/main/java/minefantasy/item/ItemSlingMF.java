package minefantasy.item;

import minefantasy.entity.EntityArrowMF;
import minefantasy.entity.EntityRockSling;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSlingMF extends ItemBow
{
    public ItemSlingMF(int id, int strength)
    {
        super(id);
        this.maxStackSize = 1;
        this.setMaxDamage(strength);
    }
    @SideOnly(Side.CLIENT)

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int time)
    {
        int var6 = this.getMaxItemUseDuration(item) - time;
        
        boolean var5 = player.capabilities.isCreativeMode;

        if (var5 || player.inventory.hasItemStack(ItemListMF.component(ItemListMF.rock)))
        {
            float var7 = (float)var6 / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if ((double)var7 < 0.1D)
            {
                return;
            }

            if (var7 > 1.0F)
            {
                var7 = 1.0F;
            }//EntityArrowMF

            EntityRockSling rock = new EntityRockSling(world, player, var7*2.0F);

            item.damageItem(1, player);
            world.playSoundAtEntity(player, data_minefantasy.sound("spearThrow"), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

            if(!var5)
            {
                consumePlayerItem(player, ItemListMF.component(ItemListMF.rock));
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(rock);
            }
        }
    }
    
    private boolean consumePlayerItem(EntityPlayer player, ItemStack item) 
    {
    	for(int a = 0; a < player.inventory.getSizeInventory(); a ++)
    	{
    		ItemStack i = player.inventory.getStackInSlot(a);
    		if(i != null && i.isItemEqual(item))
    		{
    			player.inventory.decrStackSize(a, 1);
    			return true;
    		}
    	}
    	return false;
	}
	public ItemStack onFoodEaten(ItemStack item, World world, EntityPlayer player)
    {
        return item;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack item)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack item)
    {
        return EnumAction.bow;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode || player.inventory.hasItemStack(ItemListMF.component(ItemListMF.rock)))
        {
            player.setItemInUse(item, this.getMaxItemUseDuration(item));
        }

        return item;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 0;
    }
    @Override
    public void onUsingItemTick(ItemStack item, EntityPlayer player, int i)
    {
    	super.onUsingItemTick(item, player, i);
	   	if(i % 5 == 0)
	   	{
	  		player.swingItem();
	    }
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
    	this.itemIcon = reg.registerIcon(this.getIconString());
    }
    
    @Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Weapon/"+name);
		return super.setUnlocalizedName(name);
    }
}
