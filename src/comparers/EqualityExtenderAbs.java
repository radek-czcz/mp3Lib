package comparers;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import core.Mp3Ident;

public abstract class EqualityExtenderAbs extends Mp3Ident {

	protected EqualityExtenderAbs()
			throws InvalidAudioFrameException, ReadOnlyFileException, TagException, IOException, CannotReadException {
		super();
	}
	
	@Override
	public abstract boolean equals(Object obj);
}
