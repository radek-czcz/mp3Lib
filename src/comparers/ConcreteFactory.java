package comparers;

import java.io.File;

public interface ConcreteFactory {
	public EqualityExtenderAbs createExtender(File inp);
}
