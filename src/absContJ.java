import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class absContJ extends JPanel {
	static final long serialVersionUID = 42L;
	Component cmp = (Component)this;
	
	public void paintComponent(Graphics g){
		g.drawString("new panel", 20, 20);
	}

}
