package minefantasy.client.entityrender;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.client.TextureHelperMF;
import minefantasy.entity.EntityDragonSmall;
import minefantasy.entity.EntitySkeletalKnight;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

@SideOnly(Side.CLIENT)
public class RenderSmallDragon extends RenderLiving
{
    public RenderSmallDragon(ModelBase model, float shadow)
    {
        super(model, shadow);
    }
    
    @Override
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
    	if(entity instanceof EntityDragonSmall)
    	{
    		return TextureHelperMF.getResource(((EntityDragonSmall)entity).getTexture());
    	}
		return TextureHelperMF.getResource(data_minefantasy.image("/mob/dragonRed.png"));
	}
}
