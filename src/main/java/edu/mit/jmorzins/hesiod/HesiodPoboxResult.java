package edu.mit.jmorzins.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodPoboxResult extends HesiodResult {
	List<Map<String,String>> poboxes;
	
	public HesiodPoboxResult() {
		poboxes = new ArrayList<Map<String,String>>();
	}

	public HesiodPoboxResult(HesiodResult hr) {
		poboxes = new ArrayList<Map<String,String>>();
		for (String s : hr) {
			poboxes.add(parse(s));
		}
	}
	
	public List<Map<String,String>> list() {
		return poboxes;
	}

	static Map<String, String> parse(String s) {

		// Split it into fields to store in a map.
		// Hesiod line has: 
		//  POP machine login
		Map<String,String> map = new LinkedHashMap<String,String>();
		List<String> parts = Arrays.asList(s.split("\\s"));
		ListIterator<String> iter = parts.listIterator();
		if (iter.hasNext()) map.put("type", iter.next());
		if (iter.hasNext()) map.put("host", iter.next());
		if (iter.hasNext()) map.put("name", iter.next());
		return map;
	}

	public String toString() {
		return poboxes.toString();
	}
}

