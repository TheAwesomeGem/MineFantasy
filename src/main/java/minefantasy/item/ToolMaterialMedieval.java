package minefantasy.item;

import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ToolMaterialMedieval
{
	/*
		WOOD(0, 59, 2.0F, 0, 15),
    	STONE(1, 131, 4.0F, 1, 5),
    	IRON(2, 250, 6.0F, 2, 14),
    	EMERALD(3, 1561, 8.0F, 3, 10),
    	GOLD(0, 32, 12.0F, 0, 22);
	 */
	public static EnumToolMaterial STRONGWOOD = EnumHelper.addToolMaterial("STRONGWOOD",             0, 400, 2.5F, 0, 15);
	public static EnumToolMaterial IRONBARK = EnumHelper.addToolMaterial("IRONBARK",                 1, 800, 4.0F, 1, 5);
	public static EnumToolMaterial EBONY = EnumHelper.addToolMaterial("EBONY",                       1, 1600, 5.0F, 2, 10);
	
	public static EnumToolMaterial PRIMITIVE_STONE = EnumHelper.addToolMaterial("PRIMITIVE_STONE",   1, 64, 1.8F, 0.5F, 0);
	public static EnumToolMaterial PRIMITIVE_COPPER = EnumHelper.addToolMaterial("PRIMITIVE_COPPER", 1, 180, 2.0F, 1F, 0);
	public static EnumToolMaterial TIN = EnumHelper.addToolMaterial("TIN",                           1, 200, 4.5F, 1.25F, 10);
	public static EnumToolMaterial COPPER = EnumHelper.addToolMaterial("COPPER",       1, 300, 4.8F, 1.2F, 12);
	public static EnumToolMaterial BRONZE = EnumHelper.addToolMaterial("BRONZE",                     2, 750, 5F, 1.5F, 10);
	public static EnumToolMaterial IRON = EnumHelper.addToolMaterial("IRON",           2, 500, 7.5F, 2, 18);
	public static EnumToolMaterial STEEL = EnumHelper.addToolMaterial("STEEL",         2, 1000, 7.8F, 2.5F, 22);
	public static EnumToolMaterial ORNATE = EnumHelper.addToolMaterial("ORNATE",                     2, 400, 7.5F, 1.5F, 35);
	public static EnumToolMaterial ENCRUSTED = EnumHelper.addToolMaterial("ENCRUSTED",               3, 2500, 8.5F, 3.0F, 25);
	public static EnumToolMaterial DRAGONFORGE = EnumHelper.addToolMaterial("DRAGONFORGE",           2, 3500, 9F, 3.0F, 20);
	
	public static EnumToolMaterial DEEP_IRON = EnumHelper.addToolMaterial("DEEPIRON",                4, 3000, 9F,    3.5F, 18);
	public static EnumToolMaterial BLUE_STEEL = EnumHelper.addToolMaterial("BLUESTEEL",              5, 3500, 10.5F, 4.0F, 24);
	public static EnumToolMaterial MITHRIL = EnumHelper.addToolMaterial("MITHRIL",                   6, 4000, 12F,   4.5F, 24);
	public static EnumToolMaterial MYTHIUM = EnumHelper.addToolMaterial("MYTHIUM",                   7, 4000, 13F,   5.0F, 20);
	public static EnumToolMaterial UNNAMED = EnumHelper.addToolMaterial("UNNAMED",                   8, 4000, 14F,   5.5F, 25);
	public static EnumToolMaterial ADAMANTIUM = EnumHelper.addToolMaterial("ADAMANTIUM",             9, -1,   15F,   6.0F, 30);
    
	public static EnumToolMaterial IGNOTUMITE = EnumHelper.addToolMaterial("IGNOTUMITE",             9, -1,   15F,   4.5F, 30);
	/*
    FORGED_IRON (2, 375, 7.5F, 3, 18, true, 0),
    STEEL       (2, 500, 6.0F, 2, 18, false, 0),
    FORGED_STEEL(2, 750, 7.5F, 3, 20, true, 0),
    ORNATE      (2, 700, 7.5F, 2, 35, true, 0),
    SILVER      (1, 200, 5.0F, 1, 40, false, 0),
    COPPER      (2, 200, 5F, 2, 15, false, 0),
    FORGED_COPPER(2, 300, 5.5F, 2, 20, true, 0),
    BRONZE      (2, 400, 8.0F, 3, 25, false, 0),
    FORGED_BRONZE      (2, 600, 8.5F, 3, 30, true, 0),
    REDSTEEL      (2, 1000, 8.0F, 3, 30, true, 0),
    FORGED_IGNOTUMITE      (4, -1, 15.0F, 4, 30, true, 1),
    DRAGON_STEEL      (3, 2500, 10.0F, 3, 8, true, 1),
    OBSIDIAN      (3, 300, 5.0F, 5, 5, true, 0);
    */
}
