import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

/**
 * main Window's overriden JFrame.
 * @author Kamila i Radek
 *
 */
public class mainFrameCustomized extends JFrame {
	static final long serialVersionUID = 236L;
		int restOnWidthDiff = 0;
		
		mainFrameCustomized(){
			super();
			this.addComponentListener(new ComponentListener() {
				
				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentResized(ComponentEvent e) {					
						/**restOnWidthDiff = 0;
						mainFrameCustomized frame = (mainFrameCustomized)e.getSource();
						for (int nth=0; nth < frame.getContentPane().getComponentCount(); nth++) {
							if (frame.getContentPane().getComponents()[nth].getClass() != contJ2.class) {
								restOnWidthDiff += frame.getContentPane().getComponents()[nth].getWidth();	
							}
						}**/
				}
				
				@Override
				public void componentMoved(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}

		@Override
		public Component add(Component comp, int index) {
			//if (comp.getClass() != contJ2.class) {
			//	restOnWidthDiff += comp.getWidth(); 
			//}
			return super.add(comp, index);
		}
		
		private int getRestOnWidthDiff() {
			return restOnWidthDiff;
		}

		/**private void newRestOnWidth() {
				Component[] allComps = getComponents();
				for (Component cmp : allComps) {
					if (cmp.getClass() != contJ.class) {
						restOnWidth -= cmp.getWidth();	
					}
				}
		}**/
		
		public int getRestWidth() {
			return (this.getWidth() - getRestOnWidthDiff())/2;
		}

		public int getRestWidth2() {
			restOnWidthDiff = 0;
			for (int nth1 = 0; nth1 < this.getContentPane().getComponentCount(); ++nth1)
			if (this.getContentPane().getComponents()[nth1].getClass() != contJ2.class)
			restOnWidthDiff += this.getContentPane().getComponents()[nth1].getWidth();
			return (this.getContentPane().getWidth() - restOnWidthDiff-70)/2;
		}
		
		/**@Override
		public void paintComponents(Graphics g) {
				restOnWidthDiff = 0;
				for (int nth=0; nth < this.getContentPane().getComponentCount(); ++nth) {
					restOnWidthDiff += this.getContentPane().getComponents()[nth].getWidth();
				}
				super.paintComponents(g);
		}**/
		
		/**@Override
		public void setSize(int width, int height) {
				restOnWidthDiff = 0;
				for (int nth=0; nth < this.getContentPane().getComponentCount(); ++nth) {
					restOnWidthDiff += this.getContentPane().getComponents()[nth].getWidth();
				}
		super.setSize(width, height);
		}**/
		
		/**@Override
		public void setSize(Dimension d) {
			restOnWidthDiff = 0;
			for (int nth=0; nth < this.getContentPane().getComponentCount(); ++nth) {
				restOnWidthDiff += this.getContentPane().getComponents()[nth].getWidth();
			}
			super.setSize(d);
		}**/
}
