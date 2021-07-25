package entry;

public class ButtonNameProvider implements IButtonNameProvider{
	
	String[] buttonsNames;

	@Override
	public String[] provideButtons() {
		return buttonsNames;
	}

	public String[] getButtonsNames() {
		return buttonsNames;
	}

	public void setButtonsNames(String[] buttonsNames) {
		this.buttonsNames = buttonsNames;
	}
	
}
