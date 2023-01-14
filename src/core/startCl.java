package core;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JViewport;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.source.tree.TypeCastTree;

import arch.CompareToArchiveWindow;
import arch.LocAndNameWindow;
import arch.LocAndNameWindowRefactored;
import entry.AppContext;
import entry.FilesPaths;
import entry.IButtonNameProvider;
import entry.PathProvider;

public class startCl {
	
	// class variables
	// directory to copy/move
	static File targetDir;
	static ArrayList<Mp3Ident> toDeleteFromDataFile;
	
	static PathProvider fPaths;
	static IButtonNameProvider buttonsNames; 
	// end class variables
	
	// released
	public static void main(String args[]) {
		
		//ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config2.xml");
		//fPaths = (PathProvider)applicationContext.getBean("paths");
		fPaths = (PathProvider)AppContext.getContext().getBean("paths");
		buttonsNames = (IButtonNameProvider)AppContext.getContext().getBean("buttonsBean");
		
		
		System.out.println("dek".toLowerCase().matches("(.*)ek(.*)"));
		Thread winSetup = new Thread(new thrRunner1() {      // starter()
			@Override
			public void run() {
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				starter();
				
			}
		});
		Thread thr4 = new Thread(new thrRunner1() {          // respModel
			@Override
			public void run() {
				
				// name, description
				Thread.currentThread().setName("respModel");
				String cur = "Thread " + Thread.currentThread().getName();
				System.out.println("Executing "+cur);
				// name, description END
				
				try {
					System.out.println(cur + " falling asleep");
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(cur + " wakes");
				RespModel.createFirst();
				System.out.println(cur + " finished");
				winSetup.interrupt();
				
			}
		});
		Thread thr2 = new Thread(new thrRunner2() {          // list artist in view
			public void run() {
				Thread.currentThread().setName("Artists Listing");
				String cur = "Thread " + Thread.currentThread().getName();
				System.out.println("Executing "+cur);
				try {
					System.out.println(cur + " falling asleep");
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//while (!Thread.interrupted()) {};
				System.out.println(cur + " wakes");
				startCl.fillArt();
				System.out.println(cur + " finished");
				thr4.interrupt();
			}});
		Thread thr1 = new Thread(new thrRunner3() {          // read data file
			public void run() {
				Thread.currentThread().setName("File Reader");
				String cur = "Thread "+Thread.currentThread().getName();
				System.out.println("Executing "+cur);
				try {
					System.out.println(cur + " falling asleep");
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(cur + " wakes");
				startCl.readFile();
				System.out.println(cur + " finished");
				thr2.interrupt();
			}});
		Thread thr_Sets = new Thread(new thrRunner1() {      // locsSets set.
			@Override
			public void run() {
				Thread.currentThread().setName("setting locsSets");
				String cur = "Thread "+Thread.currentThread().getName();
				System.out.println("Executing "+cur);
				locsSets.setLocsSets();
				System.out.println(cur + " finished");
				thr1.interrupt();
			}
		});
		Thread isFileStillExisting = new Thread(new thrRunner1() {		//checks if file from DB still exists, to delete it frther from DB
			@Override
			public void run() {
				// TODO Auto-generated method stub
				toDeleteFromDataFile = new ArrayList<Mp3Ident>();
				File songFile;
				
				Thread.currentThread().setName("checking files for existence");
				String cur = "Thread "+Thread.currentThread().getName();
				System.out.println("Executing "+cur);
				thr1.interrupt();
				
				ArrayList<MyPathString> listToCheck = locsSets.getLocsList();
				for (MyPathString runner : listToCheck) {
					locsSets sets = locsSets.getSetUnit(runner.getPath());
					ArrayList<Mp3Ident> songs = sets.getSongs();
					for (Mp3Ident runnerSong : songs) {
						songFile = runnerSong.fileM;
						if (!songFile.exists()) {
							toDeleteFromDataFile.add(runnerSong);
						}
					}
				}
				// TODO how to del non existing mp3Ident from data file
				System.out.println(cur + " finished");
			}
		});
		
		
		thr_Sets.start();
		thr1.start();
		thr2.start();
		thr4.start();
		winSetup.start();
		isFileStillExisting.start();
		
		// window setup
		
	}
	
	synchronized static void readFile() {
		Scanning.readDataFile();
	}
	
	static void moveOrCopyFiles(){
		

		try {
			for (Mp3Ident mp3ID : RespModel.model.getFileList()) {
			Files.copy(Paths.get(mp3ID.fileM.getAbsolutePath()),
					Paths.get(
							startCl.targetDir.getAbsolutePath() 
							+ "\\" + mp3ID.fileM.getName()),
					StandardCopyOption.REPLACE_EXISTING);
			}
			} catch (IOException | NullPointerException e1) {
			
			//e1.printStackTrace();
			if (e1.getClass() == NullPointerException.class) {
				if (e1.getMessage() == "File list empty") {
					System.out.println(e1.getMessage());
				}
				else {
					System.out.println("Path not provided");
				};
				
			}
			}
		
		
	}
	
	// puts artists to dlm list
	synchronized static void fillArt() {
		// TODO - bring the alphabetic sorting to order.
		
		//dlm.addAll(mp3Ident.arts.keySet());
		//respModel.dlmMpsAb = new DefaultListModelMpsAb<String>();
		//DefaultListModelMpsAb<String> myVar = (DefaultListModelMpsAb<String>)respModel.dlmMpsAb;
		//myVar = new DefaultListModelMpsAb<String>();
		//myVar.addAll(mp3Ident.arts.keySet());
	}
	
	// directory choose dialog window
	static void directoryChange() {
		// TODO - example for dialog window for file chooser
		JFileChooser fileChooseWindow = new JFileChooser();
		fileChooseWindow.setDialogTitle("choose target directory");
		fileChooseWindow.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooseWindow.getSelectedFile();
		fileChooseWindow.setVisible(true);
		int returnVal = fileChooseWindow.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			targetDir = new File(System.getProperty("user.dir"));
		targetDir = fileChooseWindow.getSelectedFile();
	}


	// class ActionListener override - performs button actions, except button "move 4". -> Implements event.ActionListener.actionPerformed
	private static class butListener implements ActionListener {
		// ActionListener.ActionPerformed override.
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton op = (JButton) e.getSource();

			loop: // for (String str : startCl.menuM)
			switch (op.getName()) {
			case "0":
				Scanning.scanFoldersToDataFile();
				break loop;
			case "1":
				Scanning.readDataFile();
				fillArt();
				break loop;
			case "2":
				System.exit(0);
				break loop;
			case "3":
				directoryChange();
				System.out.println("directory changed");
				break loop;
			case "4":
				File toDel = new File(fPaths.getPathDriveLetter() + ":\\arch31_g.dat");
				toDel.delete();
				break loop;
			case "6":
				moveOrCopyFiles();
				break loop;
			case "7": // "locs" button
				System.out.println("abc");
				new TabModCust();
				break loop;
			case "8":
				RespModel.getRmodel().changeModel();
				break loop;
			case "10":
				//locsSets.getAllSets().remove(locsSets.getLocsList().get(3));
				locsSets.getSetUnit(RespModel.jl1
						.getSelectedValuesList().get(0).toString()).transferToArchive();
				break loop;
			case "11":
				AppContext.getContext().getBean(LocAndNameWindowRefactored.class).showWindow();
				//LocAndNameWindow.showWindow();
				break loop;
			case "12":
				if (RespModel.jl1.getSelectedValuesList().size() != 0)
				new CompareToArchiveWindow(
						locsSets.getSetUnit(RespModel.jl1.getSelectedValuesList().get(0).toString()));
				else System.out.println("no selection made");
			}
		}
	}

	// end released

	// working by
	
	/**
	 * creates main app window.
	 * does scroll listening. -> Implements AdjustmentListener.adjustmentValueChanged interface.
	 * does artist / album selecting listening. -> Implements ListSelectionListener.valueChanged interface.
	 * does actions for button "move 4". -> Implements ActionListener.actionPerformed interface.
	 */
	static void starter() {
		
		// init
		MainFrameCustomized mainFrame = new MainFrameCustomized(); // MAIN WINDOW
		contJ2 contentArtist = new contJ2();
		contentArtist.setName("contentArtist"); // list
		contJ2 contentAlbum = new contJ2();
		contentAlbum.setName("contentAlbum"); // list
		contJ contentButtons = new contJ();
		contentButtons.setName("contentButtons"); // buttons outer container
		contJ contentButtonsInner1 = new contJ();
		contentButtonsInner1.setName("contentButtonsInner1"); // buttons inner container
		contJ contentScroll = new contJ();
		contentScroll.setName("contentScroll"); // scroll buttons container

		JScrollBarExt jsb = new JScrollBarExt(JScrollBar.VERTICAL, 0, 1, 0, 500);
		BoxLayout forBut = new BoxLayout(contentButtonsInner1, BoxLayout.Y_AXIS);

		Dimension sizeBut = new Dimension(30, 30); // min. Button size
		Dimension sizeBut2 = new Dimension(150, 34); // max. Button size
		// DefaultListModel<String> dlm2= new DefaultListModel<String>();
		Dimension dims;
		Dimension dimsForBut;
		dims = new Dimension(200, 300);
		dimsForBut = new Dimension(150, 100);
		JViewport jV = new JViewport();
		JViewport jV2 = new JViewport();
		// end
		
		jV.setView(RespModel.getRmodel().getJl1());
		jV2.setView(RespModel.getRmodel().getJl2());
		//respModel.model = new respModelArt();
		
		//jl1 = new JList<String>();
		//jl2 = new JList<String>();
		//jV.setView(jL);
		//jV2.setView(jL2);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// set propert.
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		contentArtist.setLayout(new BorderLayout());
		contentAlbum.setLayout(new BorderLayout());
		contentScroll.setLayout(new BorderLayout());
		contentButtons.setLayout(new BorderLayout());
		contentButtonsInner1.setLayout(forBut);

		// contentButtonsInner1.setBorder(new LineBorder(Color.black, 1));
		contentButtons.setBorder(new LineBorder(Color.black, 1));
		contentScroll.setBorder(new LineBorder(Color.GREEN, 1));

		/*
		 * contentScroll.setBorder(new LineBorder(Color.black, 1));
		 * contentArtist.setBorder(new LineBorder(Color.black, 1));
		 * contentAlbum.setBorder(new LineBorder(Color.black, 1));
		 * contentButtons.setBorder(new LineBorder(Color.black, 1));
		 */
		// end propert.

		// add menu buttons
		for (int nth = 0; nth < buttonsNames.provideButtons().length; nth++) {
			JButton jB = new JButton(buttonsNames.provideButtons()[nth]);
			String name = String.valueOf(nth);
			jB.setName(name);
			contentButtonsInner1.add(jB);
			if (buttonsNames.provideButtons()[nth].matches("hidden.*"))
				jB.setVisible(false);
		}
		// end

		// set button properties
		for (int nth = 0; nth < contentButtonsInner1.getComponentCount(); nth++) {
			JButton but = (JButton) contentButtonsInner1.getComponent(nth);
			but.setMinimumSize(sizeBut);
			but.setMaximumSize(sizeBut2);
			Border border = but.getBorder();
			// Border margin = new EmptyBorder(5,2,5,2);
			LineBorder lB = new LineBorder(contentButtons.getBackground(), 7);
			CompoundBorder cB = new CompoundBorder(lB, border);
			but.setBorder(cB);
			butListener bL = new butListener();
			but.addActionListener(bL);
		}
		// end button properties

		// size set
		contentArtist.setSize(dims);
		contentButtons.setSize(dimsForBut);
		contentAlbum.setSize(dims);
		contentScroll.setSize(15, 30);

		contentArtist.setPreferredSize(dims);
		contentAlbum.setPreferredSize(dims);
		contentScroll.setPreferredSize(new Dimension(15, dims.height));
		jsb.setPreferredSize(new Dimension(15, dims.height));

		contentArtist.setMaximumSize(dims);
		contentAlbum.setMaximumSize(dims);
		contentButtonsInner1.setMaximumSize(dimsForBut);
		// end

		// add container content
		contentScroll.add(jsb, BorderLayout.NORTH);
		mainFrame.add(contentScroll, FlowLayout.LEFT);
		mainFrame.add(contentAlbum, FlowLayout.LEFT);
		mainFrame.add(contentArtist, FlowLayout.LEFT);
		mainFrame.add(contentButtons, FlowLayout.LEFT);
		contentButtons.add(contentButtonsInner1, BorderLayout.NORTH);
		contentAlbum.add(jV2);
		contentArtist.add(jV);
		// end container content adding

		// scrolling listening
		jsb.addAdjustmentListener(new AdjustmentListener() {

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int difference = jV.getView().getHeight() - jV.getParent().getHeight();
				double division = ((double) difference / (double) 499) * e.getValue();
				int result = (int) division;
				System.out.println(e.getValue());
				jV.setViewPosition(new Point(0, result));
			}
		});

		// selecting listening
		/**jL.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting() == false) {
					dlm2.clear();
					JList<String> list = new JList<>();
					list = (JList<String>) e.getSource();
					int cou = list.getSelectedIndices().length;
					ArrayList<String> listSelected;
					try {
					listSelected = (ArrayList<String>) list.getSelectedValuesList();
					} catch (ClassCastException cce) {
						return;
					}

					// selected values
					Iterator<String> itListSelected = listSelected.iterator();
					// String lisItVal = lisIt.next();
					String valItSel;

					// select

					// all values
					Set<String> keysInArtAlbMap = mp3Ident.arts.keySet();
					Iterator<String> itKeysInArtAlbMap = keysInArtAlbMap.iterator();
					// String val = ksI.next();
					String valItKeysInArtAlbMap;

					while (itListSelected.hasNext()) {
						valItSel = itListSelected.next();
						TreeMap<String, ArrayList<mp3Ident>> tryStr = mp3Ident.arts.get(valItSel);
						Set<String> newStrArr = tryStr.keySet();
						dlm2.addAll(newStrArr);
					}
				}
			}
		}
	);**/

		// menu buttons listening for button "move"
		JButton but22 = (JButton) contentButtonsInner1.getComponent(6);
		/**but22.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				// Strings selected in JLists
				List<String> nStrArrArt = respModel.getRmodel().getJl1().getSelectedValuesList();
				List<String> nStrArr = respModel.getRmodel().getJl2().getSelectedValuesList();
				// Iterator<String> selectedArtistIterator = nStrArrArt.iterator();
				Iterator<String> selectedAlbumIterator = nStrArr.iterator();
				// end Strings selected

				// all mp3's to copy
				ArrayList<mp3Ident> toUse = new ArrayList<>();

				while (selectedAlbumIterator.hasNext()) {
					String albumVe = selectedAlbumIterator.next();

					Iterator<String> selectedArtistIterator = nStrArrArt.iterator();
					while (selectedArtistIterator.hasNext()) {
						String artistVe = selectedArtistIterator.next();
						TreeMap<String, ArrayList<mp3Ident>> mapAlbumToSongList = mp3Ident.arts.get(artistVe);
						if (mapAlbumToSongList.containsKey(albumVe)) {
							toUse.addAll(mapAlbumToSongList.get(albumVe));
							for (mp3Ident mp3ID : mapAlbumToSongList.get(albumVe)) {

								try {
									Files.copy(Paths.get(mp3ID.fileM.getAbsolutePath()),
											Paths.get(
													startCl.targetDir.getAbsolutePath() 
													+ "\\" + mp3ID.fileM.getName()),
											StandardCopyOption.REPLACE_EXISTING);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
				}
			}
		});**/

		mainFrame.setVisible(true);
	}

}

class DefaultListModelMpsAb<E> extends DefaultListModel<E>{
	
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}

abstract class thrRunner1 implements Runnable {
}

abstract class thrRunner2 implements Runnable {
}

abstract class thrRunner3 implements Runnable {
}
