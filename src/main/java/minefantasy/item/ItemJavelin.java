package minefantasy.item;
import java.util.List;


import minefantasy.api.weapon.*;
import minefantasy.entity.EntityBombThrown;
import minefantasy.entity.EntityThrownSpear;
import minefantasy.system.IPublicMaterialItem;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */

public class ItemJavelin extends Item
{
    private int weaponDamage;
    public boolean isUsing;
    

    public ItemJavelin(int id, int dam)
    {
    	super(id);
    	this.setFull3D();
        maxStackSize = 8;
        setCreativeTab(ItemListMF.tabWeapon);
        
    	weaponDamage = dam;
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
    public int getDamageVsEntity(Entity entity)
    {
        return 2;
    }
    public int getMaxItemUseDuration(ItemStack item)
    {
        return 72000;
    }
    public EnumAction getItemUseAction(ItemStack item)
    {
        return EnumAction.bow;
    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        player.setItemInUse(item, this.getMaxItemUseDuration(item));
        return item;
    }

	public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int time)
    {
		if(item.itemID != itemID)return;
		if(player.isSwingInProgress)return;
		
        int var6 = this.getMaxItemUseDuration(item) - time;
        
        float var7 = (float)var6 / 20.0F;
        var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

        if ((double)var7 < 0.1D)
        {
            return;
        }

        if (var7 > 1.0F)
        {
            var7 = 1.0F;
        }
        
        var7 += 0.5F;
        
        if(!world.isRemote)
        {
        	ItemStack itemspear = item.copy();
        	itemspear.stackSize = 1;
			EntityThrownSpear spear = new EntityThrownSpear(world, player, var7).setSpear(itemspear);
        	world.playSoundAtEntity(player, data_minefantasy.sound("spearThrow"), 1.2F, 0.5F / (itemRand.nextFloat() * 0.5F + 1F));
        	if(player.capabilities.isCreativeMode)
        	{
        		spear.canBePickedUp = 2;
        	}
        	world.spawnEntityInWorld(spear);
        	spear.syncSpear();
        }
        if(!player.capabilities.isCreativeMode)
        {
        	--item.stackSize;
        	if(item.stackSize <= 0)
        		player.destroyCurrentEquippedItem();
        }
            player.swingItem();
            
        
    }

	public int getSpearDamage() {
		return weaponDamage;
	}

	public int getBreakChance() {
		return 40;
	}
	@Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Weapon/"+name);
		return super.setUnlocalizedName(name);
    }
	
}
