package ru.will0376.Willmod.ConfigGui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.config;
import ru.will0376.api.gui.defGui;

class CheatsMenu extends defGui{
	public static int str = 1;
    private int str_max = 2;
    Minecraft mc = Minecraft.getMinecraft();
	public CheatsMenu(GuiScreen back) {
		super(back);
	}
	@Override
	 public void initGui()
	    {//Use 0>100 for default button, 
		 //use -100<-1 for add button
	        this.buttonList.clear();
	        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 - 20, this.height / 2 - 20 , 30, 20, I18n.format("Up", new Object[0])));
	        this.buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2 - 20, this.height /2 - 75 , 30, 20, I18n.format("Close", new Object[0])));
	        this.buttonList.add(new GuiButton(-1, (this.width - this.xSize) / 2 + 20, this.height /2 - 75 , 30, 20, I18n.format("Back", new Object[0])));
	        this.buttonList.add(new GuiButton(2, (this.width - this.xSize) / 2 - 20, this.height / 2  + 18, 30, 20, I18n.format("Down", new Object[0])));
	        if(str == 1) {
	        	if((Main.Debugmode && config.cheat))
				this.buttonList.add(new GuiButton(3, this.width /2 - 70 , this.height / 2 - 40 , 90, 20, I18n.format(enabledFB() +"FullBright(WIP)", new Object[0])));
				else
	        	this.buttonList.add(new GuiButton(1232, this.width /2 - 70 , this.height / 2 - 40 , 90, 20, I18n.format("gui.f1", new Object[0])));
		        this.buttonList.add(new GuiButton(4, this.width /2 - 70 , this.height /2 , 90, 20, I18n.format(enabled(config.renderExp)+"Render Exp", new Object[0])));
	        	this.buttonList.add(new GuiButton(5, this.width /2 - 70 , this.height / 2  + 40, 90, 20, I18n.format("gui.f3", new Object[0])));
	            /**adding menu
	             * 
	             */
	        	//this.buttonList.add(new GuiButton(-2, this.width /2  , this.height / 2 - 40 , 20, 20, I18n.format("...", new Object[0])));
	        	//this.buttonList.add(new GuiButton(-3, this.width /2  , this.height / 2 - 40 , 20, 20, I18n.format("...", new Object[0])));
	        	 /**
	             * 
	             */
	        	this.buttonList.add(new GuiButton(6, this.width /2+ 30, this.height / 2 - 40 , 90, 20, I18n.format("gui.f4", new Object[0])));
		        this.buttonList.add(new GuiButton(7, this.width /2+ 30, this.height /2 , 90, 20, I18n.format("gui.f5", new Object[0])));
	        	this.buttonList.add(new GuiButton(8, this.width /2+ 30, this.height / 2  + 40, 90, 20, I18n.format("gui.f6", new Object[0])));
	        }
	        else if(str == 2) {
	        	 this.buttonList.add(new GuiButton(9, this.width /2 - 70 , this.height / 2 - 40 , 90, 20, I18n.format("gui.f7", new Object[0])));
			        this.buttonList.add(new GuiButton(10, this.width /2 - 70 , this.height /2 , 90, 20, I18n.format("gui.f8", new Object[0])));
		        	this.buttonList.add(new GuiButton(11, this.width /2 - 70 , this.height / 2  + 40, 90, 20, I18n.format("gui.f9", new Object[0])));
		        	
			        this.buttonList.add(new GuiButton(12, this.width /2+ 30, this.height / 2 - 40 , 90, 20, I18n.format("gui.f10", new Object[0])));
			        this.buttonList.add(new GuiButton(13, this.width /2+ 30, this.height /2 , 90, 20, I18n.format("gui.f11", new Object[0])));
		        	this.buttonList.add(new GuiButton(14, this.width /2+ 30, this.height / 2  + 40, 90, 20, I18n.format("gui.f12", new Object[0])));
	        }
	    }
	 @Override
	  protected void actionPerformed(GuiButton g)
	    {
		  if (g.enabled)
	        {
	            if (g.id == 0) {//up
	            	canupdown(1);
	                this.mc.displayGuiScreen(this);
	            }
	            if (g.id == 1) {//exit
	            	this.mc.displayGuiScreen((GuiScreen)null);	   	
	            }
	            if (g.id == 2) {//down
	            	canupdown(2);
	            	this.mc.displayGuiScreen(this);	 
	            }
	            /**
	             * 
	             */
	            if(g.id == -1) {
	            	this.mc.displayGuiScreen(back);
	            }
	            
	            if(g.id == -2) {
	            }
	            /**
	             * 
	             */
	            if(g.id == 3) {//FullBright
	            	if(config.cheat) {
						if (mc.gameSettings.gammaSetting == 16)
							mc.gameSettings.gammaSetting = 1;
						else mc.gameSettings.gammaSetting = 16;
					}
	            	this.mc.displayGuiScreen(this);
	            }
	            if(g.id == 4) {
					config.renderExp = !config.renderExp;
					config.saveConfig(Main.dir);
					this.mc.displayGuiScreen(this);
				}
	            
	            if(g.id == 5) {
	            }
	            if(g.id == 6) {
	            	
	            	
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
	        drawString(this.mc.fontRenderer,I18n.format("tile.par", new Object[0])+"6"+I18n.format("tile.par", new Object[0])+"L"+ str + "/" + str_max, x - 14 , y + 86, 16777215);	 
	       if(Main.Debugmode) drawString(this.mc.fontRenderer,I18n.format("tile.par", new Object[0])+"6"+"DEBUG ENABLED!", x - 14 , y + 150, 16777215);	 
				        
	        
	    }	
	    public void canupdown(int i) {
	    	//1 - up
	    	//2 - down
	    	if(i == 1 &&(str != str_max)) {
	    		str++;
	    	}
	    	else if(i == 2 &&(str != 1)) {
	    		str--;
	    	}
	    	else if(str == 0) {
	    		str = 1;
	    	}
	    	else if(str == str_max)
	    	{
	    		str = str_max;
	    	}
	    }
	    public static String enabled(boolean b) {
	    	if(b) {
	    		String i = I18n.format("tile.par", new Object[0])+"a";
	    		return  i;
	    		
	    	}
	    	else {
	    		return I18n.format("tile.par", new Object[0])+"4";
	    	}
	    	
	    }
	    private String enabledFB() {
	    	if(mc.gameSettings.gammaSetting == 16) 
	    		return I18n.format("tile.par", new Object[0])+"a";
        	else
        		return I18n.format("tile.par", new Object[0])+"4";
	    }

}
 