package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityAnvil;
import minefantasy.block.tileentity.TileEntityForge;
import minefantasy.item.ItemHotItem;
import minefantasy.item.ItemListMF;
import minefantasy.item.tool.ItemTongs;
import minefantasy.api.anvil.IHammer;
import minefantasy.api.anvil.ITongs;
import minefantasy.api.anvil.ItemRepairHammer;
import minefantasy.api.forge.TongsHelper;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockAnvilMF extends BlockContainer 
{

    private Random rand = new Random();

    public BlockAnvilMF(int i, int n, Material m) 
    {
        super(i, m);
        float height = 1.0F / 16F * 13F;
        setBlockBounds(0F, 0F, 0F, 1F, height, 1F);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean isOpaqueCube() 
    {
        return false;
    }
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }

    @Override
    public void addCreativeItems(ArrayList itemList) {
        itemList.add(new ItemStack(this, 1, 0));
        itemList.add(new ItemStack(this, 1, 1));
        itemList.add(new ItemStack(this, 1, 2));
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) 
    {
        TileEntityAnvil tile = (TileEntityAnvil) world.getBlockTileEntity(x, y, z);
        ItemStack item = player.getHeldItem();
        
        if(item == null || tile == null)
        {
        	super.onBlockClicked(world, x, y, z, player);
        	return;
        }
        if(isHammer(item))
        {
        	useHammer(world, x, y, z, tile, player, item, 1);
        }
    }

	@Override
    public TileEntity createNewTileEntity(World w) {
        return new TileEntityAnvil();
    }

	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntityAnvil tile = (TileEntityAnvil) world.getBlockTileEntity(x, y, z);
        tile.direction = dir;
        if(stack.hasDisplayName())
        {
        	tile.setCustomName(stack.getDisplayName());
        }
    }

    /**
     * Called whenever the block is removed.
     */
	@Override
    public void breakBlock(World world, int x, int y, int z, int i1, int i2){
        TileEntityAnvil tile = (TileEntityAnvil) world.getBlockTileEntity(x, y, z);

        if (tile != null) {
            for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6) {
                ItemStack var7 = tile.getStackInSlot(var6);

                if (var7 != null) {
                    float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (var7.stackSize > 0) {
                        int var11 = this.rand.nextInt(21) + 10;

                        if (var11 > var7.stackSize) {
                            var11 = var7.stackSize;
                        }

                        var7.stackSize -= var11;
                        EntityItem var12 = new EntityItem(world, (double) ((float) x + var8), (double) ((float) y + var9), (double) ((float) z + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));

                        if (var7.hasTagCompound()) {
                            var12.getEntityItem().setTagCompound((NBTTagCompound) var7.getTagCompound().copy());
                        }

                        float var13 = 0.05F;
                        var12.motionX = (double) ((float) this.rand.nextGaussian() * var13);
                        var12.motionY = (double) ((float) this.rand.nextGaussian() * var13 + 0.2F);
                        var12.motionZ = (double) ((float) this.rand.nextGaussian() * var13);
                        world.spawnEntityInWorld(var12);
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, i1, i2);
    }

    
    @Override
    public Icon getIcon(int side, int meta)
    {
    	if(meta == 0)//stone
    	{
    		return Block.cobblestone.getIcon(side, meta);
    	}
    	if(meta <= 2)
    	{
    		return BlockListMF.storage.getIcon(side, 3);
    	}
    	if(meta <= 4)
    	{
    		return BlockListMF.storage.getIcon(side, 7);
    	}
    	if(meta <= 6)
    	{
    		return BlockListMF.storage.getIcon(side, 0);
    	}
    	if(meta <= 8)
    	{
    		return BlockListMF.storage.getIcon(side, 8);
    	}
    	
    	
    	return BlockListMF.storage.getIcon(side, 7);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2) 
    {
    	TileEntityAnvil tile = (TileEntityAnvil) world.getBlockTileEntity(x, y, z);
    	 if(tile == null)
         {
         	return super.onBlockActivated(world, x, y, z, player, i, f, f1, f2);
         }
    	 
    	if(world.isRemote)
 		{
    		int slot = tile.getSlotFor(f, f2);
            this.useInventory(world, x, y, z, tile, player, i, slot);
            
 			Packet packet = PacketManagerMF.getPacketIntegerArray(tile, new int[]{1, player.entityId, i, slot});
 			try
 			{
 				PacketDispatcher.sendPacketToServer(packet);
 			} catch(NullPointerException e)
 			{
 				System.out.println("MineFantasy: Client connection lost");
 			}
 		}
        
        return true;
    }
    
	private static boolean isHammer(ItemStack itemstack) 
	{
		return itemstack != null && (itemstack.getItem() instanceof IHammer || itemstack.getItem() instanceof ItemRepairHammer);
	}

	@Override
    public int damageDropped(int meta)
    {
    	return meta;
    }
    private static void damageItem(EntityPlayer player, ItemStack item) 
    {
    	item.damageItem(1, player);
    	
    	if(item.getItemDamage() >= item.getMaxDamage() && item.stackSize <= 1)
    	{
    		player.renderBrokenItemStack(item);
    		player.destroyCurrentEquippedItem();
    	}
	}
    
    private static void useHammer(World world, int x, int y, int z, TileEntityAnvil tile, EntityPlayer player, ItemStack item, int hitStr) 
    {
    	float pitch = 1.0F;
    	float volume = 1.0F;
    	
    	String sound = "";
		if(tile.getAnvilTier() == -1)
		{
			pitch = 0.8F;
		}
		if(hitStr == 0)
		{
			pitch += 0.2F;
			volume = 0.75F;
		}
		
		if(item.getItem() instanceof IHammer)
        {
			float efficiency = (EnchantmentHelper.getEfficiencyModifier(player)/2)+1;
			IHammer hammer = (IHammer)item.getItem();
        	if(EnchantmentHelper.getSilkTouchModifier(player))
        	{
        		if(tile.repair(2.0F*efficiency, true, 1F, false))
        		{
        			world.playSoundEffect(x, y, z, data_minefantasy.sound("repair"), volume, pitch);
        		}
        	}
        	
        	if(tile.hitWithHammer(player, hammer.getForgeLevel(), hammer.getForgeStrength()*efficiency, hitStr))
        	{
        		world.playSoundEffect(x, y, z, data_minefantasy.sound("AnvilSucceed"), volume, pitch);
        	}
        	else
        	{
        		world.playSoundEffect(x, y, z, data_minefantasy.sound("AnvilFail"), volume, pitch);
        	}
        	damageItem(player, item);
        }
		
		if(item.getItem() instanceof ItemRepairHammer)
        {
			ItemRepairHammer hammer = (ItemRepairHammer)item.getItem();
        	if(tile.repair(hammer.effectivness, hammer.canRepairEnchant, hammer.maxRepair, true))
        	{
        		world.playSoundEffect(x, y, z, data_minefantasy.sound("repair"), volume, pitch);
        	}
        	else
        	{
        		world.playSoundEffect(x, y, z, data_minefantasy.sound("AnvilFail"), volume, pitch);
        	}
        	damageItem(player, item);
        }
	}
    
    
    private static boolean onUsedWithTongs(EntityPlayer player, ItemStack tongs, int slot, TileEntityAnvil tile) 
	{
    	
		ItemStack held = TongsHelper.getHeldItem(tongs);
		if(held == null)
		{
			ItemStack out = tile.getStackInSlot(slot);
			if(out != null)
			{
				if(TongsHelper.trySetHeldItem(tongs, out))
				{
					tile.decrStackSize(slot, 1);
					return true;
				}
			}
		}
		else
		{
			if(tile.getStackInSlot(slot) == null)
			{
				ItemStack place = TongsHelper.getHeldItem(tongs);
				
				tile.setInventorySlotContents(slot, place);
				player.setCurrentItemOrArmor(0, TongsHelper.clearHeldItem(tongs, player));
				return true;
			}
		}
		return false;
	}
    
    private static boolean moveItems(EntityPlayer player, ItemStack heldItem, int slot, TileEntityAnvil tile) 
    {
    	ItemStack slotItem = tile.getStackInSlot(slot);
    	
    	if(tile.getResultSlot() != null)
    	{
    		if(tile.forgeTime <= 0 && player.inventory.addItemStackToInventory(tile.getResultSlot().copy()))
			{
				tile.setInventorySlotContents(tile.getGridSize(), null);
				return true;
			}
    	}
    	
    	if(heldItem != null)
    	{
    		if(!isHammer(slotItem) && !(heldItem.getItem() instanceof ItemTongs))
    		{
    			if(tile.worldObj.isRemote)
    			{
    				tile.gridTime = 60;
    				return true;
    			}
    			if(slotItem == null)
    			{
    				ItemStack place = heldItem.copy();
    				place.stackSize = 1;
    				tile.setInventorySlotContents(slot, place);
    				
    				heldItem.stackSize --;
    				
    				if(heldItem.stackSize <= 0)
    				{
    					player.setCurrentItemOrArmor(0, null);
    				}
    				
    				return true;
    			}
    			else
    			{
    				if(slotItem.isItemEqual(heldItem))
    				{
    					if(slotItem.stackSize < slotItem.getMaxStackSize())
    					{
    						slotItem.stackSize ++;
    						
    						heldItem.stackSize --;
    	    				
    	    				if(heldItem.stackSize <= 0)
    	    				{
    	    					player.setCurrentItemOrArmor(0, null);
    	    				}
    	    				
    						return true;
    					}
    				}
    			}
    		}
    	}
    	else
    	{
    		if(slotItem != null)
    		{
    			if(tile.forgeTime <= 0 && player.inventory.addItemStackToInventory(slotItem.copy()))
    			{
    				tile.setInventorySlotContents(slot, null);
    				return true;
    			}
    		}
    	}
		return false;
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
    
    
    
    
    
    
    
    public static boolean useInventory(World world, int x, int y, int z, TileEntityAnvil tile, EntityPlayer player, int i, int slot) 
    {
    	boolean showGUI = true;
        ItemStack itemstack = player.getHeldItem();
        
        if(i == 1)
        {
        	if(isHammer(itemstack))
            {
            	useHammer(world, x, y, z, tile, player, itemstack, 0);
            	showGUI = false;
            }
        	else if(itemstack != null && itemstack.getItem() instanceof ITongs)
        	{
        		ItemStack held = TongsHelper.getHeldItem(itemstack);
        		if(held == null)
        		{
        			ItemStack out = tile.getResultSlot();
        			if(out != null)
        			{
						if(TongsHelper.trySetHeldItem(itemstack, out))
						{
							tile.decrStackSize(tile.getGridSize(), 1);
							tile.onInventoryChanged();
							return true;
						}
        			}
        		}
        		if(slot >= 0 && onUsedWithTongs(player, itemstack, slot, tile))
        		{
        			tile.syncItems();
        			return true;
        		}
        		return false;
        	}
        	else if(slot >= 0 && moveItems(player, itemstack, slot, tile))
    		{
    			tile.syncItems();
    			return true;
    		}
        }
       
        if (i != 1 && showGUI) {
           player.openGui(MineFantasyBase.instance, 0, world, x, y, z);
        }
        return true;
    }
}
