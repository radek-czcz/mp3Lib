package viewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import core.ISong;

public class TagsValuesCompiler1 implements TagsValuesCompiler {
	
	String[] values = new String[] {
		"radek", "bartek", "hubert"	
	};
	
	public TagsValuesCompiler1() {
	}

	@Override
	public List<String> compileTagsValues() {
		
		List<String> returned;
		returned = (List<String>)Arrays.asList(values);
		return returned;
	}

}
