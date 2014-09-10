package minefantasy.system;

import java.awt.Color;
import java.util.List;

import minefantasy.MineFantasyBase;
import minefantasy.entity.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntitylistMF 
{
	
	public static int IDBase;
	public static void init()
	{
		IDBase = cfg.entityIDBase;
		addEntity(EntityHound.class, "HoundMF", Color.WHITE.hashCode(), MineFantasyBase.getColourForRGB(128, 64, 0));
		addEntity(EntityMinotaur.class, "Minotaur", MineFantasyBase.getColourForRGB(70, 50, 28), Color.BLACK.hashCode());
		addEntity(EntitySkeletalKnight.class, "SkeletalKnight", Color.GRAY.hashCode(), MineFantasyBase.getColourForRGB(100, 70, 70));
		addEntity(EntityDragonSmall.class, "SmallDragon", Color.RED.hashCode(), MineFantasyBase.getColourForRGB(221, 218, 164));
		addEntity(EntityDrake.class, "Drake", Color.GREEN.hashCode(), MineFantasyBase.getColourForRGB(221, 218, 164));
		addEntity(EntityBasilisk.class, "Basilisk", Color.BLUE.hashCode(), MineFantasyBase.getColourForRGB(221, 218, 164));

		EntityRegistry.registerModEntity(EntityBombThrown.class, "MFBomb", IDBase, MineFantasyBase.instance, 64, 1, true);IDBase ++;
		EntityRegistry.registerModEntity(EntityFirebreath.class, "fireBreath", IDBase, MineFantasyBase.instance, 64, 20, true);IDBase ++;
		EntityRegistry.registerModEntity(EntityArrowMF.class, "arrowMF", IDBase, MineFantasyBase.instance, 64, 20, false);IDBase ++;
		EntityRegistry.registerModEntity(EntityBoltMF.class, "boltMF", IDBase, MineFantasyBase.instance, 64, 20, false);IDBase ++;
		EntityRegistry.registerModEntity(EntityThrownSpear.class, "MFSpear", IDBase, MineFantasyBase.instance, 64, 20, false);
		EntityRegistry.registerModEntity(EntityRockSling.class, "MFRock", IDBase, MineFantasyBase.instance, 64, 1, false);IDBase ++;
		EntityRegistry.registerModEntity(EntityShrapnel.class, "shrapnelMF", IDBase, MineFantasyBase.instance, 64, 1, false);IDBase ++;
		
		EntityRegistry.addSpawn(EntityHound.class, cfg.houndSpawnrate/2, 3, 3, EnumCreatureType.creature, BiomeGenBase.forest);
		EntityRegistry.addSpawn(EntityHound.class, cfg.houndSpawnrate/2, 3, 3, EnumCreatureType.creature, BiomeGenBase.forestHills);
        EntityRegistry.addSpawn(EntityHound.class, cfg.houndSpawnrate/3, 3, 3, EnumCreatureType.creature, BiomeGenBase.jungle);
        EntityRegistry.addSpawn(EntityHound.class, cfg.houndSpawnrate/3, 3, 3, EnumCreatureType.creature, BiomeGenBase.jungleHills);
        EntityRegistry.addSpawn(EntityDrake.class, cfg.drakeSpawnrate, 1, 3, EnumCreatureType.monster, BiomeGenBase.plains);
        EntityRegistry.addSpawn(EntityDrake.class, cfg.drakeSpawnrate, 2, 2, EnumCreatureType.monster, BiomeGenBase.extremeHillsEdge);
        EntityRegistry.addSpawn(EntityDrake.class, cfg.drakeSpawnrate, 2, 4, EnumCreatureType.monster, BiomeGenBase.extremeHills);
        
        addSpawn(EntityHound.class, cfg.houndSpawnrate, 3, 5, EnumCreatureType.creature, Type.FOREST);
        addSpawn(EntityDrake.class, cfg.drakeSpawnrate, 1, 3, EnumCreatureType.creature, Type.PLAINS);
        
        
        addSpawn(EntitySkeletalKnight.class, cfg.knightSpawnrate, 1, 1, EnumCreatureType.monster);
        addSpawn(EntityMinotaur.class, cfg.minotaurSpawnrate, 1, 2, EnumCreatureType.monster);
        addSpawn(EntityDragonSmall.class, 1, 1, 1, EnumCreatureType.creature, Type.MOUNTAIN);
        
        addSpawn(EntityBasilisk.class, cfg.basilSpawnrate, 1, 1, EnumCreatureType.monster);
        addSpawn(EntityBasilisk.class, cfg.basilSpawnrateNether, 1, 1, EnumCreatureType.monster, Type.NETHER);
        
        addSpawn(EntityMinotaur.class, cfg.minotaurSpawnrate*5, 2, 8, EnumCreatureType.monster, Type.NETHER);
        addSpawn(EntityDragonSmall.class, cfg.dragonSpawnrateNether, 1, 2, EnumCreatureType.monster, Type.NETHER);
        addSpawn(EntityDragonSmall.class, 2, 1, 1, EnumCreatureType.monster, Type.MOUNTAIN);
        
	}
	
    private static void addEntity(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor)
    {
            if (MineFantasyBase.isDebug())
            {
            	System.out.println("MineFantasy: registerEntity " + entityClass + " with Mod ID " + IDBase);
            }
            EntityRegistry.registerModEntity(entityClass, entityName, IDBase, MineFantasyBase.instance, 128, 1, true);
            EntityList.entityEggs.put(Integer.valueOf(IDBase), new EntityEggInfo(IDBase, eggColor, eggDotsColor));
            
            EntityList.addMapping(entityClass, entityName, IDBase);
            IDBase++;
    }
    
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature)
    {
        for (BiomeGenBase biome : BiomeGenBase.biomeList)
        {
        	if(biome != null)
        	{
        		if(BiomeDictionary.isBiomeRegistered(biome))
        		{
        			if(BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.END))
        			{
        				return;
        			}
        			if(BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER))
        			{
        				return;
        			}
        			if(BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MUSHROOM))
        			{
        				return;
        			}
        		}
        		
        		EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
        	}
        }
    }
    
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeDictionary.Type type)
    {
        for (BiomeGenBase biome : BiomeGenBase.biomeList)
        {
        	if(biome != null)
        	{
        		if(BiomeDictionary.isBiomeRegistered(biome))
        		{
        			if(BiomeDictionary.isBiomeOfType(biome, type))
        			{
        				EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
        			}
        		}
        	}
        }
    }
}
