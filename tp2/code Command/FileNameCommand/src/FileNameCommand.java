import java.io.File;

import com.log8430.group9.commands.Command;

public class FileNameCommand implements Command {

	public FileNameCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(File file) {
		return file.getName();
	}

	@Override
	public boolean fileCompatible() {
		return true;
	}

	@Override
	public boolean folderCompatible() {
		return false;
	}

	@Override
	public String getName() {
		return "FileNameCommand";
	}

}
