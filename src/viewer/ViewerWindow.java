package viewer;

import javax.swing.tree.TreeModel;

public interface ViewerWindow {
	void createWindow();
	TreeModel getDataReceiver();
}
