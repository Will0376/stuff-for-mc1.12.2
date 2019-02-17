package ru.will0376.Willmod.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Runnables;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;
import ru.will0376.Willmod.Main;

@SideOnly(Side.CLIENT)
public class custGuiGameMenu extends GuiScreen
{
	private static int RandomTexture = (int) (Math.random() * 3 + 1);
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Random RANDOM = new Random();
	/**
	 * A random number between 0.0 and 1.0, used to determine if the title screen says <a
	 * href="https://minecraft.gamepedia.com/Menu_screen#Minceraft">Minceraft</a> instead of Minecraft. Set during
	 * construction; if the value is less than .0001, then Minceraft is displayed.
	 */
	private final float minceraftRoll;
	/** The splash message. */
	private String splashText;
	private GuiButton buttonResetDemo;
	/** Timer used to rotate the panorama, increases every tick. */
	private float panoramaTimer;
	/** Texture allocated for the current viewport of the main menu's panorama background. */
	private DynamicTexture viewportTexture;
	/** The Object object utilized as a thread lock when performing non thread-safe operations */
	private final Object threadLock = new Object();
	public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
	/** Width of openGLWarning2 */
	private int openGLWarning2Width;
	/** Width of openGLWarning1 */
	private int openGLWarning1Width;
	/** Left x coordinate of the OpenGL warning */
	private int openGLWarningX1;
	/** Top y coordinate of the OpenGL warning */
	private int openGLWarningY1;
	/** Right x coordinate of the OpenGL warning */
	private int openGLWarningX2;
	/** Bottom y coordinate of the OpenGL warning */
	private int openGLWarningY2;
	/** OpenGL graphics card warning. */
	private String openGLWarning1;
	/** OpenGL graphics card warning. */
	private String openGLWarning2;
	/** Link to the Mojang Support about minimum requirements */
	private String openGLWarningLink;
	private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
	private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
	private static final ResourceLocation field_194400_H = new ResourceLocation("textures/gui/title/edition.png");
	/** An array of all the paths to the panorama pictures. */
	private static final ResourceLocation[] TITLE_PANORAMA_PATHS = new ResourceLocation[] {new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
	private ResourceLocation backgroundTexture;
	/** Minecraft Realms button. */
	private GuiButton realmsButton;
	/** Has the check for a realms notification screen been performed? */
	private boolean hasCheckedForRealmsNotification;
	/**
	 * A screen generated by realms for notifications; drawn in adition to the main menu (buttons and such from both are
	 * drawn at the same time). May be null.
	 */
	private GuiScreen realmsNotification;
	private int widthCopyright;
	private int widthCopyrightRest;
	private GuiButton modButton;
	private net.minecraftforge.client.gui.NotificationModUpdateScreen modUpdateNotification;

	public custGuiGameMenu()
	{
		this.openGLWarning2 = MORE_INFO_TEXT;
		this.splashText = "missingno";
		IResource iresource = null;

		try
		{
			List<String> list = Lists.<String>newArrayList();
			iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
			String s;

			while ((s = bufferedreader.readLine()) != null)
			{
				s = s.trim();

				if (!s.isEmpty())
				{
					list.add(s);
				}
			}

			if (!list.isEmpty())
			{
				while (true)
				{
					this.splashText = list.get(RANDOM.nextInt(list.size()));

					if (this.splashText.hashCode() != 125780783)
					{
						break;
					}
				}
			}
		}
		catch (IOException var8)
		{
			;
		}
		finally
		{
			IOUtils.closeQuietly((Closeable)iresource);
		}

		this.minceraftRoll = RANDOM.nextFloat();
		this.openGLWarning1 = "";

		if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
		{
			this.openGLWarning1 = I18n.format("title.oldgl1");
			this.openGLWarning2 = I18n.format("title.oldgl2");
			this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
		}
	}

	/**
	 * Is there currently a realms notification screen, and are realms notifications enabled?
	 */
	private boolean areRealmsNotificationsEnabled()
	{
		return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.realmsNotification != null;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		if (this.areRealmsNotificationsEnabled())
		{
			this.realmsNotification.updateScreen();
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui()
	{
		this.viewportTexture = new DynamicTexture(256, 256);
		this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
		this.widthCopyright = this.fontRenderer.getStringWidth("Copyright Mojang AB. Do not distribute!");
		this.widthCopyrightRest = this.width - this.widthCopyright - 2;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
		{
			this.splashText = "Merry X-mas!";
		}
		else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
		{
			this.splashText = "Happy new year!";
		}
		else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
		{
			this.splashText = "OOoooOOOoooo! Spooky!";
		}

		int i = 24;
		int j = this.height / 4 + 48;

		if (this.mc.isDemo())
		{
			this.addDemoButtons(j, 24);
		}
		else
		{
			this.addSingleplayerMultiplayerButtons(j, 24);
		}

		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options")));
		this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit")));

		synchronized (this.threadLock)
		{
			this.openGLWarning1Width = this.fontRenderer.getStringWidth(this.openGLWarning1);
			this.openGLWarning2Width = this.fontRenderer.getStringWidth(this.openGLWarning2);
			int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
			this.openGLWarningX1 = (this.width - k) / 2;
			this.openGLWarningY1 = (this.buttonList.get(0)).y - 24;
			this.openGLWarningX2 = this.openGLWarningX1 + k;
			this.openGLWarningY2 = this.openGLWarningY1 + 24;
		}

		this.mc.setConnectedToRealms(false);

		if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.hasCheckedForRealmsNotification)
		{
			RealmsBridge realmsbridge = new RealmsBridge();
			this.realmsNotification = realmsbridge.getNotificationScreen(this);
			this.hasCheckedForRealmsNotification = true;
		}

		if (this.areRealmsNotificationsEnabled())
		{
			this.realmsNotification.setGuiSize(this.width, this.height);
			this.realmsNotification.initGui();
		}
	}

	/**
	 * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
	 */
	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
	{
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer")));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer")));
		this.buttonList.add(modButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("fml.menu.mods")));
		RandomTexture = (int) (Math.random() * 3 + 1);

	}

	/**
	 * Adds Demo buttons on Main Menu for players who are playing Demo.
	 */
	private void addDemoButtons(int p_73972_1_, int p_73972_2_)
	{
		this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo")));
		this.buttonResetDemo = this.addButton(new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo")));
		ISaveFormat isaveformat = this.mc.getSaveLoader();
		WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

		if (worldinfo == null)
		{
			this.buttonResetDemo.enabled = false;
		}
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == 0)
		{
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if (button.id == 5)
		{
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}

		if (button.id == 1)
		{
			this.mc.displayGuiScreen(new GuiWorldSelection(this));
		}

		if (button.id == 2)
		{
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if (button.id == 14 && this.realmsButton.visible)
		{
			this.switchToRealms();
		}

		if (button.id == 4)
		{
			this.mc.shutdown();
		}

		if (button.id == 6)
		{
			this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
		}

		if (button.id == 11)
		{
			this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
		}

		if (button.id == 12)
		{
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

			if (worldinfo != null)
			{
				this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion"), "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning"), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 12));
			}
		}
	}

	private void switchToRealms()
	{
		RealmsBridge realmsbridge = new RealmsBridge();
		realmsbridge.switchToRealms(this);
	}

	public void confirmClicked(boolean result, int id)
	{
		if (result && id == 12)
		{
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			isaveformat.flushCache();
			isaveformat.deleteWorldDirectory("Demo_World");
			this.mc.displayGuiScreen(this);
		}
		else if (id == 12)
		{
			this.mc.displayGuiScreen(this);
		}
		else if (id == 13)
		{
			if (result)
			{
				try
				{
					Class<?> oclass = Class.forName("java.awt.Desktop");
					Object object = oclass.getMethod("getDesktop").invoke((Object)null);
					oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
				}
				catch (Throwable throwable)
				{
					LOGGER.error("Couldn't open link", throwable);
				}
			}

			this.mc.displayGuiScreen(this);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(getRandomTexture());

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
			this.drawTexturedModalRect(j + 0, 30, 0, 0, 99, 44);
			this.drawTexturedModalRect(j + 99, 30, 129, 0, 27, 44);
			this.drawTexturedModalRect(j + 99 + 26, 30, 126, 0, 3, 44);
			this.drawTexturedModalRect(j + 99 + 26 + 3, 30, 99, 0, 26, 44);
			this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
		}
		else
		{
			this.drawTexturedModalRect(j + 0, 30, 0, 0, 155, 44);
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
		this.drawString(this.fontRenderer, "Frame: "+RandomTexture, this.width - this.fontRenderer.getStringWidth("Frame: "+RandomTexture) - 2, this.height - 30, 16777215);
		this.drawString(this.fontRenderer, "Modified by Will0376", this.width - this.fontRenderer.getStringWidth("Modified by Will0376") - 2, this.height - 20, 16777215);

		List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
		List<String> brdlist = new ArrayList<>();
		for(int i = 0;i < brandings.size();i++){
			if(brandings.get(i).contains("MCP")){
			}
			else if(brandings.get(i).contains("Forge")){
				brdlist.add("Forge:"+(brandings.get(i).split("Forge")[1]));
			}
			else{
				brdlist.add(brandings.get(i));
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


		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		synchronized (this.threadLock)
		{
			if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2)
			{
				GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
				guiconfirmopenlink.disableSecurityWarning();
				this.mc.displayGuiScreen(guiconfirmopenlink);
			}
		}
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	public void onGuiClosed()
	{
		if (this.realmsNotification != null)
		{
			this.realmsNotification.onGuiClosed();
		}
	}
	private ResourceLocation getRandomTexture(){
	//Kill me plz.
		ResourceLocation day1 = new ResourceLocation(Main.MODID + ":textures/gui/day/1.png");
		ResourceLocation day2 = new ResourceLocation(Main.MODID + ":textures/gui/day/2.png");
		ResourceLocation day3 = new ResourceLocation(Main.MODID + ":textures/gui/day/3.png");
		ResourceLocation eve1 = new ResourceLocation(Main.MODID + ":textures/gui/eve/1.png");
		ResourceLocation eve2 = new ResourceLocation(Main.MODID + ":textures/gui/eve/2.png");
		ResourceLocation eve3 = new ResourceLocation(Main.MODID + ":textures/gui/eve/3.png");
		ResourceLocation morn1 = new ResourceLocation(Main.MODID + ":textures/gui/morn/1.png");
		ResourceLocation morn2 = new ResourceLocation(Main.MODID + ":textures/gui/morn/2.png");
		ResourceLocation morn3 = new ResourceLocation(Main.MODID + ":textures/gui/morn/3.png");
		ResourceLocation night1 = new ResourceLocation(Main.MODID + ":textures/gui/night/1.png");
		ResourceLocation night2 = new ResourceLocation(Main.MODID + ":textures/gui/night/2.png");
		ResourceLocation night3 = new ResourceLocation(Main.MODID + ":textures/gui/night/3.png");
		if(getTimes().equals("morning")){
			 switch (RandomTexture){
				 case 1: return morn1;
				 case 2: return morn2;
				 case 3: return morn3;
			 }
		}
		else if(getTimes().equals("day")){
			switch (RandomTexture){
				case 1: return day1;
				case 2: return day2;
				case 3: return day3;
			}
		}
		else if(getTimes().equals("evening")){
			switch (RandomTexture){
				case 1: return eve1;
				case 2: return eve2;
				case 3: return eve3;
			}
		}
		else if(getTimes().equals("night")){
			switch (RandomTexture){
				case 1: return night1;
				case 2: return night2;
				case 3: return night3;
			}
		}

		return null;
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
}