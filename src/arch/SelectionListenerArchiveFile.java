package arch;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SelectionListenerArchiveFile implements ListSelectionListener {
	
	ArchiveData selected;

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (!e.getValueIsAdjusting())
		System.out.println("selected");
		JList jL = (JList)e.getSource();
		selected = (ArchiveData)jL.getSelectedValue();
	}
	
	ArchiveData getSelected(){
		return selected;
	}
}
