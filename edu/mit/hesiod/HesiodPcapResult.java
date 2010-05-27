package edu.mit.hesiod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodPcapResult extends HesiodResult {
	Map<String,Object> pcap = new HashMap<String,Object>();
	
	public Map<String,Object> getPcap() {
		return pcap;
	}

	public void setPcap(Map<String, Object> arg) {
		this.pcap = arg;
	}

	public HesiodPcapResult(String[] results) throws HesiodException {
		// Store bare results array, then parse.
		super(results);
		parsePcap();
	}

	public HesiodPcapResult(HesiodResult hr) throws HesiodException {
		// Store bare results array, then parse.
		super(hr.getResults());
		parsePcap();
	}

	protected void parsePcap() throws HesiodException {
		// Assume there is only one pcap.
		String s = this.getResults(0);

		// Split it into fields to store in a map.
		// Hesiod line has: 
		//  name:option:option:option:option...
		

//	       Keywords  can  be 1 to an indefinite number of characters long, and are
//	       case sensitive.  Values for  keywords  can  be  strings  (:st=string:),
//	       signed    integer    values    using    the    C   language   notation,
//	       (:nu#12:max#-2:mask#0x1EF:), or flags (:flag: to set to 1,  :flag@:  to
//	       clear  to  0).
		
		
		Map<String, Object> map = new HashMap<String,Object>();
		List<String> parts = Arrays.asList(s.split(":"));
		ListIterator<String> iter = parts.listIterator();
		String[] t;
		String part;

		map.put("name",   iter.next());
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
		
		this.pcap = map;
	}
}

