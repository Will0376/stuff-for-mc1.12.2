package ru.will0376.Willmod;

import net.minecraft.client.renderer.texture.TextureUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadPng {
	private File rootDirectory = new File( Main.dir.replace("config","")+"WillmodPng/");
	private File Morningfolder = new File(rootDirectory.toString() + "/Morning");
	private File Dayfolder = new File(rootDirectory.toString() + "/Day");
	private File Eveningfolder = new File(rootDirectory.toString() + "/Evening");
	private File Nightfolder = new File(rootDirectory.toString() + "/Night");
	public int MorningLenght;
	public int DayLenght;
	public int EveningLenght;
	public int NightLenght;

	public List<String> morning;
	public List<String> day;
	public List<String> evening;
	public List<String> night;

	public LoadPng init(){
		System.out.println("init!");
		MorningLenght = folderSize(Morningfolder);
		DayLenght = folderSize(Dayfolder);
		EveningLenght = folderSize(Eveningfolder);
		NightLenght = folderSize(Nightfolder);

		morning = getAllPng("Morning");
		day = getAllPng("Day");
		evening = getAllPng("Evening");
		night = getAllPng("Night");
		return this;
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
}
