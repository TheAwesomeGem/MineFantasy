package minefantasy.item;

import java.text.DecimalFormat;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.api.arrow.Arrows;
import minefantasy.api.weapon.CrossbowAmmo;
import minefantasy.entity.EntityArrowMF;
import minefantasy.system.ArrowsMF;
import mods.battlegear2.api.quiver.QuiverArrowRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemArrowMF extends Item
{
	public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
	private Icon[] icons;
	public ItemArrowMF(int id) 
	{
		super(id);
		setHasSubtypes(true);
		setMaxDamage(-1);
		
		addArrows();
		assignArrows();
	}
	
	
	
	private void assignArrows()
	{
		if(ArrowType.arrows.isEmpty())
		{
			return;
		}
		
		for(int a = 0; a < ArrowType.arrows.size(); a ++)
		{
			ArrowType arrow = ArrowType.arrows.get(a);
			
			ArrowsMF.addArrow(new ItemStack(itemID, 1, arrow.meta));
			CrossbowAmmo.addArrow(new ItemStack(itemID, 1, arrow.meta));
			Arrows.addArrow(new ItemStack(itemID, 1, arrow.meta));
			QuiverArrowRegistry.addArrowToRegistry(new ItemStack(itemID, 1, arrow.meta), null);
		}
	}
	public void getSubItems(int id, CreativeTabs tabs, List list)
    {
		for(int a = ArrowType.arrows.size()-1; a >= 0; a --)
		{
			ArrowType arrow = ArrowType.arrows.get(a);
        	list.add(new ItemStack(itemID, 1, arrow.meta));
        }
    }
	
	@Override
    public String getUnlocalizedName(ItemStack item)
	{
		int type = item.getItemDamage();
        ArrowType arrow = ArrowType.getArrow(type);
		if(arrow != null && arrow.getUnlocalisedDisplayName() != null)
		{
			return arrow.getUnlocalisedDisplayName();
		}
		return "item.arrow";
    }
	
	@Override
    public Icon getIconFromDamage(int id) 
	{
		ArrowType arrow = ArrowType.getArrow(id);
		if(arrow != null && arrow.getTextureName() != null)
		{
			return icons[arrow.index];
		}
        return icons[0];
    }
	
	@Override
    public void addInformation(ItemStack item, EntityPlayer player, List desc, boolean flag) 
    {
		super.addInformation(item, player, desc, flag);
		
		if(item == null)return;
		
		float dam = 0;
		ArrowType arrow = ArrowType.getArrow(item.getItemDamage());
		if(arrow != null && arrow.material != null)
		{
			dam = (float)ArrowType.getDamage(arrow);
		}
		if(dam > 0)
		{
			desc.add(EnumChatFormatting.BLUE+
			StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 0,
            new Object[] {decimal_format.format(dam*(8F/2.75F)),
            StatCollector.translateToLocal("attribute.arrow.damage")}));;
		}
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
		int length = ArrowType.arrows.size();
        this.icons = new Icon[length];

        for(int a = 0; a < ArrowType.arrows.size(); a ++)
		{
			ArrowType arrow = ArrowType.arrows.get(a);
			
            this.icons[a] = reg.registerIcon("MineFantasy:Archery/Arrow/" + arrow.getTextureName());
        }
    }
	
	/**
	 * Listed top to bottom on priority
	 * Mat Name, Material, Type, Metadata
	 */
	private void addArrows()
	{
		ArrowType.addArrow("Ignotumite", ToolMaterialMedieval.IGNOTUMITE, 2, 24);
		ArrowType.addArrow("Ignotumite", ToolMaterialMedieval.IGNOTUMITE, 1, 23);
		ArrowType.addArrow("Ignotumite", ToolMaterialMedieval.IGNOTUMITE, 0, 22);
		
		ArrowType.addArrow("DeepIron", ToolMaterialMedieval.DEEP_IRON, 2, 27);
		ArrowType.addArrow("DeepIron", ToolMaterialMedieval.DEEP_IRON, 1, 26);
		ArrowType.addArrow("DeepIron", ToolMaterialMedieval.DEEP_IRON, 0, 25);
		
		ArrowType.addArrow("Dragon", ToolMaterialMedieval.DRAGONFORGE, 2, 21);
		ArrowType.addArrow("Dragon", ToolMaterialMedieval.DRAGONFORGE, 1, 20);
		ArrowType.addArrow("Dragon", ToolMaterialMedieval.DRAGONFORGE, 0, 19);
		
		ArrowType.addArrow("Encrusted", ToolMaterialMedieval.ENCRUSTED, 2, 18);
		ArrowType.addArrow("Encrusted", ToolMaterialMedieval.ENCRUSTED, 1, 17);
		ArrowType.addArrow("Encrusted", ToolMaterialMedieval.ENCRUSTED, 0, 16);
		
		ArrowType.addArrow("Silver", ToolMaterialMedieval.ORNATE, 2, 15);
		ArrowType.addArrow("Silver", ToolMaterialMedieval.ORNATE, 1, 14);
		ArrowType.addArrow("Silver", ToolMaterialMedieval.ORNATE, 0, 13);
		
		ArrowType.addArrow("Mithril", ToolMaterialMedieval.MITHRIL, 2, 12);
		ArrowType.addArrow("Mithril", ToolMaterialMedieval.MITHRIL, 1, 11);
		ArrowType.addArrow("Mithril", ToolMaterialMedieval.MITHRIL, 0, 10);
		
		ArrowType.addArrow("Steel", ToolMaterialMedieval.STEEL, 2, 9);
		ArrowType.addArrow("Steel", ToolMaterialMedieval.STEEL, 1, 8);
		ArrowType.addArrow("Steel", ToolMaterialMedieval.STEEL, 0, 7);
		
		ArrowType.addArrow("Iron", ToolMaterialMedieval.IRON, 2, 6);
		ArrowType.addArrow("Iron", ToolMaterialMedieval.IRON, 1, 5);
		ArrowType.addArrow("Iron", ToolMaterialMedieval.IRON, 0, 4);
		
		ArrowType.addArrow("Bronze", ToolMaterialMedieval.BRONZE, 2, 3);
		ArrowType.addArrow("Bronze", ToolMaterialMedieval.BRONZE, 1, 2);
		ArrowType.addArrow("Bronze", ToolMaterialMedieval.BRONZE, 0, 1);
		
		ArrowType.addArrow("Reed", EnumToolMaterial.WOOD, 0, 0);
	}
	public boolean isBroad(ItemStack item)
	{
		if(item != null)
		{
			ArrowType arrow = ArrowType.getArrow(item.getItemDamage());
			if(arrow != null && arrow.arrowType == 2)
			{
				return true;
			}
		}		
		return false;
	}
}
