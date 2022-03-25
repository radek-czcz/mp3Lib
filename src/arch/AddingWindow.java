package arch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class AddingWindow {
	
	Component scrollableContent;
	ArrayList<Component> cmps;
	
	void prepareWindow(){
		JScrollPane ScrollPane;
		JFrame treeFrame = new JFrame();
		BorderLayout bl = new BorderLayout();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		ScrollPane = new JScrollPane(scrollableContent);
		BorderLayout fl2 = new BorderLayout();
		JPanel jp = new JPanel();
		Border br;
		br = BorderFactory.createLineBorder(Color.BLACK);
		
		treeFrame.setLayout(bl);
		jp.setBorder(br);
		jp.setLayout(fl2);
		for (Component cmp : cmps) {jp.add(cmp);}
	}
}
