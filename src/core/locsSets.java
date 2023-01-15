package core;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JOptionPane;

import arch.ArchiveData;
import arch.TransferedObject;
import comparers.IEqualityExtenderFactory;
import comparers.DifferenceOperator1;
import comparers.DifferenceOperatorBaseFactory;
import comparers.EqualityExtenderAbs;
import comparers.EqualityExtenderBaseFactory;
import comparers.EqualityMethExtender2;
import comparers.IDifferenceOperator;
import comparers.IDifferenceOperatorFactory;
import comparers.DifferenceOperatorFactory;
import entry.AppContext;
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
			target.setData(data);
		}
		
		/**
		 * constructor for ArchiveData
		 */
		public locsSets () {
			setUnit = new ArrayList<>() {
				@Override
				public String toString() {
					// TODO Auto-generated method stub
					return super.toString() + "\n";
				}
			};
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
		static locsSets getSetUnit(String keyS) throws NullPointerException{
				
				MyPathString str = new MyPathString(keyS);
				Iterator<MyPathString> overKeys = allSets.keySet().iterator();
				while (overKeys.hasNext()) {
				MyPathString runner = overKeys.next();
				if (str.getPath().startsWith(runner.getPath() + "\\") || 
						str.getPath().equals(runner.getPath()) )
					return allSets.get(runner);
				}
				throw new NullPointerException(
					"setUnit not found in locsSets, for a given key: " + keyS);
		}
		
		static void checkIfExistingAndCreate(String line) {
			
			try {locsSets ls = getSetUnit(line);
			} catch (NullPointerException e) {
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
		
		public ArrayList<Mp3Ident> compareTo(locsSets inp) {
			
			IDifferenceOperator operator;
			ArrayList<EqualityExtenderAbs> extArr =  new ArrayList<>();

			int sizeBefore = this.getSongs().size();
			int sizeAfter;
			int sizeDeleted;
			

			for (Mp3Ident ident: this.getSongs()) {
				try {
					EqualityExtenderBaseFactory factory = AppContext.getContext().getBean("comparerFactory", EqualityExtenderBaseFactory.class);
					IEqualityExtenderFactory cf = factory.getFactory();
					EqualityExtenderAbs ext = cf.createExtender(ident.getFileM());
					
					extArr.add(ext);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			

			extArr.retainAll(inp.getSongs());
			//System.out.println(extArr2);
			/*DifferenceOperatorBaseFactory factory2 = AppContext.getContext()
					.getBean(DifferenceOperatorBaseFactory.class);
			IDifferenceOperatorFactory dOF = factory2.getFactory();
			IDifferenceOperator dO = dOF.creat eOperator(this.getSongs());*/
			DifferenceOperatorFactory factory2 = AppContext.getContext()
					.getBean(DifferenceOperatorFactory.class);
			IDifferenceOperator dO = factory2.getOperator();
			dO.setSmthToOperate(extArr);
			try {dO.operateOnDifference();
			} catch (Exception e) {
				System.out.println("canceling delete");
			}
			
			
			/*
								ArrayList<EqualityExtenderAbs> extArr2 =  new ArrayList<>();
								for (Mp3Ident ident: this.getSongs()) {
									try {
										EqualityExtenderBaseFactory factory = AppContext.getContext().getBean("compFactory2", EqualityExtenderBaseFactory.class);
										IEqualityExtenderFactory cf = factory.getFactory();
										EqualityExtenderAbs ext = cf.createExtender(ident.getFileM());
										
										extArr2.add(ext);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								extArr2.retainAll(inp.getSongs());
			*/
			
			
			
			
			
			sizeAfter =  extArr.size();
			sizeDeleted = sizeBefore - sizeAfter;
			System.out.println(sizeDeleted + " out of " + sizeBefore + " songs have been deleted");
			
			return inp.getSongs();
		}
}
