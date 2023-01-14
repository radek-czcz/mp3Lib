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
		
		
		
		/*JFrame treeFrame = new JFrame();
		JTree tree;
		BorderLayout bl = new BorderLayout();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		DefaultMutableTreeNode root;
		DirectoryTreeModel treeModel;
		root = new DefaultMutableTreeNode(new String("Computer"));
		treeModel = new DirectoryTreeModel(root);
		tree = new JTree();
		JButton addButton = new JButton("Add");
		JButton addButton2 = new JButton("Done");
		BorderLayout fl2 = new BorderLayout();
		JPanel jp = new JPanel();
		
		treeFrame.setLayout(bl);
		tree.addTreeExpansionListener(treeModel);
		
		for (File runner : File.listRoots()) {
			root.add(new DefaultMutableTreeNode(runner));
			DefaultMutableTreeNode tnd = (DefaultMutableTreeNode)root.getLastChild();
			for (File runner2 : runner.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					return pathname.isDirectory();
				}}))
			{
			DefaultMutableTreeNode mtn = new DefaultMutableTreeNode(runner2);
			if	(mtn.getUserObject() != null) 
				tnd.add(mtn);
			}
		}
		
		tree.setModel(treeModel);
		tree.setPreferredSize(new Dimension(200, 600));

		Border br;
		br = BorderFactory.createLineBorder(Color.BLACK);
		
		jp.setBorder(br);
		jp.setLayout(fl2);
		jp.add(addButton, BorderLayout.NORTH);
		jp.add(addButton2, BorderLayout.SOUTH);
		
		treeFrame.add(tree, BorderLayout.CENTER);
		treeFrame.add(jp, BorderLayout.SOUTH);
		treeFrame.pack();
		treeFrame.setVisible(true);*/
	}
}
