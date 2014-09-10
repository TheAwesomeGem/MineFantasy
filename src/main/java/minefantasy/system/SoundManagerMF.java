package minefantasy.system;

import java.text.DecimalFormat;

import minefantasy.MineFantasyBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class SoundManagerMF
{
    @ForgeSubscribe
    public void onSound(SoundLoadEvent event)
    {
        try
        {
        	addSound(event, "mob/basiliskWalk.wav");
        	addSound(event, "mob/hiss1.wav");
        	addSound(event, "mob/hiss2.wav");
        	addSound(event, "mob/hiss3.wav");
        	
        	addSound(event, "mallet_use.wav");
        	addSound(event, "mallet_build.wav");
        	
        	addSound(event, "furnaceOpen.wav");
        	addSound(event, "furnaceClose.wav");
        	
        	addSound(event, "repair1.wav");
        	addSound(event, "repair2.wav");
        	addSound(event, "repair3.wav");
        	
            addSound(event, "Ignotumite.wav");
            addSound(event, "spearThrow.wav");
            addSound(event, "Throw2.wav");  
            addSound(event, "Throw1.wav");  
            
            addSound(event, "bellows.wav"); 
            addSound(event, "grinder.wav");
            addSound(event, "sharp.wav");
            addSound(event, "updateJournal.wav");
            
            
            addSound(event, "Tanning.wav");  
            addSound(event, "enchant.wav");  
            addSound(event, "divine.wav");  
            
            addSound(event, "AnvilFail1.wav");  
            addSound(event, "AnvilFail2.wav");  
            addSound(event, "AnvilFail3.wav");  
            
            addSound(event, "AnvilSucceed1.wav");  
            addSound(event, "AnvilSucceed2.wav");  
            addSound(event, "AnvilSucceed3.wav");  
            
            
            
            addSound(event, "mob/minotaurLive.wav");  
            addSound(event, "mob/minotaurHurt.wav");  
            addSound(event, "mob/minotaurDie.wav");  
            
            addSound(event, "mob/flap1.wav");  
            addSound(event, "mob/dragonhurt1.wav"); 
            addSound(event, "mob/dragon3.wav");  
            addSound(event, "mob/dragon2.wav");  
            addSound(event, "mob/dragon1.wav");  
            addSound(event, "mob/breatheFire1.wav");  
            addSound(event, "mob/bite3.wav");  
            addSound(event, "mob/bite2.wav");  
            addSound(event, "mob/bite1.wav"); 
            
            addSound(event, "mob/drakeidle1.wav");
            addSound(event, "mob/drakeidle2.wav");
            addSound(event, "mob/drakeidle3.wav");
            addSound(event, "mob/drakeidle4.wav");
            addSound(event, "mob/drakestep1.wav");
            addSound(event, "mob/drakestep2.wav");
            addSound(event, "mob/drakestep3.wav");
            addSound(event, "mob/drakestep4.wav");
            addSound(event, "mob/drakehurt1.wav");
            addSound(event, "mob/drakehurt2.wav");
            addSound(event, "mob/drakehurt3.wav");
            addSound(event, "mob/drakedie1.wav");
            addSound(event, "mob/drakedie2.wav");
            addSound(event, "mob/drakedie3.wav");
            
            addSound(event, "Weapon/crit.wav"); 
            addSound(event, "Weapon/bombBounce.wav"); 
            addSound(event, "Weapon/pulverise.wav"); 
            addSound(event, "Weapon/crossbow.wav"); 
            
            addSound(event, "Weapon/hit/blunt/metal_1.wav"); 
            addSound(event, "Weapon/hit/blunt/stone_1.wav"); 
            addSound(event, "Weapon/hit/blunt/wood_1.wav"); 
            
            addSound(event, "Weapon/hit/blade/metal_1.wav"); 
            addSound(event, "Weapon/hit/blade/stone_1.wav"); 
            addSound(event, "Weapon/hit/blade/wood_1.wav"); 
            
            
            event.manager.soundPoolSounds.addSound("minefantasy:oldbow.ogg");
            System.out.println("MineFantasy: Loaded sounds : " + "/AP_Audio/sound/MineFantasy/");
       
        }
        catch (Exception e)
        {
            System.err.println("MineFantasy: Failed to load sounds");
        }
    }
    
	private void addSound(SoundLoadEvent event, String string) {
		event.manager.soundPoolSounds.addSound(data_minefantasy.sound(string));
	}
}