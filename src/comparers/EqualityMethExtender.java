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

public class EqualityMethExtender extends core.Mp3Ident {
	
	Pattern pattern;

	EqualityMethExtender(File inp)
			throws InvalidAudioFrameException, ReadOnlyFileException, TagException, IOException, CannotReadException {
		super(inp);
	}
	
	@Override
	public boolean equals(Object obj) {
		//return super.equals(obj);
		EqualityMethExtender thisMp3 = (EqualityMethExtender)this;
		EqualityMethExtender inpMp3 = (EqualityMethExtender)obj;
		if ((thisMp3.getTagArt().equalsIgnoreCase(inpMp3.getTagArt()) &&
			thisMp3.getTagAlb().equalsIgnoreCase(inpMp3.getTagAlb()) &&
			thisMp3.getTagTit().equalsIgnoreCase(inpMp3.getTagTit())) && 
			(!thisMp3.getTagArt().equalsIgnoreCase("") &&
			!thisMp3.getTagAlb().equalsIgnoreCase("") &&
			!thisMp3.getTagTit().equalsIgnoreCase("")))
		{
		return true;
		} else {
		return false;
		}
	}
}
