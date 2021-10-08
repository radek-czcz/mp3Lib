package arch;

import java.io.Serializable;
import java.util.ArrayList;

public interface TransferedObject extends Serializable {
	String getName();
	String getPath();
	String getDescription();
	String getOwner();
	ArrayList<Object> getObjects();
}
