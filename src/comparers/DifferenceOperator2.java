package comparers;
import java.awt.Component;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import arch.ArchiveData;
import arch.ArchiveTransferer;
import core.Mp3Ident;
import core.Scanning;

public class DifferenceOperator2 implements IDifferenceOperator {

	private Collection<? extends Mp3Ident> toOperate;
	
	public DifferenceOperator2(Collection<? extends Mp3Ident> inpCollection) {
		toOperate = inpCollection;
	}
	
	public DifferenceOperator2() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void operateOnDifference() throws Exception {
		// TODO Auto-generated method stub
		long size = 0;
		if (!toOperate.isEmpty()) {
        int input = JOptionPane.showConfirmDialog(null, "Files are going to be deleted?");
        
        if (input == 2 || input == 1) {
        	throw new Exception("canceling");
        	}
		for (Mp3Ident runner: toOperate) {
			size += runner.getFileM().length();
			runner.getFileM().delete();
		}
		DecimalFormat df = new DecimalFormat("#####.#");
		System.out.println(df.format(size/Math.pow(1024, 2)) + " MB");
		} else {System.out.println("empty set");}
		
	}

	public void setSmthToOperate(Collection<? extends Mp3Ident> toOperate) {
		this.toOperate = toOperate;
	}
}
