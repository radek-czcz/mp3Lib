package comparers;

import java.util.ArrayList;
import java.util.Collection;

import core.Mp3Ident;

public interface IDifferenceOperator {
	void operateOnDifference();
	public void setSmthToOperate(Collection<? extends Mp3Ident> toOperate);
}
