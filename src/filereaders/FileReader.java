package filereaders;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import core.locsSets;

import core.startCl;
import entry.PathProvider;

public abstract class FileReader {
	
	PathProvider fPaths;
	File sourceFile;
	ArrayList<File> archives;
	ArrayList<String> resources;
	
	void prepareFiles() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config2.xml");
		fPaths = (PathProvider)applicationContext.getBean("paths");
	}
	
	void setSource(String path) {
		sourceFile = new File(path);
	}
	
	abstract void readFile();

}
