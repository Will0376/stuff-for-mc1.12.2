package ru.will0376.Willmod.Updater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.UpdLogLoading;

public class UpdateChecker {
	public void init() {
		comparison();
	}
	private String getMd5(File fl){
		String md5 = "null";
		try {
		FileInputStream fis = new FileInputStream(fl);
		md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
		} catch (FileNotFoundException e) {
			Main.log.error("[Checker version]File not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return md5;
	}
	public File getFile() {
		File fl;
		if(Main.Release)
			fl = new File(Main.dir+"/../mods/" +"willmod-" + Main.VERSION + ".jar");
		else 
			fl = new File(Main.dir+"/../../build/libs/" +"willmod-" + Main.VERSION + ".jar");
		return fl;
	}
	private String getMd5Github() {
		String md5 = UpdLogLoading.readToString("https://raw.githubusercontent.com/Will0376/stuff-for-mc1.12.2/master/md5file");
		return md5;
	}
	public boolean comparison() {
		if (getMd5Github().equals(getMd5(getFile())) ) {
			return true;
		}
		else return false;
	}
}
