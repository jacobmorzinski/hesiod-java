package edu.mit.jmorzins.hesiod;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HesiodClusterResult extends HesiodResult {
	List<Map<String,String>> clusters;
	
	public HesiodClusterResult() {
		clusters = new ArrayList<Map<String,String>>();
	}

	public HesiodClusterResult(HesiodResult hr) {
		clusters = new ArrayList<Map<String,String>>();
		for (String s : hr) {
			clusters.add(parse(s));
		}
	}
	
	public List<Map<String,String>> list() {
		return clusters;
	}

	protected Map<String,String> parse(String s) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		String[] parts = s.split("\\s", 2);
		map.put(parts[0], parts[1]);
		return map;
	}

	public String toString() {
		return clusters.toString();
	}
}

