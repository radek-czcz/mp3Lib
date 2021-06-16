
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Stream;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * Class makes DIRECTORIES WINDOW appear and perform save directories in
 * location's directory file.
 * 
 * @author Kamila i Radek
 */
public class tabModCust {

	// class variables
	static JFrame locsWindowFrame;
	static JTable locsTable;

	// constructor
	tabModCust() {
		locsWindowFrame = new JFrame();
		locsTable = new JTable(new extAbstrTab());
		setDisplayWindow();
		TableColumn tc = locsTable.getColumnModel().getColumn(1);
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("archive");
		comboBox.addItem("actual");
		tc.setCellEditor(new DefaultCellEditor(comboBox));
	}

	/**sets directories Window.
	 * Adds Listening when window closing, to save locations to file "locs.dat" (public void windowClosing(WindowEvent e))
	 * Adds listening to "click event", to add / delete lines of locations (public void mouseClicked(MouseEvent e))
	 */
	static private void setDisplayWindow() {
		locsWindowFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		locsWindowFrame.setSize(700, 400);
		locsWindowFrame.setLayout(new BorderLayout());
		//locsWindowFrame.add(new JScrollPane(locsTable));
		locsWindowFrame.add(new JScrollPane(locsTable));
		locsWindowFrame.setVisible(true);
		locsWindowFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				Stream<String> str;
				File dirDataFile = new File("g:\\locs.dat");
				FileWriter fW;
				try {
					fW = new FileWriter(dirDataFile);
					BufferedWriter bW = new BufferedWriter(fW);
					for (int nth = 0; nth < locsTable.getModel().getRowCount(); ++nth) {
						File locEntry = new File(locsTable.getModel().getValueAt(nth, 0).toString());
						if (locsTable.getModel().getValueAt(nth, 0) != "enter path" 
							&&	locEntry.exists()) {
						bW.write((String) locsTable.getModel().getValueAt(nth, 0));
						bW.newLine();
						}
					}
					bW.close();
					fW.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		
		});
		locsWindowFrame.getContentPane().getComponent(0).addMouseListener(new MouseListener() {
			
			void rightMouseClick(){
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 2) {
					rightMouseClick();
				}
				if (e.isControlDown()) {}
				JScrollPane jsp;
				jsp = (JScrollPane) e.getSource();
				JViewport jtb = (JViewport) jsp.getViewport();
				JTable jtb2 = (JTable) jtb.getView();
				TableModel jdtbm = jtb2.getModel();
				extAbstrTab myTab = (extAbstrTab) jdtbm;
				if (e.isControlDown()) {
					myTab.tableDat = Arrays.copyOf(myTab.tableDat, myTab.tableDat.length - 1);
				} else {
					myTab.tableDat = Arrays.copyOf(myTab.tableDat, myTab.tableDat.length + 1);
					myTab.tableDat[myTab.tableDat.length - 1] = new Object[3];
					myTab.tableDat[myTab.tableDat.length - 1][0] = "enter path";
				}
				/**locsWindowFrame.remove(0);
				locsTable = new JTable(myTab);
				locsWindowFrame.add(new JScrollPane(locsTable));**/
				//locsWindowFrame.repaint();
				//locsWindowFrame.getComponent(0).repaint();
				myTab.fireTableRowsInserted(myTab.tableDat.length + 1, myTab.tableDat.length + 1);

			}
		});
	}


/**
 * static class.
 * provides directories table functionality
 * 
 * @author Kamila i Radek
 */
static private class extAbstrTab extends AbstractTableModel {

	// two columns titles
	String[] cols = new String[] 
			{ "Path", "Type", "Date updated"};
	
	// two directory lines
	static Object[][] tableDat;

	/**
	 * constructor - reads lines from locs file
	 */
	extAbstrTab() {
		Object[] tempArray2 = readLocationsInFile();
		Object[][] tempArray = new Object[tempArray2.length][3];
		int nth = 0;
		while (nth < tempArray2.length) {
			tempArray[nth][0] = tempArray2[nth++];
		}
		tableDat = tempArray;
	}

	/**
	 * constructor main used method - reading from file
	 * @return
	 */
	static Object[] readLocationsInFile() {
		File dirDataFile = new File("g:\\locs.dat");
		FileReader fR;
		try {
			fR = new FileReader(dirDataFile);

		} catch (FileNotFoundException e) {
			JOptionPane jOp = new JOptionPane(new String("File not found"));
			jOp.setVisible(true);
			System.out.println("File not found");
			e.printStackTrace();
			return null;
		}
		BufferedReader bR = new BufferedReader(fR);
		Object[] locsStrArr = bR.lines().toArray();
		try {
			bR.close();
		} catch (IOException e) {
			System.out.println("file could not be closed");
			e.printStackTrace();
		}
		return locsStrArr;
	}
	
	// ovveridings and implemntations
	
	@Override
	public int getRowCount() {
		return tableDat.length;
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0: case 1:
			return tableDat[rowIndex][columnIndex];
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		if (columnIndex < 2)
			return true;
		else
			return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		tableDat[rowIndex][columnIndex] = (String) aValue;
	}

	@Override
	public String getColumnName(int column) {
		return cols[column];
	}

	// end ovveridings and implemntations
}

}
