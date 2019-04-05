package ru.will0376.Shopper.Gui;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;
import ru.will0376.Shopper.Main;
import ru.will0376.Shopper.Network.PluginMessage;

public class BuyScreen extends GuiScreen {
	int page,id;
	String money;
	HttpJsonParser htp;
	GuiScreen back;
	int xSize = 176;
	int ySize = 176;
	int Repeat = 0;
	int MaxRepeat = 64;

	public BuyScreen(GuiScreen back,int page,HttpJsonParser htp,int id,String money){
		this.htp = htp;
		this.page = page;
		//this.id = id;
		this.id =  id;
		this.back = back;
		this.money = money;
		System.out.println("Page: "+page+" id: "+id);
	}

	public void initGui(){
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1, this.width/2-65, this.height/2+60, 130, 20, I18n.format(I18n.format("shopper.nope"))));
		this.buttonList.add(new GuiButton(2, this.width/2-65, this.height/2, 130, 20, I18n.format(I18n.format("shopper.confirm"))));
		this.buttonList.add(new GuiButton(3, this.width/2+10, this.height/2 + 30, 20, 20, "+"));
		this.buttonList.add(new GuiButton(4, this.width/2-30, this.height/2 + 30, 20, 20,"-"));
	}

	public void actionPerformed(GuiButton g){

		if(g.id == 1)	this.mc.displayGuiScreen(back);
		else if(g.id == 2)	Main.network.sendToServer(new PluginMessage("buy;"+id+";"+Repeat+";"+mc.player.getName()));/**
		 TODO:	Дописать систему страниц вообще в моде.(в частности: получение id от страницы.)<DONE>
		 		Дописать отправку пакета на сервер(id блока в json;nick) -Проверить
		 		Дописать проверку данных на сервере -проверить
		 		Дописать систему изменения баланса игрока

		 */
		else if(g.id == 3)	checkRep("+");
		else if(g.id == 4)	checkRep("-");
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawWorldBackground(1);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(2896);
		mc.renderEngine.bindTexture(new ResourceLocation(Main.MODID + ":textures/gui/bg2.png"));

		this.drawTexturedModalRect(x - 35, y, 0, 0, this.xSize + 80, this.ySize);
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawRect(x+60, y+25, x+120, y+75, Integer.MIN_VALUE);//Квадратики
		drawText();
		parseJson(width/2,height/2);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	private void parseJson(int x,int y){
		RenderHelper.enableGUIStandardItemLighting();
		IForgeRegistry<Item> items = ForgeRegistries.ITEMS;
		IForgeRegistry<Block> blocks = ForgeRegistries.BLOCKS;
		try {
			JSONObject jjo = htp.getJson();
			String PriceIcon = (String) jjo.get("PriceIcon");
			drawString(mc.fontRenderer, I18n.format("tile.par")+"6"+"Money: "+money+PriceIcon,x-6-("Money: "+money+PriceIcon).length(),y-10,16777215);
				JSONObject j = (JSONObject) jjo.get(String.valueOf(id));
				String NameCode = j.get("Modid")+":"+j.get("Namefromcode");
				String Name = String.valueOf(j.get("Name"));
				int Count = Integer.valueOf(String.valueOf(j.get("Count")));
				int Prise = Integer.valueOf(String.valueOf(j.get("Prise")));
				if (j.get("Type").equals("item")) {
					for (Item it : items) {
						if(NameCode.equalsIgnoreCase(it.getRegistryName().toString())) {
							drawString(mc.fontRenderer, Name,x-3-(Name.length()),y-62,16777215);//name
							 drawString(mc.fontRenderer, String.valueOf(Count),x+10,y-40,16777215);//count per 1 buy
							drawString(mc.fontRenderer,I18n.format("shopper.price")+Prise+PriceIcon,x-18-(I18n.format("shopper.price")+Prise+PriceIcon).length(),y-22,16777215); //price
							mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(it), x-5, y-50);
						}
					}
				}
				else if (j.get("Type").equals("block")) {
					for (Block b : blocks) {
						if(NameCode.equalsIgnoreCase(b.getRegistryName().toString())) {
							drawString(mc.fontRenderer, Name,x-3-(Name.length()),y-62,16777215);//name
							drawString(mc.fontRenderer, String.valueOf(Count),x+10,y-40,16777215);//count per 1 buy
							drawString(mc.fontRenderer,I18n.format("shopper.price")+Prise+PriceIcon,x-18-(I18n.format("shopper.price")+Prise+PriceIcon).length(),y-22,16777215); //price
							mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(b), x-5, y-50);
						}
					}
				}
				drawString(mc.fontRenderer, I18n.format("tile.par")+checkMoney(Prise)+"("+Prise*(Repeat+1)+PriceIcon+")",this.width/2+62-("("+Prise*(Repeat+1)+PriceIcon+")").length(),this.height/2+36,16777215);
			}
		catch (Throwable e){
			//e.printStackTrace();
			//System.out.println("thr");
		}
	}
	private String checkMoney(int Prise){
		if(Prise*(Repeat+1) > Float.parseFloat(money))
			return "c";
		else
			return "a";
	}
	private void drawText(){
		drawString(mc.fontRenderer,I18n.format("shopper.confirmBuy"),this.width/2-15-(I18n.format("shopper.confirmBuy").length()),this.height/2-80	,16777215);
		drawString(mc.fontRenderer,I18n.format("shopper.repeatbuy"),this.width/2-82-(I18n.format("shopper.repeatbuy").length()),this.height/2+35	,16777215);
		drawString(mc.fontRenderer,I18n.format("tile.par")+"6"+Repeat,this.width/2-2,this.height/2+35	,16777215);
		drawString(mc.fontRenderer,I18n.format("shopper.repeatbuy.times"),this.width/2+40-(I18n.format("shopper.repeatbuy.times").length()),this.height/2+35,16777215);
	}
	private int division(){
		return Repeat;
	}
	private void checkRep(String state){
	if(state.equalsIgnoreCase("+") && !(Repeat >= MaxRepeat)) {
		Repeat++;
		mc.displayGuiScreen(this);
	}
	else if(state.equalsIgnoreCase("-") && !(Repeat <= 0)){
		Repeat--;
		mc.displayGuiScreen(this);
		}
	}
}
