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
	
	Mp3Ident mp3;
	Pattern pattern;
	Matcher matcher;
	
	public EqualityMethExtender2(File inp) throws InvalidAudioFrameException, ReadOnlyFileException, TagException, IOException, CannotReadException {
		super(inp);
	}
	
	@Override
	public boolean equals(Object obj) {
		//return super.equals(obj);
		pattern = Pattern.compile(getTagAlb(), Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(getTagAlb());
		return matcher.matches();
	}

	public boolean equals(Mp3Ident obj) {
		// TODO Auto-generated method stub
		return false;
	}	
}
