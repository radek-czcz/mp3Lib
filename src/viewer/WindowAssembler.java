package viewer;

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

import entry.AppContext;

public class WindowAssembler {
	
	Container window;
	Container panel;
	JComponent viewedContentComponent;
	
	public WindowAssembler() {};
	
	public void assembleWindow() {

		window.add(panel);
		this.panel.add(viewedContentComponent);
		//ColoredJTree tree = (ColoredJTree)viewedContentComponent;
		panel.add(viewedContentComponent);
		window.setSize(800, 500);
		window.setVisible(true);
		
		DefaultMutableTreeNode root1 = (DefaultMutableTreeNode)tree.getModel().getRoot();
		
		String[] row = new String[] {"radek", "abc"};
		root1.add(new DefaultMutableTreeNode(row));
		root1.add(new DefaultMutableTreeNode("ALL down"));

	}
	
	void setWindow(Container window) {
		this.window = window;
	}
	void setPanel(Container panel) {
		this.panel = panel;
	}
	void setViewedContent(JComponent viewedContent) {
		this.viewedContentComponent = viewedContent;
	}
	
	public static void main(String[] args) {
		new WindowAssembler();
	}
	
}
