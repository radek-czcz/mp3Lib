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
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.text.JTextComponent;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import entry.PathProvider;
import package1.locsSets;

public class ArchiveData {
	
	static private SortedSet<ArchiveData> setOfArchives;

	public static SortedSet<ArchiveData> getSetOfArchives() {
		return setOfArchives;
	}
	
	public static void addToSetOfArchives() {
		File locAndName = LocAndNameWindow.getPath();
	}
	
	String name;
	locsSets data;
	
	ArchiveData(String name) {
		this.name = name;
		data = new locsSets(this);
	}

static class LocAndNameWindow {
	
	private static String name;
	private static File path;
	
	public static void main(String[] args) {
		first();
	}
	
	static void first() {
		
		ApplicationContext aC = new ClassPathXmlApplicationContext("config2.xml");
		
		//setOfArchives = (SortedSet<File>)aC.getBean("tree");
		
		Component dialog = (Component)aC.getBean("entryWindow");
		Container cntr = (Container)dialog;
		RootPaneContainer rpc = (RootPaneContainer)cntr;
		JComponent jL;
		{
		Container cntr2 = (Container)cntr.getComponent(0);
		System.out.println("end");
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
				path = new File(archive[0].toString());
				
				System.out.println( "perform action: name of archive=" 
				+ stringComp.getText() 
				+ " selected=" + path);
			}
		});
		
		dialog.setSize(200, 150);
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
}
