package viewer.dataexchange;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import viewer.AbstractDataProvider;

public class DataProvider1 extends AbstractDataProvider {
	

	@Override
	protected void sendDataToModel(Object song) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)window.getDataReceiver().getRoot();
		node.add(new DefaultMutableTreeNode(song));
	}
}
