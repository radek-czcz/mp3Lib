package viewer;

import entry.AppContext;

public class tester {
	IViewerWindow window;
	AbstractDataProvider provider;
	
	public void setWindow(IViewerWindow window) {
		this.window = window;
	}
	public void setProvider(AbstractDataProvider provider) {
		this.provider = provider;
	}
	public void work() {
		
		window.createWindow();
		provider.setWindow(window);
		provider.sendDataToModel("hubert czy¿owicz");
		provider.sendDataToModel("bartek czy¿owicz");
		provider.sendDataToModel("radek czy¿owicz");
	}
	
	public static void main(String[] args) {
		tester tester1 = AppContext.getContext().getBean("TesterBean", tester.class);
		tester1.work();
	}
}
