package minefantasy.block;

import java.util.Random;

import minefantasy.system.data_minefantasy;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BlockIgnotumite extends BlockMedieval{

	public BlockIgnotumite(int i, int n, Material m) {
		super(i, m);
		
        this.setTickRandomly(true);
        this.disableStats();
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
		if (rand.nextInt(20) == 0)
        {
			world.playSound((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, data_minefantasy.sound("Ignotumite"), 2.5F, rand.nextFloat() * 0.4F + 0.5F, true);
        }
    }
}
