import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocsSetsHashMap<K, V> extends HashMap<K, V> {
	
	ArrayList<ILocsSetsListener> listenersList= new ArrayList<>();
	
	public void registerListener(ILocsSetsListener listener) {
		listenersList.add(listener);
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}
	
	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return super.put(key, value);
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		super.putAll(m);
	}
	
	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		V temp;
		temp = super.remove(key);
		for (ILocsSetsListener locsSetsListener : listenersList) {
			locsSetsListener.locsSetsChanged();;
		}
		return temp;
	}
}
