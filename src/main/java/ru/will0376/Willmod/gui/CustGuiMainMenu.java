package ru.will0376.Willmod.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import ru.will0376.Willmod.Main;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CustGuiMainMenu extends GuiMainMenu {
	private static int RandomTextureMorning = 0;
	private static int RandomTextureDay = 0;
	private static int RandomTextureEvering = 0;
	private static int RandomTextureNight = 0;

	private File rootDirectory = new File( Main.dir.replace("config","")+"WillmodPng/");
	private File Morningfolder = new File(rootDirectory.toString() + "/Morning");
	private File Dayfolder = new File(rootDirectory.toString() + "/Day");
	private File Everingfolder = new File(rootDirectory.toString() + "/Evering");
	private File Nightfolder = new File(rootDirectory.toString() + "/Night");
	private int MorningLenght = folderSize(Morningfolder);
	private int DayLenght = folderSize(Dayfolder);
	private int EveringLenght = folderSize(Everingfolder);
	private int NightLenght = folderSize(Nightfolder);

	@Override
	public void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
	{
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer")));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_, I18n.format("menu.multiplayer")));
		this.buttonList.add(new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("fml.menu.mods")));

		if(MorningLenght != 0){
			RandomTextureMorning = (int) (Math.random() * MorningLenght + 1);
		}

		if(DayLenght != 0){
			RandomTextureDay = (int) (Math.random() * DayLenght + 1);
		}

		if(EveringLenght != 0){
			RandomTextureEvering = (int) (Math.random() * EveringLenght + 1);
		}

		if(NightLenght != 0){
			RandomTextureNight = (int) (Math.random() * NightLenght + 1);
		}

	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		String tmp = choose();
		if(tmp.equals("null")){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID + ":textures/gui/def.png"));
		}
		else {
			try {
				new TextureUtil().uploadTextureImage(0, ImageIO.read(new File(tmp)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(0.0D, (double)this.height, 0.0D).tex(0, (double) (1F + (float)mouseX)).endVertex();
		bufferbuilder.pos((double)this.width, (double)this.height, 0.0D).tex((double)1F, (double)(1F + (float)mouseX)).endVertex();
		bufferbuilder.pos((double)this.width, 0.0D, 0.0D).tex((double)1F , (double)mouseX).endVertex();
		bufferbuilder.pos(0, 0, 0).tex(0, mouseX).endVertex();
		tessellator.draw();

		//title textures + splash
		this.mc.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int j = this.width / 2 - 137;
		if ((double)this.minceraftRoll < 1.0E-4D)
		{
			this.drawTexturedModalRect(j, 30, 0, 0, 99, 44);
			this.drawTexturedModalRect(j + 99, 30, 129, 0, 27, 44);
			this.drawTexturedModalRect(j + 99 + 26, 30, 126, 0, 3, 44);
			this.drawTexturedModalRect(j + 99 + 26 + 3, 30, 99, 0, 26, 44);
			this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
		}
		else
		{
			this.drawTexturedModalRect(j, 30, 0, 0, 155, 44);
			this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
		}
		this.mc.getTextureManager().bindTexture(field_194400_H);
		drawModalRectWithCustomSizedTexture(j + 88, 67, 0.0F, 0.0F, 98, 14, 128.0F, 16.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
		GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
		float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
		f = f * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
		GlStateManager.scale(f, f, f);
		this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, -256);
		GlStateManager.popMatrix();
		//End

		String s = "Minecraft 1.12.2";

		if (this.mc.isDemo())
		{
			s = s + " Demo";
		}
		else
		{
			s = s + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType());
		}
		this.drawString(this.fontRenderer, "Frame: "+getFrameNumber(), this.width - this.fontRenderer.getStringWidth("Frame: "+getFrameNumber()) - 2, this.height - 30, 16777215);
		this.drawString(this.fontRenderer, "Modified by Will0376", this.width - this.fontRenderer.getStringWidth("Modified by Will0376") - 2, this.height - 20, 16777215);

		List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
		List<String> brdlist = new ArrayList<>();
		for (String branding : brandings) {
			if (branding.contains("MCP")) {
			} else if (branding.contains("Forge")) {
				brdlist.add("Forge:" + (branding.split("Forge")[1]));
			} else {
				brdlist.add(branding);
			}
		}
		for (int brdline = 0; brdline < brdlist.size(); brdline++)
		{
			String brd = brdlist.get(brdline);
			if (!Strings.isNullOrEmpty(brd))
			{
				this.drawString(this.fontRenderer, brd, 2, this.height - ( 10 + brdline * (this.fontRenderer.FONT_HEIGHT + 1)), 16777215);
			}
		}
		this.drawString(this.fontRenderer, "Copyright Mojang AB. Do not distribute!", this.widthCopyrightRest, this.height - 10, -1);
		//System.out.println(Main.Debugmode);
		if(Main.Debugmode){
			this.drawString(this.fontRenderer, "DebugMode:", this.width - this.fontRenderer.getStringWidth("DebugMode:") - 2, 0, 16777215);
			this.drawString(this.fontRenderer, getText("Morning")+MorningLenght, this.width - this.fontRenderer.getStringWidth(getText("Morning")+MorningLenght) - 2, 10, 16777215);
			this.drawString(this.fontRenderer, getText("Day")+DayLenght, this.width - this.fontRenderer.getStringWidth(getText("Day")+DayLenght) - 2, 20, 16777215);
			this.drawString(this.fontRenderer, getText("Evering")+EveringLenght, this.width - this.fontRenderer.getStringWidth(getText("Evering")+EveringLenght) - 2, 30, 16777215);
			this.drawString(this.fontRenderer, getText("Night")+NightLenght, this.width - this.fontRenderer.getStringWidth(getText("Night")+NightLenght) - 2, 40, 16777215);

		}
		drawButton(mouseX,mouseY,partialTicks);

	}
	private String getText(String name){
		if(name.toLowerCase().equals(getTimes()))
			return "--> "+ name+": ";
		else
			return name+": ";
	}
	private void drawButton(int mouseX, int mouseY, float partialTicks){
		for (GuiButton guiButton : this.buttonList) {
			((GuiButton) guiButton).drawButton(this.mc, mouseX, mouseY, partialTicks);
		}

		for (GuiLabel guiLabel : this.labelList) {
			((GuiLabel) guiLabel).drawLabel(this.mc, mouseX, mouseY);
		}
	}

	private String getTimes(){
		LocalTime now = LocalTime.now();
		int lt = now.getHour();
		int mb = 8,db = 11,eb = 18,nb = 0;
		if (lt >= mb && lt < db) {
			return  "morning";
		}
		else if(lt >= db && lt <eb) {
			return "day";
		}
		else if(lt >= eb && lt <= 23) {
			return "evening";
		}
		else if(lt >= nb && lt < mb ) {
			return "night";
		}
		return "day";
	}
	private List<String> getAllPng(String foldername){
		List<String> allpath = new ArrayList<String>();
      File directory = new File(rootDirectory.toString()+"/" + foldername);
      try {
		  for (String path : directory.list()) {
			  if (path != null)
				  allpath.add(directory+"/"+path);
		  }
	  }
      catch (NullPointerException e){ /** **/}
      return allpath;
	}

	private static int folderSize(File directory) {
		if(!directory.exists()){
			return 0;
		}
		int length = directory.listFiles().length;
		return length;
	}

	private String choose(){
		List<String> morning = getAllPng("Morning");
		List<String> day = getAllPng("Day");
		List<String> evering = getAllPng("Evering");
		List<String> night = getAllPng("Night");
		switch(getTimes()){
			case "morning":
				if(RandomTextureMorning != 0)
					return morning.get(RandomTextureMorning -1);
				else
					return "null";
			case "day":
				if(RandomTextureDay != 0)
					return day.get(RandomTextureDay -1);
				else
					return "null";
			case "evering":
				if(RandomTextureEvering != 0)
					return evering.get(RandomTextureEvering -1);
				else
					return "null";
			case "night":
				if(RandomTextureNight != 0)
					return night.get(RandomTextureNight -1);
				else
					return "null";
		}
		return "null";
	}
	private String getFrameNumber(){
		switch(getTimes()){
			case "morning":
				if(RandomTextureMorning != 0)
					return String.valueOf(RandomTextureMorning);
				else
					return "Default";
			case "day":
				if(RandomTextureDay != 0)
					return String.valueOf(RandomTextureDay);
				else
					return "Default";
			case "evering":
				if(RandomTextureEvering != 0)
					return String.valueOf(RandomTextureEvering);
				else
					return "Default";
			case "night":
				if(RandomTextureNight != 0)
					return String.valueOf(RandomTextureNight);
				else
					return "Default";
		}
		return String.valueOf(0);
	}
}
