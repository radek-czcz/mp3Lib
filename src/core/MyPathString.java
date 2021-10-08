package core;

public class MyPathString implements Comparable {
	
	private String path;
	
	MyPathString(String inp){
		this.path = inp;
	}
	
	public String getPath() {
		return path;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		MyPathString objM = (MyPathString)obj;
		
		if (objM.path.startsWith(this.path)) {
			return true;
		} else
			return false;
	}
	
	@Override
	public String toString() {
	// TODO Auto-generated method stub
	return this.path;
	}

	@Override
	public int compareTo(Object o) {
	// TODO Auto-generated method stub
	return 0;
	}

}
