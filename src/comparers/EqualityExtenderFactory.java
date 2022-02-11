package comparers;

public class EqualityExtenderFactory extends EqualityExtenderBaseFactory {

	@Override
	public EqualityExtenderAbs createExtender() {
		return (EqualityExtenderAbs)entry.AppContext.getContext().getBean("CompareExtensionMethodBean");
	}
}
