package edu.mit.jmorzins.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HesiodServiceResult extends HesiodResult {
	List<Map<String,String>> services;
	
	public HesiodServiceResult() {
		services = new ArrayList<Map<String,String>>();
	}
	
	public HesiodServiceResult(HesiodResult hr) {
		 services = new ArrayList<Map<String,String>>();
		 for (String s : hr) {
			 services.add(parse(s));
		 }
	}
	
	public List<Map<String,String>> list() {
		return services;
	}

	static Map<String,String> parse(String s) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		List<String> parts = Arrays.asList(s.split("\\s",3));
		Iterator<String> iter = parts.iterator();
		if (iter.hasNext()) map.put("name",  iter.next());
		if (iter.hasNext()) map.put("proto", iter.next());
		if (iter.hasNext()) map.put("port",  iter.next());
		return map;
	}

	public String toString() {
		return services.toString();
	}
}

