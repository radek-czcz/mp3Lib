package core;
import java.io.File;

public interface IArchiveDataSource {
	String getArchiveName();
	File getDirectoryToScan();
}
