package ru.will0376.Willmod.Updater;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiUpdated extends GuiScreen {
	private final GuiScreen lastScreen;
	public GuiUpdated(GuiScreen lastScreen) {
		this.lastScreen = lastScreen;
	}
	public void initGui()
	    {
		 this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 6 + 120, I18n.format("Close this gui")));
		 this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 6 + 160, I18n.format("Close Minecraft*")));
	    }
	  protected void actionPerformed(GuiButton bd)
	    {
	        if (bd.enabled)
	        {
	        	if(bd.id == 1) {
	        		Updater.beUpdates = false;
	        		this.mc.displayGuiScreen(null);
	        	}
	        	if(bd.id == 2) {
						openWebpage("https://github.com/Will0376/stuff-for-mc1.12.2");
					this.mc.shutdown();
	        	}	
	        }
	      }
	  public void drawScreen(int mouseX, int mouseY, float partialTicks)
	    {
	        this.drawDefaultBackground();
	        this.drawCenteredString(this.fontRenderer, "[WillMod] A new version has been released!", this.width / 2, 15, 16777215);
	        this.drawCenteredString(this.fontRenderer, "You can close this Gui and play with the old version, or download new version and restart minecraft", this.width / 2, 15 + 20, 16777215);
	        this.drawCenteredString(this.fontRenderer, "The browser will automatically open*", this.width / 2, this.height / 2 + 100, 16777215);
	        super.drawScreen(mouseX, mouseY, partialTicks);
	    }
	  
	  public static boolean openWebpage(String url) {
		try {
			URI uri = new URI(url);
			 Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			    	  desktop.browse(uri);
			            return true;
			    	}
			}
			  catch (URISyntaxException | IOException e1) {
				  	e1.printStackTrace();
		    return false;
				}
		return false;
		}
}

