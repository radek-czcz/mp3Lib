package viewer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		Component cmp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		cmp.setForeground(new Color(120, 180 ,115));
		setForeground(new Color(120, 180 ,115));
		
		return cmp;
	}

}
