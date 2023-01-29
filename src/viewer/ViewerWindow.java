package viewer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

import entry.AppContext;

public class ViewerWindow {
	
	JFrame window;
	JComponent panel;
	JComponent viewedContentComponent;
	
	public ViewerWindow() {
		this.window = new JFrame();
		this.panel = AppContext.getContext().getBean("panelBean",javax.swing.JComponent.class);
		viewedContentComponent = AppContext.getContext().getBean("coloredJTreeBean", javax.swing.JComponent.class);
		this.panel.add(viewedContentComponent);
		
		window.add(panel);
		ColoredJTree tree = (ColoredJTree)viewedContentComponent;
		TreeCellRenderer treeRenderer = tree.getCellRenderer();
		
		//panel.add(tree);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("ALL");
		tree.setModel(new DefaultTreeModel(root));
		
		//String[] row = new String[] {"radek", "abc"};
		
		//root.add(new DefaultMutableTreeNode(row));
		
		window.setSize(800, 500);
		window.setVisible(true);
		
		root.add(new DefaultMutableTreeNode("ALL down"));

	}
	
	public static void main(String[] args) {
		new ViewerWindow();
	}
	
	void readArchiveData() {
		
	}
}
