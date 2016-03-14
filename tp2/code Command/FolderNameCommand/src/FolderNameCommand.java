import java.io.File;

import com.log8430.group9.commands.Command;

public class FolderNameCommand implements Command {

	public FolderNameCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(File file) {
		return file.getName();
	}

	@Override
	public boolean fileCompatible() {
		return false;
	}

	@Override
	public boolean folderCompatible() {
		return true;
	}

	@Override
	public String getName() {
		return "FolderNameCommand";
	}

}
