package arch;

import java.io.File;
import java.io.FileFilter;


public class ArchiveFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory() || !pathname.getName().contains(".ada"))
		return false;
		else return true;
	}
}
