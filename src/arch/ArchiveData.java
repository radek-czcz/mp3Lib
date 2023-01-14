package arch;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.RowMapper;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import core.locsSets;
import core.Mp3Ident;
import core.MyTreeNode;
import core.Scanning;



public class ArchiveData implements Comparable<ArchiveData>, TransferedObject, Serializable{
	
	static private SortedSet<ArchiveData> setOfArchives = new TreeSet<ArchiveData>();

	public static SortedSet<ArchiveData> getSetOfArchives() {
		return setOfArchives;
	}
	
	String name;
	locsSets data;
	
	static void readListOfArchiveData() {
		File archiveDir = new File("archivefiles");
		for (File runner: archiveDir.listFiles(new ArchiveFileFilter())){
			ArchiveData newAd = new ArchiveData();
			newAd.name = runner.getName();
			setOfArchives.add(newAd);
		}
	}

	public void addToSetOfArchives() {
		setOfArchives.add(this);
	}

	ArchiveData() {
		new locsSets(this);
	}

@Override
	public int compareTo(ArchiveData o) {
		// TODO Auto-generated method stub
		return this.name.compareToIgnoreCase(o.name);
	}

@Override
public String getName() {
	return name;
}

@Override
public String getPath() {
	return data.getLocPath().getPath();
}

@Override
public String getDescription() {
	// TODO Auto-generated method stub
	return "";
}

@Override
public String getOwner() {
	// TODO Auto-generated method stub
	return "";
}

@Override
public ArrayList<Mp3Ident> getObjects() {
	ArrayList<Mp3Ident> retVal = new ArrayList<>();
	if (data != null && data.getSongs() != null && !data.getSongs().isEmpty()) {
		retVal = (ArrayList)data.getSongs();
		return retVal;
	}
	else
		data.getSongs().addAll(Scanning.readArchiveFile(name));
	retVal = (ArrayList)data.getSongs();
	return retVal;
}

@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.getName();
}

public void setData(locsSets data) {
	this.data = data;
}

public locsSets getData() {
	return data;
}
}
