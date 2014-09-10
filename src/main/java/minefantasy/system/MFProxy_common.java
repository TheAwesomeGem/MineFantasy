package minefantasy.system;

import minefantasy.block.tileentity.*;
import minefantasy.client.TileEntityTripHammerRenderer;
import minefantasy.client.gui.*;
import minefantasy.client.gui.hound.*;
import minefantasy.container.*;
import minefantasy.entity.EntityHound;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class MFProxy_common implements IGuiHandler {
	public void registerRenderInformation() {

	}
// MFProxy_client
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		switch (ID) {
		case 0:
			return new GuiAnvil(player.inventory, (TileEntityAnvil) tile);
		case 1:
		{
			TileEntitySmelter bloom = (TileEntitySmelter)tile;
			if(bloom != null)
			{
				if(bloom.getTier() == 0)
					return new GuiBloomery(player.inventory, (TileEntitySmelter) tile);
				if(bloom.getTier() >= 1)
					return new GuiCrucible(player.inventory, (TileEntitySmelter) tile);
			}
		}
		case 2:
		{
			for (Object entity : world.loadedEntityList) {
				if (((Entity) entity).entityId == x && y == 0)
					return getEntityGui(player, world, (Entity)entity, z);
			}
		}
		break;
		case 3:
			return new GuiTailor(player.inventory, (TileEntityTailor) tile);
		case 4:
			return new GuiRack(player.inventory, (TileEntityWeaponRack) tile);
		case 6:
			return new GuiForge(player.inventory, (TileEntityForge) tile);
		case 7:
				return new GuiBFurnace(meta, player, (TileEntityBFurnace) tile);
		case 8:
		{
			return new GuiOven(player.inventory, (TileEntityOven) tile);
		}
		
		case 9:
			return new GuiHammer(player.inventory, (TileEntityTripHammer) tile);
		case 11:
			return new GuiRoast(player.inventory, (TileEntityRoast) tile);
		case 12:
			return new GuiFurnaceMF(player, player.inventory, (TileEntityFurnaceMF) tile);
		
		}
		
		
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		switch (ID) {
		case 0:
			return new ContainerAnvil(player.inventory, (TileEntityAnvil) tile);
		case 1:
		{
			TileEntitySmelter bloom = (TileEntitySmelter)tile;
			if(bloom != null)
			{
				if(bloom.getTier() == 0)
					return new ContainerBloomery(player.inventory, (TileEntitySmelter) tile);
				if(bloom.getTier() >= 1)
					return new ContainerCrucible(player.inventory, (TileEntitySmelter) tile);
			}
		}
		case 2:
		{
			for (Object entity : world.loadedEntityList) {
				if (((Entity) entity).entityId == x)
					return getEntityContainer(player, world, (Entity)entity, z);
			}
		}
		break;
		case 3:
			return new ContainerTailor(player.inventory, (TileEntityTailor) tile);
		case 4:
			return new ContainerRack(player.inventory,
					(TileEntityWeaponRack) tile);
		case 6:
			return new ContainerForge(player.inventory, (TileEntityForge) tile);
		case 7:
				return new ContainerBFurnace(meta, player, (TileEntityBFurnace) tile);
		case 8:
			return new ContainerOven(player.inventory, (TileEntityOven) tile);
		case 9:
			return new ContainerHammer(player.inventory, (TileEntityTripHammer) tile);
			
		case 11:
			return new ContainerRoast(player.inventory,
					(TileEntityRoast) tile);
			
		case 12:
			return new ContainerFurnaceMF(player, player.inventory,
					(TileEntityFurnaceMF) tile);
		}	
		return null;
	}

	public World getClientWorld() {
		return null;
	}

	public void openEntityGui(EntityPlayer player, Entity interact) {

	}

	public void registerTileEntities() 
	{
		EntitylistMF.init();
		GameRegistry.registerTileEntity(TileEntityLantern.class, "MFLantern");
		GameRegistry.registerTileEntity(TileEntityBellows.class, "Bellows");
		GameRegistry.registerTileEntity(TileEntityDogBowl.class, "Dogbowl");
		GameRegistry.registerTileEntity(TileEntityAnvil.class, "Anvil");
		GameRegistry.registerTileEntity(TileEntitySmelter.class, "Bloom");
		GameRegistry.registerTileEntity(TileEntityForge.class, "Forge");
		GameRegistry.registerTileEntity(TileEntityTanningRack.class, "Tanner");
		GameRegistry.registerTileEntity(TileEntityWeaponRack.class, "Rack");
		GameRegistry.registerTileEntity(TileEntityRoast.class, "MFSpitRoast");
		GameRegistry.registerTileEntity(TileEntityBFurnace.class, "Blastfurnace");
		GameRegistry.registerTileEntity(TileEntityTripHammer.class, "MFTripHammer");
		GameRegistry.registerTileEntity(TileEntityFurnaceMF.class, "MFFurnace");
		GameRegistry.registerTileEntity(TileEntityPrepBlock.class, "MFFoodPrep");
		GameRegistry.registerTileEntity(TileEntityTailor.class, "MFTailor");
		GameRegistry.registerTileEntity(TileEntitySpinningWheel.class, "MFSpinningWheel");
		GameRegistry.registerTileEntity(TileEntityFirepit.class, "firepitMF");
		GameRegistry.registerTileEntity(TileEntityOven.class, "ovenMF");
		GameRegistry.registerTileEntity(TileEntityRoad.class, "roadMF");
	}

	private Object getEntityGui(EntityPlayer player, World world, Entity entity, int id) {
		if(entity instanceof EntityHound)
		{
			if(id == 1)
				return new GuiHoundRename(player, world, (EntityHound)entity);
			
			if(id == 0)
			{
				return new GuiHound((EntityHound)entity, player);
			}
			if(id == 2)
			{
				return new GuiPackHound(player, (EntityHound)entity, ((EntityHound)entity).getAvailableRows());
			}
			if(id == 3)
			{
				return new GuiHoundStats((EntityHound)entity, player);
			}
		}
		
		return null;
	}
	
	private Object getEntityContainer(EntityPlayer player, World world, Entity entity, int id) {
		if(entity instanceof EntityHound)
		{
			if(id == 0)
			{
				return new ContainerHoundArmour(player, (EntityHound)entity);
			}
			if(id == 2)
			{
				return new ContainerPackHound(player.inventory, ((EntityHound)entity).pack, ((EntityHound)entity).getAvailableRows());
			}
			if(id == 3)
			{
				return new ContainerHoundStats((EntityHound)entity);
			}
		}
		
		return null;
	}
	public void registerPlayerbase() {
	}
	public void registerTickHandlers() 
	{
	}
}