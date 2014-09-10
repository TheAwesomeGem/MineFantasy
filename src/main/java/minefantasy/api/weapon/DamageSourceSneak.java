package minefantasy.api.weapon;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class DamageSourceSneak extends DamageSource
{

	public DamageSourceSneak(String title)
	{
		super(title);
	}
}
