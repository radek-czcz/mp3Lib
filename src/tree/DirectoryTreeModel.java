package tree;

import java.io.File;
import java.io.FileFilter;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import core.MyTreeNode;

public class DirectoryTreeModel extends DefaultTreeModel implements TreeExpansionListener {


    public DirectoryTreeModel(TreeNode root) {
        super(root, false);
    }

	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
		TreePath 				tp;
		DefaultMutableTreeNode 				objNode;
		DefaultMutableTreeNode 	firstLeaf;
		File 					fileObj;
		File 					fileObj2;
		File[] 					filArr;
		Object 					obj1;
		
		objNode = (DefaultMutableTreeNode)event.getPath().getLastPathComponent();
		//System.out.println(objNode);
		//System.out.println(objNode.getUserObject());
		
		fileObj = (File)objNode.getUserObject();
		System.out.println(fileObj);
		
		firstLeaf = objNode.getFirstLeaf();
		
		do {
			
			fileObj = (File)firstLeaf.getUserObject();
			filArr = fileObj.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					if (pathname.listFiles() != null)
					return pathname.isDirectory();
					else return false;
				}
			});
			try {
			if (filArr != null || filArr.length != 0){
			for (File filRunner : filArr)
				firstLeaf.add(new MyTreeNode(new File(filRunner.getAbsolutePath()) {
					@Override
					public String toString() {
						// TODO Auto-generated method stub
						return super.getName();
					}
				}));}
			} catch (NullPointerException e) 
			{e.printStackTrace();}
			firstLeaf = firstLeaf.getNextSibling();
		} while (firstLeaf != null);
		
		firstLeaf = objNode.getFirstLeaf();
		fileObj = (File)firstLeaf.getUserObject();
		

		



		
		//this.add(new MyTreeNode(filArr[0]));

	}

	@Override
	public void treeCollapsed(TreeExpansionEvent event) {
		// TODO Auto-generated method stub

	}

}