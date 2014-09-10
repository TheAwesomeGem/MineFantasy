package minefantasy.system;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntityDamageSource;

public class EntityDamageSourceAP extends EntityDamageSource
{
	public EntityDamageSourceAP(String type, Entity attacker) 
	{
		super(type, attacker);
		this.setDamageBypassesArmor();
	}
	
	public static EntityDamageSourceAP causeDamage(Entity attacker)
	{
		String type = attacker instanceof EntityPlayer ? "player" : "mob";
		return new EntityDamageSourceAP(type, attacker);
	}
}
