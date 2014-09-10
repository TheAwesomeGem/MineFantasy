package minefantasy.client;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 * 
 * Code based off Battlegear Spears by Nerd Boy.
 */
import minefantasy.item.I2HWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

public class MF_BigWeaponRenderer implements IItemRenderer {

	private Minecraft mc;
    private RenderItem itemRenderer;
    private boolean rotate;
    private float scale;
    private float offset;
    
    public MF_BigWeaponRenderer setGreatsword()
    {
    	offset = 1.0F;
    	return this;
    }
    public MF_BigWeaponRenderer setAxe()
    {
    	offset = 1.0F;
    	return this;
    }
    public MF_BigWeaponRenderer setBlunt()
    {
    	offset = 1.2F;
    	return this;
    }
    public MF_BigWeaponRenderer setScythe()
    {
    	offset = 1.8F;
    	return this;
    }
    public MF_BigWeaponRenderer()
    {
    	this(true);
    }
    public MF_BigWeaponRenderer(boolean rot)
    {
    	this(rot, 2.0F);
    }
    public MF_BigWeaponRenderer(boolean rot, float sc)
    {
    	rotate = rot;
    	scale = sc;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type.equals(ItemRenderType.EQUIPPED) || type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        GL11.glPushMatrix();

        if (mc == null) {
            mc = FMLClientHandler.instance().getClient();
            itemRenderer = new RenderItem();
        }
        this.mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        Tessellator tessellator = Tessellator.instance;

        if (type == ItemRenderType.EQUIPPED)
        {
        	GL11.glTranslatef(0.25F*offset, -0.25F*offset, 0);
            GL11.glTranslatef(-1.0F, 0F, 0);
            GL11.glScalef(scale,scale,1);
            Icon icon = item.getIconIndex();

            RenderManager.instance.itemRenderer.renderItemIn2D(tessellator,
            		icon.getMaxU(),
                    icon.getMinV(),
                    icon.getMinU(),
                    icon.getMaxV(),
                    icon.getIconWidth(),
                    icon.getIconHeight(), 1F/16F);
            if (item != null && item.hasEffect(0)) 
            {
            	TextureHelperMF.renderEnchantmentEffects(tessellator);
            }

        }else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) 
        {
        	GL11.glTranslatef(0.25F*offset, -0.25F*offset, 0);
        	GL11.glTranslatef(-0.75F, -0.25F, 0);
            GL11.glScalef(scale,scale,1);
            Icon icon = item.getIconIndex();

            RenderManager.instance.itemRenderer.renderItemIn2D(tessellator,
            		icon.getMaxU(),
                    icon.getMinV(),
                    icon.getMinU(),
                    icon.getMaxV(),
                    icon.getIconWidth(),
                    icon.getIconHeight(), 1F/16F);

            if (item != null && item.hasEffect(0)) {
               TextureHelperMF.renderEnchantmentEffects(tessellator);
            }
        }

        GL11.glPopMatrix();
    }
}