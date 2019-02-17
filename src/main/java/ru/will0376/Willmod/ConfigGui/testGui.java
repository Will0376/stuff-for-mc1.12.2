package ru.will0376.Willmod.ConfigGui;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ru.will0376.Willmod.config;
import ru.will0376.api.gui.defGui;

public class testGui extends defGui {//for tests

	public testGui(GuiScreen back) {
		super(back);
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
        try {
			drawString(this.mc.fontRenderer, I18n.format("tile.par", new Object[0])+getrandomcolor()+I18n.format("tile.par", new Object[0])+getrandomstile()+I18n.format("test.gui", new Object[0]), x +75, y+50 , 16777215);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	private String getrandomcolor() throws InterruptedException {
		
		int i_max = 15;
		int i = 0 + (int) (Math.random() * i_max);
				if(i >=0 && i <= 9) {
					Thread.sleep(20);
					return String.valueOf(i);
				}
				else if(i == 10) {
					Thread.sleep(20);
					return "a";
				}
				else if(i==11) {
					Thread.sleep(20);
					return "b";
				}
				else if(i==12) {
					Thread.sleep(20);
					return "c";
				}
				else if(i==13) {
					Thread.sleep(20);
					return "d";
				}
				else if(i==14) {
					Thread.sleep(20);
					return "e";
				}
				else if(i==15) {
					Thread.sleep(20);
					return "f";
				}
				Thread.sleep(20);
				return i+"";
	}
	private String getrandomstile() throws InterruptedException {
		int i = 0 + (int) (Math.random() * 5);
		if(i ==0) {
			Thread.sleep(20);
			return "K";
		}
		else if(i ==1) {
			Thread.sleep(20);
			return "L";
		}
		else if(i ==2) {
			Thread.sleep(20);
			return "M";
		}
		else if(i ==3) {
			Thread.sleep(20);
			return "N";
		}
		else if(i ==4) {
			Thread.sleep(20);
			return "O";
		}
		else if(i ==5) {
			Thread.sleep(20);
			return "R";
		}
		return "R";			
	}
					
}
