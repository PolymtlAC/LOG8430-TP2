import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.log8430.group9.commands.Command;

public class RenameCommand implements Command {

	public RenameCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(File file) {
		String result = "error";
		JPanel panel = new JPanel();
		String inputValue = JOptionPane.showInputDialog("Please input a value");
		if(inputValue != null){
			if(rename(file, inputValue))
				result = "done";
		}
		else
			result = "cancelled by user";
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
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Rename";
	}
	public boolean rename(File source, String nomDest){
		int index = source.getName().lastIndexOf(".");
		String type ="";
		if(index >=0)
			type = source.getName().substring(index);
		File destination = new File(source.getParent()+"\\"+nomDest+type); 
		return source.renameTo(destination);
		
	}
}
