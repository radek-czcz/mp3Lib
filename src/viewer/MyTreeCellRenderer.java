package viewer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Collection;

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
		this.add(new JLabel(TagCompiler.compileTagsValues()));
		//al.add(new JLabel("radek"));
		//al.add(new JLabel("bartek"));
        //String         stringValue = tree.convertValueToText(value, selected,
                //expanded, leaf, row, hasFocus);

		//this.tree = tree;
		//this.hasFocus = hasFocus;
		//setText(stringValue);
		//jp.add(new JLabel(value.toString()));
		//jp.setLayout(lm);
		//jp.setSize(size[0], size[1]);
		
		/*for (JLabel lab: al) {
			jp.add(lab);
		}*/
		return this;
	}
	
	public MyTreeCellRenderer() {}

}
