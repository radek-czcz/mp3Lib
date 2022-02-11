package comparers;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Mp3Ident;

public class EqualityMethExtender2 extends EqualityExtenderAbs {
	
	Pattern pattern;
	Matcher matcher;
	
	EqualityMethExtender2()
			throws InvalidAudioFrameException, ReadOnlyFileException, TagException, IOException, CannotReadException {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		//return super.equals(obj);
		pattern = Pattern.compile(getTagAlb(), Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(getTagAlb());
		return true;
	}
}
