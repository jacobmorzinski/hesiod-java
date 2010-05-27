package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodServiceResult extends HesiodResult {
	List<Map<String,String>> service = new ArrayList<Map<String,String>>();
	
	public List<Map<String,String>> getService() {
		return service;
	}

	public void setService(List<Map<String, String>> arg) {
		this.service = arg;
	}

	public HesiodServiceResult(String[] results) throws HesiodException {
		// Store bare results array, then parse.
		super(results);
		parseService();
	}

	public HesiodServiceResult(HesiodResult hr) throws HesiodException {
		// Store bare results array, then parse.
		super(hr.getResults());
		parseService();
	}

	protected void parseService() throws HesiodException {

		for (String s : this.results) {
			Map<String,String> map = new LinkedHashMap<String,String>();
			List<String> parts = Arrays.asList(s.split("\\s",3));
			ListIterator<String> iter = parts.listIterator();
			if (iter.hasNext()) map.put("name",  iter.next());
			if (iter.hasNext()) map.put("proto", iter.next());
			if (iter.hasNext()) map.put("port",  iter.next());
			
			service.add(map); // Accumulate this map in service list.
		}
	}
}

