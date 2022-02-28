package comparers;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class EqualityExtenderFactory1 implements IEqualityExtenderFactory {

	@Override
	public EqualityExtenderAbs createExtender(File inp) {
		// TODO Auto-generated method stub
		try {
			return new EqualityMethExtender(inp);
		} catch (InvalidAudioFrameException | ReadOnlyFileException | TagException | IOException
				| CannotReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
