import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AllMp3IdentTreeMap<K,V> extends TreeMap<K, V> implements ILocsSetsListener{
	@Override
	public void locsSetsChanged(Object key) {
		// TODO Auto-generated method stub
		ArrayList<mp3Ident> toRemove = locsSets.getAllSets().get(key).getSongs();
		for (mp3Ident runner : toRemove) {
			TreeMap<String, ArrayList<mp3Ident>> tree = (TreeMap<String, ArrayList<mp3Ident>>)this.get(runner.tagArt);
			ArrayList<mp3Ident> list = (ArrayList<mp3Ident>)tree.get(runner.tagAlb);
			list.remove(runner);
		}
	}
}
