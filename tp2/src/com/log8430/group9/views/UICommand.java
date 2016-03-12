package com.log8430.group9.views;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.log8430.group9.commands.Command;

/**
 * This class represents the graphical interface for commands.
 * An instance of the class is a line on the command panel in the main window.
 * Contains the command, the button and the result label. 
 * Also connect the button click with the command's execution.
 */
public class UICommand extends Composite {
	
	protected Command command;
	protected Button commandButton;
	protected Label commandResult;
	protected File currentFile;
	
	/**
	 * Constructor UICommand.
	 * <p>
	 * Create the graphical interface for one command.
	 * </p>
	 * @param command
	 */
	public UICommand(Command command,Composite parent,int style) {
		super (parent,style);
		this.command = command;
		this.commandButton = new Button(this,SWT.PUSH);
		this.commandButton.setText(command.getName());
		this.commandButton.setEnabled(false);
		this.commandResult = new Label(this, SWT.SINGLE);

		this.commandButton.addListener(SWT.Selection,event -> {
			this.execute();
		});
		
		
		GridLayout grid = new GridLayout();
		grid.numColumns = 2;
		this.setLayout(grid);
		
		GridData gridData= new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gridData.minimumWidth = 200;
		commandButton.setLayoutData(gridData);
		GridData gridData2= new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gridData2.minimumWidth = 800;
		commandResult.setLayoutData(gridData2);
		
	}

	/**
	 * Executes the command with the current file selected.
	 * Print the result in the label next to the button.
	 */
	public void execute() {
		try {
			this.commandResult.setText(this.command.execute(currentFile));
		} catch(Exception e) {
			this.commandResult.setText(e.getMessage());
		}
	
	}
	
	/**
	 * Clear the result label on the graphical interface.
	 */
	public void clear() {
		this.commandResult.setText("");
	}
	
	/**
	 * Modifies the current file. 
	 * Checks if the command is executable with the file type (file or folder) 
	 * and enables or disables the button consequently.
	 * 
	 * @param file the new current file
	 */
	public void setCurrentFile(File file) {
		this.currentFile = file;
		this.commandButton.setEnabled(this.isEnabled());
		this.clear();
	}
	
	/**
	 * Returns if the command can be executed giving the current file type (file or folder).
	 * 
	 * @return a boolean telling if the command can be executed giving the current file type (file or folder).
	 */
	public boolean isEnabled() {
		if(this.currentFile.isDirectory() && !this.command.folderCompatible()) {
			return false;
		} else if(!this.currentFile.isDirectory() && !this.command.fileCompatible()) {
			return false;
		} else {
			return true;
		}
	}
	
}
