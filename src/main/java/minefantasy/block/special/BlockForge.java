package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.api.anvil.IHammer;
import minefantasy.api.anvil.ITongs;
import minefantasy.api.forge.HeatableItem;
import minefantasy.api.forge.ILighter;
import minefantasy.api.forge.TongsHelper;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityForge;
import minefantasy.item.EnumHammerType;
import minefantasy.item.ItemHotItem;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.item.tool.ItemHammer;
import minefantasy.item.tool.ItemTongs;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockForge extends BlockContainer
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private static Random rand = new Random();

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;

    public BlockForge(int id)
    {
        super(id, Material.rock);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        setBlockBounds(0, 0, 0, 1, 0.45F, 1);
    }
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
   
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntityForge tile = (TileEntityForge) world.getBlockTileEntity(x, y, z);
        tile.direction = dir;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        if (meta == 1 || meta == 3)
        {
        	return 10;
        }
    	return super.getLightValue(world, x, y, z);
    }
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }

    @Override
    public Icon getIcon(int side, int meta)
    {
    	return Block.stone.getIcon(side, 0);
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
    	TileEntityForge tile = (TileEntityForge)world.getBlockTileEntity(x, y, z);
        if (tile.isBurning())
        {
        	for(int a = 0; a < rand.nextInt(11); a ++)
        	world.spawnParticle("smoke", (double)x+(rand.nextDouble()*0.8D)+0.1D, (double)y+0.3D, (double)z+(rand.nextDouble()*0.8D)+0.1D, 0.0D, 0.02D, 0.0D);
        	
        	if(rand.nextInt(10) == 0)
        	world.spawnParticle("lava", (double)x+(rand.nextDouble()*0.8D)+0.1D, (double)y+0.3D, (double)z+(rand.nextDouble()*0.8D)+0.1D, 0.0D, 0.02D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f, float f1, float f2)
    {
    	ItemStack held = player.getHeldItem();
    	TileEntityForge tile = (TileEntityForge)world.getBlockTileEntity(x, y, z);
    	
        if(tile == null)
        {
        	return super.onBlockActivated(world, x, y, z, player, side, f, f1, f2);
        }
        tile.findConstruction();
        if(tile.constructed != 1)
        {
        	return super.onBlockActivated(world, x, y, z, player, side, f, f1, f2);
        }
        
        
        if(cfg.lightForge && held != null)//LightForge
        {
        	if(tile.isLit && held.itemID == Block.sand.blockID)
        	{
        		tile.extinguishByHand();
        		return true;
        	}
	        if(tile.fuel > 0 && (held.getItem() instanceof ILighter || held.getItem() instanceof ItemFlintAndSteel))
			{
				boolean fire = false;
				world.playSoundEffect((double)x + 0.5D, (double)y - 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
	        	world.spawnParticle("flame", (double)x + 0.5D, (double)y - 0.5D, (double)z + 0.5D, 0F, 0F, 0F);
	        	
				if(held.getItem() instanceof ILighter)
				{
					ILighter lighter = (ILighter)held.getItem();
					if(lighter.canLight())
					{
		            	if(rand.nextDouble() < lighter.getChance())
		                {
		                	fire = true;
		                }
					}
				}

				if(fire || held.getItem() instanceof ItemFlintAndSteel)
				{
					if(!world.isRemote)
	            	{
	            		tile.setLit(true);
	            		held.damageItem(1, player);
	            	}
				}
				return true;
			}
	        if(!tile.isLit && tile.fuel > 0 && held.getItem() instanceof ItemHammer)
	        {
	        	ItemHammer hammer = (ItemHammer)held.getItem();
	        	if(hammer.getMaterial() == ToolMaterialMedieval.DRAGONFORGE)
	        	{
        			world.playSoundEffect((double)x + 0.5D, (double)y - 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
    	        	world.spawnParticle("flame", (double)x + 0.5D, (double)y - 0.5D, (double)z + 0.5D, 0F, 0F, 0F);
    	        	
    	        	if(!world.isRemote)
	            	{
	            		tile.setLit(true);
	            		held.damageItem(1, player);
	            	}
	        		return true;
	        	}
	        }
        }
        if(world.isRemote)
		{
        	int slot = tile.getSlotFor(f, f2);
        	this.useInventory(world, x, y, z, tile, player, side, slot);
           
			Packet packet = PacketManagerMF.getPacketIntegerArray(tile, new int[]{2, player.entityId, side, slot});
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
	
	private static void tryBurn(EntityPlayer player) 
    {
		if(player.capabilities.isCreativeMode)
		{
			return;
		}
    	if(rand.nextInt(2) == 0)
    	{
    		if(player.getCurrentArmor(2) != null)
    		{
    			if(player.getCurrentArmor(2).itemID == ItemListMF.apronSmithy.itemID)
    			{
    				return;
    			}
    		}
    		player.setFire(1);
    	}
		
	}
	private boolean canHeatItem(ItemStack item) {
    	if(HeatableItem.canHeatItem(item))
    		return true;
    	
		return item.itemID == ItemListMF.hotItem.itemID;
	}
	/**

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity createNewTileEntity(World w)
    {
        return new TileEntityForge();
    }

    @Override
    public int damageDropped(int meta)
    {
    	return (int)Math.floor(meta/2);
    }
    /**
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World world, int x, int y, int z, int i1, int i2)
    {
        if (!keepFurnaceInventory)
        {
        	TileEntityForge tile = (TileEntityForge)world.getBlockTileEntity(x, y, z);

            if (tile != null)
            {
                for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6)
                {
                    ItemStack var7 = tile.getStackInSlot(var6);

                    if (var7 != null)
                    {
                        float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (var7.stackSize > 0)
                        {
                            int var11 = this.rand.nextInt(21) + 10;

                            if (var11 > var7.stackSize)
                            {
                                var11 = var7.stackSize;
                            }

                            var7.stackSize -= var11;
                            EntityItem var12 = new EntityItem(world, (double)((float)x + var8), (double)((float)y + var9), (double)((float)z + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));

                            if (var7.hasTagCompound())
                            {
                            	 var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
                            }

                            float var13 = 0.05F;
                            var12.motionX = (double)((float)this.rand.nextGaussian() * var13);
                            var12.motionY = (double)((float)this.rand.nextGaussian() * var13 + 0.2F);
                            var12.motionZ = (double)((float)this.rand.nextGaussian() * var13);
                            world.spawnEntityInWorld(var12);
                        }
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, i1, i2);
    }
    
    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
    	TileEntityForge tile = (TileEntityForge)world.getBlockTileEntity(x, y, z);

        if (tile != null)
        {
        	if(tile.isBurning())
        	{
        		entity.setFire(10);
        	}
        }
    }

    
    private static boolean onUsedWithTongs(EntityPlayer player, ItemStack tongs, int slot, TileEntityForge tile) 
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
				tile.setInventorySlotContents(slot, TongsHelper.getHeldItem(tongs));
				player.setCurrentItemOrArmor(0, TongsHelper.clearHeldItem(tongs, player));
				return true;
			}
		}
		return false;
	}
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getAABBPool().getAABB((double)x, (double)y, (double)z, (double)(x+1), (double)(y + 0.42D), (double)(z + 1));
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	if(world.isRemote)
    	{
    		return;
    	}
    	
    	TileEntityForge tile = (TileEntityForge)world.getBlockTileEntity(x, y, z);
    	if(entity == null || tile == null)
    	{
    		return;
    	}
    	if(!tile.isBurning())
    	{
    		return;
    	}
    	
    	if(entity instanceof EntityItem)
    	{
    		if(entity.ticksExisted % 25 == 0)
    		{
	    		entity.motionY = 0.20000000298023224D;
	    		entity.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
    		}
    	}
    	else
    	{
    		entity.attackEntityFrom(DamageSource.inFire, 1.0F);
    	}
        
    	entity.setFire(10);
    }
    
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
    
    
    
    public static boolean useInventory(World world, int x, int y, int z, TileEntityForge tile, EntityPlayer player, int i, int slot) 
    {
        if (tile != null)
        {
        	if(tile.isBurning())
        	{
        		tryBurn(player);
        	}
        	ItemStack itemstack = player.getHeldItem();
        	
        	
        	//SPLASH
        	if(itemstack != null && itemstack.itemID == Item.potion.itemID && itemstack.getItemDamage() == 0)
        	{
        		tile.splashWater();
        		if(!player.capabilities.isCreativeMode)
        		player.setCurrentItemOrArmor(0, new ItemStack(Item.glassBottle));
        		return true;
        	}
        	
        	//FUEL
        	if(itemstack != null && tile.fuel < tile.getMaxFuel() && tile.isItemFuel(itemstack))
        	{
        		tile.consumeFuel(itemstack);
        		
        		if(!player.capabilities.isCreativeMode)
        		{
        			itemstack.stackSize --;
        		}
        		
        		return true;
        	}
        	
        	if(i == 1)
        	{
	        	//HEATABLE
	        	if(tile.tryAddItem(slot, itemstack))
	        	{
	        		if(!player.capabilities.isCreativeMode)
	        		{
	        			itemstack.stackSize --;
	        		}
	        		
	        		return true;
	        	}
	        	
	        	//TONGS
	        	if(slot >= 0 && itemstack != null && itemstack.getItem() instanceof ITongs)
	        	{
	        		if(onUsedWithTongs(player, itemstack, slot, tile))
	        		{
	        			tile.syncItems();
	        			return true;
	        		}
	        	}

        	}
        	
            player.openGui(MineFantasyBase.instance, 6, world, x, y, z);
        }

        return true;
    }
    
    @Override
    public float getBlockHardness(World world, int x, int y, int z)
	{
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 4 || meta == 5)return Block.obsidian.blockHardness;//OBSIDIAN
    			
    	return super.getBlockHardness(world, x, y, z);
    }
	
	@Override
    public float getExplosionResistance(Entity explosion, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 4 || meta == 5)return Block.obsidian.blockResistance;//OBSIDIAN
    			
    	return super.getExplosionResistance(explosion, world, x, y, z, explosionX, explosionY, explosionZ);
    }
}
