package arch;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListenerAdapter extends MouseAdapter {

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseDragged(e);
		e.getSource().getClass();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("pressed");	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
	}
	
	
	
	

}
