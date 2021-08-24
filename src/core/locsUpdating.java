import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;


public class locsUpdating implements Runnable {//, FileFilterLocUpdate {
	
	public static void main(String[] args) {

	}
	
	locsSets locToUpdate;
	
	void update() {
		String pathMain = locToUpdate.getLocPath().getPath();
		File fileToUpdate = new File(pathMain);
		File[] listDirsA = fileToUpdate.listFiles(new FileFilterLocUpdate());
		ArrayList<File> listDirs = new ArrayList<>();
		Collections.addAll(listDirs, listDirsA);
		Iterator<File> listDirsIt = listDirs.iterator();
		while (listDirsIt.hasNext()) {
			File runner = listDirsIt.next();
			long dUpdLong = runner.lastModified();
			Date dUpdDate = new Date(dUpdLong);
		}
	}
	
	locsUpdating(locsSets inp){
		locToUpdate = inp;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}

class FileFilterLocUpdate implements java.io.FileFilter {
	
	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory())
			return true;
		else
			return false;
	}
}
