package ru.will0376.Willmod;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.will0376.Willmod.proxys.CommonProxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Mod(
		   modid = Main.MODID,
		   name = Main.NAME,
		   version = Main.VERSION,
		   clientSideOnly = Main.clientSideOnly
		)

public class Main {
	
	   public static final String MODID = "willmod";
	   public static final String NAME = "Willmod";
	   public static final String VERSION = "2.4";
	   public static final boolean clientSideOnly = true; // def: true
	   public static boolean Debugmode; //change before compilation def: false
	   public static final boolean Deleteconfigfordebug = false;//change before compilation def: false
	   public static final boolean Release = true;//change before compilation def: true
	   public static final String cfgDefaultName = MODID + ".cfg";
	   public static String dir;
	   public static Object objJsonUpdList;
	   public static Logger log;

	    @SidedProxy(clientSide =  "ru.will0376.Willmod.proxys.ClientProxy", serverSide = " ru.will0376.Willmod.proxys.ServerProxy")
	    public static CommonProxy proxy;
	    
	    @EventHandler
	    public void preInit(FMLPreInitializationEvent event)
	    {
	        proxy.preInit(event);
	    	String cfdir = event.getModConfigurationDirectory().toString();
	    	dir = cfdir;
	    	config.init(cfdir);
			checkFolders(dir.replace("config","")+"WillmodPng");
			checkFolders(dir.replace("config","")+"WillmodPng/"+"Morning");
			checkFolders(dir.replace("config","")+"WillmodPng/"+"Day");
			checkFolders(dir.replace("config","")+"WillmodPng/"+"Evering");
			checkFolders(dir.replace("config","")+"WillmodPng/"+"Night");
			Debugmode = config.debugmode;
	    	log = event.getModLog();
	    } 

	    @EventHandler
	    public void init(FMLInitializationEvent event)
	    {
	        proxy.init(event);
	    }

	    @EventHandler
	    public void postInit(FMLPostInitializationEvent event)
	    {
	        proxy.postInit(event);
	    }
	    private void checkFolders(String path){
	    	if(!new File(path).exists())
				new File(path).mkdir();
		}

}
