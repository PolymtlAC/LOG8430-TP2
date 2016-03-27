/*******************************************************************************
 * Copyright (c) 2010 - 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <lars.Vogel@gmail.com> - Bug 419770
 *******************************************************************************/
package com.log8430.group9.views;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
/**
 * Partie visuelle gerant l'affichage de l'arbre ainsi que le bouton pour changer de racine.
 * l'arbre est complété suivant les expensions que demande l'utilisateur.
 * La selection d'un element met a jour le fichier courant pour l'activation des commandes
 * A la selection, on verifie l'etat de l'option d'autoRun pour lancer les commandes au besoin
 * @author Alexandre
 *
 */
public class TreePart implements InterPartCom{
	/**
	 * temps pour la gestion du rafraichiment de l'affichage
	 */
	int precedentTime = 0;
	/**
	 * arbre representant l'arborescence de dossier et de fichier
	 */
	protected Tree fileTree;
	/**
	 * bouton gerant le changement de racine pour l'arbre
	 */
	protected Button selectRoot;
	/**
	 *  fichier couramment selectionné. C'est sur celui ci que sera effectué les actions de commandes
	 */
	protected File currentFile;

	/**
	 * construction des elements visuels que l'on ajoute à un element "parent"
	 * @param parent element sur lequel est ajouté les composants graphiques
	 */
	public void createComposite(Composite parent) {
		//creation du layout general il se compose d'une unique colonne et de 5 lignes
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);



		fileTree = new Tree(parent, SWT.SINGLE | SWT.BORDER);
		//affectation de la zone d'affichage de l'abre, il prend 4 lignes du layout et est en mesure 
		//de modifier sa taille si son conteneur change de taille
		GridData gridDataTree = new GridData(GridData.FILL,GridData.FILL,true,true,1,4);
		fileTree.setLayoutData(gridDataTree);

		selectRoot = new Button(parent, SWT.PUSH);
		GridData gridDataButton = new GridData(GridData.CENTER,GridData.FILL,false,false,1,1);
		selectRoot.setLayoutData(gridDataButton);
		selectRoot.setText("Select a file or a folder");
		// affectation du comportement associé au bouton de selection de la racine
		selectRoot.addListener(SWT.Selection,new Listener() {

			@Override
			public void handleEvent(Event event) {
				selectFolder();
			}

		});
		//selection d'une racine parmi le(s) racines existantes
		File[] roots = File.listRoots();
		if(roots.length > 0)
		{
			File root = roots[0];
			setRoot(root);
		}
		//ajout du comportement a la demande d'expansion de l'arbre
		fileTree.addListener(SWT.Expand, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem) e.item;
				expand(item);
			}
		});
		//ajout du comportement a la selection d'un element de l'arbre
		fileTree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem) e.item;
				valueChanged(item);
			}
		});
		fileTree.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				if(e.time - precedentTime > 1000){
					refresh(null);
					precedentTime = e.time;
				}
			}
		});
	}

	/**
	 * fontion gerant la selection de la nouvelle racine.
	 */
	private void selectFolder() {
		Shell shell = new Shell();
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
		String result = dialog.open();
		if(result == null)
			return;
		else{
			File root = new File(result);
			setRoot(root);
			setCurrentFile(root);
		}

	}
	/**
	 * fonction effectuant la modification de l'arbre lors du changement de racine
	 * @param root
	 */
	private void setRoot(File root){
		//on supprime tout les elements de l'arbre
		fileTree.removeAll();
		//creation du noeud racine
		TreeItem rootItem = new TreeItem(fileTree, SWT.NONE);
		if(root.getName().equals(""))
			rootItem.setText(root.getAbsolutePath());
		else
			rootItem.setText(root.getName());
		rootItem.setData(root);
		//expansion initiale de l'arbre
		expand(rootItem);
		rootItem.setExpanded(true);
	}
	/**
	 * cette fonction s'occupe de recuperer les fichier et sous dossier de l'item dont on demande
	 * l'expansion. Elle crée et ajoute les nouveaux items a l'arbre
	 * @param item
	 */
	private void expand(TreeItem item){
		if (item == null)
			return;
		//afin de pouvoir etendre les dossiers, de base un item vide leur est ajouté
		//lors de l'expansion, cet item n'est plus necessaire, on le supprime donc pour le remplacer
		//par le contenu du dossier
		item.removeAll();
		File root = (File) item.getData();
		File[] files = root.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			addItem(file, item);
		}
	}

	private void addItem(File file,TreeItem item){
		TreeItem treeItem = new TreeItem(item, SWT.NONE);
		if(file.getName().equals(""))
			treeItem.setText(file.getAbsolutePath());
		else
			treeItem.setText(file.getName());
		treeItem.setData(file);
		//ajout de l'element vide pour rendre les sous dossiers capable de s'expandre
		if (file.isDirectory()) {
			new TreeItem(treeItem, SWT.NONE);
		}
	}
	/**
	 * fonction gerant la recuperation du fichier dans l'item selecionné
	 * @param item
	 */
	private void valueChanged(TreeItem item) {
		if (item == null)
			return;
		final File file = (File) item.getData();
		if(file == null)
			return;

		this.setCurrentFile(file);
	}
	/**
	 * fonction gerant le changement de fichier courrant et demandant l'exectution des commandes si
	 * l'autoRun est activé
	 * @param file
	 */
	private void setCurrentFile(File file) {
		this.currentFile = file;
		for(UICommand command : ressource.getCommands()) {
			command.setCurrentFile(file);
			if(ressource.getAutoRun() && command.isEnabled()) {
				command.execute();
			}
		}

	}

	/**
	 * fonction permettant de rafraichir l'affichage de l'arbre dans le cas de modification de celui ci
	 * @param rootItem element a mettre a jour, si null on prend la racine de l'arbre
	 */
	private void refresh(TreeItem rootItem){
		if(rootItem == null && this.fileTree.getItemCount() <= 0){
			return;
		}
		else if(rootItem == null && this.fileTree.getItemCount() > 0){
			rootItem = this.fileTree.getItem(0);
		}

		File rootFile = (File) rootItem.getData();
					
		ArrayList<String> childrenFileName = new ArrayList<>();
		ArrayList<File> childrenFile = new ArrayList<>();
		File[] list = rootFile.listFiles();
		if(list == null){
			list = new File[0];
		}
		for(File file : list){
			childrenFileName.add(file.getName());
			childrenFile.add(file);
		}

		//recuperation des enfants dans l'arbre
		TreeItem[] childrenItem = rootItem.getItems();
		ArrayList<String> childrenName = new ArrayList<>();
		for(TreeItem item : childrenItem){
			File file = (File)item.getData();
			childrenName.add(file.getName());
			if(childrenFileName.contains(file.getName())){
				childrenFile.remove(file);
				if(file.isDirectory() && item.getExpanded()){
					refresh(item);
				}
			}
			else{
				item.dispose();
			}
		}
		if(childrenFile.size() > 0){
			for(File file: childrenFile){
				addItem(file, rootItem);
			}
		}

	}
}