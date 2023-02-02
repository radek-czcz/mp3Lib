package viewer;

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import entry.AppContext;

public class ViewerWindow implements IViewerWindow {

	Container window;
	Container panel;
	ColoredJTree viewedContentComponent;
	
	public ViewerWindow() {
	}
	
	public void createWindow(){
		this.window.add(panel);
		this.panel.add(viewedContentComponent);	

		//TreeCellRenderer treeRenderer = tree.getCellRenderer();	
		//panel.add(tree);
		//DefaultMutableTreeNode root1 = (DefaultMutableTreeNode)tree.getModel().getRoot();
		
		
		//DefaultMutableTreeNode root = new DefaultMutableTreeNode("ALL");
		//tree.setModel(new DefaultTreeModel(root));
		
		//String[] row = new String[] {"radek", "abc"};
		
		//root1.add(new DefaultMutableTreeNode(row));
		
		window.setSize(800, 500);
		window.setVisible(true);
		
		//root1.add(new DefaultMutableTreeNode("ALL down"));
	}
	
	public static void main(String[] args) {
		ViewerWindow myWindow = AppContext.getContext().getBean("viewerBean", ViewerWindow.class);
		myWindow.createWindow();
	}

	public void setWindow(Container window) {
		this.window = window;
	}

	public void setPanel(Container panel) {
		this.panel = panel;
	}

	public void setViewedContentComponent(ColoredJTree viewedContentComponent) {
		this.viewedContentComponent = viewedContentComponent;
	}

	@Override
	public TreeModel getDataReceiver() {
		return viewedContentComponent.getModel();
	}
}
