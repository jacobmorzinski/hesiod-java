package edu.mit.hesiod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodPoboxResult extends HesiodResult {
	Map<String,String> pobox = new HashMap<String,String>();
	
	public Map<String,String> getPobox() {
		return pobox;
	}

	public void setPobox(Map<String, String> arg) {
		this.pobox = arg;
	}

	public HesiodPoboxResult(String[] results) throws HesiodException {
		// Store bare results array, then parse into pobox info.
		super(results);
		parsePobox();
	}

	public HesiodPoboxResult(HesiodResult hr) throws HesiodException {
		// Store bare results array, then parse into pobox info.
		super(hr.getResults());
		parsePobox();
	}

	protected void parsePobox() throws HesiodException {
		// Assume there is only one pobox.
		String s = this.getResults(0);

		// Split it into fields to store in a map.
		// Hesiod line has: 
		//  POP machine login
		Map<String,String> map = new HashMap<String,String>();
		List<String> parts = Arrays.asList(s.split("\\s"));
		ListIterator<String> iter = parts.listIterator();
		map.put("type", iter.next()); // WARNING: obsolete/vestigial
		map.put("host", iter.next());
		map.put("name", iter.next());
		
		this.pobox = map;
	}
}

