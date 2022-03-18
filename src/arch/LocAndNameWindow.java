package arch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ItemSelectable;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.RootPaneContainer;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import core.Mp3Ident;
import core.Scanning;
import core.Scanning2;
import entry.AppContext;
import tree.DirectoryTreeModel;

public class LocAndNameWindow {

private static String name;
private static File path;

public static void showWindow() {
	
	ApplicationContext aC = AppContext.getContext();
	
	//setOfArchives = (SortedSet<File>)aC.getBean("tree");
	
	Component dialog = (Component)aC.getBean("entryWindow");
	Container cntr = (Container)dialog;
	RootPaneContainer rpc = (RootPaneContainer)cntr;
	JComponent jL;
	{
	Container cntr2 = (Container)cntr.getComponent(0);
	}
	rpc.getContentPane().setLayout((LayoutManager)aC.getBean("layout"));
	
	// maybe not needed - listeners
	dialog.addMouseListener((MouseListener) aC.getBean("MouseListenerBean"));
	
	jL = (JComponent)aC.getBean("JLabelBean1");
	
	//JLabel "name"
	Component c0 = rpc.getContentPane().add(jL);
	jL.setOpaque(true);
	c0.setPreferredSize(new Dimension(40, 20));
	c0.setBackground(new Color(200,150,90));

	//JtextField name's input field
	Component c1 = rpc.getContentPane().add((Component)aC.getBean("TextFieldBean"));
	c1.setPreferredSize(new Dimension(100, 20));
	
	//JLabel "drive selection"
	Component c2 = rpc.getContentPane().add((Component)aC.getBean("JLabelBean2"));
	jL = (JComponent) c2;
	jL.setOpaque(true);
	jL.setBackground(new Color(180,120,200));
	c2.setPreferredSize(new Dimension(40, 20));
	
	//JComboBox drive's selection
	File[] drives = File.listRoots();
	Component c3 = rpc.getContentPane().add(new JComboBox(drives));
	//Component c3 = rpc.getContentPane().add((Component)aC.getBean("TextFieldBean"));
	c3.setPreferredSize(new Dimension(100, 20));
	
	//Button "read Data"
	Component jB =  rpc.getContentPane().add((Component)aC.getBean("ButtonBean"));
	AbstractButton aB = (AbstractButton)jB;
	
	/**
	 * defines function for button "read data"
	 */
	aB.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// name object
			
			JTextComponent stringComp = (JTextComponent)c1;
			
			//build archive File
			ItemSelectable iS = (ItemSelectable)c3;
			Object[] archive = iS.getSelectedObjects();
			ArchiveData newArchive = new ArchiveData();
			// name new archive
			newArchive.name = stringComp.getText();
			// scan data and add to created ArchiveData
			newArchive.data = Scanning.scanFolders(new File((String)iS.getSelectedObjects()[0].toString()));
			try {
			newArchive.addToSetOfArchives();
			ArchiveTransferer aT = new ArchiveTransferer();
			aT.setTo(newArchive);
			aT.transferToArchive();
			} catch (Exception exc) {
				exc.printStackTrace();
			} finally {
				// arch.ArchiveDataInfo adi = new ArchiveDataInfo();
			}
			
			System.out.println( "perform action: name of archive=" 
			+ stringComp.getText() 
			+ " selected=" + path);
		}
	});
	
	// show tree button
	Component treeButton = rpc.getContentPane().add(new JButton("show tree"));
	AbstractButton aB2 = (AbstractButton)treeButton;
	
	/**
	 * defines functioning of "show tree" button
	 */
	aB2.addActionListener(new ActionListener() {
		
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
		}}
	
			);
	
	
	dialog.setSize(200, 200);
	dialog.setVisible(true);
	
	Window window = (Window)dialog;
	window.addWindowListener((WindowListener) aC.getBean("WindowListenerBean"));
	}

static String getName() {
	return name;
}

static File getPath() {
	return path;
}
}