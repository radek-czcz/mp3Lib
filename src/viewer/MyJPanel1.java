package viewer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MyJPanel1 extends JPanel {
	
	MyJPanel1(){
		super();
		setLayout(new GridLayout());
		setBorder(new LineBorder(Color.white/*new Color(255, 255, 214)*/, 10));
	}

}
