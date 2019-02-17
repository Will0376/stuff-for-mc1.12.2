package ru.will0376.api.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.config;
import ru.will0376.Willmod.ConfigGui.guiMods;

public class defGui extends GuiScreen  {
	protected GuiScreen back;
    protected int xSize = 176;
    protected int ySize = 166;
    protected int x;
    protected int y;
	public defGui(GuiScreen back) {
		this.back = back;
	}
	 public void initGui()
	    {
	        this.buttonList.clear();
	        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 5, this.height / 2  + 55, 176, 20, I18n.format("Back", new Object[0])));
	    }
	 protected void actionPerformed(GuiButton g) {
		 if (g.id == 0)//back to menu
         {
             this.mc.displayGuiScreen(back);
             
         }
	 }
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
	    }
	 
}
