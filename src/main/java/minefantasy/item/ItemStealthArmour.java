package minefantasy.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.tactic.IStealthArmour;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class ItemStealthArmour extends ItemArmourMFOld implements IStealthArmour{

	public ItemStealthArmour(int i, EnumArmorMaterial mat, int render,
			int type, String s) {
		super(i, ArmourDesign.SOLID, mat, render, type, s);
	}

	@SideOnly(Side.CLIENT)
	@Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
		if(canTurnInvisible())
		{
			ModelBiped model = new ModelBiped();
			
			if(entityLiving.getActivePotionEffect(Potion.invisibility) != null)
			{
				model.bipedBody.isHidden = true;
				model.bipedHead.isHidden = true;
				model.bipedHeadwear.isHidden = true;
				model.bipedRightArm.isHidden = true;
				model.bipedLeftArm.isHidden = true;
				model.bipedRightLeg.isHidden = true;
				model.bipedLeftLeg.isHidden = true;
				model.bipedCloak.isHidden = true;
				
				return model;
			}
		}
        return null;
    }
	
	@Override
	public EnumRarity getRarity(ItemStack item)
	{
		return EnumRarity.uncommon;
	}
	@Override
	public float darknessAmplifier() {
		return 1.15F;
	}

	@Override
	public float noiseReduction() {
		return 1.0F;
	}

	@Override
	public boolean quietRun() {
		return true;
	}

	@Override
	public boolean canTurnInvisible() {
		return true;
	}
}
