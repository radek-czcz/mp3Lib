package core;
import java.awt.Dimension;

	public class contJ extends absContJ{
		static final long serialVersionUID = 48L;
		public Dimension getPreferredSize() {
			return new Dimension(this.getWidth(), this.getParent().getSize().height-20);
		}
		
	}
