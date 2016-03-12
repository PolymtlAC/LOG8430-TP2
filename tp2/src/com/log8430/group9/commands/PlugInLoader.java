package com.log8430.group9.commands;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class PlugInLoader {
	private static final String CommandExtension_ID = 
			"com.log8430.group9.commandExtension";
	protected ArrayList<Command> CommandsList = new ArrayList<Command>();
	private static PlugInLoader instance;
	
	public static PlugInLoader getInstance(){
		if(instance == null){
			instance = new PlugInLoader();
			instance.loadAllCommands();
		}
		return instance;
	}
	
	public void loadAllCommands(){
		IConfigurationElement[] contributions = 
				Platform.getExtensionRegistry().getConfigurationElementsFor(CommandExtension_ID);
		try {
			for (IConfigurationElement e : contributions) {
				final Object o =
						e.createExecutableExtension("class");
				if (o instanceof Command) {
					CommandsList.add((Command) o);
				}
			}
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public ArrayList<Command> getCommandsList(){
		return CommandsList;
	}
}
