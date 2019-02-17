package ru.will0376.Willmod.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListWorldSelection;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.config;
import ru.will0376.Willmod.ConfigGui.guiMods;
import ru.will0376.Willmod.Updater.GuiUpdated;
import ru.will0376.Willmod.Updater.Updater;
import ru.will0376.Willmod.gui.custGuiGameMenu;

public class events {
    /*@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true) // For tests
    public void OpenGuiMultiplayer(GuiOpenEvent event)
    {
        if (event.getGui()  instanceof GuiMultiplayer)
        {
        	event.setGui(new custGuiMultiplayer(new GuiMainMenu()));
        }    
        
        
    }*/
    @SuppressWarnings("unused")
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void loadLastWorld(GuiOpenEvent event) {
    	if (event.getGui()  instanceof custGuiGameMenu && Main.Debugmode && config.useLLW) {
    		event.setGui(new GuiWorldSelection(new custGuiGameMenu()));
    		GuiListWorldSelection guiListWorldSelection = new GuiListWorldSelection(new GuiWorldSelection(new custGuiGameMenu()), Minecraft.getMinecraft(), 100, 100, 32, 100 - 64, 36);
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
    public void updatedMod(GuiOpenEvent e) {
    	if(e.getGui() instanceof custGuiGameMenu && Updater.beUpdates) {
    		e.setGui(new GuiUpdated(null));
    	}
    }
	@SubscribeEvent
	public void CustomGameMenu(GuiOpenEvent e) {
		if(e.getGui() instanceof GuiMainMenu) {
			e.setGui(new custGuiGameMenu());
		}
	}
}
