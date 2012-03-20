package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodGrplistResult extends HesiodResult {
	List<List<Map<String,String>>> groups;
	
	public HesiodGrplistResult() {
		groups = new ArrayList<List<Map<String,String>>>();
	}
	
	public HesiodGrplistResult(HesiodResult hr) {
		groups = new ArrayList<List<Map<String,String>>>();
		for (String s : hr) {
			groups.add(parse(s));
		}
	}
	
	public List<List<Map<String,String>>> list() {
		return groups;
	}

	static List<Map<String, String>> parse(String s) {

		// Split it into fields to store in a map.
		// Input is name:gid:name:gid:name:gid:name:gid with a possible trailing ":"
		List<Map<String,String>> grplist = new ArrayList<Map<String,String>>();
		List<String> parts = Arrays.asList(s.split(":"));
		ListIterator<String> iter = parts.listIterator();
		while (iter.hasNext()) {
			String name = iter.next();
			if (iter.hasNext()) {
				String gid = iter.next();
				Map<String,String> map = new LinkedHashMap<String, String>();
				map.put("name", name);
				map.put("gid", gid);
				grplist.add(map);
			}
		}
		return grplist;
	}

	public String toString() {
		return groups.toString();
	}
}

