package comparers;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import arch.ArchiveData;
import arch.ArchiveTransferer;
import core.Mp3Ident;
import core.Scanning;

public class DifferenceOperator1 implements IDifferenceOperator {

	private Collection<? extends Mp3Ident> toOperate;
	
	public DifferenceOperator1(Collection<? extends Mp3Ident> inpCollection) {
		toOperate = inpCollection;
	}
	
	public DifferenceOperator1() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void operateOnDifference() {
		// TODO Auto-generated method stub
		int size = 0;
		for (Mp3Ident runner: toOperate) {
			size += runner.getFileM().length();
		}
		DecimalFormat df = new DecimalFormat("#####.#");
		System.out.println(df.format(size/Math.pow(1024, 2)) + " MB");
		
	}

	public void setToOperate(Collection<? extends Mp3Ident> toOperate) {
		this.toOperate = toOperate;
	}
}
