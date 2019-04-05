package ru.will0376.Shopper.Gui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.json.simple.JSONObject;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.will0376.Shopper.ChatForm;
import ru.will0376.Shopper.Main;
import ru.will0376.Shopper.Network.PluginMessage;

public class GuiShopper extends GuiScreen{
	String money ;
	HttpJsonParser htp;
	int xSize = 176;
	int ySize = 176;

	int page = 1;
	int maxPage = getMaxPage();
	int maxId = 1;


	public GuiShopper(String url,String money) {
			htp = new HttpJsonParser(url);
			this.money = money;
	}

	public void initGui(){
		int offset = 1;
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(-1, this.width/2-65, this.height/2+80, 130, 20, I18n.format(I18n.format("shopper.back"))));
		this.buttonList.add(new GuiButton(-2, this.width/2-95 , this.height/2+80, 20, 20, "<-"));
		this.buttonList.add(new GuiButton(-3, this.width/2+75, this.height/2+80, 20, 20, "->"));
		this.buttonList.add(new GuiButton(-4, this.width/2+160, this.height/2+80, 40, 20, I18n.format("shopper.debug.reload")));
		for(int y = 1;y <= 3;y++) {
			for (int x = 1; x <= 8; x++) {

				this.buttonList.add(new GuiButton((offset), this.width / 2 - 229 + (49 * x), this.height / 2 - 124 + (60 * y), 30, 20, I18n.format("shopper.buy")));
				offset++;
			}
		}
		maxPage = getMaxPage();
	}

	public void actionPerformed(GuiButton g){
		int offset = 1;
		if(g.id == -1)	this.mc.displayGuiScreen(null);
		else if(g.id == -2)	changePage(0);
		else if(g.id == -3)	changePage(1);
		else if(g.id == -4){	//this.mc.displayGuiScreen(this)
			Main.network.sendToServer(new PluginMessage("OpenShopper;ReloadConfig;"+ Minecraft.getMinecraft().player.getName()));
		}
		//else {

			for(int i = (page-1) * 24; i < (page-1) * 24 + 24;i++){
					if (g.id == offset) {
						if(i < maxId-1 && i >= 0) {
							System.out.println("=============Opened BuyScreen with id:" + (i + 1) + "=============");
							System.out.println("=============Opened BuyScreen with offset:" + offset + "=============");
							Minecraft.getMinecraft().displayGuiScreen(new BuyScreen(this, page, htp, i+1, money));
							break;
						}
						else {
							Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatForm.prefix+"Page "+(i+1)+" not found :3"));
							break;
						}
					}
				offset++;
				System.out.println("off: "+offset);
				}
			//}
		}

	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		int x = (this.width - this.xSize) / 2-105;
		int y = (this.height - this.ySize) / 2;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(2896);
		mc.renderEngine.bindTexture(new ResourceLocation(Main.MODID + ":textures/gui/bg2.png"));

		drawTextured(x,y,mouseX);//текстура
		drawString(mc.fontRenderer,I18n.format("shopper.page")+page+"/"+getMaxPage(),this.width/2-150-(I18n.format("shopper.page")+page+"/"+getMaxPage()).length(),this.height/2+85,16777215);
		for(int i = 0; i < 3;i++)
		for(int ii = 0; ii <= 7;ii++)
		drawRect((x+50)+(ii*49), (y+25)+(i*60), (x+5)+(ii*49), (y-15)+(i*60), Integer.MIN_VALUE);//Квадратики
		drawItems(x,y);
		int xx = Mouse.getEventX()*width/mc.displayWidth-2;
		int yy = super.height-Mouse.getEventY()*height/mc.displayHeight-7;
		drawDot(xx,yy);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	private void drawDot(int x,int y){
		drawString(mc.fontRenderer,".",x+2,y-2,16777215);
		drawString(mc.fontRenderer,"...",x,y,16777215);
		drawString(mc.fontRenderer,".",x+2,y+2,16777215);
	}
	private void drawItems(int x,int y){
		RenderHelper.enableGUIStandardItemLighting();
		IForgeRegistry<Item> items = ForgeRegistries.ITEMS;
		IForgeRegistry<Block> blocks = ForgeRegistries.BLOCKS;
		try {
			JSONObject jjo = htp.getJson();
			String PriceIcon = (String) jjo.get("PriceIcon");
			drawString(mc.fontRenderer, I18n.format("tile.par")+"6"+"Money: "+money+PriceIcon,this.width/2+110-("Money: "+money+PriceIcon).length(),this.height/2+85,16777215);
			int offset = 0;
			maxId = jjo.size();
			for(int i = (page-1) * 24; i < (page-1) * 24 + 24 && i < jjo.size()-1; i++){
					JSONObject j = (JSONObject) jjo.get(String.valueOf(i+1));
					String Modid = j.get("Modid")+":";
					String NameCode = Modid + j.get("Namefromcode");
					String Name = String.valueOf(j.get("Name"));
					int Count = Integer.valueOf(String.valueOf(j.get("Count")));
					int Prise = Integer.valueOf(String.valueOf(j.get("Prise")));
					int FixTextX = Integer.valueOf(String.valueOf(j.get("FixTextX")));
					int FixTextY = Integer.valueOf(String.valueOf(j.get("FixTextY")));
					int FixIconX = Integer.valueOf(String.valueOf(j.get("FixIconX")));
					int FixIconY = Integer.valueOf(String.valueOf(j.get("FixIconY")));
					int FixCountX = Integer.valueOf(String.valueOf(j.get("FixCountX")));
					int FixCountY = Integer.valueOf(String.valueOf(j.get("FixCountY")));
					if (j.get("Type").equals("item")) {
						for (Item it : items) {
							if (NameCode.equalsIgnoreCase(it.getRegistryName().toString())) {
								offset++;
								drawBlocks(Name, offset, x, y, FixTextX, FixTextY, FixCountX, FixCountY, FixIconX, FixIconY, PriceIcon, Count, Prise, it);
							}
						}
					} else if (j.get("Type").equals("block")) {
						for (Block b : blocks) {
							if (NameCode.equalsIgnoreCase(b.getRegistryName().toString())) {
								offset++;
								drawBlocks(Name, offset, x, y, FixTextX, FixTextY, FixCountX, FixCountY, FixIconX, FixIconY, PriceIcon, Count, Prise, Item.getItemFromBlock(b));
							}
						}
					}
				}
		}
		catch (Throwable e){
			//e.printStackTrace();
			System.out.println("thr");
		}
	}
	private void drawBlocks(String Name,int i,int x,int y,int FixTextX,int FixTextY,int FixCountX,int FixCountY,int FixIconX,int FixIconY,String PriceIcon,int Count,int Prise,Item ts){
		if(i >= 9 && i <=16){
			int constX = -392;
			int constY = 59;
			drawString(mc.fontRenderer, Name, (x+20)+((i-1)*49) - (Name.length()) + (FixTextX)+constX, y + ((i / 9) - 15)+constY + (FixTextY), 16777215);
			if (Count != 1)
				drawString(mc.fontRenderer, String.valueOf(Count), (x + 31) + ((i - 1) * 49) + (FixCountX)+constX, y + ((i / 9) + 12)+constY + (FixCountY), 16777215);
			drawString(mc.fontRenderer, "P: " + Prise + PriceIcon, (x + 10) + ((i - 1) * 49) - ("P: " + Prise).length()+constX, y + ((i / 9) + 16)+constY, 16777215);
			mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(ts), (x - 30) + (i * 49) + (FixIconX)+constX, y +constY + (FixIconY));
		}
		else if (i >=17 && i <= 24){
			int constX = -392 * 2;
			int constY = 119;
			drawString(mc.fontRenderer, Name, (x+20)+((i-1)*49) - (Name.length()) + (FixTextX)+constX, y + ((i / 9) - 15)+constY + (FixTextY), 16777215);
			if (Count != 1)
				drawString(mc.fontRenderer, String.valueOf(Count), (x + 31) + ((i - 1) * 49) + (FixCountX)+constX, y + ((i / 9) + 12)+constY + (FixCountY), 16777215);
			drawString(mc.fontRenderer, "P: " + Prise + PriceIcon, (x + 10) + ((i - 1) * 49) - ("P: " + Prise).length()+constX, y + ((i / 9) + 16)+constY-1, 16777215);
			mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(ts), (x - 30) + (i * 49) + (FixIconX)+constX, y +constY + (FixIconY));
		}
		else{
			drawString(mc.fontRenderer, Name, (x+20)+((i-1)*49) - (Name.length()) + (FixTextX), y + ((i / 9) - 15) + (FixTextY), 16777215);
			if (Count != 1)
				drawString(mc.fontRenderer, String.valueOf(Count), (x + 31) + ((i - 1) * 49) + (FixCountX), y + ((i / 9) + 12) + (FixCountY), 16777215);
			drawString(mc.fontRenderer, "P: " + Prise + PriceIcon, (x + 10) + ((i - 1) * 49) - ("P: " + Prise).length(), y + ((i / 9) + 16), 16777215);
			mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(ts), (x - 30) + (i * 49) + (FixIconX), y + (((i-1) / 9) * 59) + (FixIconY));
		}
	}
	private void drawTextured(int x,int y,int mouseX){
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(x, y+(176+20), 0.0D).tex(0, (double) (1F + (float)mouseX)).endVertex();
		bufferbuilder.pos(x+400, y+(176+20), 0.0D).tex((double)1F, (double)(1F + (float)mouseX)).endVertex();
		bufferbuilder.pos(x+400 , y-20.0D, 0.0D).tex((double)1F , (double)mouseX).endVertex();
		bufferbuilder.pos(x, y-20, 0).tex(0, mouseX).endVertex();
		tessellator.draw();
	}
	private int getMaxPage(){
		if(htp == null)
			return 1;
		else{
			if (htp.getJson().size()-1 <= 24)
				return 1;
			return (htp.getJson().size()-1) / 24 +1;
		}
	}
	private void changePage(int i){
		//i = 0 - down
		//i = 1 - up
		if(i == 0 && (page != 1)) {
			page--;
			this.mc.displayGuiScreen(this);
		}
		else if(i == 1 && page != maxPage){
			page++;
			this.mc.displayGuiScreen(this);
		}
	}
}
