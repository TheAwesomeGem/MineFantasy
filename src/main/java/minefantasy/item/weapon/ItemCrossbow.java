package minefantasy.item.weapon;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import minefantasy.api.aesthetic.IWeaponrackHangable;
import minefantasy.api.weapon.CrossbowAmmo;
import minefantasy.api.weapon.EnumAmmo;
import minefantasy.entity.EntityArrowMF;
import minefantasy.item.BoltType;
import minefantasy.item.EnumCrossbowType;
import minefantasy.item.ItemListMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrossbow extends ItemBow implements IWeaponrackHangable
{

	public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
	public EnumCrossbowType firearm;
	public EnumAmmo ammoItem;
	public int cap = 1;
	private Icon[] head;
	private Icon stock;

	public ItemCrossbow(int id, EnumAmmo ammo, EnumCrossbowType type) {
		super(id);
		setFull3D();
		setMaxDamage(type.getDurability());
		firearm = type;
		setCreativeTab(ItemListMF.tabArcher);
		setMaxStackSize(1);
		ammoItem = ammo;
		cap = type.getMaxAmmo();
	}
	@SideOnly(Side.CLIENT)
    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int dam)
	{
		return head[0];
	}
	
	/**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
	@Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int time)
    {
    	if(getReloadStage(item) == 2)
    	{
    		getNbt(item).setInteger("Reload", 0);
    		return;
    	}
    	
    	if(player.isSwingInProgress)
    		return;
    	
    	boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
    	if(getReloadStage(item) == 1)
    		
    	if(getReloadStage(item) == 1 && time <= (item.getMaxItemUseDuration() - (this.firearm.getReload()*0.9)))
    	{
    		onEaten(item, world, player);
    		player.swingItem();
    	}
    	if((!player.isSneaking() || getAmmunition(item) == cap) && getReloadStage(item) == 0)
    	{
    		//SHOOT
    		int ammo = getAmmunition(item);
        	if( ammo > 0)
        	{
        		tryFire(item, world, player);
        	}
    	}
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
    	boolean use = false;
    	NBTTagCompound nbt = getNbt(item);
    	int ammo = getAmmunition(item);
    	if(ammo <= 0 || (ammo < cap && player.isSneaking()))
    	{
    		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;

        	if(infinite || hasAmmo(player))
        	{
        		nbt.setInteger("Reload", 1);
        		use = true;
        	}
   		}
    	else
    	{
    		if(ammo > 0 && getReloadStage(item) != 2)
    		{
    			use = true;
    			nbt.setInteger("Reload", 0);
    		}
    	}
    	if(getReloadStage(item) == 2)
    	{
    		player.setItemInUse(item, item.getMaxItemUseDuration());
    	}
    	if(use)
    	player.setItemInUse(item, item.getMaxItemUseDuration());
    	
    	return item;
    }
    
    @Override
    public void onUsingItemTick(ItemStack item, EntityPlayer player, int time)
    {
    	if(getReloadStage(item) == 1 && time == (item.getMaxItemUseDuration() - (this.firearm.getReload())))
    	{
    		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
    		ItemStack load = getLoadedShot(player, EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0);
    		
    		if(load != null)
    		{
	    		if(!player.worldObj.isRemote)
	    		player.swingItem();
	    		int ammo = getAmmunition(item);
	    		addAmmo(item, load);
	    		
	    		if(!player.worldObj.isRemote)
	        		player.worldObj.playSoundAtEntity(player, "random.click", 1, 1);
	    		
	    		player.swingItem();
	    		getNbt(item).setInteger("Reload", 2);
    		}
    	}
    }

	private ItemStack getLoadedShot(EntityPlayer player, boolean infinite) {
		if(ammoItem == EnumAmmo.ARROW)
		{
			return CrossbowAmmo.tryLoadArrow(player, infinite);
		}
		else
		{
			return CrossbowAmmo.tryLoadBolt(player, infinite);
		}
	}
	@Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player)
	{
		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
		ItemStack load = getLoadedShot(player, EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0);
		
		if(load != null)
		{
			NBTTagCompound nbt = getNbt(item);
	    	int ammo = getAmmunition(item);
	    	int reload = nbt.getInteger("Reload");
	    	if(reload == 1)
	    	{
	    		addAmmo(item, load);
	    		
	    		if(!world.isRemote)
	        		player.worldObj.playSoundAtEntity(player, "random.click", 1, 1);
	    		
	    		if(ammo < cap)
	    		{
	    			nbt.setInteger("Reload", 1);
	    			player.setItemInUse(item, item.getMaxItemUseDuration());
	    		}
	    	}
		}
    	return item;
	}
    
	
    @Override
    public int getMaxItemUseDuration(ItemStack item)
    {
    	if(getReloadStage(item) == 0 || getAmmunition(item) >= (cap - 1) || getReloadStage(item) == 2)
    		return 72000;
    	
    	return firearm.getReload();
    }
    
    
    public NBTTagCompound getNbt(ItemStack item)
    {
    	if(!item.hasTagCompound())
    		item.setTagCompound(new NBTTagCompound());
    	
    	return item.getTagCompound();
    }
    
    public int getAmmunition(ItemStack item)
    {
    	NBTTagCompound nbt = getNbt(item);
    	if(!nbt.hasKey("Ammo"))
    		return 0;
    	
    	return nbt.getInteger("Ammo");
    }
    
    public void setAmmunitionCount(ItemStack item, int ammo)
    {
    	NBTTagCompound nbt = getNbt(item);
    	nbt.setInteger("Ammo", ammo);
    }
    
    public void addAmmo(ItemStack weapon, ItemStack ammo)
    {
    	if(ammo == null)return;
   
    	int ammunition = getAmmunition(weapon)+1;
    	setAmmunitionCount(weapon, ammunition);
    	
    	NBTTagCompound nbt = getNbt(weapon);
    	
    	nbt.setInteger("Ammo_" + ammunition, ammo.itemID);
    	nbt.setInteger("AmmoSub_" + ammunition, ammo.getItemDamage());
    }
    
    public void removeAmmo(ItemStack weapon)
    {
    	int ammunition = getAmmunition(weapon);
    	setAmmunitionCount(weapon, ammunition-1);
    	
    	NBTTagCompound nbt = getNbt(weapon);
    	
    	nbt.setInteger("Ammo_" + ammunition, 0);
    	nbt.setInteger("AmmoSub_" + ammunition, 0);
    }
    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List desc, boolean flag) 
    {
    	float dam = firearm.getDamage();
		if(dam > 0)
		{
			desc.add(EnumChatFormatting.BLUE+
			StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 0,
            new Object[] {decimal_format.format(dam),
            StatCollector.translateToLocal("attribute.arrow.force")}));;
		}
		
		
        NBTTagCompound nbt = getNbt(item);
        
        if(getAmmunition(item) <= 0)
        	desc.add(EnumChatFormatting.RED + "Not Loaded");
        else
        {
        	if(cap > 1)
        	desc.add("Ammo: " + getAmmunition(item));
        }
        
        if(item.isItemEnchanted())
        	desc.add("");
        
        super.addInformation(item, player, desc, flag);
    }
    
    public float getReloadSpeed()
    {
    	return 30/firearm.getReload();
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack item)
    {
    	if(getReloadStage(item) == 2)
    		return EnumAction.none;
    	if(getReloadStage(item) == 0)
    		return EnumAction.bow;
        return EnumAction.block;
    }
    
    private int getReloadStage(ItemStack item) {
    	NBTTagCompound nbt = getNbt(item);
    	if(!nbt.hasKey("Reload"))
    		return 0;
    	
    	return nbt.getInteger("Reload");
	}
	private void tryFire(ItemStack item, World world, EntityPlayer player) 
	{ 
    	if(player.isSwingInProgress)
    		return;
    	
    	if(world.isRemote)return;
    	
        boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;

        if (getAmmunition(item) > 0)
        {
        	int ammo = getAmmunition(item);
        	
        	ItemStack load = getNextShot(item, ammo);
        		
            if(!CrossbowAmmo.shoot(item, world, player, firearm.getAccuracy(), firearm.getDamage(), load))
            {
            	return;
            }
            item.damageItem(1, player);
            world.playSoundAtEntity(player, data_minefantasy.sound("Weapon.crossbow"), 4.0F, 1.0F);
            removeAmmo(item);
        }
	}
	
	
	public ItemStack getNextShot(ItemStack item, int ammo) 
	{
		NBTTagCompound nbt = getNbt(item);
    	
    	int id = 0;
    	int sub = 0;
    	if(nbt.hasKey("Ammo_"+ammo))
    	{
    		id = nbt.getInteger("Ammo_" + ammo);
    	}
    	if(nbt.hasKey("AmmoSub_"+ammo))
    	{
    		sub = nbt.getInteger("AmmoSub_" + ammo);
    	}
    	if(id > 0)
    	{
    		return new ItemStack(id, 1, sub);
    	}
    	
    	return null;
	}
	public Icon getHeadItem(ItemStack item)
	{
		return getAmmunition(item) == 0 ? head[0] : head[1];
	}
	public Icon getStockItem(ItemStack item)
	{
		return stock;
	}
	public int getItemEnchantability()
    {
        return firearm.getEnchantment();
    }

	public boolean alterTexture() {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg)
    {
		stock = reg.registerIcon("MineFantasy:Weapon/" + firearm.getName() + "_stock");
		
		head = new Icon[2];
		head[0] = reg.registerIcon("MineFantasy:Weapon/" + firearm.getName() + "_unload");
		head[1] = reg.registerIcon("MineFantasy:Weapon/" + firearm.getName() + "_loaded");
    }
	
	public boolean hasAmmo(EntityPlayer player)
	{
		if(ammoItem == EnumAmmo.ARROW)
		{
			return CrossbowAmmo.hasArrow(player);
		}
		else
		{
			return CrossbowAmmo.hasBolt(player);
		}
	}
	public float headXoffset()
	{
		if(firearm == EnumCrossbowType.HEAVY_CROSSBOW)
			return -0.2F;
		
		return 0.0F;
	}
	public float headYoffset()
	{
		if(firearm == EnumCrossbowType.HEAVY_CROSSBOW)
			return 0.1F;
		
		return 0.0F;
	}
	@Override
	public boolean canUseRenderer(ItemStack item) 
	{
		return true;
	}
	
	/*
	 * EntityArrowMF shot = new EntityArrowMF(world, player, firearm.getAccuracy(), power).bolt();
	            shot.setDamage(firearm.getDamage());
	            int enc_Power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);
	
	            if (enc_Power > 0)
	            {
	                shot.setDamage(shot.getDamage() + (double)enc_Power * 0.5D + 0.5D);
	            }
	
	            int enc_Punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);
	
	            if (enc_Punch > 0)
	            {
	                shot.setKnockbackStrength(enc_Punch);
	            }
	
	            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0)
	            {
	                shot.setFire(100);
	            }
	
	            if (infinite)
	            {
	                shot.canBePickedUp = 2;
	            }
	
	            Random rand = new Random();
	            if (!world.isRemote)
	            {
	                world.spawnEntityInWorld(shot);
	            }
	 */
}
