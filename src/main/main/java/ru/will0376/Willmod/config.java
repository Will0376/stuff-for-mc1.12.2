package ru.will0376.Willmod;

import static java.lang.Math.toIntExact;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class config {
	/**    
	 * init() - initializes a config, loading it. If the config doesn't exist - creates it.
	 * !!!private!!! createConfig() - creates a config
	 *  saveConfig() - saves the current config
	 *  loadConfig() - loads the config
	 */
	private static File dirr;
		static void init(String dir) {
			File fl = new File(dir+"/"+Main.cfgDefaultName);
			dirr = fl;
			
			if(Main.Deleteconfigfordebug) {
				createConfig(fl); 
				Main.log.info("=== Creating a new config for debugging ===");
			}
		if(!fl.exists()) {
				createConfig(fl);
				System.out.println("=== Creating a new config ===");
		}
		
		loadConfig(fl);
		
		}
		private static void createConfig(File fl) {
			try {
				fl.createNewFile();
				FileWriter writer = new FileWriter(fl);
				writer.write(buildJson());
				writer.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
		}
		
		public static void saveConfig(String dir) {
			
			File fl = new File(dir+"/"+Main.MODID+".cfg");
			try {
				FileWriter writer = new FileWriter(fl);
				writer.write(buildJson());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		private static void loadConfig(File fl) {
			  JSONParser parser = new JSONParser();
			  try {
				Object obj = parser.parse(new FileReader(fl));
				loadJson(obj,fl);
			} catch (IOException | ParseException e) {
				FMLLog.log.error("=== Hmm, Error: "+e.getClass().getSimpleName()+" StackTrace:  ===");
				e.printStackTrace();
				FMLLog.log.error("=== === === === === === === === ===  ===");
				FMLLog.log.error("=== Recreated Config file ===");
				createConfig(fl); 
				FMLLog.log.error("=== Done ===");
				FMLLog.log.error("=== Reload Config file ===");
				loadConfig(fl);
				FMLLog.log.error("=== Done ===");
			}
		}
		//all booleans
		public static boolean HUD = true;
		///
		public static boolean coloredFPS = true;
		public static boolean drawSlots = true;
		public static boolean drawArrows = true;
		///
		public static boolean drawArrowInTheCenter = true;
		public static boolean renderExp = true;
		public static boolean cheat = false;
		public static boolean useLLW = false;
		public static boolean enabledUpdater = false;
		public static boolean debugmode = false;
		//end

		private static String buildJson() {
			 JSONObject jo = new JSONObject();
			jo.put("debugmode", debugmode);
			 jo.put("HUD", HUD);
			 jo.put("coloredFPS",coloredFPS);
			 jo.put("drawSlots",drawSlots);
			 jo.put("drawArrows",drawArrows);
			 jo.put("drawArrowInTheCenter",drawArrowInTheCenter);
			 jo.put("renderExp", renderExp);
			 jo.put("cheat", cheat);
			 jo.put("useLLW", useLLW);
			 jo.put("enabledUpdater", enabledUpdater);
			 return jo.toJSONString();
		}
		

		private static void loadJson(Object obj,File fl) {
			JSONObject jsonObject = (JSONObject) obj;
			try {
			HUD = (boolean) jsonObject.get("HUD");
			coloredFPS = (boolean) jsonObject.get("coloredFPS");
			drawSlots = (boolean) jsonObject.get("drawSlots");
			drawArrows = (boolean) jsonObject.get("drawArrows");
			drawArrowInTheCenter = (boolean) jsonObject.get("drawArrowInTheCenter");
			renderExp = (boolean) jsonObject.get("renderExp");
			cheat = (boolean) jsonObject.get("cheat");
			useLLW = (boolean) jsonObject.get("useLLW");
			enabledUpdater = (boolean) jsonObject.get("enabledUpdater");
			debugmode = (boolean) jsonObject.get("debugmode");
			}
			catch(NullPointerException | NumberFormatException | ClassCastException e) {
				if(e.getClass().getSimpleName().equals("ClassCastException")) {
					FMLLog.log.error("[Wmod]=== Hmm, Сritical error: "+e.getClass().getSimpleName()+" StackTrace:  ===");
					e.printStackTrace();
					FMLLog.log.error("[Wmod]=== === === === === === === === ===  ===");
						throw new ReportedException(new CrashReport("Please, tell to Will0376 about this crash", e));
				
				}
				else {
				FMLLog.log.error("[Wmod]=== Hmm, Error: "+e.getClass().getSimpleName()+" StackTrace:  ===");
				e.printStackTrace();
				FMLLog.log.error("[Wmod]=== === === === === === === === ===  ===");
				FMLLog.log.error("[Wmod]=== Recreated Config file ===");
				createConfig(fl); 
				FMLLog.log.error("[Wmod]=== Done ===");
				FMLLog.log.error("[Wmod]=== Reload Config file ===");
				loadConfig(fl);
				FMLLog.log.error("[Wmod]=== Done ===");
				}
			}
		}
}
