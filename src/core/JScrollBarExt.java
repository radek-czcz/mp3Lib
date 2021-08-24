package core;
import java.awt.Dimension;

import javax.swing.JScrollBar;

public class JScrollBarExt extends JScrollBar {
	static final long serialVersionUID = 4221L;
	public JScrollBarExt(int orientation, int value, int extent, int min, int max) {
		super(orientation,value,extent,min,max);
	}
	
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		return new Dimension(this.getWidth(), this.getParent().getSize().height);
	}
}
