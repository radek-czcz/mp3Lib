package comparers;

public class EqualityExtenderBaseFactory {
	
	IEqualityExtenderFactory factory;

	public void setFactory(IEqualityExtenderFactory factory) {
		this.factory = factory;
	}

	public IEqualityExtenderFactory getFactory() {
		return factory;
	}
	
	public EqualityExtenderBaseFactory() {
		// TODO Auto-generated constructor stub
	}
	
}
