package package1;

public interface mp3Comparable {
	static public enum equalityCategory {sameOnCD, sameOnBase, sameOnMyComp}
	equalityCategory compareMp3(mp3Ident compInp);
}
