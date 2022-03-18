package core;

import java.io.File;

public interface ISong {

	String toString();

	File getFileM();

	String getTagGen();

	String getTagArt();

	String getTagAlb();

	String getTagTit();

	String getTagTra();

	String getTagYea();

	String getTagCom();

	int getTagLen();

}