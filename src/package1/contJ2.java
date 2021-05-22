package package1;
import java.awt.Dimension;

public class contJ2 extends absContJ{
	static final long serialVersionUID = 422L;
	public Dimension getPreferredSize() {
		return new Dimension(
				//this.getParent().getSize().width,
				(((mainFrameCustomized)(this.
						getParent().
						getParent().
						getParent().
						getParent()))).getRestWidth2(),
				this.getParent().getSize().height-20);
	}

}