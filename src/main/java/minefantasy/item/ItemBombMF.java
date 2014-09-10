package minefantasy.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.entity.EntityBombThrown;
import minefantasy.system.data_minefantasy;
import mods.battlegear2.api.IOffhandDual;
import mods.battlegear2.api.PlayerEventChild.OffhandAttackEvent;
import mods.battlegear2.api.weapons.IBattlegearWeapon;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemBombMF extends ItemMedieval implements IBattlegearWeapon, IOffhandDual
{
	/**
	 * 0 = shrapnel
	 * 1 = fire
	 * 2 = poison
	 * 3 = concussion
	 */
	public static Icon[] icons = new Icon[4];
	public ItemBombMF(int i)
	{
		this(i, true);
	}
    public ItemBombMF(int i, boolean tab) {
        super(i, false, 16);
        setHasSubtypes(true);
        if(tab);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    public String getItemDisplayName(ItemStack item) 
    {
        int type = item.getItemDamage();
        if (type == 0) {
            return StatCollector.translateToLocal("bomb.mf.shrapnel");
        }
        if (type == 1) {
            return StatCollector.translateToLocal("bomb.mf.fire");
        }
        if (type == 2) {
            return StatCollector.translateToLocal("bomb.mf.poison");
        }
        if (type == 3) {
            return StatCollector.translateToLocal("bomb.mf.concussion");
        }
        return StatCollector.translateToLocal("bomb.mf");
    }
    
    @Override
    public Icon getIconFromDamage(int dam)
    {
    	if(dam >= icons.length)
    	{
    		dam = icons.length-1;
    	}
		return icons[dam];
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
    	if(player.isSwingInProgress)
    		return item;
    	
    	player.setItemInUse(item, this.getMaxItemUseDuration(item));
    	world.playSoundAtEntity(player, "random.fuse", 0.2F, 1);
    	return item;
    }
	//THROWS TIMED BOMBS
    @Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int useTime)
    {
        throwTimedItem(item, world, player, useTime);
    }
    private void throwTimedItem(ItemStack item, World world,
			EntityPlayer player, int useTime) {
    	if (!player.capabilities.isCreativeMode)
        {
            --item.stackSize;
        }
    	if(item.stackSize <= 0)
    		player.destroyCurrentEquippedItem();
        

        if(!world.isRemote)
        {
        	int type = item.getItemDamage();
    		world.spawnEntityInWorld(new EntityBombThrown(world, player, (int)useTime, type));
            
        }
        player.swingItem();
	}
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
    	for(int a = 0; a < icons.length; a ++)
    	{
    		list.add(new ItemStack(id, 1, a));
    	}
    }
	public int getMaxItemUseDuration(ItemStack item)
    {
    	return 20;
    }
	@Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player)
    {
    	onPlayerStoppedUsing(item, world, player, 0);
    	return item;
    }
    
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
        this.icons[0] = reg.registerIcon("MineFantasy:Weapon/bomb/bomb_base");
        this.icons[1] = reg.registerIcon("MineFantasy:Weapon/bomb/bomb_fire");
        this.icons[2] = reg.registerIcon("MineFantasy:Weapon/bomb/bomb_poison");
        this.icons[3] = reg.registerIcon("MineFantasy:Weapon/bomb/bomb_concussion");
    }
	@Override
	public boolean sheatheOnBack(ItemStack item) 
	{
		return false;
	}
	@Override
	public boolean allowOffhand(ItemStack mainhand, ItemStack offhand)
	{
		return true;
	}
	@Override
	public boolean isOffhandHandDual(ItemStack off) 
	{
		return true;
	}
	@Override
	public boolean offhandAttackEntity(OffhandAttackEvent event,
			ItemStack mainhandItem, ItemStack offhandItem) 
	{
		return true;
	}
	@Override
	public boolean offhandClickAir(PlayerInteractEvent event,
			ItemStack mainhandItem, ItemStack offhandItem) 
	{
		return true;
	}
	@Override
	public boolean offhandClickBlock(PlayerInteractEvent event,
			ItemStack mainhandItem, ItemStack offhandItem)
	{
		return true;
	}
	@Override
	public void performPassiveEffects(Side effectiveSide,
			ItemStack mainhandItem, ItemStack offhandItem)
	{
		
	}
}
