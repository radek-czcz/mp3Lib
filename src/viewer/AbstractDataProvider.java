package viewer;

public abstract class AbstractDataProvider {
	
	IViewerWindow window;

	abstract void sendDataToModel(Object song);

	public AbstractDataProvider() {
	}

	public void setWindow(IViewerWindow window) {
		this.window = window;
	}
	
}
