import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import entry.PathProvider;

public class ArchiveData {
	
	static private SortedSet<File> setOfArchives; // = new TreeSet<>();

	public SortedSet<File> getSetOfArchives() {
		return setOfArchives;
	}

	void setSetOfArchives(SortedSet<File> setOfArchives) {
		this.setOfArchives = setOfArchives;
	}
	
	public void addToSetOfArchives() {
		File locAndName = getLocationOfFilesFromUser();
		//setOfArchives.add(directory);
	}

	private File getLocationOfFilesFromUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
	
	}

static class LocAndNameWindow {
	public static void main(String[] args) {
		first();
	}
	
	static void first() {
		
		ApplicationContext aC = new ClassPathXmlApplicationContext("config2.xml");
		
		//setOfArchives = (SortedSet<File>)aC.getBean("tree");
		
		Component dialog = (Component)aC.getBean("entryWindow");
																	System.out.println(aC.getType("entryWindow"));
		Container cntr = (Container)dialog;
		RootPaneContainer rpc = (RootPaneContainer)cntr;
		JComponent jL;
		
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
		Component c1 = rpc.getContentPane().add(new JTextField("abc"));
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
		
		dialog.setSize(200, 150);
		dialog.setVisible(true);
		
		Window window = (Window)dialog;
		window.addWindowListener((WindowListener) aC.getBean("WindowListenerBean"));
		}
	}
}
