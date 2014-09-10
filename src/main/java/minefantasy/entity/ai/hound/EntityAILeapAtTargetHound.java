package minefantasy.entity.ai.hound;

import minefantasy.entity.EntityHound;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILeapAtTarget;

public class EntityAILeapAtTargetHound extends EntityAILeapAtTarget
{
	private EntityHound hound;
	public EntityAILeapAtTargetHound(EntityHound dog, float chance) 
	{
		super(dog, chance);
		hound = dog;
	}
	
	@Override
	public boolean shouldExecute()
	{
		return hound.shouldLeapAttack() ? super.shouldExecute() : false;
	}

}
