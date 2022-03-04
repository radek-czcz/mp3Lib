package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileFilter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;

import tree.DirectoryTreeModel;

public class testFrame {
	
	public static void main(String[] args) {
		JFrame treeFrame = new JFrame();
		BorderLayout bl = new BorderLayout();
		treeFrame.setLayout(bl);
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
				//treeFrame.setLayout(fl);
				

		DefaultMutableTreeNode root;
		DirectoryTreeModel treeModel;
		JTree tree;
		
		root = new DefaultMutableTreeNode(new String("Computer"));
		treeModel = new DirectoryTreeModel(root);
		tree = new JTree();
		tree.addTreeExpansionListener(treeModel);
		treeFrame.setVisible(true);

		for (File runner : File.listRoots()) {
			root.add(new DefaultMutableTreeNode(runner));
			DefaultMutableTreeNode tnd = (DefaultMutableTreeNode)root.getLastChild();
			
			for (File runner2 : runner.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					return pathname.isDirectory();
				}
			})) {DefaultMutableTreeNode mtn = new DefaultMutableTreeNode(runner2);
				
			if	(mtn.getUserObject() != null)
				tnd.add(mtn);
			}
			
		}
		
		tree.setModel(treeModel);
		
		JButton addButton = new JButton("Add");
		JButton addButton2 = new JButton("Done");
;
		
		treeFrame.add(tree, BorderLayout.CENTER);
		
		treeFrame.setVisible(true);
		//treeFrame.setSize(300, 700);
		JPanel jp = new JPanel();
		treeFrame.add(jp, BorderLayout.SOUTH);
		Border br;
		br = BorderFactory.createLineBorder(Color.BLACK);
		jp.setBorder(br);
		//jp.setPreferredSize(new Dimension(200, 200));
		BorderLayout fl2 = new BorderLayout();
		jp.setLayout(fl2);
		
		jp.add(addButton, BorderLayout.NORTH);
		jp.add(addButton2, BorderLayout.SOUTH);
		tree.setPreferredSize(new Dimension(200, 600));
		treeFrame.pack();

		
		System.out.println("window shown");
	}
	
	

}
