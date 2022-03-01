package comparers;

import java.io.File;
import java.util.Collection;

import core.Mp3Ident;

public interface IEqualityExtenderFactory {
	public EqualityExtenderAbs createExtender(File inp);
}