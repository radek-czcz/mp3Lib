package arch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import core.Mp3Ident;
import core.Scanning;
import tree.DirectoryTreeModel;

public class TreeButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JTree tree;
		JButton doneButton;
		JButton addButton;
		DefaultMutableTreeNode root;
		DirectoryTreeModel treeModel;
		JScrollPane ScrollPane;
		ArchiveData aD = new ArchiveData();
		ArrayList<File> treeSelection= new ArrayList<>();
		JFrame treeFrame = new JFrame();
		BorderLayout bl = new BorderLayout();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		root = new DefaultMutableTreeNode(new String("Computer"));
		treeModel = new DirectoryTreeModel(root);
		tree = new JTree();
		ScrollPane = new JScrollPane(tree);
		
		
		/**
		 * defines functioning of selection in tree
		 */
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode objNode;
				objNode = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
				treeSelection.add((File)objNode.getUserObject());
				System.out.println(objNode.getUserObject().toString() + " added to selection");
			}
		});
		
		/**
		 * defines function for "add" button
		 */
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(treeSelection);
				ArrayList<Mp3Ident> scannedCollection = new ArrayList<>();
				ArrayList<Mp3Ident> scannedAll =  new ArrayList<>();
				//ArchiveData aD;
				aD.name = "default";
				// scan data and add to created ArchiveData
				try {
				for (File runner : treeSelection) {
					scannedCollection.addAll(Scanning.scanFoldersToCollection(runner));
				}} catch (NullPointerException e1) {
					treeSelection.clear();
					return;}
				
				if (scannedCollection.isEmpty()) return;
				aD.getData().getSongs().addAll(scannedCollection);
				System.out.println(scannedCollection);
				
				try {
				//newArchive.addToSetOfArchives();
				//ArchiveTransferer aT = new ArchiveTransferer();
				//aT.setTo(newArchive);
				//aT.transferToArchive();
				} catch (Exception exc) {
					exc.printStackTrace();
				} finally {
					// arch.ArchiveDataInfo adi = new ArchiveDataInfo();
					treeSelection.clear();
				}
			}
		});
		doneButton = new JButton("Done");
		
		/**
		 * defines function for "done" button
		 */
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JFrame f = new JFrame(); 
				System.out.println(aD.getData().getSongs());
				if (!aD.getData().getSongs().isEmpty()) {
				aD.name = JOptionPane.showInputDialog(null, "Enter Name");      
				try {
					aD.addToSetOfArchives();
					ArchiveTransferer aT = new ArchiveTransferer();
					aT.setTo(aD);
					aT.transferToArchive();
					} catch (Exception exc) {
						exc.printStackTrace();
					} finally {
						// arch.ArchiveDataInfo adi = new ArchiveDataInfo();
					}
				} else System.out.println("empty library");
			}
		});
		
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
		//tree.setPreferredSize(new Dimension(200, 600));

		Border br;
		br = BorderFactory.createLineBorder(Color.BLACK);
		
		jp.setBorder(br);
		jp.setLayout(fl2);
		jp.add(addButton, BorderLayout.NORTH);
		jp.add(doneButton, BorderLayout.SOUTH);
		
		treeFrame.add(ScrollPane, BorderLayout.CENTER);
		treeFrame.add(jp, BorderLayout.SOUTH);
		treeFrame.pack();
		treeFrame.setVisible(true);
	}

}
