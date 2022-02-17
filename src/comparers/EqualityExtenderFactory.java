package comparers;

public class EqualityExtenderFactory {
	
	ConcreteFactory factory;

	public void setFactory(ConcreteFactory factory) {
		this.factory = factory;
	}

	public ConcreteFactory getFactory() {
		return factory;
	}
	
	public EqualityExtenderFactory() {
		// TODO Auto-generated constructor stub
	}
	
}
