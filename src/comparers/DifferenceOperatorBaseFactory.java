package comparers;
import java.util.Collection;
import core.Mp3Ident;

public class DifferenceOperatorBaseFactory {
	
	IDifferenceOperatorFactory factory;
	
	public DifferenceOperatorBaseFactory() {
		// TODO Auto-generated constructor stub
	}

	public IDifferenceOperatorFactory getFactory() {
		return factory;
	}

	public void setFactory(IDifferenceOperatorFactory factory) {
		this.factory = factory;
	}
}
