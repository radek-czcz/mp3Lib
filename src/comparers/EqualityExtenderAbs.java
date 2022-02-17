package comparers;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import core.Mp3Ident;
import entry.AppContext;

public abstract class EqualityExtenderAbs extends Mp3Ident {
	
	EqualityExtenderAbs extender;


	EqualityExtenderAbs(File inp) throws InvalidAudioFrameException, ReadOnlyFileException, TagException, IOException, CannotReadException {
		super(inp);
	}
	
	/*
	 * public EqualityExtenderAbs() { // TODO Auto-generated constructor stub }
	 */
	
}
