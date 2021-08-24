package arch;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.text.JTextComponent;

import arch.ArchiveData.LocAndNameWindow;

public class ArchiveButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//get provided name
		String archiveName = LocAndNameWindow.getName();
		
		//get archive File
		File archiveDir = LocAndNameWindow.getPath();
		
		System.out.println("perform action: name of archive=" + archiveName + " selected=" + archiveDir.getPath());
	}

}
