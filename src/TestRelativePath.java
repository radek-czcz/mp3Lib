import java.io.File;
import java.io.IOException;

public class TestRelativePath {

	public static void main(String[] args) {
		
		String filePath = new String("app//abc.txt");
		File newFile = new File(filePath);
		
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdir();
		}
			
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(newFile.getAbsolutePath());

	}

}
