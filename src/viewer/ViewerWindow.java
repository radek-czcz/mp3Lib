package viewer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import entry.AppContext;

public class ViewerWindow {
	
	JFrame window;
	JComponent panel;
	JComponent viewedContentComponent;
	
	public ViewerWindow() {
		this.window = new JFrame();
		this.panel = AppContext.getContext().getBean("panelBean",javax.swing.JComponent.class);
		viewedContentComponent = AppContext.getContext().getBean("ViewerContentBean", javax.swing.JComponent.class);
		this.panel.add(viewedContentComponent);
		
		window.add(panel);
		JTree tree = (JTree)viewedContentComponent;
		panel.add(tree);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("ALL");
		tree.setModel(new DefaultTreeModel(root));
		
		
		window.setSize(800, 500);
		window.setVisible(true);

		
	}
	
	public static void main(String[] args) {
		new ViewerWindow();
	}
}
