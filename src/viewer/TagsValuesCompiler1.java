package viewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import core.ISong;

public class TagsValuesCompiler1 implements TagsValuesCompiler {
	
	String[] values = new String[] {
		"radek", "bartek", "hubert"	
	};

	@Override
	public Collection<String> compileTagsValues() {
		
		ArrayList<String> returned;
		returned = (ArrayList<String>)Arrays.asList(values);
		return returned;
	}

}
