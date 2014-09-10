package minefantasy.item.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class ItemVanillaBow extends ItemBow{

	public ItemVanillaBow(int id) {
		super(id);
	}
	
	public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int time)
    {
        int power = (this.getMaxItemUseDuration(item) - time);
        power *= model.speed; // Speeds up the power in relation to ticks used
        
        power = (int)((float)power / 20F * getMaxPower());//scales the power down from full
        
        if(power > getMaxPower())power = (int)getMaxPower();//limits the power to max
        
        ArrowLooseEvent event = new ArrowLooseEvent(player, item, power);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        power = event.charge;
        
        boolean var5 = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;

        if (var5 || player.inventory.hasItem(Item.arrow.itemID))
        {
            float var7 = (float)power / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if ((double)var7 < 0.1D)
            {
                return;
            }

            if (var7 > 1.0F)
            {
                var7 = 1.0F;
            }

            EntityArrow var8 = new EntityArrow(world, player, var7 * 2.0F);

            if (var7 == 1.0F)
            {
                var8.setIsCritical(true);
            }

            int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);

            if (var9 > 0)
            {
                var8.setDamage(var8.getDamage() + (double)var9 * 0.5D + 0.5D);
            }

            int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);

            if (var10 > 0)
            {
                var8.setKnockbackStrength(var10);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0)
            {
                var8.setFire(100);
            }

            item.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

            if (var5)
            {
                var8.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Item.arrow.itemID);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(var8);
            }
        }
    }

	/**
     * Gets the power of the bow
     * 20 is the power of V bows(max)
     */
    private float getMaxPower() 
    {
    	return 20F * model.power;
	}
    private final EnumBowType model = EnumBowType.SHORTBOW;
}
