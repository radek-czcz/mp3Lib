package viewer;

import entry.AppContext;

public class ViewerAssembler {
	ViewerWindow window;
	DataProviderAbstract provider;
	
	public void setWindow(ViewerWindow window) {
		this.window = window;
	}
	
	public void setProvider(DataProviderAbstract provider) {
		this.provider = provider;
	}
	
	public DataProviderAbstract getProvider() {
		return provider;
	}

	public void work() {
		
		window.createWindow();
		provider.setWindow(window);
		//provider.sendDataToModel("hubert czy¿owicz");
	}
}
