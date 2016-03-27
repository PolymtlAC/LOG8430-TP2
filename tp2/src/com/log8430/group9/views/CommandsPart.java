package com.log8430.group9.views;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.log8430.group9.commands.Command;
import com.log8430.group9.commands.PlugInLoader;
/**
 * Partie visuelle gerant l'affichage des commandes ainsi que les boutons pour effacer les resultats et pour selectionné l'autoRun
 * Les commandes sont chargé au demarrage et affiché.
 * @author Alexandre
 *
 */
public class CommandsPart implements InterPartCom{
	/**
	 * partie de la vue contenant l'ensemble des commandes
	 */
	protected Composite commandsContainer;
	/**
	 * bouton pour effacer les resultats des commandes
	 */
	protected Button clear;
	/**
	 * selection de l'option d'execution automatique
	 */
	protected Button autoRun;
	/**
	 * instance de la classe gerant le chargement et la mise a jour de l'ensemble de commandes
	 */
	protected PlugInLoader plugInLoader;

	/**
	 * fonction creant les elements de vue pour la partie commandes
	 * @param parent
	 */
	public void createComposite(Composite parent) {
		//recuperation de l'instance gerant la recuperation des commandes
		plugInLoader = PlugInLoader.getInstance();
		//le layout utilisé est composé de 2 colonnes et de 5 lignes
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		parent.setLayout(gridLayout);	


		final ScrolledComposite scrollPane = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.BORDER);
		GridData gridDataScrollPane = new GridData(GridData.FILL,GridData.FILL,true,true,3,4);
		scrollPane.setLayoutData(gridDataScrollPane);
		//creation de container pour les commandes
		commandsContainer = new Composite(scrollPane, SWT.NONE);
		scrollPane.setContent(commandsContainer);
		GridLayout gridLayoutCC = new GridLayout();
		gridLayoutCC.numColumns = 1;
		commandsContainer.setLayout(gridLayoutCC);
		commandsContainer.setSize(commandsContainer.computeSize(SWT.BORDER, SWT.DEFAULT));
		//recuperation des commandes
		loadCommands();
		//creation des bouton clear et autoRun
		clear = new Button(parent, SWT.PUSH);
		clear.setText("Clear");
		GridData gridDataClear = new GridData(GridData.FILL,GridData.FILL,true,false,1,1);
		clear.setLayoutData(gridDataClear);
		autoRun = new Button(parent, SWT.CHECK);
		autoRun.setText("autoRun");
		GridData gridDataAutoRun = new GridData(GridData.FILL,GridData.FILL,false,false,1,1);
		autoRun.setLayoutData(gridDataAutoRun);
		//ajout du comportement lors de la selection de l'option d'execution automatique
		autoRun.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {	              
				ressource.setAutoRun(autoRun.getSelection());
			}
		});
		//ajout du comportement lors de la demande d'effacement des resultatsZ
		clear.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {	              
				for(UICommand command : ressource.getCommands()){
					command.clear();
				}
			}
		});





	}
	/**
	 * fonction gerant l'ajout et la suppression des commandes et mise a jour de l'affichage
	 */
	public void loadCommands(){
		ArrayList<Command> liste = plugInLoader.getCommandsList();
		ArrayList<UICommand> UIListe = new ArrayList<>();

		for(Command command : liste) {
			UICommand uiCommand = new UICommand(command, commandsContainer, SWT.NONE);
			UIListe.add(uiCommand);
		}
		//mise a jout de la liste des commandesS
		ressource.setCommands(UIListe);
		// ceci permet de mettre a jour la taille du container des commandes afin que le scroll s'adapte a la taille de ce qu'il contient.
		commandsContainer.setSize(commandsContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		commandsContainer.layout();
	}

	
}
