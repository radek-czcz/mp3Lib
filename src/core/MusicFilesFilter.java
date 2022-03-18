package core;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Array;

public class MusicFilesFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {

		boolean tr;
		try {
			tr = Array.getLength(pathname.listFiles()) == 0;
		} catch (NullPointerException exc) {
			if (pathname.isDirectory())
				return false;
		}
		if ((pathname.getName().toLowerCase().matches(".*.mp3") || pathname.isDirectory()))

			return true;
		else
			return false;
	}

}
