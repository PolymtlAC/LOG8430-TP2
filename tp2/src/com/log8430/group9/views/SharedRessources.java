package com.log8430.group9.views;

import java.util.ArrayList;
/**
 * cette classe permet de conserver les données utiles pour la communication entres les differentes partie de la vue
 * elle met a disposition les ressources partagés et les methodes pour y acceder en lecture et ecriture
 * @author Alexandre
 *
 */
public class SharedRessources {
	/**
	 * objet pour le pattern singleton
	 */
	private static SharedRessources ressource;
	/**
	 * element de synchronisation pour l'autoexecution de toute les commandes a la selection d'un fichier
	 */
	private boolean autoRun;
	/**
	 * ensemble des commandes accessibles par les differents elements de vue, sert notamment pour l'autoRun
	 */
	private ArrayList<UICommand> commands;
	/**
	 * fonction de recuperation de l'instance de ressources partagé (pattern singleton)
	 * @return l'instance contenant les ressources partagées
	 */
	public static SharedRessources getInstance(){
		if(ressource == null){
			ressource = new SharedRessources();
		}
		return ressource;
	}
	
	private SharedRessources(){
	}
	/**
	 * accesseur en ecriture de l'autoRun
	 * @param autoRun
	 */
	public void setAutoRun(boolean autoRun){
		this.autoRun = autoRun;
	}
	/**
	 * accesseur en lecture de l'autoRun
	 * @return
	 */
	public boolean getAutoRun(){
		return this.autoRun;
	}
	/**
	 * accesseur en ecriture de l'ensemble des commandes
	 * @param newCommands
	 */
	public void setCommands(ArrayList<UICommand> newCommands){
		commands = newCommands;
	}
	/**
	 * accesseur en lecture de l'ensemble des commandes
	 * @return
	 */
	public ArrayList<UICommand> getCommands(){
		return commands;
	}
	
}
