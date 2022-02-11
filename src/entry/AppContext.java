package entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {
	
	static private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config2.xml");
	
	static public ApplicationContext getContext() {
		return applicationContext;
	}
}
