import java.io.BufferedReader;

import java.io.EOFException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.ListModel;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.ID3Tags;
import org.jaudiotagger.tag.id3.ID3v1FieldKey;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v23Frames;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.mp4.Mp4FieldKey;
import org.jaudiotagger.tag.reference.GenreTypes;
import org.jaudiotagger.tag.reference.ID3V2Version;

import textio.TextIO;

/**
 * class performing location file actions.
 * reads locations.
 * writes mp3Ident Objects to File.
 * @author Kamila i Radek
 *
 */
public class sstr {

	/**
	 * Scanning folders.
	 * Used to scan folders and files. Reads mp3Ident to object data file from locations in file locs.dat
	 */
	static void scanFoldersToDataFile() {
		File locations;
		TextIO.readFile(startCl.fPaths.getPathDriveLetter() + ":\\locs.dat");
		String line;

		while ( ! TextIO.eof() ) {
			line = TextIO.getlnString();
			locsSets.checkIfExistingAndCreate(line);
			locations = new File(line);
			scanFolders(locations);
		}
	}

	/**
	 * Scanning folders.
	 * Used to scan folders and files. Looper for files in location.
	 * 
	 * @param inp
	 */
	static void scanFolders(File inp) {
		// declarations
		mp3Ident song;
		// filter
		FileFilter myFilt = new FileFilter() {
			public boolean accept(File pathname) {

				boolean tr;
				try {
					tr = Array.getLength(pathname.listFiles()) == 0;
				} catch (NullPointerException exc) {
					if (pathname.isDirectory())
						return false;
				}
				if ((pathname.getName().toLowerCase().matches(".*.mp3") || pathname.isDirectory()))

					return true;
				else
					return false;
			}
		};
		// listing with filter
		File[] listOfFiles = inp.listFiles(myFilt);
		// looping and creating mp3Ident objects
		for (File wrk : listOfFiles) {
			if (wrk.isDirectory()) {
				if (wrk.listFiles().length == 0) {
					wrk.delete();
				} else
					scanFolders(wrk);
			} else {
				try {
					song = new mp3Ident(wrk);
				} catch (InvalidAudioFrameException | ReadOnlyFileException 
						| TagException | IOException
						| CannotReadException e) {
					continue;
				}
				
				// add to locsSets
				locsSets.getSetUnit(song.fileM.getPath()).addMp3(song);
				// write to data file
				writeScannedFileToDataFile(song);
			}
		}
	}

	/**
	 * Scanning folders.
	 * Used to scan folders and files. Writes one mp3Ident Object direct to file ( and to locsSets )
	 * 
	 * @param inp
	 */
	static void writeScannedFileToDataFile(mp3Ident inp) {
		// TODO scanning CD to data file, anchor point.
		//JFileChooser jfc = new JFileChooser();
		//jfc.showSaveDialog(null);
		try (FileOutputStream fos = new FileOutputStream(startCl.fPaths.getPathDriveLetter() + ":\\arch31_g.dat", true);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(inp);
			oos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reading data file.
	 * Used to read mp3Idents from data file. Reads one mp3Ident object from data file.
	 * performed in serial read of objects from file.
	 * 
	 * @param inp
	 * @return
	 */
	static mp3Ident readMp3idFromDataFile(File inp, FileInputStream fisInp) throws EOFException {
		locsSets lcSet = null;
		mp3Ident mp3 = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		/*
		 * try { fis = new FileInputStream("d:\\arch.data"); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); }
		 */
		// if (fis != null) {
		try {
			ois = new ObjectInputStream(fisInp);
		} catch (IOException e) {
			// e.printStackTrace();
			if (e.getClass() == EOFException.class) {
				System.out.println("End of file reached");
				throw (EOFException) e;
			}
		}
		try {
			//lcSet = (locsSets) ois.readObject();
			mp3 = (mp3Ident) ois.readObject();
		} catch (EOFException | NullPointerException e) {
			return null;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		// }
		return mp3;
	}

	/**
	 * Reading data file.
	 * Used to read mp3Idents from data file. Reads whole data file to mp3Ident array
	 */
	static void readDataFile() {
		/**Iterator<myPathString> it = locsSets.getLocsList().iterator();
		while (it.hasNext()) {
		}**/
		File source = new File(startCl.fPaths.getPathDriveLetter() + ":\\arch31_g.dat");
		FileInputStream fis = null;
		mp3Ident buff;

		try {
			fis = new FileInputStream(source);
		} catch (FileNotFoundException e) {
			javax.swing.JOptionPane.showMessageDialog(null, "file " + source.getAbsolutePath() +  " not found");
			e.printStackTrace();
			return;
		}

		while (true) {

			try {
				buff = readMp3idFromDataFile(source, fis);
				mp3Ident.addToTreeMap(buff);
				try {
				locsSets.getSetUnit(buff.fileM.getParent()).addMp3(buff);
				} catch (NullPointerException npe) {
					System.out.println(buff.fileM.getPath() + " not added. Path belongs not to locsSets");
				}
			} catch (EOFException w) {
				//locsSets.getSetUnit("sss");
				return;
				
			}
			/**
			 * try{ fis.close(); } catch (IOException exc) { System.out.println("error IO");
			 * }
			 **/
		}
	}
	
	/**
	 * UNUSED. Writing all Objects from data file to TEXT file
	 * Unused in GUI.
	 */
	public static void printToFile(String fileInp) {
		mp3Ident mp3;
		File source = new File(fileInp);
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(source);
		} catch (FileNotFoundException e) {
			javax.swing.JOptionPane.showMessageDialog(null, "file not found");
			e.printStackTrace();
		}
		TextIO.writeFile(startCl.fPaths.getPathDriveLetter() + ":\\new.txt");
		while (true) {
			try {
				mp3 = readMp3idFromDataFile(source, fis);
			} catch (EOFException w) {
				return;
			}

			mp3.printOne(mp3);
		}
	}

	/**
	 * UNUSED. get value of Double Replay Gain as String
	 * unused in GUI
	 * @param inp Input file
	 */
	static String getRepGain(AudioFile inp)

	{

		String str = new String();
		Iterator<TagField> fields = inp.getTag().getFields();
		while (fields.hasNext()) {

			TagField tF = fields.next();
			TextIO.putln(tF.getId());
			TextIO.putln(tF.toString());
			if (tF.toString().matches(".*replaygain_track_gain.*")) {
				str = tF.toString();
				break;
			}
		}

		return str;
	}

}





/**
 * class of mp3Ident Object. Creates all database of mp3Ident Objects. Artist sorted map.
 * @author Kamila i Radek
 *
 */
class mp3Ident implements Serializable {

		File fileM;
		String tagGen;
		String tagArt;
		String tagAlb;
		String tagTit;
		String tagTra;
		String tagYea;
		String tagCom;
		int tagLen;

		public static TreeMap<String, TreeMap<String, ArrayList<mp3Ident>>> arts = new TreeMap<>();

		/**
		 * Constructor for mp3Ident - reads tags from input mp3 file with jaudiotagger,
		 * stores tags in mp3Ident Object
		 * 
		 * @param inp
		 * @throws InvalidAudioFrameException
		 * @throws ReadOnlyFileException
		 * @throws TagException
		 * @throws IOException
		 * @throws CannotReadException
		 */
		mp3Ident(File inp) throws InvalidAudioFrameException, ReadOnlyFileException,
		TagException, IOException, CannotReadException{
			AudioFile audioFil = null;
			try {
			audioFil=AudioFileIO.read(inp);
			} catch (CannotReadException | IOException | TagException | 
				ReadOnlyFileException | InvalidAudioFrameException e) {
				if (!(e instanceof InvalidAudioFrameException)) {
					e.printStackTrace();
				}
				if (e instanceof InvalidAudioFrameException) {
					throw e;
				}
				}
			finally {
				if (audioFil == null)
				audioFil = new AudioFile();
			}
			MP3File mFile = null;
			try {
				mFile = new MP3File(inp);
			} catch (IOException | TagException | ReadOnlyFileException | CannotReadException
					| InvalidAudioFrameException e) {
			}
			finally {
				if (mFile == null)
				mFile = new MP3File();
			}
			Tag tg = audioFil.getTagAndConvertOrCreateAndSetDefault();
			ID3v1Tag v1Tag = mFile.getID3v1Tag();
			
			fileM = inp;
			tagGen = getID1dat(v1Tag, tg, FieldKey.GENRE);
			tagArt = getID1dat(v1Tag, tg, FieldKey.ARTIST);
			tagAlb = getID1dat(v1Tag, tg, FieldKey.ALBUM);
			tagTit = getID1dat(v1Tag, tg, FieldKey.TITLE);
			tagTra = tg.getFirst(FieldKey.TRACK);//+";";
			tagYea = tg.getFirst(FieldKey.YEAR);//+";";
			tagCom = tg.getFirst(FieldKey.COMMENT);//+";";
			tagLen = audioFil.getAudioHeader().getTrackLength();//+";";
			/*audioFil.getFile().getParent()+";"
			audioFil.getFile().getName()*/
			
	// temporary test
			
			addToTreeMap(this);
	
			/**if (arts.get(tagArt) == null) {
				TreeMap<String, ArrayList<String>> workedA = new TreeMap<>();
				ArrayList<String> workedS = new ArrayList<>();
				workedS.add(tagTit);
				workedA.put(tagAlb, workedS);
				arts.put(tagArt, workedA);
			}
			else {
				if (arts.get(tagArt).keySet().contains(tagAlb)){
					arts.get(tagArt).get(tagAlb).add(tagTit);
				}
				else {
					ArrayList<String> titArr = new ArrayList<>(); 
					titArr.add(tagTit);
					arts.get(tagArt).put(tagAlb, titArr);
				}
				
				if (tagCom == "rat4") {
					
				}**/
					
				/**TreeMap<String, ArrayList<String>> workedA = arts.get(tagAlb);
				if (workedA == null) {
					ArrayList<String> workedS = new ArrayList<>();
					workedS.add(tagTit);
					workedA = new TreeMap<String, ArrayList<String>>();
					workedA.put(tagAlb, workedS);
				}
				else {
					worked.get(tagArt).get(tagAlb).add(tagTit);
				}**/
				
			}
			
		/**
		 * Used by constructor. checking which tags are contained, returns string or TextIO sends data to OutputStream
		 * Used in constructor.
		 * @param Tag1
		 * @param Tag2
		 * @param fK
		 * @return
		 */
		private static String getID1dat(ID3v1Tag Tag1, Tag Tag2, FieldKey fK) {
			
			//chcecking which tags are contained
				if (Tag2.hasField(fK)) {
					//TextIO.put(Tag2.getFirst(fK)+";");
					return Tag2.getFirst(fK)+"";
				}
				else {
					if (Tag1 != null && Tag1.hasField(fK)) 
						//TextIO.put(Tag1.getValue(fK, 1)+";");
						return Tag1.getValue(fK, 1)+"";
					else
						//TextIO.put(";");
						return "";
			}
		}

		/**
		 * Map creating.
		 * @param inp
		 */
		static void addToTreeMap(mp3Ident inp){
			if (arts.get(inp.tagArt) == null) {
				//create mp3Ident list
				ArrayList<mp3Ident> tListSongs = new ArrayList<>();
				//put mp3Ident to list
				tListSongs.add(inp);
				//create associations album - songlist map
				TreeMap<String, ArrayList<mp3Ident>> tMapSongs = new TreeMap<>();
				//associate list with album key
				tMapSongs.put(inp.tagAlb, tListSongs);
				//associate album with artist
				arts.put(inp.tagArt, tMapSongs);
			}
			else {
				if (arts.get(inp.tagArt).keySet().contains(inp.tagAlb)){
					arts.get(inp.tagArt).get(inp.tagAlb).add(inp);
				}
				else {
					ArrayList<mp3Ident> titArr = new ArrayList<>(); 
					titArr.add(inp);
					arts.get(inp.tagArt).put(inp.tagAlb, titArr);
				}
			}
		}
		
		/**
		 * UNUSED. writes one record to txt file, from collMus array.
		 * @param mp3Inp
		 */
		void printOne(mp3Ident mp3Inp) {
		
			Scanner scr = new Scanner(tagCom);
		
				TextIO.put(tagGen);
				TextIO.put(tagArt);
				TextIO.put(tagAlb);
				TextIO.put(tagTit);
				TextIO.put(tagTra);
				TextIO.put(tagYea);
				TextIO.put(scr.nextLine()+";");
				scr.close();
				TextIO.put(tagLen);
				TextIO.put(fileM.getParentFile().getPath()+";");
				TextIO.putln(fileM.getName()+";");
			}

		public String toString() {
				StringBuffer sb = new StringBuffer();
				if (!tagGen.equals(""))
					sb.append(tagGen);
				if (!tagArt.equals("") && !sb.toString().equals(""))
					sb.append(" ; " + tagArt);
				else if (!tagArt.equals("") && sb.toString().equals(""))
					sb.append(tagArt);
				if (!tagAlb.equals("") && !sb.toString().equals(""))
					sb.append(" ; " + tagAlb);
				else if (!tagAlb.equals("") && sb.toString().equals(""))
					sb.append(tagAlb);
				if (!tagTit.equals("") && !sb.toString().equals(""))
					sb.append(" ; " + tagTit);
				else if(!tagTit.equals("") && sb.toString().equals(""))
					sb.append(tagTit);
				return sb.toString();
		}

		
}


/**class creates mp3Ident collection for each every location existing in the location list.
 * 
 * @author Kamila i Radek
 *
 */


class myPathString implements Comparable {
	
		private String path;
		
		myPathString(String inp){
			this.path = inp;
		}
		
		String getPath() {
			return path;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			myPathString objM = (myPathString)obj;
			myPathString objM2 = (myPathString)this;
			
			if (objM.path.startsWith(this.path)) {
				return true;
			} else
				return false;
		}
		
		@Override
		public String toString() {
		// TODO Auto-generated method stub
		return this.path;
		}

		@Override
		public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
		}

}

class sameAudioFinder{
	
}
