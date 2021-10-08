package core;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import arch.ArchiveData;
import arch.TransferedObject;
import textio.TextIO;

public class locsSets implements Runnable {
		
		// static vars.
		private static LocsSetsHashMap<MyPathString, locsSets> allSets = new LocsSetsHashMap<>();
		
		public static LocsSetsHashMap<MyPathString, locsSets> getAllSets() {
			return allSets;
		}

		// instance vars.
		private MyPathString locPath;
		private HashMap<String, HashSet<Mp3Ident>> hMapAlbums = new HashMap<>();
		private ArrayList<Mp3Ident> setUnit;
	
		/**
		 * constructor
		 * @param path
		 */
		private locsSets(String path) {
			locPath = new MyPathString(path);
			setUnit = new ArrayList<>();
			allSets.put(locPath, this);
		}
		
		public locsSets(ArchiveData target) {
			locsSets data = new locsSets();
		}
		
		/**
		 * constructor for ArchiveData
		 */
		public locsSets () {
			setUnit = new ArrayList<>();
		}
		
		/**
		 * should check the existing file, if are deleted / still avalible
		 */
		@Override
		public void run() {
			for (MyPathString nth: allSets.keySet()) {
					ArrayList<Mp3Ident> innerMp3Identlist = allSets.get(nth).setUnit;
					for (Mp3Ident nth2: innerMp3Identlist) {
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
				
				MyPathString str = new MyPathString(keyS);
				Iterator<MyPathString> overKeys = allSets.keySet().iterator();
				while (overKeys.hasNext()) {
				MyPathString runner = overKeys.next();
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
		static ArrayList<MyPathString> getLocsList() {
			ArrayList<MyPathString> ret = new ArrayList<>();
			ret.addAll(allSets.keySet());
			return ret;
		}
		
		// instance methods
	
		/** 
		 * returns path only
		 * @return
		 */
		public MyPathString getLocPath(){
			return locPath;
		}
		
		/**
		 * adds mp3Ident to his location collection
		 * @param toAdd
		 */
		void addMp3(Mp3Ident toAdd) {
			this.setUnit.add(toAdd);
			
			// artist-album map creation
				
				String keyS = toAdd.tagArt + " - " + toAdd.tagAlb;
				if (hMapAlbums.containsKey(keyS)) {
					HashSet<Mp3Ident> listGet;
					listGet = hMapAlbums.get(keyS);
					listGet.add(toAdd);
				} else {
					HashSet<Mp3Ident> newAl = new HashSet<>();
					hMapAlbums.put(keyS, newAl);
					newAl.add(toAdd);
				}
		}
		

		public ArrayList<Mp3Ident> getSongs(){
			return setUnit;
		}

		// instance methods
		
		HashMap<String, HashSet<Mp3Ident>> getHMapAlbums(){
			return hMapAlbums;
		}

		public void transferToArchive() {
			String checkedPath = startCl.fPaths.getPath();
			if (checkedPath == null) {
				checkedPath = "archive.dat";
			} else {
				checkedPath = startCl.fPaths.getPath() + "archive.dat";
			}
			try (
					FileOutputStream fos = new FileOutputStream(checkedPath, true);
					ObjectOutputStream oos = new ObjectOutputStream(fos)
				) 
			{
				while (!getSongs().isEmpty()) {
					Mp3Ident thisMp3 = getSongs().get(0);
					oos.writeObject(thisMp3);
					getSongs().remove(thisMp3);
				}
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
