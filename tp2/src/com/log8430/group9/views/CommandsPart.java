package com.log8430.group9.views;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class CommandsPart {
	protected Composite commandsContainer;
	protected Button clear;
	protected Button autoRun;
	
	 @PostConstruct
	public void createComposite(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = true;
		parent.setLayout(gridLayout);
		
		commandsContainer = new Composite(parent, SWT.NONE);
		GridData gridDataCommandsContainer = new GridData(GridData.FILL,GridData.FILL,true,true,2,4);
		commandsContainer.setLayoutData(gridDataCommandsContainer);
		
		clear = new Button(parent, SWT.PUSH);
		clear.setText("Clear");
		GridData gridDataClear = new GridData(GridData.FILL,GridData.FILL,true,false,1,1);
		clear.setLayoutData(gridDataClear);
		autoRun = new Button(parent, SWT.CHECK);
		autoRun.setText("autoRun");
		GridData gridDataAutoRun = new GridData(GridData.CENTER,GridData.FILL,false,false,1,1);
		autoRun.setLayoutData(gridDataAutoRun);
		
	}
}
