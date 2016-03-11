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

import javax.annotation.PostConstruct;
import javax.swing.JFileChooser;
import javax.swing.event.TreeSelectionEvent;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreePart {
	protected Tree fileTree;
	protected Button selectRoot;
	protected File currentFile;

	@PostConstruct
	public void createComposite(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);



		fileTree = new Tree(parent, SWT.SINGLE | SWT.BORDER);
		GridData gridDataTree = new GridData(GridData.FILL,GridData.FILL,true,true,1,4);
		fileTree.setLayoutData(gridDataTree);

		selectRoot = new Button(parent, SWT.PUSH);
		GridData gridDataButton = new GridData(GridData.CENTER,GridData.FILL,false,false,1,1);
		selectRoot.setLayoutData(gridDataButton);
		selectRoot.setText("Select a file or a folder");
		selectRoot.addListener(SWT.Selection,new Listener() {

			@Override
			public void handleEvent(Event event) {
				selectFolder();
			}

		});
		
		File[] roots = File.listRoots();
		if(roots.length > 0)
		{
			File root = roots[0];
			setRoot(root);
		}
		fileTree.addListener(SWT.Expand, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem) e.item;
				expand(item);
			}
		});

		fileTree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem) e.item;
				valueChanged(item);
			}
		});

	}


	private void selectFolder() {
		Shell shell = new Shell();
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
		String result = dialog.open();
		if(result.equals(null))
			return;
		else{
			File root = new File(result);
			setRoot(root);
			setCurrentFile(root);
		}

	}

	private void setRoot(File root){
		fileTree.removeAll();
		TreeItem rootItem = new TreeItem(fileTree, SWT.NONE);
		if(root.getName().equals(""))
			rootItem.setText(root.getAbsolutePath());
		else
			rootItem.setText(root.getName());
		rootItem.setData(root);
		expand(rootItem);
		rootItem.setExpanded(true);
	}
	
	private void expand(TreeItem item){
		if (item == null)
			return;
		System.out.println(item.getItemCount());
		File root = (File) item.getData();
		File[] files = root.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			TreeItem treeItem = new TreeItem(item, SWT.NONE);
			if(file.getName().equals(""))
				treeItem.setText(file.getAbsolutePath());
			else
				treeItem.setText(file.getName());
			treeItem.setData(file);
			if (file.isDirectory()) {
				new TreeItem(treeItem, SWT.NONE);
			}
		}
	}
	private void valueChanged(TreeItem item) {
		if (item == null)
			return;
		final File file = (File) item.getData();
		System.out.println(file.getAbsolutePath());

		if(file == null)
			return;

		this.setCurrentFile(file);
	}
	/**
	 * 
	 * @param file
	 */
	//TODO copmpleter cette fonctionnalité afin de transmettre le fichier selectionné aux commandes.
	private void setCurrentFile(File file) {
		this.currentFile = file;
		/*
        for(UICommand command : commands) {
			command.setCurrentFile(file);

			if(this.autoRunCheckBox.isSelected() && command.isEnabled()) {
				command.execute();
			}
		}
		 */
	}
}