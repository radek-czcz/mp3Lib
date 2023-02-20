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

public class ViewerWindow1 implements ViewerWindow {

	Container window;
	Container panel;
	ColoredJTree viewedContentComponent;
	
	public ViewerWindow1() {
	}
	
	public void createWindow(){
		this.window.add(panel);
		this.panel.add(viewedContentComponent);	
		window.setSize(800, 500);
		window.setVisible(true);
	}

	public static void main(String[] args) {
		ViewerWindow1 myWindow = AppContext.getContext().getBean("viewerBean", ViewerWindow1.class);
		myWindow.createWindow();
	}

	@Override
	public TreeModel getDataReceiver() {
		return viewedContentComponent.getModel();
	}

	public void setViewedContentComponent(ColoredJTree viewedContentComponent) {
		this.viewedContentComponent = viewedContentComponent;
	}

	public void setPanel(Container panel) {
		this.panel = panel;
	}

	public void setWindow(Container window) {
		this.window = window;
	}
}
