import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.log8430.group9.commands.Command;

public class CopyCommand implements Command {

	public CopyCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(File file) {
		String result = "error";
		JPanel panel = new JPanel();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setSelectedFile(file);
		int returnVal = fileChooser.showOpenDialog(panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File destFile = fileChooser.getSelectedFile();
            boolean status = copier(file.toPath(), destFile.toPath());
            if(status)
            	result = "done";
        } else {
           result = "cancelled by user";
        }
		return result;
	}

	@Override
	public boolean fileCompatible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean folderCompatible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "File copy";
	}
	
	private boolean copier(Path source, Path destination) { 
	    try { 
	        Files.copy(source, destination); 
	        // Il est également possible de spécifier des options de copie. 
	        // Ici : écrase le fichier destination s'il existe et copie les attributs de la source sur la destination.  
	       //Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES); 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	        return false; 
	    } 
	    return true; 
	}
}
