package com.log8430.group9.views;

import java.util.ArrayList;

public class SharedRessources {
	private static SharedRessources ressource;
	private boolean autoRun;
	private ArrayList<UICommand> commands;
	
	public static SharedRessources getInstance(){
		if(ressource == null){
			ressource = new SharedRessources();
		}
		return ressource;
	}
	
	private SharedRessources(){
	}
	
	public void setAutoRun(boolean autoRun){
		this.autoRun = autoRun;
	}
	public boolean getAutoRun(){
		return this.autoRun;
	}

	public void setCommands(ArrayList<UICommand> newCommands){
		commands = newCommands;
	}
	public ArrayList<UICommand> getCommands(){
		return commands;
	}
	
}
