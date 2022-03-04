package core;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AllMp3IdentTreeMap2<K,V> extends TreeMap<K, V> {
	
	PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
	}
	
	
	
}
