package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.List;

public class HesiodSlocResult extends HesiodResult {
	List<String> locations;
	
	public HesiodSlocResult() {
		locations = new ArrayList<String>();
	}
	
	public HesiodSlocResult(HesiodResult hr) {
		locations = new ArrayList<String>();
		for (String s : hr) {
			locations.add(s);
		}
	}
	
	public List<String> list() {
		return locations;
	}

	public String toString() {
		return locations.toString();
	}
}

