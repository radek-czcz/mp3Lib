package arch;

import java.io.Serializable;
import java.util.ArrayList;

import core.Mp3Ident;

public interface TransferedObject extends Serializable {
	String getName();
	String getPath();
	String getDescription();
	String getOwner();
	ArrayList<Mp3Ident> getObjects();
}
