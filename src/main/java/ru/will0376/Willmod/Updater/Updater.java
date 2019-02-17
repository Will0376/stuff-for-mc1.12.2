package ru.will0376.Willmod.Updater;

import java.text.DecimalFormat;

import ru.will0376.Willmod.Main;
import ru.will0376.Willmod.config;

public class Updater {
	public static boolean beUpdates = false;
	public void init() {
		update(checkedOutdate());
	}
	private boolean checkedOutdate() {
		return new UpdateChecker().comparison();
	}
	private void update(boolean outdated) {
	if(config.enabledUpdater && Main.Release) {
		if(!outdated) {
		beUpdates = true;
			}		
		}
	}
	private String getVersion() {
		
		double ver = Double.parseDouble(Main.VERSION) + 0.1;
		DecimalFormat df = new DecimalFormat("#.#");
		ver = Double.parseDouble(df.format(ver));
		return "willmod-"+ver+".jar";
	}
}
