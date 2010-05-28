package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodPcapResult implements Iterable<Map<String,Object>> {
	List<Map<String,Object>> pcaps;
	
	public HesiodPcapResult() {
		pcaps = new ArrayList<Map<String,Object>>();
	}

	public HesiodPcapResult(HesiodResult hr) throws HesiodException {
		pcaps = new ArrayList<Map<String,Object>>();
		for (String s : hr) {
		pcaps.add(parse(s));
		}
	}

	static Map<String,Object> parse(String s) throws HesiodException {

		// Split it into fields to store in a map.
		// Hesiod line has: 
		//  name:option:option:option:option...
		
//      From printcap(5) man page:
//	       Keywords  can  be 1 to an indefinite number of characters long, and are
//	       case sensitive.  Values for  keywords  can  be  strings  (:st=string:),
//	       signed    integer    values    using    the    C   language   notation,
//	       (:nu#12:max#-2:mask#0x1EF:), or flags (:flag: to set to 1,  :flag@:  to
//	       clear  to  0).
		
		
		Map<String, Object> map = new LinkedHashMap<String,Object>();
		List<String> parts = Arrays.asList(s.split(":"));
		ListIterator<String> iter = parts.listIterator();
		String[] t;
		String part;

		if (iter.hasNext()) map.put("name", iter.next());

		while (iter.hasNext()) {
			part = iter.next();
			if (part.contains("=")) {
				// String value
				t = part.split("=", 2);
				map.put(t[0], t[1]);
			} else if (part.contains("#")) {
				// Integer value
				t = part.split("#", 2);
				try {
					map.put(t[0], Integer.parseInt(t[1]));
				} catch (NumberFormatException e) {
					throw new HesiodException("Error parsing pcap part: " + part, e);
				}
			} else if (part.contains("@")) {
				// Boolean flag: false
				t = part.split("@");
				map.put(t[0], false);
			} else {
				// Boolean flag: true
				map.put(part, true);
			}
		}
		
		return map;
	}

	public Iterator<Map<String, Object>> iterator() {
		return pcaps.iterator();
	}
	
	public String toString() {
		return pcaps.toString();
	}
}

