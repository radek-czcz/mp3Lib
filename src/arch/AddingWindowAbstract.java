package arch;

import java.awt.Component;
import java.util.ArrayList;

public abstract class AddingWindowAbstract {
	
	protected ArrayList<Component> components;
	
	/**
	 * sets and shows window to step adding
	 */
	abstract void prepareAndShow();

	/**
	 * sets components to be added to adding-window 
	 * @param cmps
	 */
	public void setComponents(ArrayList<Component> cmps) {
		this.components = cmps;
	}
}
