package core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class RespModel implements Mp3IdentListListener,
	ILocsSetsListener{
	
	private static ArrayList<Mp3Ident> fileList;
	static JList<?> jl1 = new JList<String>();;
	static JList<?> jl2 = new JList<String>();;
	static DefaultListModelMpsAb<?> dlmMpsAb;
	static DefaultListModelMpsAb<?> dlmStr;
	
	static enum viewType {
		art, dirs
	}
	static viewType vType = viewType.art;
	static RespModel model;
	
	abstract void changeModel() ;
	
	public static RespModel getRmodel() {
		return model;
	}
	
	ArrayList<Mp3Ident> getFileList() throws NullPointerException{
		if (fileList == null) {
			throw new NullPointerException("File list empty");
		}
		return fileList;
	}
	
	static void setFileList(ArrayList<Mp3Ident> inp) {
		fileList = inp;
	}
	
	RespModel() {
	}
	
	public JList getJl1() {
		return jl1;
	}
	
	JList getJl2() {
		return jl2;
	}
	
	static void createFirst(){
		model = new respModelArt();
		Mp3Ident.arts.registerListener(model);
		locsSets.getAllSets().registerListener(model);
	}
	
	void first() {
		jl1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
	abstract void scnd();
	
	}

class respModelArt extends RespModel {

	respModelArt() {
		dlmMpsAb = new DefaultListModelMpsAb<String>();
		DefaultListModelMpsAb<String> methVar = (DefaultListModelMpsAb<String>) dlmMpsAb;
		//jl1 = new JList<String>();
		//jl2 = new JList<String>();
		JList<String> jlRef1 = (JList<String>)jl1;
		jlRef1.setModel(methVar);
		Set<String> toAdd = Mp3Ident.arts.keySet();
		methVar.addAll(toAdd);
		scnd();
	}
	
	@Override
	void scnd() {
		first();
		// selecting listening
		if (jl1.getListSelectionListeners().length != 0)
			for (int runner = 0; runner < jl1.getListSelectionListeners().length; ++runner)
				jl1.removeListSelectionListener(jl1.getListSelectionListeners()[runner]);
		if (jl2.getListSelectionListeners().length != 0)
			for (int runner = 0; runner < jl2.getListSelectionListeners().length; ++runner)
				jl2.removeListSelectionListener(jl2.getListSelectionListeners()[runner]);
		jl1.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting() == false) {
					dlmStr = new DefaultListModelMpsAb<String>();
					DefaultListModelMpsAb<String> methDlmStr = (DefaultListModelMpsAb<String>)dlmStr;
					JList<String> methJl2 = (JList<String>) jl2;
					ArrayList<Mp3Ident> toUse = new ArrayList<>();
					// dlmStr.clear();

					JList<String> list = (JList<String>) e.getSource();

					List<String> listSelected;
					try {
						listSelected = (List<String>) list.getSelectedValuesList();
					} catch (ClassCastException cce) {
						return;
					}

					// selected values iterator
					Iterator<String> itListSelected = listSelected.iterator();
					// String lisItVal = lisIt.next();
					String valItSel;

					// all values iterator
					Iterator<String> albNames;
					Iterator<Mp3Ident> itKeysInArtAlbMap;
					// String val = ksI.next();

					while (itListSelected.hasNext()) {
						valItSel = itListSelected.next();
						TreeMap<String, ArrayList<Mp3Ident>> tryStr = Mp3Ident.arts.get(valItSel);
						
						//add albums to be viewed in JL2
						Set<String> newStrArr = tryStr.keySet();
						methDlmStr.addAll(newStrArr);
						
						//add song Files to list to being copied / moved
						
						albNames = tryStr.keySet().iterator();
						while (albNames.hasNext()) {
							
							itKeysInArtAlbMap = tryStr.get(albNames.next()).iterator();
							while (itKeysInArtAlbMap.hasNext())
								toUse.add(itKeysInArtAlbMap.next());
						}
						
					}
					methJl2.setModel(methDlmStr);
					setFileList(toUse);
					
				}
			}
		
		});
		
		jl2.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				// Strings selected in JLists
				List<String> nStrArrArt = RespModel.getRmodel().getJl1().getSelectedValuesList();
				List<String> nStrArr = RespModel.getRmodel().getJl2().getSelectedValuesList();
				Iterator<String> selectedAlbumIterator = nStrArr.iterator();
				// end Strings selected

				// all mp3's to copy
				ArrayList<Mp3Ident> toUse = new ArrayList<>();

				while (selectedAlbumIterator.hasNext()) {
					String albumVe = selectedAlbumIterator.next();

					Iterator<String> selectedArtistIterator = nStrArrArt.iterator();
					while (selectedArtistIterator.hasNext()) {
						String artistVe = selectedArtistIterator.next();
						TreeMap<String, ArrayList<Mp3Ident>> mapAlbumToSongList = Mp3Ident.arts.get(artistVe);
						if (mapAlbumToSongList.containsKey(albumVe)) {
							toUse.addAll(mapAlbumToSongList.get(albumVe));
							
							/**for (mp3Ident mp3ID : mapAlbumToSongList.get(albumVe)) {

								try {
									Files.copy(Paths.get(mp3ID.fileM.getAbsolutePath()),
											Paths.get(
													startCl.targetDir.getAbsolutePath() 
													+ "\\" + mp3ID.fileM.getName()),
											StandardCopyOption.REPLACE_EXISTING);
					 			} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}**/
							}
						}
					}
				setFileList(toUse);
				}
			
		});
	}
	
	@Override
	void changeModel() {
		respModelDir newrm = new respModelDir();
		model = newrm;
	}

	@Override
	public void mp3IdentListChanged() {
		// TODO Auto-generated method stub
		Set<String> toAdd = Mp3Ident.arts.keySet();
		DefaultListModelMpsAb<String> methVar = (DefaultListModelMpsAb<String>) dlmMpsAb;
		methVar.removeAllElements();
		methVar.addAll(toAdd);
	}

	@Override
	public void locsSetsChanged(Object key) {
		// TODO Auto-generated method stub
		respModelDir newrm = new respModelDir();
		model = newrm;
	}
}

class respModelDir extends RespModel {	
	
	respModelDir() {
		
		dlmMpsAb = new DefaultListModelMpsAb<MyPathString>();
		DefaultListModelMpsAb<MyPathString> myMod = (DefaultListModelMpsAb<MyPathString>)dlmMpsAb;
		JList<MyPathString> myList = (JList<MyPathString>)jl1;
		myList.setModel(myMod);
		myMod.addAll(locsSets.getLocsList());

		scnd();
		
	}
	
	@Override
	void scnd() {
		first();
		JList<MyPathString> jl = (JList<MyPathString>) jl1;
		// selecting listening
		if (jl1.getListSelectionListeners().length != 0)
			for (int runner = 0; runner < jl1.getListSelectionListeners().length; ++runner)
				jl1.removeListSelectionListener(jl1.getListSelectionListeners()[runner]);
		if (jl2.getListSelectionListeners().length != 0)
			for (int runner = 0; runner < jl2.getListSelectionListeners().length; ++runner)
				jl2.removeListSelectionListener(jl2.getListSelectionListeners()[runner]);
		jl1.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				e.getValueIsAdjusting();
				if (e.getValueIsAdjusting() == false) {
					DefaultListModelMpsAb<String> jld = new DefaultListModelMpsAb<String>();
					JList<String> jlStr = (JList<String>)jl2;
					
					JList<?> list;
					list = (JList<?>) e.getSource();

					// all selected myPathStrings
					ArrayList<MyPathString> listSelected;
					try {
						listSelected = (ArrayList<MyPathString>) list.getSelectedValuesList();
					} catch (ClassCastException cce) {
						return;
					}

					// iterator over selected myPathStrings
					Iterator<MyPathString> itListSelected = listSelected.iterator();
					MyPathString valItListSelected;

					// all values of myPathString
					Set<MyPathString> allMyPathStrings = new TreeSet<>();
					allMyPathStrings.addAll(locsSets.getLocsList());

					// iterator over all myPathStrings
					Iterator<MyPathString> valAllMyPathStrings = allMyPathStrings.iterator();
					
					// ArrayList to gather selection
					ArrayList<String> sortedList = new ArrayList<>();
					{
						DefaultListModel<String> methVar = (DefaultListModel<String>) dlmStr;
					while (itListSelected.hasNext()) {
						valItListSelected = itListSelected.next();
						locsSets tryStr = locsSets.getSetUnit(valItListSelected.getPath());
						sortedList.addAll(tryStr.getHMapAlbums().keySet());
					}
					sortedList.sort(new Comparator<String>() {

						@Override
						public int compare(String o1, String o2) {
							// TODO Auto-generated method stub
							return o1.compareToIgnoreCase(o2);

						}
					});
					
					// add the collection of arbum-artist data to list model
					jld.addAll(sortedList);
					
					// set this list model as jl2 model
					dlmStr = jld;
					ListModel lm = (ListModel)dlmStr;
					jlStr.setModel(lm);
					}
				}
			}
		});
		jl2.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList<?> JListSel = (JList<?>)e.getSource();
				ArrayList<Mp3Ident> selectedMp3 = new ArrayList<>();
				if (e.getValueIsAdjusting() == false) {
					List<MyPathString> list1selected = (List<MyPathString>)jl1.getSelectedValuesList();
					Iterator<MyPathString> l1It = list1selected.iterator();
					List<String> listSelected = (List<String>)JListSel.getSelectedValuesList();
					Iterator<String> l2It = listSelected.iterator();
					
					while (l2It.hasNext()) {
						String it2String = l2It.next();
						while (l1It.hasNext()) {
							MyPathString it1String = l1It.next();
							if (locsSets.getSetUnit(it1String.getPath()).getHMapAlbums().get(it2String) != null)
							selectedMp3.addAll(locsSets.getSetUnit(it1String.getPath()).getHMapAlbums().get(it2String));
						}
					}

				}
				setFileList(selectedMp3);
				
			}
		});
	}

	@Override
	void changeModel() {
		
		respModelArt newrm = new respModelArt();
		model = newrm;
		
	}

	@Override
	public void mp3IdentListChanged() {
		// TODO Auto-generated method stub
		DefaultListModelMpsAb<MyPathString> myMod = (DefaultListModelMpsAb<MyPathString>)dlmMpsAb;
		myMod.removeAllElements();
		myMod.addAll(locsSets.getLocsList());
	}

	@Override
	public void locsSetsChanged(Object key) {
		// TODO Auto-generated method stub
		respModelArt newrm = new respModelArt();
		model = newrm;
	}
}