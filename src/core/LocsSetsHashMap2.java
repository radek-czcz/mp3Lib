package core;
import java.util.ArrayList;
import java.util.HashMap;

public class LocsSetsHashMap2 extends HashMap<MyPathString, locsSets> {
	
	ArrayList<ILocsSetsListener> listenersList= new ArrayList<>();
	
	public void registerListener(ILocsSetsListener listener) {
		listenersList.add(listener);
	}
	@Override
	public locsSets remove(Object key) {
		// TODO Auto-generated method stub
		locsSets temp;
		for (ILocsSetsListener locsSetsListener : listenersList) {
			if (locsSetsListener instanceof RespModel) {}
			else locsSetsListener.locsSetsChanged(key);
		}
		temp = super.remove(key);
		for (ILocsSetsListener locsSetsListener : listenersList) {
			if (locsSetsListener instanceof RespModel) {
			locsSetsListener.locsSetsChanged(key);
			} else {}
		}
		return temp;
	}

	public boolean containsPath(MyPathString key) {
		
		if (str.getPath().startsWith(runner.getPath() + "\\") || 
				str.getPath().equals(runner.getPath()) )
			return allSets.get(runner);
		
		return false;
	}
}
