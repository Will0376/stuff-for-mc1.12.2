package ru.will0376.Willmod.proxys;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.UpdLogLoading;
import ru.will0376.Willmod.Updater.Updater;
import ru.will0376.Willmod.events.RegKey;
import ru.will0376.Willmod.events.events;
import ru.will0376.Willmod.gui.onGuiGuiIngame;
import ru.will0376.Willmod.render.RenderXPOrbWM;

public class ClientProxy extends CommonProxy  {//Client
	
	   public void preInit(FMLPreInitializationEvent event) {
		   super.preInit(event);
		   
		   RegKey.preInit();  
		   Main.objJsonUpdList =  UpdLogLoading.LoadJson("https://raw.githubusercontent.com/Will0376/stuff-for-mc1.12.2/master/Upd.Log/Upd.json");
	    }

	    public void init(FMLInitializationEvent event) {
	    	super.init(event);
	     
	    	MinecraftForge.EVENT_BUS.register(this);
	    	MinecraftForge.EVENT_BUS.register(new events());
	    }

	    public void postInit(FMLPostInitializationEvent event) {
	    	super.postInit(event);
	    	MinecraftForge.EVENT_BUS.register(new onGuiGuiIngame());
	    	Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityXPOrb.class, new RenderXPOrbWM(Minecraft.getMinecraft().getRenderManager()));
	    	new	Updater().init();
	    }
}
