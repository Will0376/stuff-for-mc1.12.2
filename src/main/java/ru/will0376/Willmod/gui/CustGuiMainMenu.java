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
import org.lwjgl.opengl.GL11;
import ru.will0376.Willmod.LoadPng;
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
	private static int RandomTextureEvening = 0;
	private static int RandomTextureNight = 0;
	private static float minceraftRollRandom = 0;
	public String old = "";
	LoadPng v = new LoadPng().init();

	@Override
	public void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
	{
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer")));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_, I18n.format("menu.multiplayer")));
		this.buttonList.add(new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("fml.menu.mods")));

		if(v.MorningLenght != 0){
			RandomTextureMorning = (int) (Math.random() * v.MorningLenght + 1);
		}

		if(v.DayLenght != 0){
			RandomTextureDay = (int) (Math.random() * v.DayLenght + 1);
		}

		if(v.EveningLenght != 0){
			RandomTextureEvening = (int) (Math.random() * v.EveningLenght + 1);
		}

		if(v.NightLenght != 0){
			RandomTextureNight = (int) (Math.random() * v.NightLenght + 1);
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
				if(!old.equals(tmp)) {
					new TextureUtil().uploadTextureImage(0, ImageIO.read(new File(tmp)));
					old = tmp;
				}
			} catch (IOException e) {
				e.printStackTrace();
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID + ":textures/gui/def.png"));
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
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/minecraft.png"));
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
			this.drawString(this.fontRenderer, getText("Morning")+v.MorningLenght, this.width - this.fontRenderer.getStringWidth(getText("Morning")+v.MorningLenght) - 2, 10, 16777215);
			this.drawString(this.fontRenderer, getText("Day")+v.DayLenght, this.width - this.fontRenderer.getStringWidth(getText("Day")+v.DayLenght) - 2, 20, 16777215);
			this.drawString(this.fontRenderer, getText("Evening")+v.EveningLenght, this.width - this.fontRenderer.getStringWidth(getText("Evening")+v.EveningLenght) - 2, 30, 16777215);
			this.drawString(this.fontRenderer, getText("Night")+v.NightLenght, this.width - this.fontRenderer.getStringWidth(getText("Night")+v.NightLenght) - 2, 40, 16777215);
			this.drawString(this.fontRenderer,"Fps: "+Minecraft.getMinecraft().getDebugFPS(),this.width /2 - this.fontRenderer.getStringWidth("fps: "),0, 16777215);
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
			guiButton.drawButton(this.mc, mouseX, mouseY, partialTicks);
		}

		for (GuiLabel guiLabel : this.labelList) {
			guiLabel.drawLabel(this.mc, mouseX, mouseY);
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


	private String choose(){

		switch(getTimes()){
			case "morning":
				if(RandomTextureMorning != 0)
					return v.morning.get(RandomTextureMorning -1);
				else
					return "null";
			case "day":
				if(RandomTextureDay != 0)
					return v.day.get(RandomTextureDay -1);
				else
					return "null";
			case "evening":
				if(RandomTextureEvening != 0)
					return v.evening.get(RandomTextureEvening -1);
				else
					return "null";
			case "night":
				if(RandomTextureNight != 0)
					return v.night.get(RandomTextureNight -1);
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
				if(RandomTextureEvening != 0)
					return String.valueOf(RandomTextureEvening);
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
	public static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel) {


		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

		/*
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0.0D, y + height, zLevel, 0.0D, 1.0D);
		tessellator.addVertexWithUV(x + width, y + height, zLevel, 1.0D, 1.0D);
		tessellator.addVertexWithUV(x + width, y + 0.0D, zLevel, 1.0D, 0.0D);
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, zLevel, 1.0D, 0.0D);
		*/
		bufferbuilder.pos(x + 0.0D, y + height, zLevel).tex(0.0D, 1.0D).endVertex();
		bufferbuilder.pos(x + width, y + height, zLevel).tex(1.0D, 1.0D).endVertex();
		bufferbuilder.pos(x + width, y + 0.0D, zLevel).tex(1.0D, 0.0D).endVertex();
		bufferbuilder.pos(x + 0.0D, y + 0.0D, zLevel).tex(1.0D, 0.0D).endVertex();
		tessellator.draw();
	}
}
