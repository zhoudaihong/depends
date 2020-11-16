package depends.matrix.core;

import java.io.Serializable;

public class LocationInfo implements Serializable {
	String object;
	String file;
	Integer startLineNumber;
	Integer endLineNumber;
	public LocationInfo(String object, String file, Integer startLineNumber,Integer endLineNumber ){
		if (startLineNumber ==null) startLineNumber = 0;
		if (endLineNumber ==null) endLineNumber = 0;
		this.object = object;
		this.file = file;
		this.startLineNumber = startLineNumber;
		this.endLineNumber = endLineNumber;
	}

	public String getObject() {
		return object;
	}

	public String getFile() {
		return file;
	}

	public Integer getStartLineNumber() {
		return startLineNumber;
	}
	
	public Integer getEndLineNumber() {
		return endLineNumber;
	}

	@Override
	public String toString(){
		return object + "(" + file + ":"+ startLineNumber +")";
	}
}