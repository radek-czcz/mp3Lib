package viewer.dataexchange;

import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import core.Mp3Ident;
import core.locsSets;
import viewer.DataProviderAbstract;

public class DataProvider2 extends DataProviderAbstract {
	
	locsSets locSet;
	
	@Override
	public void sendDataToModel(Object song) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)window.getDataReceiver().getRoot();
		locsSets set1 = (locsSets)song;
		Iterator<Mp3Ident> iter = set1.getSongs().iterator();
		while (iter.hasNext())
			
		node.add(new DefaultMutableTreeNode(iter.next().getTagArt()));
	}

	public void setLocSet(locsSets locSet) {
		this.locSet = locSet;
	}
}
