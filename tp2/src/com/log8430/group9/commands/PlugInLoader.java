package com.log8430.group9.commands;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
/**
 * classe gerant le chargement des plugins de commandes
 * cette classe utilise le pattern singleton 
 * @author Alexandre
 *
 */
public class PlugInLoader implements IRegistryChangeListener{
	/**
	 * ID du point d'extension defini pour les commandes
	 */
	private static final String CommandExtension_ID = 
			"com.log8430.group9.commandExtension";
	/**
	 * liste des commandes chargées
	 */
	protected ArrayList<Command> commandsList = new ArrayList<Command>();
	/**
	 * instance pour le pattern singleton
	 */
	private static PlugInLoader instance;
	/**
	 * recuperation de l'instance
	 * @return l'instance permettant la gestion du chargement des plugins
	 */
	public static PlugInLoader getInstance(){
		if(instance == null){
			instance = new PlugInLoader();
			instance.loadAllCommands();
			instance.startup();
		}
		return instance;
	}
	/**
	 * fonction gerant actuellement la recuperation des commandes
	 */
	public void loadAllCommands(){
		IConfigurationElement[] contributions = 
				Platform.getExtensionRegistry().getConfigurationElementsFor(CommandExtension_ID);
		try {
			for (IConfigurationElement e : contributions) {
				final Object o =
						e.createExecutableExtension("class");
				if (o instanceof Command) {
					commandsList.add((Command) o);
				}
			}
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
	}
	/**
	 * accesseur pour la recuperation des commandes chargées
	 * @return la liste des commandes chargées
	 */
	public ArrayList<Command> getCommandsList(){
		return commandsList;
	}
	/**
	 * fonction visant a remplacer loadAllCommand et gerant le chargement dynamique
	 * non fonctionnel pour le moment
	 * utilise un listener sur les changement dans le plugin registry
	 * cependant a l'heure actuel la facon de modifier ce registre est inconnu
	 */
	public void startup() {
	      IExtensionRegistry reg = Platform.getExtensionRegistry();
	      IExtensionPoint pt = reg.getExtensionPoint(CommandExtension_ID);
	      IExtension[] ext = pt.getExtensions();
	      System.out.println("liste size = " + ext.length);
	      /*for (int i = 0; i < ext.length; i++){
	    	  ext[i]
	      }*/ 
	      reg.addRegistryChangeListener(this);
	   }
	/**
	 * fonction gerant la mise a jour de la liste des commandes lors de changement dans la liste des plugins
	 * non fonctionnel
	 */
	@Override
	public void registryChanged(IRegistryChangeEvent event) {
		System.out.println("changement of registry");
		
	}
	
	
	
	
	
}
