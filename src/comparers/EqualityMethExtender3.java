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

public class EqualityMethExtender3 extends EqualityExtenderAbs {
	
	Mp3Ident mp3;
	Pattern pattern;
	Matcher matcher;
	
	public EqualityMethExtender3(File inp) throws InvalidAudioFrameException, ReadOnlyFileException, TagException, IOException, CannotReadException {
		super(inp);
	}
	
	@Override
	public boolean equals(Object obj) {
		//return super.equals(obj);
		EqualityMethExtender3 thisMp3 = (EqualityMethExtender3)this;
		Mp3Ident inpMp3 = (Mp3Ident)obj;
		if ((thisMp3.getTagArt().equalsIgnoreCase(inpMp3.getTagArt()) &&
			thisMp3.getTagAlb().equalsIgnoreCase(inpMp3.getTagAlb()) &&
			thisMp3.getTagTit().equalsIgnoreCase(inpMp3.getTagTit()) && 
			thisMp3.getTagGen().equalsIgnoreCase(inpMp3.getTagGen()) &&
			thisMp3.getTagCom().equalsIgnoreCase(inpMp3.getTagCom()) &&
			thisMp3.getTagYea().equalsIgnoreCase(inpMp3.getTagYea()) &&
			thisMp3.getTagLen() == (inpMp3.getTagLen())) &&
			(!thisMp3.getTagArt().equalsIgnoreCase("") &&
			!thisMp3.getTagAlb().equalsIgnoreCase("") &&
			!thisMp3.getTagTit().equalsIgnoreCase("")
			))
		{
		return true;
		} else {
		return false;
		}
	}

	public boolean equals(Mp3Ident obj) {
		//return super.equals(obj);
		EqualityMethExtender3 thisMp3 = (EqualityMethExtender3)this;
		Mp3Ident inpMp3 = (Mp3Ident)obj;
		if (thisMp3.getTagArt().equalsIgnoreCase(inpMp3.getTagArt()) &&
			thisMp3.getTagAlb().equalsIgnoreCase(inpMp3.getTagAlb()) &&
			thisMp3.getTagTit().equalsIgnoreCase(inpMp3.getTagTit()) && 
			thisMp3.getTagGen().equalsIgnoreCase(inpMp3.getTagGen()) &&
			thisMp3.getTagLen() == (inpMp3.getTagLen()) &&
			(!thisMp3.getTagArt().equalsIgnoreCase("") &&
			!thisMp3.getTagAlb().equalsIgnoreCase("") &&
			!thisMp3.getTagTit().equalsIgnoreCase("") &&
			!thisMp3.getTagGen().equalsIgnoreCase("")
			))
		{
		return true;
		} else {
		return false;
		}
	}	
}
