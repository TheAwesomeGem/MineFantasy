package minefantasy.item.weapon;

import java.text.DecimalFormat;

import cpw.mods.fml.relauncher.Side;

import minefantasy.api.weapon.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Warpick:
 * does low damage, but with high AP
 * 2x armour damage
 */
public class ItemWarpick extends ItemWeaponMF implements IWeaponPenetrateArmour, IWeaponCustomSpeed
{
	private static Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneSingleSlab, Block.stoneDoubleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered};
    public float efficiencyOnProperMaterial = 2.0F;
    private float APdamage;
    
	public ItemWarpick(int id, EnumToolMaterial material) 
	{
		super(id, material);
		DecimalFormat decimal_format = new DecimalFormat("#.#");
		
		APdamage = baseDamage*getAPPercent();
		
		if(!(this instanceof ItemWarhammer))
		{
			this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial()*0.75F;
			MinecraftForge.setToolClass(this,   "pickaxe", material.getHarvestLevel());
		}
	}

	@Override
	public float getDamageModifier() 
	{
		return 0.75F;
	}

	@Override
	public int getHitTime(ItemStack weapon, EntityLivingBase target)
	{
		return 3;
	}

	@Override
	public float getAPDamage() 
	{
		return APdamage;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack item, World world, int x, int y, int z, int meta, EntityLivingBase entity)
    {
        item.damageItem(1, entity);
        return true;
    }
	
	@Override
    public boolean canHarvestBlock(Block block, ItemStack stack)
    {
		if(!(this instanceof ItemWarhammer))
		{
			return ForgeHooks.canToolHarvestBlock(block, 0, stack);
		}
		return super.canHarvestBlock(block);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    @Override
    public float getStrVsBlock(ItemStack item, Block block, int metadata)
    {
    	if(!(this instanceof ItemWarhammer))
    	{
    		return ForgeHooks.isToolEffective(item, block, metadata) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(item, block);
    	}
    	return super.getStrVsBlock(item, block, metadata)*3.0F;
    }	

	@Override
	public float getDurability() 
	{
		return 1.5F;
	}

	@Override
	public float getArmourDamageBonus() 
	{
		return 1.5F;
	}

	@Override
	public boolean buffDamage() 
	{
		return true;
	}

	@Override
	public boolean sheatheOnBack(ItemStack item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOffhandHandDual(ItemStack off) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offhandClickAir(PlayerInteractEvent event,
			ItemStack mainhandItem, ItemStack offhandItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getHandsUsed() 
	{
		return 1;
	}
	
	//SPECIAL EFFECT(WARHAMMER): On hit: "Heavy Strike": explosive damage, increases strength, and adds saturation
	@Override
	public void onHit(float damage, ItemStack weapon, EntityLivingBase target, EntityLivingBase attacker)
	{
		super.onHit(damage, weapon, target, attacker);
		
		if(target != null && target instanceof EntityLivingBase)
		{
			if(rand.nextFloat()*3 < getDebilitation())
			{
				if(this instanceof ItemWarhammer)
				{
					attacker.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 300, 0));
					
					if(attacker instanceof EntityPlayer)
					{
						((EntityPlayer)attacker).getFoodStats().addStats(0, 2F);
					}
					
					target.worldObj.createExplosion(attacker, target.posX, target.posY + (target.height/2), target.posZ, 1.0F, false);
					target.addPotionEffect(new PotionEffect(Potion.confusion.id, 150, rand.nextInt(3)));
				}
				else
				{
					target.playSound("minefantasy:Weapon.crit", 1.0F, 1.0F);
					target.addPotionEffect(new PotionEffect(Potion.weakness.id, 600, rand.nextInt(3)));
				}
				
				target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, rand.nextInt(3)));
			}
		}
	}
	public float getDebilitation()
	{
		return 0.4F;
	}
	
	@Override
	protected float getAPPercent()
	{
		return 0.5F;
	}
}
