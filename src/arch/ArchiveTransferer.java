package arch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import core.Mp3Ident;
import core.LocsSetsTransferer;

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
		Iterator<Mp3Ident> iter = to.getObjects().iterator();
		while (iter.hasNext())
			try (FileOutputStream fos = new FileOutputStream("archivefiles//" + to.getName() + ".ada", true);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(iter.next());
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	@Override
	public void transferToCurrent() {
		// TODO Auto-generated method stub
	}
}
