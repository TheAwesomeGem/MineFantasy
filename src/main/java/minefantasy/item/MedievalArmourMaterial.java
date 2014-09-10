package minefantasy.item;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraftforge.common.EnumHelper;

public class MedievalArmourMaterial
{
	public static EnumArmorMaterial BASIC = EnumHelper.addArmorMaterial("Basic", 0, new int[]               {2, 6, 5, 2}, 0);
	
	
	public static EnumArmorMaterial RAWHIDE = EnumHelper.addArmorMaterial("RAWHIDE", 3, new int[]           {0, 2, 1, 0}, 0);
	public static EnumArmorMaterial ROUGHLEATHER = EnumHelper.addArmorMaterial("ROUGHLEATHER", 3, new int[] {1, 3, 2, 1}, 8);
	public static EnumArmorMaterial LEATHER = EnumHelper.addArmorMaterial("LEATHER", 5, new int[]           {1, 3, 2, 1}, 15);
	public static EnumArmorMaterial APRON = EnumHelper.addArmorMaterial("APRON", 12, new int[]              {0, 1, 0, 0}, 0);
	public static EnumArmorMaterial STEALTH = EnumHelper.addArmorMaterial("STEALTH", 10, new int[]          {1, 2, 2, 1}, 25);
	
	public static EnumArmorMaterial BRONZE = EnumHelper.addArmorMaterial("BRONZE", 20, new int[]            {2, 5, 4, 1}, 10);
	public static EnumArmorMaterial IRON = EnumHelper.addArmorMaterial("IRON", 18, new int[]                {2, 6, 5, 2}, 12);
	public static EnumArmorMaterial GUILDED = EnumHelper.addArmorMaterial("GUILDED", 10, new int[]          {2, 6, 5, 2}, 25);
	public static EnumArmorMaterial STEEL = EnumHelper.addArmorMaterial("STEEL", 30, new int[]              {3, 7, 5, 2}, 15);
	public static EnumArmorMaterial ENCRUSTED = EnumHelper.addArmorMaterial("ENCRUSTED", 40, new int[]      {3, 8, 6, 2}, 10);
	public static EnumArmorMaterial DRAGON = EnumHelper.addArmorMaterial("DRAGONFORGE", 50, new int[]       {3, 7, 5, 3}, 15);
	
	public static EnumArmorMaterial DEEP_IRON = EnumHelper.addArmorMaterial("DEEPIRON",    45, new int[]    {3, 8, 6, 3}, 15);
	public static EnumArmorMaterial BLUE_STEEL = EnumHelper.addArmorMaterial("BLUESTEEL",  50, new int[]    {4, 9, 7, 4}, 20);
	public static EnumArmorMaterial MITHRIL = EnumHelper.addArmorMaterial("MITHRIL"      , 55, new int[]    {5, 9, 7, 5}, 18);
	public static EnumArmorMaterial MYTHIUM = EnumHelper.addArmorMaterial("MYTHIUM",       60, new int[]    {5, 10, 8, 5}, 22);
	public static EnumArmorMaterial UNNAMED = EnumHelper.addArmorMaterial("UNNAMED",       70, new int[]    {5, 11, 9, 6}, 20);
	public static EnumArmorMaterial ADAMANTIUM = EnumHelper.addArmorMaterial("ADAMANTIUM", 80, new int[]    {6, 11, 9, 7}, 25);
	

	
	/*
	CHAIN(15, new int[]{2, 5, 4, 1}, 12),
    IRON(15, new int[]{2, 6, 5, 2}, 9),
    GOLD(7, new int[]{2, 5, 3, 1}, 25),
    DIAMOND(33, new int[]{3, 8, 6, 3}, 10);
     * 
    HIDE(2, new int[]{1, 2, 1, 1}, 5, 0),
    STEEL(20, new int[]{3, 7, 5, 2}, 15, 0),
    DRAGON_STEEL(30, new int[]{3, 7, 5, 2}, 35, 1),
    COPPER(10, new int[]{3, 7, 5, 2}, 35, 0),
    TIN(8, new int[]{3, 7, 5, 2}, 35, 0),
    BRONZE(18, new int[]{3, 7, 5, 2}, 35, 0),
    FULLPLATE(40, new int[]{4, 10, 8, 4}, 20, 0);
    */
}
