package ru.will0376.Willmod.ConfigGui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.UpdLogLoading;
import ru.will0376.Willmod.config;
import ru.will0376.api.gui.defGui;

public class UpdLog extends defGui {
	public double ver =  Double.parseDouble(Main.VERSION);
	public double max_ver =  Double.parseDouble(Main.VERSION);
	public double min_ver =  0.9;
	private boolean updlog = false;
	
	public UpdLog(GuiScreen back) {
		super(back);
	}
	
	@Override
		 public void initGui()
		    {
		updlog = false;
		        this.buttonList.clear();
		        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 - 20, this.height / 2 - 20 , 30, 20, I18n.format("Up", new Object[0])));
		        this.buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2 - 20, this.height /2 - 75 , 30, 20, I18n.format("Close", new Object[0])));
		        this.buttonList.add(new GuiButton(2, (this.width - this.xSize) / 2 - 20, this.height / 2  + 18, 30, 20, I18n.format("Down", new Object[0])));
		        this.buttonList.add(new GuiButton(3, this.width /2 - 60 , this.height / 2 - 75 , 30, 20, I18n.format("Back", new Object[0])));
		        this.buttonList.add(new GuiButton(4, this.width /2 + 70, this.height / 2 - 75 , 50, 20, I18n.format("Reload Log", new Object[0])));
		        
	}
	 @Override
	  protected void actionPerformed(GuiButton g)
	    {
		  if (g.enabled)
	        {
	            if (g.id == 0)//up
	            {
	            	canupdown(1);
	                this.mc.displayGuiScreen(this);
	                
	            }
	            if (g.id == 1) //exit
	            {
	            	this.mc.displayGuiScreen((GuiScreen)null);	   	
	            }
	            if (g.id == 2)//down
	            {
	            	canupdown(2);
	            	this.mc.displayGuiScreen(this);	 
	            }
	            if (g.id == 3)//Back
	            {	
	            	this.mc.displayGuiScreen(new guiMods(null));	
	            	
	            }
	            if (g.id == 4)//Reload Log
	            {    
	            	updlog = true;
	            	
	            	Main.objJsonUpdList =  UpdLogLoading.LoadJson("https://raw.githubusercontent.com/Will0376/stuff-for-mc1.12.2/master/Upd.Log/Upd.json");
	            	this.mc.displayGuiScreen(this);	
	            	
	            }
	        }
	    }
	public void parseObjJson() {
		DecimalFormat df = new DecimalFormat("#.#");
		ver = Double.parseDouble(df.format(ver));
		if(!Main.objJsonUpdList.equals(null)) {
		JSONObject jsonObject = (JSONObject) Main.objJsonUpdList;
		String update =	"Update "+ver+": "+ (String) jsonObject.get("Update "+ver);
		
		String arrWords[] = update.split(" ");  
		ArrayList<String> arrPhrases = new ArrayList<String>();

		StringBuilder stringBuffer = new StringBuilder();
		int cnt = 0; 
		int index = 0; 
		int length = arrWords.length;

		while (index != length) {  
		  if (cnt + arrWords[index].length() <= 30) {
		    cnt += arrWords[index].length() + 1; 
		    stringBuffer.append(arrWords[index]).append(" "); 
		    index++;
		  } else {
		    arrPhrases.add(stringBuffer.toString()); 
		    stringBuffer = new StringBuilder();
		    cnt = 0; 
		  }

		}

		if (stringBuffer.length() > 0) {
		   arrPhrases.add(stringBuffer.toString());
		}
		ArrayList<String> arr = new ArrayList<String>();
		for(int i = arrPhrases.size(); i >0;i--) {
			arr.add(arrPhrases.get(i - 1));
		}
		for(int i = 0;i < arr.size();i++) {
			drawString(this.mc.fontRenderer,arr.get(i), (this.width - this.xSize) / 2 + 20, this.height / 2 - 20 - (i * 10), 16777215);
		}
		}
		else {
			drawString(this.mc.fontRenderer,"Json = null!", (this.width - this.xSize) / 2 + 20, this.height / 2 - 50, 16777215);
		}
	}
	public void canupdown(int i) {
    	//1 - up
    	//2 - down
    	if(i == 1&&(ver != max_ver)) {
    		ver = ver + 0.1;
    	}
    	else if(i==2&&(ver != min_ver)) {
    		ver = ver - 0.1;
    	}
    	else if(ver == min_ver) {
    		ver = min_ver;
    	}
    	else if(ver >= max_ver) {
    		ver = max_ver;
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
        parseObjJson();
        if(updlog)
        drawString(this.mc.fontRenderer,"Reloading", this.width /2 + 77, this.height / 2 - 50, 16777215);
       
    }
}
