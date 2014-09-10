package minefantasy.block.special;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.api.forge.ILighter;
import minefantasy.block.tileentity.TileEntityFirepit;
import minefantasy.system.cfg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.common.ForgeDirection;

public class BlockFirepit extends BlockContainer
{

	private Random rand = new Random();
	public BlockFirepit(int id)
    {
        super(id, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.5F, 0.8F);
    }
	
	public int idDropped(int id, Random rand, int meta)
    {
		return 0;
    }
	@Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		if(world.isRemote)
		{
			return;
		}
		
		TileEntityFirepit firepit = (TileEntityFirepit)world.getBlockTileEntity(x, y, z);
        if(firepit != null && player.getHeldItem() != null)
        {
        	if(firepit.isBurning() && player.getHeldItem().getItem() == Item.stick)
        	player.getHeldItem().itemID = Block.torchWood.blockID;
        }
    }
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2)
	{
		if(world.isRemote)
		{
			return false;
		}
		TileEntityFirepit firepit = (TileEntityFirepit)world.getBlockTileEntity(x, y, z);
		
		if(firepit != null)
		{
			ItemStack held = player.getHeldItem();
			boolean burning = firepit.isBurning();
			
			if(held != null)
			{
				if(burning)
		        {
		        	if(held.itemID == Block.sand.blockID)
		        	{
		        		firepit.extinguish();
		        		return true;
		        	}
		        	if(held.getItem() == Item.stick)
		        	{
		        		held.itemID = Block.torchWood.blockID;
		        		return true;
		        	}
		        }
				else if(firepit.fuel > 0)
				{
					if(held.getItem() instanceof ILighter)
					{
						world.playSoundEffect((double)x + 0.5D, (double)y - 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
		            	world.spawnParticle("flame", (double)x + 0.5D, (double)y - 0.5D, (double)z + 0.5D, 0F, 0F, 0F);
		            	
						ILighter lighter = (ILighter)held.getItem();
						if(lighter.canLight())
						{
			            	if(rand.nextDouble() < lighter.getChance())
			                {
			            		if(!world.isRemote)
			                	{
			                		firepit.setLit(true);
			                		held.damageItem(1, player);
			                	}
			                }
			            	return true;
						}
					}
					if(held.getItem() instanceof ItemFlintAndSteel)
					{
						world.playSoundEffect((double)x + 0.5D, (double)y - 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
		            	world.spawnParticle("flame", (double)x + 0.5D, (double)y - 0.5D, (double)z + 0.5D, 0F, 0F, 0F);
		            	
						if(!world.isRemote)
	                	{
	                		firepit.setLit(true);
	                		held.damageItem(1, player);
	                	}
						return true;
					}
				}
				if(firepit.addFuel(held) && !player.capabilities.isCreativeMode)
				{
					if(held.stackSize == 1)
					{
						if(held.getItem().hasContainerItem())
						{
							player.setCurrentItemOrArmor(0, held.getItem().getContainerItemStack(held));
						}
						else
						{
							player.setCurrentItemOrArmor(0, null);
						}
					}
					else
					{
						held.stackSize --;
						if(held.getItem().hasContainerItem())
						{
							if(!player.inventory.addItemStackToInventory(held.getItem().getContainerItemStack(held)))
							{
								player.dropPlayerItem(held.getItem().getContainerItemStack(held));
							}
						}
					}
					return true;
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, i, f, f1, f2);
	}

	@Override
	public TileEntity createNewTileEntity(World world) 
	{
		return new TileEntityFirepit();
	}
	
	@Override
    public Icon getIcon(int side, int meta)
    {
		return Block.planks.getIcon(side, 0);
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
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	if(world.getBlockMetadata(x, y, z) == 1)
    	{
    		return 15;
    	}
    	return super.getLightValue(world, x, y, z);
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	if(world.isRemote)
    	{
    		return;
    	}
    	TileEntityFirepit tile = (TileEntityFirepit)world.getBlockTileEntity(x, y, z);
    	if(entity == null || tile == null)
    	{
    		return;
    	}
    	if(entity instanceof EntityItem)
    	{
    		EntityItem entIt = (EntityItem)entity;
    		if(entIt.getEntityItem() != null)
    		{
    			for(int a = 0; a < entIt.getEntityItem().stackSize; a ++)
    			{
    				if(tile.addFuel(entIt.getEntityItem()))
    				{
    					entIt.getEntityItem().stackSize --;
    				}
    				if(entIt.getEntityItem().stackSize <= 0)
    				{
    					entIt.setDead();
    				}
    			}
    		}
    		if(tile.isBurning() && entity.ticksExisted % 25 == 0)
    		{
	    		entity.motionY = 0.20000000298023224D;
	    		entity.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
    		}
    	}
    	else
    	{
    		if(tile.isBurning())
    		entity.attackEntityFrom(DamageSource.inFire, 1.0F);
    	}
        if(tile.isBurning())
    	entity.setFire(2);
    }
    @Override
    public void breakBlock(World world, int x, int y, int z, int i1, int i2)
    {
    	TileEntityFirepit tile = (TileEntityFirepit)world.getBlockTileEntity(x, y, z);
    	if(tile != null)
    	{
    		int charcoal = tile.getCharcoalDrop();
    		
    		if(charcoal > 0)
    		{
    			float xDrop = this.rand.nextFloat() * 0.8F + 0.1F;
                float yDrop = this.rand.nextFloat() * 0.8F + 0.1F;
                float zDrop = this.rand.nextFloat() * 0.8F + 0.1F;
                
    			for(int c = 0; c < charcoal; c ++)
    			{
    				EntityItem drop = new EntityItem(world, (double)((float)x + xDrop), (double)((float)y + yDrop), (double)((float)z + zDrop), new ItemStack(Item.coal, 1, 1));

                    float jumpFactor = 0.05F;
                    drop.motionX = (double)((float)this.rand.nextGaussian() * jumpFactor);
                    drop.motionY = (double)((float)this.rand.nextGaussian() * jumpFactor + 0.2F);
                    drop.motionZ = (double)((float)this.rand.nextGaussian() * jumpFactor);
                    world.spawnEntityInWorld(drop);
    			}
    		}
    	}
    	super.breakBlock(world, x, y, z, i1, i2);
    }
    
    @Override
    public void registerIcons(IconRegister reg){}
}
