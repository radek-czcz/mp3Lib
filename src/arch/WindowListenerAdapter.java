package arch;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowListenerAdapter extends WindowAdapter {
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("adapter closed");
		super.windowClosed(e);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("adapter closing");
		super.windowClosing(e);
	}
	
	
}
