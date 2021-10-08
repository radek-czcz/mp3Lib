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
import java.util.Arrays;
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
	public static locsSets scanFolders(File inp) {
		// declarations
		Mp3Ident song;
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
		locsSets temp = new locsSets();
		locsSets temp2;
		for (File wrk : listOfFiles) {
			if (wrk.isDirectory()) {
				if (wrk.listFiles().length == 0) {
					wrk.delete();
				} else
					temp.getSongs().addAll(scanFolders(wrk).getSongs());
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
	static Mp3Ident readMp3idFromDataFile(File inp, FileInputStream fisInp) throws EOFException {
		locsSets lcSet = null;
		Mp3Ident mp3 = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;

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
			mp3 = (Mp3Ident) ois.readObject();
		} catch (EOFException | NullPointerException e) {
			return null;
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

		File source = new File(startCl.fPaths.getPathDriveLetter() + ":\\arch31_g.dat");
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
				//locsSets.getSetUnit("sss");
				return;
				
			}
		}
	}
	
	/**
	 * UNUSED. Writing all Objects from data file to TEXT file
	 * Unused in GUI.
	 */
	public static void printToFile(String fileInp) {
		Mp3Ident mp3;
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



static void readArchiveFile(String inp) {

	File source = new File(inp); // = new File(startCl.fPaths.getPathDriveLetter() + ":\\arch31_g.dat");
	FileInputStream fis = null;
	Mp3Ident buff;

	try {
		fis = new FileInputStream(source);
	} catch (FileNotFoundException e) {
		//javax.swing.JOptionPane.showMessageDialog(null, "file " + source.getAbsolutePath() +  " not found");
		e.printStackTrace();
		return;
	}

	while (true) {

		try {
			buff = readMp3idFromDataFile(source, fis);
			//Mp3Ident.addToTreeMap(buff);
			try {
			//locsSets.getSetUnit(buff.fileM.getParent()).addMp3(buff);
			} catch (NullPointerException npe) {
				System.out.println(buff.fileM.getPath() + " not added. Path belongs not to locsSets");
			}
		} catch (EOFException w) {
			return;
			
		}
	}
}
}
