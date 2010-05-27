package edu.mit.hesiod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodGroupResult extends HesiodResult {
	Map<String,String> group = new HashMap<String,String>();
	
	public Map<String,String> getGroup() {
		return group;
	}

	public void setGroup(Map<String, String> arg) {
		this.group = arg;
	}

	public HesiodGroupResult(String[] results) throws HesiodException {
		// Store bare results array, then parse into group info.
		super(results);
		parseGroup();
	}

	public HesiodGroupResult(HesiodResult hr) throws HesiodException {
		// Store bare results array, then parse into group info.
		super(hr.getResults());
		parseGroup();
	}

	protected void parseGroup() throws HesiodException {
		// Assume there is only one group.
		String s = this.getResults(0);

		// Split it into fields to store in a map.
		// Hesiod line has: 
		//  name:*:gid:
		// These are traditionally named:
		//  name:passwd:gid:mem
		Map<String,String> map = new HashMap<String,String>();
		List<String> parts = Arrays.asList(s.split(":"));
		ListIterator<String> iter = parts.listIterator();
		map.put("name",   iter.next());
		map.put("passwd", iter.next());
		map.put("gid",    iter.next());
		map.put("mem",    iter.hasNext() ? iter.next() : "");
		
		this.group = map;
	}
}

