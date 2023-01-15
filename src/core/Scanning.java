package core;

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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.ListModel;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.ID3Tags;
import org.jaudiotagger.tag.id3.ID3v1FieldKey;
import org.jaudiotagger.tag.id3.ID3v23Frames;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.mp4.Mp4FieldKey;
import org.jaudiotagger.tag.reference.GenreTypes;
import org.jaudiotagger.tag.reference.ID3V2Version;

import entry.AppContext;
import textio.TextIO;

/**
 * class performing location file actions.
 * reads locations.
 * writes mp3Ident Objects to File.
 * @author Kamila i Radek
 *
 */
public class Scanning {

	/**
	 * Scanning folders.
	 * Used to scan folders and files. Reads mp3Ident to object data file from locations in file locs.dat
	 */
	static void scanFoldersToDataFile() {
		File locations;
		TextIO.readFile(startCl.fPaths.getPathDriveLetter() + ":" + startCl.fPaths.getPath() + "\\locs.dat");
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
	public static locsSets scanFolders(File inp) {
		// declarations
		Mp3Ident song;
		// filter
		FileFilter myFilt = new MusicFilesFilter();
		// listing with filter
		File[] listOfFiles = inp.listFiles(myFilt);
		// looping and creating mp3Ident objects
		locsSets temp = new locsSets();
		for (File wrk : listOfFiles) {
			if (wrk.isDirectory()) {
				if (wrk.listFiles().length == 0) {
					wrk.delete();
				} else
					try {
					temp.getSongs().addAll(scanFolders(wrk).getSongs());
					} catch (NullPointerException e) {}
			} else {
				try {
					song = new Mp3Ident(wrk);
				} catch (InvalidAudioFrameException | ReadOnlyFileException 
						| TagException | IOException
						| CannotReadException e) {
					continue;
				}
				
				// add to locsSets
				
				try {
				locsSets.getSetUnit(song.fileM.getPath()).addMp3(song);
				// write to data file
				writeScannedFileToDataFile(song);
				} catch (NullPointerException ex) {
					// TODO uzupelniæ
					temp.addMp3(song);
				}
			}
		}
		if (!temp.getSongs().isEmpty())
			return temp;
		else return locsSets.getSetUnit(inp.getAbsolutePath());
	}

	/**
	 * Scanning folders.
	 * Used to scan folders and files. Writes one mp3Ident Object direct to file ( and to locsSets )
	 * 
	 * @param inp
	 */
	static void writeScannedFileToDataFile(Mp3Ident inp) {
		// TODO scanning CD to data file, anchor point.
		//JFileChooser jfc = new JFileChooser();
		//jfc.showSaveDialog(null);
		try (FileOutputStream fos = new FileOutputStream(startCl.fPaths.getPathDriveLetter() + ":" + startCl.fPaths.getPath() + "\\arch31_g.dat", true);
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
	static Mp3Ident readMp3idFromDataFile(File inp, FileInputStream fisInp) throws EOFException {

		Mp3Ident mp3 = null;
		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(fisInp);
		} catch (IOException e) {
			e.printStackTrace();
			if (e.getClass() == EOFException.class) {
				System.out.println("End of file reached");
				throw (EOFException) e;
			}
		}
		try {
			mp3 = (Mp3Ident) ois.readObject();
		} catch (EOFException | NullPointerException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return mp3;
	}

	/**
	 * Reading data file.
	 * Used to read mp3Idents from data file. Reads whole data file to mp3Ident array
	 */
	static void readDataFile() {

		File source = new File(startCl.fPaths.getPathDriveLetter() + ":" + startCl.fPaths.getPath() + "\\arch31_g.dat");
		FileInputStream fis = null;
		Mp3Ident buff;

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
				Mp3Ident.addToTreeMap(buff);
				try {
				locsSets.getSetUnit(buff.fileM.getParent()).addMp3(buff);
				} catch (NullPointerException npe) {
					System.out.println(buff.fileM.getPath() + " not added. Path belongs not to locsSets");
				}
			} catch (EOFException w) {
				w.printStackTrace();
				break;
			}
		}
		
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
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



static public ArrayList<Mp3Ident> readArchiveFile(String inp) {

	File source = new File("archivefiles//" + inp); // = new File(startCl.fPaths.getPathDriveLetter() + ":\\arch31_g.dat");
	FileInputStream fis = null;
	Mp3Ident buff;
	ArrayList<Mp3Ident> mp3IdentColl = new ArrayList<>();

	if (!source.exists())
		source = new File("archivefiles//" + inp + ".ada");
	try {
		fis = new FileInputStream(source);
	} catch (FileNotFoundException e) {
		//javax.swing.JOptionPane.showMessageDialog(null, "file " + source.getAbsolutePath() +  " not found");
		e.printStackTrace();
	}

	while (true) {

		try {
			buff = readMp3idFromDataFile(source, fis);
			mp3IdentColl.add(buff);
			//Mp3Ident.addToTreeMap(buff);
		} catch (EOFException w) {
			break;
		}
	}
	return mp3IdentColl;
}

	public static ArrayList<Mp3Ident> scanFoldersToCollection(File folder){
		// declarations
		Mp3Ident song;
		ArrayList<Mp3Ident> songs = new ArrayList<>();
		
		// filter
		FileFilter myFilt = new MusicFilesFilter();
		
		// listing with filter
		File[] listOfFiles = folder.listFiles(myFilt);
		
		// looping and creating mp3Ident objects
		locsSets temp = new locsSets();
		for (File wrk : listOfFiles) {
			if (wrk.isDirectory()) {
				if (wrk.listFiles().length == 0) {
					wrk.delete();
				} else
					try {
					songs.addAll(scanFoldersToCollection(wrk));
					} catch (NullPointerException e) {}
			} else {
				try {
					song = new Mp3Ident(wrk);
					songs.add(song);
				} catch (InvalidAudioFrameException | ReadOnlyFileException 
						| TagException | IOException
						| CannotReadException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		if (songs.isEmpty())
			return null;
		else return songs;
	}

}
