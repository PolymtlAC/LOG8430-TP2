package com.log8430.group9.commands;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.di.annotations.Execute;

public class EvaluateContributionsHandler {
	private static final String CommandExtension_ID = 
			"com.log8430.group9.commandExtension";
	@Execute
	public void execute(IExtensionRegistry registry) {
		getNomsHorloges();
		System.out.println("********************************************************");

		IConfigurationElement[] config =
				registry.getConfigurationElementsFor(CommandExtension_ID);
		try {
			for (IConfigurationElement e : config) {
				final Object o =
						e.createExecutableExtension("class");
				if (o instanceof Command) {
					System.out.println(((Command) o).getName());
				}
			}
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
	}

	static private void getNomsHorloges() {
		//String extensionPointId = "com.eclipsetotale.tutorial.horloge.horloges";
		IConfigurationElement[] contributions = 
				Platform.getExtensionRegistry().getConfigurationElementsFor(CommandExtension_ID);
		System.out.println(contributions.length);
		for (int i = 0; i < contributions.length; i++) {
			System.out.println(contributions[i].getAttribute("nom"));
		}
	}
}
