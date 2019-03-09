package ru.will0376.Willmod.ConfigGui;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.config;
import ru.will0376.api.gui.defGui;

public class ConfigHUDGui extends defGui {    
	private GuiTextField text;
	public ConfigHUDGui(GuiScreen back) {
		super(back);
	}
	@Override
	 public void initGui()
	    {
	        this.buttonList.clear();
	        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 5, this.height / 2  + 55, 176, 20, I18n.format("Back", new Object[0])));
	        if(Main.Debugmode)this.buttonList.add(new GuiButton(100, (this.width - this.xSize) / 2 - 25, this.height /2 + 30 , 60, 20, I18n.format("update gui", new Object[0])));
	        this.buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2, this.height /2 - 50, 70, 20, I18n.format(guiMods.enabled(config.coloredFPS)+"Colored FPS", new Object[0])));
	        this.buttonList.add(new GuiButton(2, (this.width - this.xSize) / 2 , this.height /2  , 70, 20, I18n.format(guiMods.enabled(config.drawSlots)+"Draw Slots", new Object[0])));
	        this.buttonList.add(new GuiButton(3, (this.width - this.xSize) / 2+120, this.height /2 - 50 , 70, 20, I18n.format(guiMods.enabled(config.drawArrows)+"Draw Arrows", new Object[0])));
	        this.buttonList.add(new GuiButton(5, (this.width - this.xSize) / 2+105, this.height /2, 100, 20, I18n.format(guiMods.enabled(config.drawArrowInTheCenter)+"Draw Arrow In The Center", new Object[0])));
	        
	       this.buttonList.add(new GuiButton(4, (this.width - this.xSize) / 2-20, this.height /2 - 75 , 50, 20, I18n.format(guiMods.enabled(config.cheat)+"From edge", new Object[0])));
	     //TODO this.buttonList.add(new GuiButton(5, (this.width - this.xSize) / 2+20, this.height /2 - 75 , 20, 20, I18n.format("UL", new Object[0]))); 
	       //TODO this.buttonList.add(new GuiButton(5, (this.width - this.xSize) / 2+185, this.height /2 - 75 , 20, 20, I18n.format("UR", new Object[0]))); 
	       //TODO this.buttonList.add(new GuiButton(6, (this.width - this.xSize) / 2-20, this.height /2 + 30 , 20, 20, I18n.format("DL", new Object[0])));
	       //TODO this.buttonList.add(new GuiButton(7, (this.width - this.xSize) / 2+185, this.height /2 + 30 , 20, 20, I18n.format("DR", new Object[0]))); 
	        
	    }
    
	@Override
	  protected void actionPerformed(GuiButton g)
	    {
		  if (g.enabled)
	        {
	            if (g.id == 0)//back to menu
	            {
	                this.mc.displayGuiScreen(this.back);
	                
	            }
	          if (g.id == 100) //Update
	            {
	            	this.mc.displayGuiScreen(this);  	
	            }
	           if (g.id == 1)//Colored FPS
	            {
	        	   if(!config.coloredFPS)
	            		config.coloredFPS = true;          		
	            	else
	            		config.coloredFPS = false;  
						config.saveConfig(Main.dir);
	            	this.mc.displayGuiScreen(this);	  
	            }          
	            if (g.id == 2)//Draw Slots
	            {
	            	if(!config.drawSlots)
	            		config.drawSlots = true;          		
	            	else
	            		config.drawSlots = false;  
						config.saveConfig(Main.dir);
	            	this.mc.displayGuiScreen(this);	 
	            	  }
	            if (g.id == 3)//Draw Arrows
	            {
	            	if(!config.drawArrows)
	            		config.drawArrows = true;          		
	            	else
	            		config.drawArrows = false;  
						config.saveConfig(Main.dir);
	            	this.mc.displayGuiScreen(this);	 
	            }
	            if(g.id == 4) {//For cheaters ;)
	            	if(!config.cheat)
	            		config.cheat = true;          		
	            	else
	            		config.cheat = false;  
						config.saveConfig(Main.dir);
	            	this.mc.displayGuiScreen(this);	 
	            }
	            if(g.id == 5) {//Draw Arrow In The Center
	            	if(!config.drawArrowInTheCenter)
	            		config.drawArrowInTheCenter = true;          		
	            	else
	            		config.drawArrowInTheCenter = false;  
						config.saveConfig(Main.dir);
	            	this.mc.displayGuiScreen(this);	 
	            }
	        }
	   }
	@Override
	    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	    {
		    int x = (this.width - this.xSize) / 2;
		    int y = (this.height - this.ySize) / 2;
		    this.x = x;
		    this.y = y; 
		    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		    GL11.glDisable(2896);
		    super.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/demo_background.png"));
	        this.drawTexturedModalRect(x - 30, y, 0, 0, this.xSize + 80, this.ySize);
	        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);	        
	        drawString(this.mc.fontRenderer, I18n.format("tile.par", new Object[0])+"e"+"Settings HUD", x + 65, y + 10, 16777215);
	        drawString(this.mc.fontRenderer, "Enabled: "+guiMods.enabled(config.HUD)+config.HUD, x+ 65, y+125, 16777215); 
	    }
}
