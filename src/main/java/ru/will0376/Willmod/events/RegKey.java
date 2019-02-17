package ru.will0376.Willmod.events;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RegKey {

    private static final String CAT_NAME = "WillMod";
    public static final KeyBinding use = new KeyBinding("Open menu", Keyboard.KEY_P, CAT_NAME);

    public static void preInit() {
        ClientRegistry.registerKeyBinding(use); 
    }
    
}