package com.log8430.group9.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
/**
 * Fenetre principal de l'application
 * Ce charge de creer une perspective et un placement pour les differents elements de vue et charge ses elements dans leur place respective
 * @author Alexandre
 *
 */
public class MainWindow extends ViewPart{
	/**
	 * container general permettant le redimensionnement des elements de vue
	 */
	private SashForm windowSash;
	/**
	 * element de vue pour la gestion des commandes
	 */
	private CommandsPart commandsPart;
	/**
	 * elements de vue pour l'affichage de l'arbre
	 */
	private TreePart treePart;
	
	@Override
	public void createPartControl(Composite parent) {
		windowSash = new SashForm(parent, SWT.HORIZONTAL);
		Composite part1 = new Composite(windowSash, SWT.NONE);
		Composite part2 = new Composite(windowSash,SWT.NONE);
		treePart = new TreePart();
		treePart.createComposite(part1);
		commandsPart = new CommandsPart();
		commandsPart.createComposite(part2);
		
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
