package minefantasy.system;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class data_minefantasy {
    public static String game_version = "M - 1.6.4";
    public static String baseImages ="textures";
    public static String armour = "minefantasy:textures/armour/";
    public static String soundDir = "minefantasy:";
    
    public static String image(String s)
    {
        return baseImages + s;
    }
    public static String sound(String s)
    {
        return soundDir + s;
    }
    public static String getSoundDir(String s)
    {
    	return soundDir + s;
    }
}
