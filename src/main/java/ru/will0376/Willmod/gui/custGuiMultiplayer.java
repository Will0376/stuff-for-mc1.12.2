package ru.will0376.Willmod.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ServerListEntryLanScan;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.resources.I18n;

public class custGuiMultiplayer extends GuiMultiplayer { //for tests
	GuiButton btnEditServer;
	    GuiButton btnDeleteServer;
	    GuiButton btnSelectServer;
	    private static String currentServerIP = "127.0.0.1:25565";
		private static ServerListEntryNormal lastServer;
	    
	public custGuiMultiplayer(GuiScreen parentScreen) {
		super(parentScreen);
	}	
	@Override
	public void createButtons()
    {
		
		this.btnEditServer = this.addButton(new GuiButton(7, this.width / 2 - 154, this.height - 28, 20, 20, I18n.format("selectServer.edit")));
        this.btnDeleteServer = this.addButton(new GuiButton(2, this.width / 2 - 74, this.height - 28, 70, 20, I18n.format("selectServer.delete")));
        this.btnSelectServer = this.addButton(new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("selectServer.select")));
        this.buttonList.add(new GuiButton(4, this.width / 2 , this.height - 52, 100, 20, I18n.format("selectServer.direct")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 52, 100, 20, I18n.format("selectServer.add")));
        this.buttonList.add(new GuiButton(8, this.width / 2 + 4, this.height - 28, 70, 20, I18n.format("selectServer.refresh")));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, I18n.format("gui.cancel")));
        try {
        this.selectServer(this.serverListSelector.getSelected());
      
        }
        catch(NullPointerException e) {
        	e.printStackTrace();
        }
        
        }
	
	
	@Override
	 public void updateScreen()
	 {
        super.updateScreen();
    }

	@Override
	  public void selectServer(int index)
	  {
	        this.serverListSelector.setSelectedSlotIndex(index);
	        GuiListExtended.IGuiListEntry guilistextended$iguilistentry = index < 0 ? null : this.serverListSelector.getListEntry(index);
	        this.btnSelectServer.enabled = false;
	        this.btnEditServer.enabled = false;
	        this.btnDeleteServer.enabled = false;

	        if (guilistextended$iguilistentry != null && !(guilistextended$iguilistentry instanceof ServerListEntryLanScan))
	        {
	            this.btnSelectServer.enabled = true;

	            if (guilistextended$iguilistentry instanceof ServerListEntryNormal)
	            {
	                this.btnEditServer.enabled = true;
	                this.btnDeleteServer.enabled = true;
	            }
	        }
	    }

}
