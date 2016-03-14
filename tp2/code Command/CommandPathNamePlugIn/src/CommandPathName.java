import java.io.File;

import com.log8430.group9.commands.Command;

public class CommandPathName implements Command {

	public CommandPathName() {
		System.out.println("test");
	}

	@Override
	public String execute(File file) {
		// TODO Auto-generated method stub
		return file.getAbsolutePath();
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
		return "CommandPathName";
	}

}
