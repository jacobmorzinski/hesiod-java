package edu.mit.jmorzins.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodGroupResult extends HesiodResult {
	List<Map<String,String>> groups;
	
	public HesiodGroupResult() {
		groups = new ArrayList<Map<String,String>>();
	}

	public HesiodGroupResult(HesiodResult hr) {
		groups = new ArrayList<Map<String,String>>();
		for (String s : hr) {
			groups.add(parse(s));
		}
	}
	
	public List<Map<String,String>> list() {
		return groups;
	}

	static Map<String, String> parse(String s) {

		// Split it into fields to store in a map.
		// Hesiod line has: 
		//  name:*:gid:
		// These are traditionally named:
		//  name:passwd:gid:mem
		Map<String,String> map = new LinkedHashMap<String,String>();
		List<String> parts = Arrays.asList(s.split(":"));
		ListIterator<String> iter = parts.listIterator();
		if (iter.hasNext()) map.put("name",   iter.next());
		if (iter.hasNext()) map.put("passwd", iter.next());
		if (iter.hasNext()) map.put("gid",    iter.next());
		if (iter.hasNext()) map.put("mem",    iter.next());
		return map;
	}

	public String toString() {
		return groups.toString();
	}
}

