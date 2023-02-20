package viewer;

import entry.AppContext;

public class tester {
	ViewerWindow window;
	DataProviderAbstract provider;
	
	public void setWindow(ViewerWindow window) {
		this.window = window;
	}
	
	public void setProvider(DataProviderAbstract provider) {
		this.provider = provider;
	}
	
	public void work() {
		
		window.createWindow();
		provider.setWindow(window);
		provider.sendDataToModel("hubert czy�owicz");
		provider.sendDataToModel("bartek czy�owicz");
		provider.sendDataToModel("radek czy�owicz");
	}
	
	public static void main(String[] args) {
		tester tester1 = AppContext.getContext().getBean("TesterBean", tester.class);
		tester1.work();
	}
}
