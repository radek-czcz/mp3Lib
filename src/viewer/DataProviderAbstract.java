package viewer;

public abstract class DataProviderAbstract {
	
	protected ViewerWindow window;

	abstract public void sendDataToModel(Object song);

	public DataProviderAbstract() {
	}

	public void setWindow(ViewerWindow window) {
		this.window = window;
	}
	
}
