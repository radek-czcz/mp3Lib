package mmm;

import arch.ArchiveData.LocAndNameWindow;
import junit.framework.TestCase;

public class TestLocAndNameWindow extends TestCase {
	
	void testFirst(){
		LocAndNameWindow window = new LocAndNameWindow();
		window.first();
		assertEquals(true, window.toString().isEmpty());
	}

}
