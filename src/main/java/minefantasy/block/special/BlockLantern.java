package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.block.tileentity.TileEntityLantern;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockLantern extends BlockContainer {

    public BlockLantern(int i, int n, Material m) {
        super(i, m);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        float offset = 0.25F;
        return AxisAlignedBB.getAABBPool().getAABB((double) ((float) x + offset), (double) y, (double) ((float) z + offset), (double) ((float) (x + 1) - offset), (double) ((float) (y + 1) - offset), (double) ((float) (z + 1) - offset));
    }

    @Override
    public Icon getIcon(int side, int meta)
    {
    	return Block.torchWood.getIcon(side, meta);
    }
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        float partX = (float) x + 0.5F;
        float partY = (float) y + 0.5F + rand.nextFloat() * 6.0F / 16.0F;
        float partZ = (float) z + 0.5F;
        float zRand = rand.nextFloat() * 0.6F - 0.3F;
        float xRand = rand.nextFloat() * 0.6F - 0.3F;
        world.spawnParticle("smoke", (double) (partX + xRand), (double) partY, (double) (partZ + zRand), 0.0D, 0.0D, 0.0D);
        world.spawnParticle("flame", (double) (partX + xRand), (double) partY, (double) (partZ + zRand), 0.0D, 0.0D, 0.0D);

    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
    	float offset = 0.25F;
        return AxisAlignedBB.getAABBPool().getAABB((double) ((float) x + offset), (double) y, (double) ((float) z + offset), (double) ((float) (x + 1) - offset), (double) ((float) (y + 1) - offset), (double) ((float) (z + 1) - offset));
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

  

    @Override
    public void addCreativeItems(ArrayList itemList) {
        itemList.add(new ItemStack(this));
    }
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }
    @Override
    public TileEntity createNewTileEntity(World w) {
        return new TileEntityLantern();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
}
