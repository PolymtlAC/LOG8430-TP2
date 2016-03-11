package com.log8430.group9.views;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class CommandsPart {
	protected Composite commandsContainer;
	protected Button clear;
	protected Button autoRun;

	@PostConstruct
	public void createComposite(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		parent.setLayout(gridLayout);	


		final ScrolledComposite scrollPane = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.BORDER);
		GridData gridDataScrollPane = new GridData(GridData.FILL,GridData.FILL,true,true,3,4);
		scrollPane.setLayoutData(gridDataScrollPane);

		commandsContainer = new Composite(scrollPane, SWT.NONE);
		scrollPane.setContent(commandsContainer);
		GridLayout gridLayoutCC = new GridLayout();
		gridLayoutCC.numColumns = 1;
		commandsContainer.setLayout(gridLayoutCC);
		commandsContainer.setSize(commandsContainer.computeSize(SWT.BORDER, SWT.DEFAULT));
		
		clear = new Button(parent, SWT.PUSH);
		clear.setText("Clear");
		GridData gridDataClear = new GridData(GridData.FILL,GridData.FILL,true,false,1,1);
		clear.setLayoutData(gridDataClear);
		autoRun = new Button(parent, SWT.CHECK);
		autoRun.setText("autoRun");
		GridData gridDataAutoRun = new GridData(GridData.FILL,GridData.FILL,false,false,1,1);
		autoRun.setLayoutData(gridDataAutoRun);
		
		
		//TODO cette action est pour tester la mise a jour des dimmension du scroll a l'ajout d'element
		//il faudra utiliser son contenu lors du chargement des commandes
		clear.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {	              
	              Button button = new Button(commandsContainer, SWT.PUSH);
	              button.setText("button ");
	              // reset the minimum width and height so children can be seen - method 2
	              commandsContainer.setSize(commandsContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	              commandsContainer.layout();
	          }
	      });
		
		
		//permet de connaitre l'état dans lequel se trouve le bouton
		//autoRun.getSelection();
		
		

	}
}
