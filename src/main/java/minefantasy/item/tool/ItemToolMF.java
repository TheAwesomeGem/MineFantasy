package minefantasy.item.tool;

import java.util.List;

import com.google.common.collect.Multimap;

import minefantasy.api.aesthetic.IWeaponrackHangable;
import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemToolMF extends Item implements IWeaponrackHangable 
{
	/** Damage versus entities. */
    public float damageVsEntity;
    
	public ItemToolMF(int id)
	{
		this(id, 1);
	}
	public ItemToolMF(int i, int s)
    {
        super(i);
        maxStackSize = s;
        setCreativeTab(ItemListMF.tabTool);
    }
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		if(tabs != ItemListMF.tabTool)
		{
			super.getSubItems(id, tabs, list);
		}
    }
	
    public ItemToolMF(int i, int s, int d)
    {
        this(i, s);
        setMaxDamage(d);
    }
	@Override
    public boolean isFull3D() {
        return true;
    }
	
    @Override
	public Item setUnlocalizedName(String name)
    {
		this.setTextureName("minefantasy:Tool/"+name);
		return super.setUnlocalizedName(name);
    }
    
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }
	@Override
	public boolean canUseRenderer(ItemStack item) {
		// TODO Auto-generated method stub
		return true;
	}
}
