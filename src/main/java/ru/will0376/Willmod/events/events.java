package ru.will0376.Willmod.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListWorldSelection;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.config;
import ru.will0376.Willmod.ConfigGui.guiMods;
import ru.will0376.Willmod.gui.CustGuiMainMenu;
import ru.will0376.Willmod.gui.custGuiMultiplayer;

public class events {
    @SubscribeEvent
    public void OpenGuiMultiplayer(GuiOpenEvent e)
    {
        if (e.getGui()  instanceof GuiMultiplayer && config.customMultiplayer)
        {
        	e.setGui(new custGuiMultiplayer(new GuiMainMenu()));
        }    
        
        
    }
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void loadLastWorld(GuiOpenEvent event) {
    	if (event.getGui()  instanceof CustGuiMainMenu && Main.Debugmode && config.useLLW) {
    		event.setGui(new GuiWorldSelection(new CustGuiMainMenu()));
    		GuiListWorldSelection guiListWorldSelection = new GuiListWorldSelection(new GuiWorldSelection(new CustGuiMainMenu()), Minecraft.getMinecraft(), 100, 100, 32, 100 - 64, 36);
    		guiListWorldSelection.getListEntry(0).joinWorld();
    	}
    }

    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (RegKey.use.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new guiMods(null));
        }
        
    }
	@SubscribeEvent
	public void CustomGameMenu(GuiOpenEvent e) {
		if(e.getGui() instanceof GuiMainMenu) {
			//e.setGui(new custGuiGameMenu());
			e.setGui(new CustGuiMainMenu());
		}
	}
}
