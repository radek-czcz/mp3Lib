package core;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import arch.ArchiveData;
import textio.TextIO;

public class locsSets implements Runnable, LocsSetsTransferer {
		
		// static vars.
		private static LocsSetsHashMap<myPathString, locsSets> allSets = new LocsSetsHashMap<>();
		
		public static LocsSetsHashMap<myPathString, locsSets> getAllSets() {
			return allSets;
		}

		// instance vars.
		private myPathString locPath;
		private HashMap<String, HashSet<mp3Ident>> hMapAlbums = new HashMap<>();
		private ArrayList<mp3Ident> setUnit;
	
		/**
		 * constructor
		 * @param path
		 */
		private locsSets(String path) {
			locPath = new myPathString(path);
			setUnit = new ArrayList<>();
			allSets.put(locPath, this);
		}
		
		/**
		 * constructor for ArchiveData
		 * @param name - Name of the archive
		 * @param arch - ArchiveData, to be added to
		 */
		public locsSets (ArchiveData arch) {
		
		}
		
		/**
		 * should check the existing file, if are deleted / still avalible
		 */
		@Override
		public void run() {
			for (myPathString nth: allSets.keySet()) {
					ArrayList<mp3Ident> innerMp3Identlist = allSets.get(nth).setUnit;
					for (mp3Ident nth2: innerMp3Identlist) {
						if ( ! nth2.fileM.exists());
						innerMp3Identlist.remove(nth2);
				}
			}
		}
		
		// static
		/**
		 *reads all locations from file to map. 
		 */
		public static void setLocsSets() {
			synchronized (allSets) {
				TextIO.readFile(startCl.fPaths.getPathDriveLetter() + ":\\locs.dat");
				String line;
		
				while ( ! TextIO.eof() ) {
					line = TextIO.getlnString();
					locsSets.checkIfExistingAndCreate(line);
				}
			}
		}
		
		/**
		 * returns collection of mp3Ident
		 * @param keyS
		 * @return
		 */
		static locsSets getSetUnit(String keyS){
				
				myPathString str = new myPathString(keyS);
				Iterator<myPathString> overKeys = allSets.keySet().iterator();
				while (overKeys.hasNext()) {
				myPathString runner = overKeys.next();
				if (str.getPath().startsWith(runner.getPath() + "\\") || 
						str.getPath().equals(runner.getPath()) )
					return allSets.get(runner);
				}
				
				System.out.println("folder " + keyS + " does not exist in locsSets");
				return null;
				
		}
		
		static void checkIfExistingAndCreate(String line) {
			if (getSetUnit(line) == null) {
				new locsSets(line);
				System.out.println("/" + line + "/" + " to locsSets added");
			}
		}
		
		/**
		 * returns list to view in column viewer
		 * @return
		 */
		static ArrayList<myPathString> getLocsList() {
			ArrayList<myPathString> ret = new ArrayList<>();
			ret.addAll(allSets.keySet());
			return ret;
		}
		
		// instance methods
	
		/** 
		 * returns path only
		 * @return
		 */
		myPathString getLocPath(){
			return locPath;
		}
		
		/**
		 * adds mp3Ident to his location collection
		 * @param toAdd
		 */
		void addMp3(mp3Ident toAdd) {
			this.setUnit.add(toAdd);
			
			// artist-album map creation
				
				String keyS = toAdd.tagArt + " - " + toAdd.tagAlb;
				if (hMapAlbums.containsKey(keyS)) {
					HashSet<mp3Ident> listGet;
					listGet = hMapAlbums.get(keyS);
					listGet.add(toAdd);
				} else {
					HashSet<mp3Ident> newAl = new HashSet<>();
					hMapAlbums.put(keyS, newAl);
					newAl.add(toAdd);
				}
		}
		

		ArrayList<mp3Ident> getSongs(){
			return setUnit;
		}

		// instance methods
		
		HashMap<String, HashSet<mp3Ident>> getHMapAlbums(){
			return hMapAlbums;
		}


		/**
		 * moves locsSets ArrayList of mp3Ident to archive file
		 */
		@Override
		public void transferToArchive() {
			String checkedPath = startCl.fPaths.getPath();
			if (checkedPath == null) {
				checkedPath = "archive.dat";
			} else {
				checkedPath = startCl.fPaths.getPath() + "archive.dat";
			}
			try (FileOutputStream fos = new FileOutputStream(checkedPath, true);
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				while (!getSongs().isEmpty()) {
					mp3Ident thisMp3 = getSongs().get(0);
					//System.out.println(getSongs().size());
					//ystem.out.println(thisMp3.fileM.getPath());
					oos.writeObject(thisMp3);
					getSongs().remove(thisMp3);
					//System.out.println(getSongs().size());
					/*if (getSongs().size() == 1) {
						System.out.println("loop ended");
					}*/
					//System.out.println(getSongs().size());
				}  
				//System.out.println("------empty");
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void transferToCurrent() {
			// TODO Auto-generated method stub
			
		}
}