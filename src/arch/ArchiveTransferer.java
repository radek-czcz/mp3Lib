package arch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import core.LocsSetsTransferer;
import core.startCl;

public class ArchiveTransferer implements LocsSetsTransferer {

	TransferedObject to;
	
	protected void setTo(TransferedObject inp) {
		to = inp;
	}
	
	@Override
	public void transferToArchive() throws FileNotFoundException, IOException {
		File dir = new File("archivefiles");
		if (!dir.exists()) {
			dir.mkdir();
		}
		FileOutputStream fos = new FileOutputStream("archivefiles//" + to.getName() + ".ada", true);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Iterator<Object> iter = to.getObjects().iterator();
		while (iter.hasNext()) {
		oos.writeObject(iter.next());
		}
		oos.close();
		}

	@Override
	public void transferToCurrent() {
		// TODO Auto-generated method stub
	}
}
