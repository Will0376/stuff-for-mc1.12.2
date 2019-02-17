package ru.will0376.Willmod.gui;

import java.awt.List;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.Sys;

import akka.event.Logging.Error;
import ibxm.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemClock;
import net.minecraft.item.ItemCompass;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.config;

public class onGuiGuiIngame extends Gui {
	static Minecraft mc = Minecraft.getMinecraft();	
	 public GameSettings gameSettings;	 
	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent.Post event) throws Exception{
		switch(event.getType()) {
		case ALL: {
			ScaledResolution resolution = new ScaledResolution(mc);
			Date date = new Date();
			SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm:ss");
			String color = "";


			if(config.coloredFPS)color = I18n.format("tile.par", new Object[0]) + colorForFPS(getFPS());
			
			//width - x 
			//height - y
			if (!this.mc.gameSettings.showDebugInfo && config.HUD) {
			drawString(this.mc.fontRenderer,I18n.format("tile.time", new Object[0]) + ": " + formatForDateNow.format(date), getx() + 0, 0, 16777215);//draw time
			drawString(this.mc.fontRenderer,I18n.format("tile.fps", new Object[0]) + color + getFPS(), getx() + 0, 0+ 10, 16777215); //draw FPS
			
			//mc.ingameGUI.drawTexturedModalRect(event.getResolution().getScaledWidth() - 20 * 100, 0, 0, 0, 4000, 11);
			renderAll(); //draw slot,arrows
			}
			break;
		}
		default:break;
		}

	}
	/*private ArrayList getNamesBooleans() {
		ArrayList checklist = new ArrayList();
		checklist.add(1,"drawSlots");
		checklist.add(2,"drawArrows");
		return checklist;
	}
	private void getY(String checkname) { //TODO
		Field namef = null;
		ArrayList al = new ArrayList();
		ArrayList checklist = getNamesBooleans();
		boolean search;
		try {
			Class cfgclass = new config().getClass(); 
			search = namef.getBoolean(checkname);
			
				for(Field fld : cfgclass.getFields()) {
					for(Object obj : checklist)
						if(obj.equals(checkname)) {
							
						}
					}
		}
		catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
	}*/
	private void renderAll() {
		drawSlots();
		drawArrows();
		drawPing();
		drawLimiter();
		drawFood_Hp();
		mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.CLOCK),getx()+0 ,50);
		mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.COMPASS),getx()+20 ,50);		
	}
	private void drawPing() {
		try {
		drawString(this.mc.fontRenderer, "Ping: "+ mc.player.connection.getPlayerInfo(mc.player.getGameProfile().getId()).getResponseTime(), getx()+40, 0+ 10, 16777215); 
		 	}
		catch(NullPointerException e) {
			Main.log.error("Player = null!");
		}
		
	}
	private void drawFood_Hp() {
		int hp = Math.round(mc.player.getHealth());
		int food = mc.player.getFoodStats().getFoodLevel();
		if(hp >= 0 && hp <= 5) 
			drawString(this.mc.fontRenderer, I18n.format("tile.par", new Object[0])+"chp: "+ hp, getx()+0 , 0+ 40, 16777215);
			
			else if(hp > 5 && hp <= 15)
				drawString(this.mc.fontRenderer, I18n.format("tile.par", new Object[0])+"6hp: "+ hp, getx()+0 , 0+ 40, 16777215);
			
			else drawString(this.mc.fontRenderer,  I18n.format("tile.par", new Object[0])+"ahp: "+ hp, getx()+0 , 0+ 40, 16777215);
		
		if(food >= 0 && food <= 4) 
			drawString(this.mc.fontRenderer, I18n.format("tile.par", new Object[0])+"c"+I18n.format("HUD.food", new Object[0])+ food, getx()+43 , 0+ 40, 16777215);
			
			else if(food >= 5 && food <= 10)
				drawString(this.mc.fontRenderer, I18n.format("tile.par", new Object[0])+"6"+I18n.format("HUD.food", new Object[0])+ food, getx()+43 , 0+ 40, 16777215);
			
			else drawString(this.mc.fontRenderer,  I18n.format("tile.par", new Object[0])+"a"+I18n.format("HUD.food", new Object[0])+ food, getx()+43 , 0+ 40, 16777215);
		
	}
	private void drawLimiter() {
		try {
			if(getUnicode()) {//on
				
				for(int i = 0;i<44;i++) {//left
					drawString(this.mc.fontRenderer,"|", getx()-2, 0 + i - 1, 16777215);
				}
				for(int i = 0;i<75;i++) {//buttom
					drawString(this.mc.fontRenderer,"-",getx()+ 0+i-2, 0+ 45, 16777215);
				}
				for(int i = 0;i<44;i++) {//right
					drawString(this.mc.fontRenderer,"|",getx()+ 0 + 75, 0 + i - 1, 16777215);
				}
				for(int i = 0;i<75;i++) {//top
					drawString(this.mc.fontRenderer,"-",getx()+ 0+i - 2, 0-5 , 16777215);
				}
			
			}
			else { //off
				for(int i = 0;i<44;i++) {//left
					drawString(this.mc.fontRenderer,"|", getx()-2, 0 + i - 1, 16777215);
				}
				for(int i = 0;i<87;i++) {//buttom
					drawString(this.mc.fontRenderer,"-",getx()+ 0+i-2, 0+ 45, 16777215);
				}
				for(int i = 0;i<44;i++) {//right
					drawString(this.mc.fontRenderer,"|",getx()+ 0 + 87, 0 + i - 1, 16777215);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	private boolean getUnicode() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return mc.isUnicode();
	}	
	public int getFPS() throws IllegalArgumentException, IllegalAccessException{
		int fps = Integer.valueOf(mc.getDebugFPS());
		return fps;
	}
	private String colorForFPS(int fpsc) {
		if (fpsc < 0)
			return "8";
		else if (fpsc < 20) {
			return "4";
		}
		else if (fpsc < 30) {
			return "c";
		}
		else if (fpsc <= 60) {
			return "6";
		}
		else 
			return "a";
	}
	

		
		private void drawSlots() {
        	int all_slot = getInventoryCount();
        	try {
        	String colorslot = "";
            if(all_slot >= 0 && all_slot <= 20) {
            	colorslot = "a";
            }
            else if(all_slot >= 21 && all_slot <= 30) {
            	colorslot = "6";
            }
            else if(all_slot >= 31 && all_slot <= 35) {
            	colorslot = "c";
            }
            else {
            	colorslot = "4";
            }
            if(config.drawSlots)
        	drawString(this.mc.fontRenderer,I18n.format("HUD.slots", new Object[0]) +I18n.format("tile.par", new Object[0])+colorslot+ all_slot + "/36(" + (36 - all_slot)+")", getx()+0 , 0+ 20, 16777215);
        	}
        	catch(Exception e) {
        		Main.log.error("[DrawSlots]lol, i catch java.lang.NullPointerException. Player = null?");
			}
	}
		private void drawArrows() {
			int all = getArrowCount();		
				if(mc.player != null ) {                  
					//ScaledResolution sr = new ScaledResolution(mc);
			            String colorarr = "";
			            if(all == 0) {
			            	colorarr = "4";
			            }
			            else  if (all >= 1 && all <= 10) {
			            	colorarr = "c";
			            }
			            else if(all >= 11 && all <= 20) {
			            	colorarr = "6";
			            }
			            else {
			            	colorarr = "a";
			            }
			            if(config.drawArrows && !mc.player.isCreative()) {
						drawString(this.mc.fontRenderer,I18n.format("HUD.arrows", new Object[0]) +I18n.format("tile.par", new Object[0])+colorarr+ getArrowCount() + "("+(all/64)+")",getx()+ 0 , 0+ 30, 16777215);
			            }
			            else if(config.drawArrows) {
						drawString(this.mc.fontRenderer,I18n.format("HUD.arrows", new Object[0]) +I18n.format("tile.par", new Object[0])+"a"+I18n.format("tile.inf", new Object[0])+ "("+I18n.format("tile.inf", new Object[0])+")",getx()+ 0 , 0+ 30, 16777215);
			            }
					    if(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.BOW && config.drawArrowInTheCenter) {
					    	drawCenteredString(mc.fontRenderer, getArrowCount()+"", new ScaledResolution(mc).getScaledWidth() / 2, new ScaledResolution(mc).getScaledHeight() / 2 - 7 + 16 + 10, 16777215);
					    }
					}
				}
		
		
		public int getx() {
			if (!config.cheat) {
				return 0;
			}
			else return 90;
		}
		private static int getArrowCount() {
		    int c = 0;
		    try {
		    for(int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
		        if(mc.player.inventory.getStackInSlot(i).getItem() == Items.ARROW)
		        	c += mc.player.inventory.getStackInSlot(i).getCount();
		    		}  		    
		    }
			catch(Exception e) {
				Main.log.error("[DrawArrows]lol, i catch java.lang.NullPointerException. Player = null?");
			}
		    return c;
		}
		private static int getInventoryCount() {
		    int c = 0;
		    try {
		    for(int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
		        if(mc.player.inventory.getStackInSlot(i).getItem() != Items.AIR)
		        	c++;
		    	}
		    if(mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() != Items.AIR) {
		    	c--;
		    	}
		    for(int i = 0; i < mc.player.inventory.armorInventory.size();i++) {
		    	if(mc.player.inventory.armorItemInSlot(i).getItem() != Items.AIR) {
		    		c--;
		    		}
		   		}		    
		    }
			catch(Exception e) {
				Main.log.error("[DrawInventory]lol, i catch java.lang.NullPointerException. Player = null?");
			}
		    return c;
		}
}
