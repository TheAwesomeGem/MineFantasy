package minefantasy.api.tactic;

import net.minecraft.entity.Entity;

/**
 * 
 * @author AnonymousProductions
 * 
 * Be sure to use canEntityBeSeen(thief) if you only want a reaction when guards can see it
 * 
 *
 */
public interface IGuard {
	void onDetectPickpocket(Entity thief, Entity mark);
	void onDetectAssault(Entity attacker, Entity defender);
	void onDetectMurder(Entity attacker, Entity victum);
}
