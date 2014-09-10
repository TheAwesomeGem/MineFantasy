package minefantasy.client;

import minefantasy.block.tileentity.*;
import minefantasy.client.entityrender.*;
import minefantasy.entity.*;
import minefantasy.item.ItemListMF;
import minefantasy.system.EntitylistMF;
import minefantasy.system.EventManagerMF;
import minefantasy.system.MFProxy_common;
import minefantasy.system.WeaponHandlerClient;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
public class MFProxy_client extends MFProxy_common
{

	// MFProxy_common
	@Override
	public void registerRenderInformation() 
	{
		RenderingRegistry.registerBlockHandler(new BlockRendererMF());
	     
	    registerItemRenderer();
		RenderingRegistry.registerEntityRenderingHandler(EntityDrake.class, new RenderDrake(new ModelDrake(), 1.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHound.class, new RenderHound(new ModelHound(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMinotaur.class, new RenderMinotaur(new ModelMinotaur(), 2F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletalKnight.class, new RenderSkeletalKnight(new ModelSkeletalKnight()));
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonSmall.class, new RenderSmallDragon(new ModelSmallDragon(), 2F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFirebreath.class, new RenderFirebreath());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBasilisk.class, new RenderBasilisk());
	    RenderingRegistry.registerEntityRenderingHandler(EntityArrowMF.class, new RenderArrowMF());
		RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, new RenderShrapnel());
		RenderingRegistry.registerEntityRenderingHandler(EntityBoltMF.class, new RenderBoltMF());
		RenderingRegistry.registerEntityRenderingHandler(EntityBombThrown.class, new RenderBomb());
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownSpear.class, new RenderThrownItem());
		RenderingRegistry.registerEntityRenderingHandler(EntityRockSling.class, new RenderSnowball(ItemListMF.misc, ItemListMF.rock));
		
	}


	private void registerItemRenderer()
	{
		MinecraftForgeClient.registerItemRenderer(ItemListMF.tongsStone.itemID, new RenderTongs());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.tongsTin.itemID, new RenderTongs());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.tongsCopper.itemID, new RenderTongs());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.tongsBronze.itemID, new RenderTongs());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.tongsIron.itemID, new RenderTongs());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.tongsSteel.itemID, new RenderTongs());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.tongsMithril.itemID, new RenderTongs());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.tongsDeepIron.itemID, new RenderTongs());
		
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerBronze.itemID, new MF_DaggerRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerIron.itemID, new MF_DaggerRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerSteel.itemID, new MF_DaggerRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerMithril.itemID, new MF_DaggerRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerEncrusted.itemID, new MF_DaggerRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerDragon.itemID, new MF_DaggerRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerDeepIron.itemID, new MF_DaggerRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerOrnate.itemID, new MF_DaggerRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.daggerIgnotumite.itemID, new MF_DaggerRenderer());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeBronze.itemID, new MF_BigWeaponRenderer().setAxe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeIron.itemID, new MF_BigWeaponRenderer().setAxe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeSteel.itemID, new MF_BigWeaponRenderer().setAxe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeEncrusted.itemID, new MF_BigWeaponRenderer().setAxe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeOrnate.itemID, new MF_BigWeaponRenderer().setAxe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeDragon.itemID, new MF_BigWeaponRenderer().setAxe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeDeepIron.itemID, new MF_BigWeaponRenderer().setAxe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeMithril.itemID, new MF_BigWeaponRenderer().setAxe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.battleaxeIgnotumite.itemID, new MF_BigWeaponRenderer().setAxe());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.morningstarBronze.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.morningstarSteel.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatmaceEncrusted.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.morningstarIron.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.morningstarDragon.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatmaceDeepIron.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatmaceOrnate.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.morningstarMithril.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.morningstarIgnotumite.itemID, new MF_BigWeaponRenderer().setBlunt());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearBronze.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearIron.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearSteel.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearEncrusted.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearMithril.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearDragon.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearDeepIron.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearOrnate.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearIgnotumite.itemID, new MFSpearRenderer());

		MinecraftForgeClient.registerItemRenderer(ItemListMF.scytheBronze.itemID, new MF_BigWeaponRenderer().setScythe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.scytheIron.itemID, new MF_BigWeaponRenderer().setScythe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.scytheSteel.itemID, new MF_BigWeaponRenderer().setScythe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.scytheMithril.itemID, new MF_BigWeaponRenderer().setScythe());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.scytheDeepIron.itemID, new MF_BigWeaponRenderer().setScythe());

		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordBronze.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordIron.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordSteel.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordMithril.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordEncrusted.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordDragon.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordDeepIron.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordOrnate.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.greatswordIgnotumite.itemID, new MF_BigWeaponRenderer(false).setGreatsword());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.shortbow.itemID, new MF_BowRenderer(false));
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowBronze.itemID, new MF_BowRenderer(false));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowIron.itemID, new MF_BowRenderer(false));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowOrnate.itemID, new MF_BowRenderer(false));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowSteel.itemID, new MF_BowRenderer(false));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowDragon.itemID, new MF_BowRenderer(false));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowMithril.itemID, new MF_BowRenderer(false));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowDeepIron.itemID, new MF_BowRenderer(false));
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowComposite.itemID, new MF_BowRenderer(false));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowIronbark.itemID, new MF_BowRenderer(false));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bowEbony.itemID, new MF_BowRenderer(false));
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.longbow.itemID, new MF_BowRenderer(true));
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.crossbowHand.itemID, new RenderCrossbow(2.0F));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.crossbowLight.itemID, new RenderCrossbow(2.0F));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.crossbowRepeat.itemID, new RenderCrossbow(2.0F));
		MinecraftForgeClient.registerItemRenderer(ItemListMF.crossbowHeavy.itemID, new RenderCrossbow(2.0F));
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearStone.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.spearCopper.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.javelin.itemID, new MFSpearRenderer());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.sawBronze.itemID, new MFSawRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.sawIron.itemID, new MFSawRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.sawSteel.itemID, new MFSawRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.sawMithril.itemID, new MFSawRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.sawDeepIron.itemID, new MFSawRenderer());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardBronze.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardIron.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardSteel.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardEncrusted.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardMithril.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardDragon.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardDeepIron.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardOrnate.itemID, new MFSpearRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.halbeardIgnotumite.itemID, new MFSpearRenderer());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerBronze.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerSteel.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerEncrusted.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerIron.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerDragon.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerDeepIron.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerOrnate.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerBronze.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerMithril.itemID, new MF_BigWeaponRenderer().setBlunt());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.warhammerIgnotumite.itemID, new MF_BigWeaponRenderer().setBlunt());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceBronze.itemID, new RenderLance());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceIron.itemID, new RenderLance());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceSteel.itemID, new RenderLance());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceEncrusted.itemID, new RenderLance());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceOrnate.itemID, new RenderLance());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceDragon.itemID, new RenderLance());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceDeepIron.itemID, new RenderLance());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceMithril.itemID, new RenderLance());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.lanceIgnotumite.itemID, new RenderLance());
		
		//SHIELDS//
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bucklerBronze.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bucklerIron.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bucklerSteel.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bucklerMithril.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bucklerEncrusted.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bucklerDragonforge.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bucklerGuilded.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.bucklerDeepIron.itemID, new RenderShield());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.kiteBronze.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.kiteIron.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.kiteSteel.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.kiteMithril.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.kiteEncrusted.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.kiteDragonforge.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.kiteGuilded.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.kiteDeepIron.itemID, new RenderShield());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.towerBronze.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.towerIron.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.towerSteel.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.towerMithril.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.towerEncrusted.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.towerDragonforge.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.towerGuilded.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.towerDeepIron.itemID, new RenderShield());
		
		MinecraftForgeClient.registerItemRenderer(ItemListMF.shieldWood.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.shieldIronbark.itemID, new RenderShield());
		MinecraftForgeClient.registerItemRenderer(ItemListMF.shieldEbony.itemID, new RenderShield());
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override
	public void registerTickHandlers()
	{
		TickRegistry.registerTickHandler(new WeaponHandlerClient(), Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(new HudHandlerMF());
	}
	public void registerTileEntities()
	{
		EntitylistMF.init();
		
		ClientRegistry.registerTileEntity(TileEntityLantern.class, "MFLantern",new TileEntityLanternRenderer());
		ClientRegistry.registerTileEntity(TileEntityBellows.class, "Bellows", new TileEntityBellowsRenderer());
		ClientRegistry.registerTileEntity(TileEntityDogBowl.class, "Dogbowl", new TileEntityDogbowlRenderer());
		ClientRegistry.registerTileEntity(TileEntityAnvil.class, "Anvil",new TileEntityAnvilRenderer());
		ClientRegistry.registerTileEntity(TileEntitySmelter.class, "Bloom",new TileEntitySmelterRenderer());
		ClientRegistry.registerTileEntity(TileEntityForge.class, "Forge",new TileEntityForgeRenderer());
		ClientRegistry.registerTileEntity(TileEntityTanningRack.class, "Tanner",new TileEntityTanningRackRenderer());
		ClientRegistry.registerTileEntity(TileEntityWeaponRack.class, "Rack",new TileEntityWeaponRackRenderer());
		GameRegistry.registerTileEntity(TileEntityBFurnace.class, "Blastfurnace");
		ClientRegistry.registerTileEntity(TileEntityTripHammer.class, "MFTripHammer", new TileEntityTripHammerRenderer());
		ClientRegistry.registerTileEntity(TileEntityRoast.class, "MFSpitRoast",new TileEntityRoastRenderer());
		ClientRegistry.registerTileEntity(TileEntityFurnaceMF.class, "MFFurnace", new TileEntityFurnaceRendererMF());
		ClientRegistry.registerTileEntity(TileEntityPrepBlock.class, "MFFoodPrep", new TileEntityFoodPrepRenderer());
		ClientRegistry.registerTileEntity(TileEntityTailor.class, "MFTailor", new TileEntityTailorRenderer());
		ClientRegistry.registerTileEntity(TileEntitySpinningWheel.class, "MFSpinningWheel", new TileEntitySpinningWheelRenderer());
		ClientRegistry.registerTileEntity(TileEntityFirepit.class, "firepitMF", new TileEntityFirepitRenderer());
		ClientRegistry.registerTileEntity(TileEntityOven.class, "ovenMF", new TileEntityOvenRenderer());
		GameRegistry.registerTileEntity(TileEntityRoad.class, "roadMF");
	}
}