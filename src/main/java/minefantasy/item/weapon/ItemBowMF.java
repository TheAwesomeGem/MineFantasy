package minefantasy.item.weapon;

import java.text.DecimalFormat;
import java.util.List;

import minefantasy.api.aesthetic.IWeaponrackHangable;
import minefantasy.api.arrow.Arrows;
import minefantasy.api.arrow.ISpecialBow;
import minefantasy.entity.EntityArrowMF;
import minefantasy.item.ArrowType;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBowMF extends ItemBow implements ISpecialBow, IWeaponrackHangable
{
	public static final DecimalFormat decimal_format = new DecimalFormat("#.##");
	public Icon[] iconArray = new Icon[3];
	private final EnumBowType model;
	private EnumToolMaterial material = EnumToolMaterial.WOOD;
	
	public ItemBowMF(int id, EnumToolMaterial mat, EnumBowType type)
    {
		this(id, (int)((float)mat.getMaxUses()*type.durability), type);
		material = mat;
    }
    public ItemBowMF(int id, int dura, EnumBowType type)
    {
        super(id);
        model = type;
        this.maxStackSize = 1;
        this.setMaxDamage(dura);
        setCreativeTab(ItemListMF.tabArcher);
    }
    @SideOnly(Side.CLIENT)

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }
    
    public int getId()
    {
    	if(this == ItemListMF.bowIronbark)
    		return 1;
    	if(this == ItemListMF.bowEbony)
    		return 2;
    	if(this == ItemListMF.longbow)
    		return 3;
    	
    	return 0;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int time)
    {
        int power = (this.getMaxItemUseDuration(item) - time);
        power *= model.speed; // Speeds up the power in relation to ticks used
        
        power = (int)((float)power / 20F * getMaxPower());//scales the power down from full
        
        if(power > getMaxPower())power = (int)getMaxPower();//limits the power to max
        
        ArrowLooseEvent event = new ArrowLooseEvent(player, item, power);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        power = event.charge;
        
        boolean var5 = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;

        if (var5 || player.inventory.hasItem(Item.arrow.itemID))
        {
            float var7 = (float)power / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if ((double)var7 < 0.1D)
            {
                return;
            }

            if (var7 > 1.0F)
            {
                var7 = 1.0F;
            }

            EntityArrow var8 = new EntityArrow(world, player, var7 * 2.0F);

            if (var7 == 1.0F)
            {
                var8.setIsCritical(true);
            }

            int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);

            if (var9 > 0)
            {
                var8.setDamage(var8.getDamage() + (double)var9 * 0.5D + 0.5D);
            }

            int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);

            if (var10 > 0)
            {
                var8.setKnockbackStrength(var10);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0)
            {
                var8.setFire(100);
            }

            item.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

            if (var5)
            {
                var8.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Item.arrow.itemID);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(var8);
            }
        }
    }

    /**
     * Gets the power of the bow
     * 20 is the power of V bows(max)
     */
    private float getMaxPower() 
    {
    	return 20F * model.power;
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
    
    public void addInformation(ItemStack item, EntityPlayer player, List desc, boolean flag) 
    {
		super.addInformation(item, player, desc, flag);
		
		float power = this.model.power;
		desc.add(EnumChatFormatting.BLUE+
		StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 0,
        new Object[] {decimal_format.format(power),
        StatCollector.translateToLocal("attribute.arrow.force")}));;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        ArrowNockEvent event = new ArrowNockEvent(player, item);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }
        
        if (player.capabilities.isCreativeMode || player.inventory.hasItem(Item.arrow.itemID))
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
        return 1;
    }
    public Icon getIconIndex(ItemStack stack, int useRemaining)
    {
    	float multiplier = 1.0F / model.speed; //Reverses the decimal (eg. 0.5 becomes 2.0)
        if (stack != null)
        {
            if (useRemaining >= 18*multiplier) return iconArray[2];//The return values are
            if (useRemaining >  13*multiplier) return iconArray[1];//the icon indexes (in the /Tutorial/Items.png file)
            if (useRemaining >   0) return iconArray[0];
        }
        return this.getIconFromDamage(0);
    }
    
    @Override
    public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
    {
    	super.onUpdate(item, world, entity, i, b);
    	if(!item.hasTagCompound())
    		item.setTagCompound(new NBTTagCompound());
    	item.stackTagCompound.setInteger("Use", i);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
    	this.itemIcon = reg.registerIcon(this.getIconString());
        for (int i = 0; i < 3; ++i)
        {
            this.iconArray[i] = reg.registerIcon(this.getIconString() + "_" + (i+1));
        }
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * used to cycle through icons based on their used duration, i.e. for the bow
     */
    public Icon getItemIconForUseDuration(int use)
    {
        return this.iconArray[use];
    }
    @Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Archery/"+name);
		return super.setUnlocalizedName(name);
    }
	public int getDrawAmount(int timer) 
	{
		float multiplier = 1.0F / model.speed; //Reverses the decimal (eg. 0.5 becomes 2.0)
		if (timer >= 18 * multiplier)
            return 2;
        else if (timer > 13 * multiplier)
        	return 1;
        else if (timer > 0)
        	return 0;
		
		return -2;
	}
	public ItemStack getArrow(ItemStack item) 
	{
		ItemStack arrow = Arrows.getLoadedArrow(item);
		if(arrow == null)
		{
			return new ItemStack(Item.arrow);
		}
		return arrow;
	}
	
	/**
	 * Adds an arrow to an item for rendering arrow = null when taking it off
	 * @param bow the bow used
	 * @param arrow the arrow applied
	 */
	public static void loadArrow(EntityPlayer player, ItemStack bow, ItemStack arrow, boolean sendPacket)
	{
		NBTTagCompound nbt = getOrApplyNBT(bow);
		
		if(arrow == null)
		{
			nbt.removeTag("arrowID");
			nbt.removeTag("arrowSub");
		}
		else
		{
			nbt.setInteger("arrowID", arrow.itemID);
			nbt.setInteger("arrowSub", arrow.getItemDamage());
			
			if(sendPacket)
			{
				try
				{
					Packet packet = PacketManagerMF.getArrowItemPacket(player, arrow.itemID, arrow.getItemDamage());
					
					PacketDispatcher.sendPacketToAllAround(player.posX, player.posY, player.posZ, 16D, player.dimension, packet);
				}
				catch(Exception e)
				{
					System.err.println("MineFantasy: Failed to send arrow render packet to bow");
				}
			}
		}
	}
	
	private static NBTTagCompound getOrApplyNBT(ItemStack item)
	{
		if(!item.hasTagCompound())
		{
			item.setTagCompound(new NBTTagCompound());
		}
		return item.getTagCompound();
	}
	
	
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		if(tabs != ItemListMF.tabArcher)
		{
			super.getSubItems(id, tabs, list);
			return;
		}
		if(id != ItemListMF.shortbow.itemID)
			return;
		
		add(list, ItemListMF.shortbow);
		
		add(list, ItemListMF.bowBronze);
		add(list, ItemListMF.bowIron);
		add(list, ItemListMF.bowOrnate);
		add(list, ItemListMF.bowSteel);
		add(list, ItemListMF.bowDragon);
		add(list, ItemListMF.bowDeepIron);
		add(list, ItemListMF.bowMithril);
		
		add(list, ItemListMF.bowComposite);
		add(list, ItemListMF.bowIronbark);
		add(list, ItemListMF.bowEbony);
		
		add(list, ItemListMF.longbow);
		
		ItemListMF.arrowMF.getSubItems(ItemListMF.arrowMF.itemID, tabs, list);
		ItemListMF.boltMF.getSubItems(ItemListMF.arrowMF.itemID, tabs, list);
    }
	private void add(List list, Item item) {
		list.add(new ItemStack(item));
	}
	
	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		if (getMaterial() == ToolMaterialMedieval.EBONY)
		{
			return rarity(itemStack, 1);
		}
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
	private EnumToolMaterial getMaterial() {
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
	public Entity modifyArrow(Entity arrow) 
	{
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE)
		{
			arrow.setFire(60);
		}
		return arrow;
	}
	@Override
	public boolean canUseRenderer(ItemStack item) 
	{
		return true;
	}
}
