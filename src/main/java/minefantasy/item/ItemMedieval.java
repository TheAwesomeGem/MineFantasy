package minefantasy.item;
import minefantasy.system.data_minefantasy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemMedieval extends Item{
    public boolean FullSize;
    public boolean using;
    public ItemMedieval(int i, boolean b, int s)
    {
        super(i);
        setCreativeTab(CreativeTabs.tabMaterials);
        FullSize = b;
        maxStackSize = (int)Math.max(1, s);
    }
    public ItemMedieval(int i, boolean b, int s, int d)
    {
        this(i, b, s);
        setMaxDamage(d);
    }
    @Override
    public boolean isFull3D() {
        return FullSize;
    }
    @Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Misc/"+name);
		return super.setUnlocalizedName(name);
    }
}
