package core;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AllMp3IdentTreeMap<K,V> extends TreeMap<K, V> implements ILocsSetsListener{
	ArrayList<Mp3IdentListListener> listeners = new ArrayList<>();
	
	public void registerListener(Mp3IdentListListener listener){
		listeners.add(listener);
	}
	
	@Override
	public V put(K key, V value) {

		V temp;
		temp = super.put(key, value);
		for (Mp3IdentListListener mp3IdentListListener : listeners) {
			mp3IdentListListener.mp3IdentListChanged();
		}
		return temp;
	}
	
	@Override
	public V remove(Object key) {

		V temp;
		temp = super.remove(key);
		for (Mp3IdentListListener mp3IdentListListener : listeners) {
			mp3IdentListListener.mp3IdentListChanged();
		}
		return temp;
	}
	
	@Override
	public void clear() {

		super.clear();
		for (Mp3IdentListListener mp3IdentListListener : listeners) {
			mp3IdentListListener.mp3IdentListChanged();
		}
	}
	
	@Override
	public void locsSetsChanged(Object key) {
		// TODO Auto-generated method stub
		ArrayList<Mp3Ident> toRemove = locsSets.getAllSets().get(key).getSongs();
		for (Mp3Ident runner : toRemove) {
			TreeMap<String, ArrayList<Mp3Ident>> tree = (TreeMap<String, ArrayList<Mp3Ident>>)this.get(runner.tagArt);
			ArrayList<Mp3Ident> list = (ArrayList<Mp3Ident>)tree.get(runner.tagAlb);
			list.remove(runner);
		}
	}
}
