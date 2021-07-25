package entry;

public class FilesPaths implements PathProvider {
	
	String pathDriveLetter;
	String path;
	String[] buttons;

	public String[] getButtons() {
		return buttons;
	}

	public void setButtons(String[] buttons) {
		this.buttons = buttons;
	}

	public void setPathDriveLetter(String pathDriveLetter) {
		this.pathDriveLetter = pathDriveLetter;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathDriveLetter() {
		return pathDriveLetter;
	}

	public String getPath() {
		return path;
	}
}
