package arch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import core.locsSets;

public class CompareToArchiveWindow {
	
	locsSets selected;

	public CompareToArchiveWindow(locsSets selection) {
		selected = selection;
		// TODO Auto-generated constructor stub
		JFrame window = new JFrame();
		JPanel jP = new JPanel();
		BoxLayout bL = new BoxLayout(jP, BoxLayout.Y_AXIS);
		jP.setLayout(bL);
		JButton compareButton = new JButton("compare");

		//JList list = new JList<>(ArchiveData.getSetOfArchives().toArray());
		ArchiveData.readListOfArchiveData();
		JList<ArchiveData> list = new JList(ArchiveData.getSetOfArchives().toArray());
		SelectionListenerArchiveFile sL = new SelectionListenerArchiveFile();
		compareButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArchiveData aD = sL.getSelected();
				aD.getObjects();
				locsSets toCompare = selected;
				toCompare.compareTo(aD.getData());
				
			}
		});
		list.addListSelectionListener(sL);
		jP.add(list);
		jP.add(compareButton);
		window.add(jP);
		window.setSize(400, 400);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
	}
}
