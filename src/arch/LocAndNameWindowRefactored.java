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
import entry.AppContext;
import tree.DirectoryTreeModel;

public class LocAndNameWindowRefactored {
	
	private Component frame;
	private LayoutManager layoutManager;
	private MouseListener mouseListener;
	private JComponent label;
	private Component textInput;
	private Component driveSelection;
	private Component button;
	private ActionListener treeButtonListener;
	
	private String name;
	private File path;
	
	public LocAndNameWindowRefactored() {
		
	}
	
	private void setWindow() {
		
		ApplicationContext aC = AppContext.getContext();
		
		Component dialog = frame;
		Container cntr = (Container)dialog;
		RootPaneContainer rpc = (RootPaneContainer)cntr;
		JComponent jL;
		{
		Container cntr2 = (Container)cntr.getComponent(0);
		}
		rpc.getContentPane().setLayout(layoutManager);
		
		// maybe not needed - listeners
		dialog.addMouseListener(mouseListener);
		
		jL = label;
		
		//JLabel "name"
		Component c0 = rpc.getContentPane().add(jL);
		jL.setOpaque(true);
		c0.setPreferredSize(new Dimension(40, 20));
		c0.setBackground(new Color(200,150,90));
	
		//JtextField name's input field
		Component c1 = rpc.getContentPane().add(textInput);
		c1.setPreferredSize(new Dimension(100, 20));
		
		//JLabel "drive selection"
		Component c2 = rpc.getContentPane().add(driveSelection);
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
		Component jB =  rpc.getContentPane().add(button);
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
		aB2.addActionListener(treeButtonListener);
		
		
		dialog.setSize(200, 200);
		
	}
	
	public void showWindow() {
		frame.setVisible(true);
		}
	
	String getName() {
		return name;
	}
	
	File getPath() {
		return path;
	}
	
	public void setFrame(Component frame) {
		this.frame = frame;
	}
	
	public void setLayoutManager(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}
	
	public void setLabel(JComponent label) {
		this.label = label;
	}
	
	public void setTextInput(Component textInput) {
		this.textInput = textInput;
	}
	
	public void setDriveSelection(Component driveSelection) {
		this.driveSelection = driveSelection;
	}
	
	public void setButton(Component button) {
		this.button = button;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPath(File path) {
	this.path = path;
	}
	
	public void setTreeButtonListener(ActionListener treeButtonListener) {
		this.treeButtonListener = treeButtonListener;
	}
}