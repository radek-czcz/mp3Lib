package viewer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class MyTreeCellRenderer extends JPanel implements TreeCellRenderer {
	
	TagsValuesCompiler TagCompiler;
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		
		this.removeAll();
		this.add(TagCompiler.compileTagsValues(value));
		return this;
	}
	
	public void setTagCompiler(TagsValuesCompiler tagCompiler) {
		TagCompiler = tagCompiler;
	}

	public MyTreeCellRenderer() {
	}

}
