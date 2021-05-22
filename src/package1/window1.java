package package1;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class window1 {
	
	public static void main(String args[]) throws FileNotFoundException, EOFException,
		InvalidAudioFrameException, ReadOnlyFileException, TagException, CannotReadException {
		
		mp3Ident ob;
		DefaultListModel<mp3Ident> dlm = new DefaultListModel<>();
		File source = new File("g:\\arch31_g.dat");
		FileInputStream fis = new FileInputStream(source);
		mp3Ident mp3 = null;
		ObjectInputStream ois = null;


		try {
			ois = new ObjectInputStream(fis);
		} catch (IOException e) {
			//e.printStackTrace();
			if (e.getClass() == new EOFException().getClass()) {
				System.out.println("End of file reached");
				throw (EOFException)e;
			}
		}
		try {
			ob = (mp3Ident)ois.readObject();
			mp3Ident ob1 = new mp3Ident(new File("G:\\mp3 pobrane C\\Moose Dawa - 2018 - Reflekt\\Moose Dawa - Chances (feat. Andras Szilagyi).mp3")) {
				@Override
				public String toString() {
					// TODO Auto-generated method stub
					return this.tagTit;
				}
			};
			System.out.println("end");
			dlm.addElement(ob1);
			//mp3 = (mp3Ident)ois.readObject();
		} catch (EOFException | NullPointerException e) {

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	
		JList jL = new JList(dlm);
		
		JFrame jF = new JFrame();
		JViewport jV = new JViewport();
		jV.setView(jL);

		jF.add(jV);
		jF.setVisible(true);
		jF.setSize(200, 200);
		jL.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				JList<mp3Ident> newSel = (JList<mp3Ident>)e.getSource();
				ArrayList<mp3Ident> listSelected = (ArrayList<mp3Ident>)newSel.getSelectedValuesList();
				System.out.println("selected");
			}
		});
	}
	
	/**class mp3IdentDisplCust extends mp3Ident{
		
		
		
		mp3IdentDisplCust(File inp) throws InvalidAudioFrameException, ReadOnlyFileException, TagException, IOException,
				CannotReadException {
			super(inp);
		}

		@Override
		public String toString() {
			return this.tagTit;
		}
	}**/
}
