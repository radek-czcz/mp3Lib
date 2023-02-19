package viewer;

public abstract class AbstractDataProvider {
	
	protected IViewerWindow window;

	abstract protected void sendDataToModel(Object song);

	public AbstractDataProvider() {
	}

	public void setWindow(IViewerWindow window) {
		this.window = window;
	}
	
}
