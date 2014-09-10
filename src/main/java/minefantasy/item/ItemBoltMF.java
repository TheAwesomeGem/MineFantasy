package minefantasy.item;

import java.text.DecimalFormat;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.api.weapon.CrossbowAmmo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class ItemBoltMF extends Item
{
	public static final DecimalFormat decimal_format = new DecimalFormat("#.###");
	private Icon[] icons;
	public ItemBoltMF(int id) 
	{
		super(id);
		setHasSubtypes(true);
		setMaxDamage(-1);
		
		addBolts();
		assignBolts();
	}
	
	
	
	private void assignBolts()
	{
		if(BoltType.bolts.isEmpty())
		{
			return;
		}
		
		for(int a = 0; a < BoltType.bolts.size(); a ++)
		{
			BoltType Bolt = BoltType.bolts.get(a);
			
			CrossbowAmmo.addBolt(new ItemStack(itemID, 1, Bolt.meta));
		}
	}
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		for(int a = BoltType.bolts.size()-1; a >= 0; a --)
		{
			BoltType Bolt = BoltType.bolts.get(a);
        	list.add(new ItemStack(itemID, 1, Bolt.meta));
        }
    }
	
	@Override
    public String getItemDisplayName(ItemStack item) 
	{
        int type = item.getItemDamage();
        BoltType Bolt = BoltType.getBolt(type);
		if(Bolt != null && Bolt.getDisplayName() != null)
		{
			return Bolt.getDisplayName();
		}
		return "Bolt";
	}
	
	@Override
    public Icon getIconFromDamage(int id) 
	{
		BoltType Bolt = BoltType.getBolt(id);
		if(Bolt != null && Bolt.getTextureName() != null)
		{
			return icons[Bolt.index];
		}
        return icons[0];
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
		int length = BoltType.bolts.size();
        this.icons = new Icon[length];

        for(int a = 0; a < BoltType.bolts.size(); a ++)
		{
			BoltType Bolt = BoltType.bolts.get(a);
			
            this.icons[a] = reg.registerIcon("MineFantasy:Archery/Arrow/" + Bolt.getTextureName());
        }
    }
	
	@Override
    public void addInformation(ItemStack item, EntityPlayer player, List desc, boolean flag) 
    {
		super.addInformation(item, player, desc, flag);
		
		if(item == null)return;
		
		float dam = 0;
		BoltType bolt = BoltType.getBolt(item.getItemDamage());
		if(bolt != null && bolt.material != null)
		{
			dam = (float)bolt.getDamage(bolt);
		}
		if(dam >= 0)
		{
			desc.add(EnumChatFormatting.BLUE+
			StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 0,
            new Object[] {decimal_format.format(dam),
            StatCollector.translateToLocal("attribute.arrow.damage")}));;
		}
    }
	
	/**
	 * Listed top to bottom on priority
	 * Mat Name, Material, Type, Metadata
	 */
	private void addBolts()
	{
		BoltType.addBolt("Ignotumite", ToolMaterialMedieval.IGNOTUMITE, 8);
		BoltType.addBolt("DeepIron", ToolMaterialMedieval.DEEP_IRON, 9);
		BoltType.addBolt("Dragon", ToolMaterialMedieval.DRAGONFORGE, 7);
		BoltType.addBolt("Encrusted", ToolMaterialMedieval.ENCRUSTED, 6);
		BoltType.addBolt("Silver", ToolMaterialMedieval.ORNATE, 5);
		BoltType.addBolt("Mithril", ToolMaterialMedieval.MITHRIL, 4);
		BoltType.addBolt("Steel", ToolMaterialMedieval.STEEL, 3);
		BoltType.addBolt("Iron", ToolMaterialMedieval.IRON, 2);
		BoltType.addBolt("Bronze", ToolMaterialMedieval.BRONZE, 1);
		BoltType.addBolt("Flint", EnumToolMaterial.WOOD, 2, 0);
	}
}
