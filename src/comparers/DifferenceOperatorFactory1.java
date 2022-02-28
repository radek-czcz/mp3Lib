package comparers;

import java.util.Collection;

import core.Mp3Ident;

public class DifferenceOperatorFactory1 implements IDifferenceOperatorFactory {
	
	Collection<Mp3Ident> toOperate;

	@Override
	public DifferenceOperator1 createOperator(Collection<Mp3Ident> inp) {
		// TODO Auto-generated method stub
		return new DifferenceOperator1(inp);
	}}
