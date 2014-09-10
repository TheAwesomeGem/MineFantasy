package minefantasy.api.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class DamageSourceAP extends DamageSource
{
	/**
	 * Use this damage source if you have a blunt weapon(only a portion)
	 * Eg. A mace has 25% armour penetration, so a quarter of its damage uses this
	 * (the rest uses normal MC damage)
	 */
	public static final DamageSource blunt = new DamageSourceAP("blunt_mf").setDamageBypassesArmor();
	/**
	 * This is used for armour piercing weapons(like blunt weapons)
	 * It represents a blunt force, plate armour can resist this
	 */
	public DamageSourceAP(String name)
	{
		super(name);
	}
	@Override
	public ChatMessageComponent getDeathMessage(EntityLivingBase entity)
    {
        return ChatMessageComponent.createFromText("");
    }
}
