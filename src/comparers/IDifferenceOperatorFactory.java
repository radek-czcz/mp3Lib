package comparers;

import java.util.Collection;

import core.Mp3Ident;

public interface IDifferenceOperatorFactory {
	IDifferenceOperator createOperator(Collection<Mp3Ident> inp);
}
