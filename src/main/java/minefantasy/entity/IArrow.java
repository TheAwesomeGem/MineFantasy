package minefantasy.entity;

import net.minecraft.entity.Entity;

public interface IArrow {
	void setDamage(double d);
	void setKnockbackStrength(int i);
	void setIsCritical(boolean b);
	void setFire(int i);
	Entity getInstance();
	double getDamage();
}
