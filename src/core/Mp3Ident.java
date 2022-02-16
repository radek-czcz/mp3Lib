package core;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.ID3v1Tag;

import textio.TextIO;

/**
 * class of mp3Ident Object. Creates all database of mp3Ident Objects. Artist sorted map.
 * @author Kamila i Radek
 *
 */
public class Mp3Ident implements Serializable {

		protected File fileM;
		protected String tagGen;
		protected String tagArt;
		protected String tagAlb;
		protected String tagTit;
		protected String tagTra;
		protected String tagYea;
		protected String tagCom;
		protected int tagLen;

		public static AllMp3IdentTreeMap<String, TreeMap<String, ArrayList<Mp3Ident>>> arts = new AllMp3IdentTreeMap<>();

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
		protected Mp3Ident(File inp) throws InvalidAudioFrameException, ReadOnlyFileException,
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
		
		protected Mp3Ident() {
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
		static void addToTreeMap(Mp3Ident inp){
			if (arts.get(inp.tagArt) == null) {
				//create mp3Ident list
				ArrayList<Mp3Ident> tListSongs = new ArrayList<>();
				//put mp3Ident to list
				tListSongs.add(inp);
				//create associations album - songlist map
				TreeMap<String, ArrayList<Mp3Ident>> tMapSongs = new TreeMap<>();
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
					ArrayList<Mp3Ident> titArr = new ArrayList<>(); 
					titArr.add(inp);
					arts.get(inp.tagArt).put(inp.tagAlb, titArr);
				}
			}
		}
		
		/**
		 * UNUSED. writes one record to txt file, from collMus array.
		 * @param mp3Inp
		 */
		void printOne(Mp3Ident mp3Inp) {
		
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

		@Override
		public boolean equals(Object obj) {
			return true;
}

		protected File getFileM() {
			return fileM;
		}

		protected String getTagGen() {
			return tagGen;
		}

		protected String getTagArt() {
			return tagArt;
		}

		protected String getTagAlb() {
			return tagAlb;
		}

		protected String getTagTit() {
			return tagTit;
		}

		protected String getTagTra() {
			return tagTra;
		}

		protected String getTagYea() {
			return tagYea;
		}

		protected String getTagCom() {
			return tagCom;
		}

		protected int getTagLen() {
			return tagLen;
		}
		
		
}