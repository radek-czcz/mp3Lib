package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface LocsSetsTransferer {
	void transferToArchive() throws FileNotFoundException, IOException;
	void transferToCurrent();
}