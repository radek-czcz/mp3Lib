package viewer;

import java.util.Hashtable;
import java.util.Stack;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ColoredJTree extends JTree {
	
	@Override
	public void setCellRenderer(TreeCellRenderer x) {
		// TODO Auto-generated method stub
		super.setCellRenderer(x);
	}
	
	ColoredJTree(){
		this(getRoot());
	}
	
    public ColoredJTree(TreeModel newModel) {
        super(newModel);
    }
	
    protected static TreeModel getRoot() {
        DefaultMutableTreeNode      root = new DefaultMutableTreeNode("Archive content");
        return new DefaultTreeModel(root);
    }

}
