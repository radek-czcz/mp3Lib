package comparers;

import java.util.ArrayList;
import java.util.Collection;
import core.Mp3Ident;

public class DifferenceOperator1 implements IDifferenceOperator {

	private Collection<? extends Mp3Ident> toOperate;
	
	public DifferenceOperator1(Collection<? extends Mp3Ident> inpCollection) {
		toOperate = inpCollection;
	}
	
	@Override
	public ArrayList<Mp3Ident> operateOnDifference() {
		// TODO Auto-generated method stub
		return null;
	}
}
