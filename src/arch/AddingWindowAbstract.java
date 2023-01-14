package arch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.border.Border;

public abstract class AddingWindowAbstract {
	
	Component scrollableContent;
	
	protected ArrayList<Component> components;

	/**
	 * sets components to be added to adding-window 
	 * @param cmps
	 */
	public void setComponents(ArrayList<Component> cmps) {
		this.components = cmps;
	}
	
	/**
	 * sets and shows window to step adding
	 */
	void prepareAndShow(){
		JScrollPane scrollPane;
		JFrame treeFrame = new JFrame();
		BorderLayout bl = new BorderLayout();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		scrollPane = new JScrollPane(scrollableContent);
		FlowLayout fl2 = new FlowLayout();
		JPanel jp = new JPanel();
		Border br;
		br = BorderFactory.createLineBorder(Color.BLACK);
		

		
		treeFrame.setLayout(bl);
		jp.setBorder(br);
		jp.setLayout(fl2);
		
		Iterator<Component> iter = components.iterator();
		while (iter.hasNext()){
			Component iterCmp = iter.next();
			try {
				jp.add((AbstractButton)iterCmp);
			} catch (ClassCastException e) {
				System.out.println("cast error");
			}
			if (iterCmp instanceof Scrollable)
			scrollPane = new JScrollPane(iterCmp);
		}
		
		treeFrame.add(scrollPane, BorderLayout.CENTER);
		treeFrame.add(jp, BorderLayout.SOUTH);
		treeFrame.pack();
		treeFrame.setVisible(true);
	}
	
	public void setScrollableContent(Component scrollableContent) {
		this.scrollableContent = scrollableContent;
	}
}
