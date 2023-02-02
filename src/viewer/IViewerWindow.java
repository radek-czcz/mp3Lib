package viewer;

import javax.swing.tree.TreeModel;

public interface IViewerWindow {
	void createWindow();
	TreeModel getDataReceiver();
}
