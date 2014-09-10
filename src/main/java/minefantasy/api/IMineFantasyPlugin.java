package minefantasy.api;

/**
 * 
 * @author AnonymousProductions
 * Implement this file for whatever file handles the stuff that happens when minefantasy is loaded
 * use !MineFantasyAPI.isModLoaded() to add scripts for when the mod is not loaded
 */
public interface IMineFantasyPlugin {
	void initWithMineFantasy();
	String pluginName();
}
