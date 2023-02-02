package viewer;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class DataProvider1 extends AbstractDataProvider {
	

	@Override
	void sendDataToModel(Object song) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)window.getDataReceiver().getRoot();
		node.add(new DefaultMutableTreeNode(song));
	}

}
