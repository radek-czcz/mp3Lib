package viewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import core.ISong;

public class TagsValuesCompiler1 implements TagsValuesCompiler {
	
	String[] values = new String[] {
		"radek", "bartek", "hubert"	
	};
	
	public TagsValuesCompiler1() {
	}

	@Override
	public JPanel compileTagsValues(Object inp) {
		
		JPanel returned;
		returned = new JPanel();
		returned.add(new JLabel(Arrays.toString(values)));
		return returned;
	}

}
