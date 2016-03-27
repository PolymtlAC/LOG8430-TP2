package Tests;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.easymock.EasyMock;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.log8430.group9.commands.Command;
import com.log8430.group9.commands.PlugInLoader;
import com.log8430.group9.views.CommandsPart;
import com.log8430.group9.views.TreePart;
import com.log8430.group9.views.UICommand;

public class Tests{
	private Command mockCommandFile = null;
	private Command mockCommandDir = null;
	private Command mockCommandFD = null;
	private PlugInLoader pluginLoader = null;
	private CommandsPart commandsPart = null;
	private File fileTest;
	private File dossierTest;
	private ArrayList<MockUICommand> UICommandsList = new ArrayList<>();
	@Before
	public void setUp() throws Exception {
		//creation des mock compatible fichier, dossier, les deux
		mockCommandFile = EasyMock.createMock(Command.class);
		mockCommandDir = EasyMock.createMock(Command.class);
		mockCommandFD = EasyMock.createMock(Command.class);
		ArrayList<Command> commands = new ArrayList<>();
		commands.add(mockCommandFile);
		commands.add(mockCommandDir);
		commands.add(mockCommandFD);

		//redefinition du Loader pour fournir les mocks
		pluginLoader = new PlugInLoader() {
			@Override
			public ArrayList<Command> getCommandsList(){
				return commands;
			}
		};
		//creation des elements de tests
		this.fileTest = new File("dossierTest/fichierTest");
		this.dossierTest = new File("dossierTest");
		

	}
	//emulation de la creation de composant graphique des commandes avec la technologie SWING
	public void createMockUICommand(ArrayList<Command> commands){
		for(Command command : commands){
			UICommandsList.add(new MockUICommand(command));
		}
	}
	@Test
	public void testNumberOfCommands() {
		int retour = 3;
		createMockUICommand(pluginLoader.getCommandsList());
		Assert.assertEquals("La valeur retournee est invalide", retour, UICommandsList.size());
	}
	/**
	 * test si les commandes sont actives ou non ainsi que le fonctionnement de l'execution pour un fichier
	 */
	@Test
	public void testExecutionFOnFile(){
		EasyMock.expect(mockCommandFile.fileCompatible()).andReturn(true);		 
		EasyMock.expect(mockCommandFile.execute(fileTest)).andReturn("fichierTest");
		EasyMock.expect(mockCommandDir.fileCompatible()).andReturn(false);		 
		EasyMock.expect(mockCommandDir.execute(fileTest)).andReturn("dossierTest");
		EasyMock.expect(mockCommandFD.fileCompatible()).andReturn(true);		 
		EasyMock.expect(mockCommandFD.execute(fileTest)).andReturn("fichierTest");
		EasyMock.replay(mockCommandFile);
		EasyMock.replay(mockCommandDir);
		EasyMock.replay(mockCommandFD);
		
		createMockUICommand(pluginLoader.getCommandsList());
		ArrayList<Boolean> returnedValues = new ArrayList<>();
		returnedValues.add(Boolean.TRUE);
		returnedValues.add(Boolean.FALSE);
		returnedValues.add(Boolean.TRUE);
		
		for(int i = 0; i< UICommandsList.size();i++){

			UICommandsList.get(i).setCurrentFile(fileTest);
			Assert.assertEquals("La commande n'a pas le comportement adequat ", returnedValues.get(i), UICommandsList.get(i).commandButton.isEnabled());
			UICommandsList.get(i).execute();
			if(UICommandsList.get(i).commandButton.isEnabled()){
				Assert.assertEquals("La valeur retourné est fausse", "fichierTest", UICommandsList.get(i).getCommandResult().getText());
			}
		}
	}
	/**
	 * test si les commandes sont actives ou non ainsi que le fonctionnement de l'execution pour un dossier
	 */
	@Test
	public void testExecutionOnFolder(){
		EasyMock.expect(mockCommandFile.folderCompatible()).andReturn(false);		 
		EasyMock.expect(mockCommandFile.execute(dossierTest)).andReturn("fichierTest");
		EasyMock.expect(mockCommandDir.folderCompatible()).andReturn(true);		 
		EasyMock.expect(mockCommandDir.execute(dossierTest)).andReturn("dossierTest");
		EasyMock.expect(mockCommandFD.folderCompatible()).andReturn(true);		 
		EasyMock.expect(mockCommandFD.execute(dossierTest)).andReturn("dossierTest");
		EasyMock.replay(mockCommandFile);
		EasyMock.replay(mockCommandDir);
		EasyMock.replay(mockCommandFD);
		
		createMockUICommand(pluginLoader.getCommandsList());
		ArrayList<Boolean> returnedValues = new ArrayList<>();
		returnedValues.add(Boolean.FALSE);
		returnedValues.add(Boolean.TRUE);
		returnedValues.add(Boolean.TRUE);
		
		for(int i = 0; i< 1;i++){
			System.out.println(i);
			UICommandsList.get(i).setCurrentFile(dossierTest);
			Assert.assertEquals("La commande n'a pas le comportement adequat ", returnedValues.get(i), UICommandsList.get(i).commandButton.isEnabled());
			UICommandsList.get(i).execute();
			if(UICommandsList.get(i).commandButton.isEnabled()){
				Assert.assertEquals("La valeur retourné est fausse", "dossierTest", UICommandsList.get(i).getCommandResult().getText());
			}
		}
	}
	
	
}
