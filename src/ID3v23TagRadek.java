import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.id3.ID3v23FieldKey;
import org.jaudiotagger.tag.id3.ID3v23Tag;

public class ID3v23TagRadek extends ID3v23Tag {
	
	public String getFirst(ID3v23FieldKey id3v23FieldKey) throws KeyNotFoundException{
		String newStr;
		newStr = super.getFirst(id3v23FieldKey);
		newStr=newStr.substring(1, newStr.indexOf(";"));
		return newStr;
	}

}
