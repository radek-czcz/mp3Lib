package entry;

public class FilesPaths implements PathProvider {
	
	String pathDriveLetter;
	String path;

	public void setPathDriveLetter(String pathDriveLetter) {
		this.pathDriveLetter = pathDriveLetter;
	}

	public void setPath(String path) {
		if (path==null) {
			this.path="";
		}
		this.path = path;
	}

	public String getPathDriveLetter() {
		return pathDriveLetter;
	}

	public String getPath() {
		return path;
	}
}
