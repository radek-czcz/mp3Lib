package arch;
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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.RootPaneContainer;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import core.locsSets;
import tree.DirectoryTreeModel;
import core.Mp3Ident;
import core.MyTreeNode;
import core.Scanning;



public class ArchiveData implements Comparable<ArchiveData>, TransferedObject, Serializable{
	
	static private SortedSet<ArchiveData> setOfArchives = new TreeSet<ArchiveData>();

	public static SortedSet<ArchiveData> getSetOfArchives() {
		return setOfArchives;
	}
	
	private String name;
	private locsSets data;
	
	static void readListOfArchiveData() {
		File archiveDir = new File("archivefiles");
		for (File runner: archiveDir.listFiles()){
			ArchiveData newAd = new ArchiveData();
			newAd.name = runner.getName();
			setOfArchives.add(newAd);
		}
	}

	public void addToSetOfArchives() {
		setOfArchives.add(this);
	}

	ArchiveData() {
		new locsSets(this);
	}

@Override
	public int compareTo(ArchiveData o) {
		// TODO Auto-generated method stub
		return this.name.compareToIgnoreCase(o.name);
	}

public static class LocAndNameWindow {
	
	private static String name;
	private static File path;
	
	public static void first() {
		
		ApplicationContext aC = new ClassPathXmlApplicationContext("config2.xml");
		
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
		dialog.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("resized");
				e.getSource().getClass();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		}
		);
		
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
		aB2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame treeFrame = new JFrame();
				treeFrame.setLayout(new FlowLayout(FlowLayout.CENTER));

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
				treeFrame.add(addButton);
				treeFrame.add(addButton2);
				
				treeFrame.add(tree);
				treeFrame.pack();
				treeFrame.setVisible(true);
				
				System.out.println("window shown");
				
				
			
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

@Override
public String getName() {
	return name;
}

@Override
public String getPath() {
	return data.getLocPath().getPath();
}

@Override
public String getDescription() {
	// TODO Auto-generated method stub
	return "";
}

@Override
public String getOwner() {
	// TODO Auto-generated method stub
	return "";
}

@Override
public ArrayList<Mp3Ident> getObjects() {
	ArrayList<Mp3Ident> retVal = new ArrayList<>();
	if (data != null && data.getSongs() != null && !data.getSongs().isEmpty()) {
		retVal = (ArrayList)data.getSongs();
		return retVal;
	}
	else
		data.getSongs().addAll(Scanning.readArchiveFile(name));
	retVal = (ArrayList)data.getSongs();
	return retVal;
}

@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.getName();
}

public void setData(locsSets data) {
	this.data = data;
}

public locsSets getData() {
	return data;
}
}
