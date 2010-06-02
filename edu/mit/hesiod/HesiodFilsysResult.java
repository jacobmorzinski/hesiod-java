package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HesiodFilsysResult extends HesiodResult {
	List<Map<String,String>> filsys;
	
	public HesiodFilsysResult() {
		filsys = new ArrayList<Map<String,String>>();
	}
	
	public HesiodFilsysResult(HesiodResult hr) throws HesiodException {
		filsys = new ArrayList<Map<String,String>>();
		for (String s : hr) {
			filsys.add(parse(s));
		}
		// Filesystems can have an optional priority.  Sort:
		Collections.sort(filsys, new HesiodFilsysSorter());
	}
	
	public List<Map<String,String>> parse() {
		return filsys;
	}

	Map<String,String> parse(String s) throws HesiodException {
		// Split on whitespace and using those words as values
        // in a map (whose keys depend on the FS type).
		
		Map<String,String> map = new LinkedHashMap<String,String>();
		String[] temp = s.split("\\s",2);

		String type = temp[0];
		map.put("type", type);

		String rest = temp[1];
		List<String> parts = Arrays.asList(rest.split("\\s"));
		Iterator<String> iter = parts.iterator();

		// Modern hesiod DCM creates: NFS, RVD, AFS, ERR, MUL
		if (type.matches("^(AFS|UFS|LOC)$") ) {
			if (iter.hasNext()) map.put("location",   iter.next());
			if (iter.hasNext()) map.put("mode",       iter.next());
			if (iter.hasNext()) map.put("mountpoint", iter.next());
			if (iter.hasNext()) map.put("priority",   iter.next());
		} else if (type.matches("^(NFS|RVD)$")) {
			if (iter.hasNext()) map.put("location",   iter.next());
			if (iter.hasNext()) map.put("server",     iter.next());
			if (iter.hasNext()) map.put("mode",       iter.next());
			if (iter.hasNext()) map.put("mountpoint", iter.next());
			if (iter.hasNext()) map.put("priority",   iter.next());
		} else if (type.equals("ERR")) {
			map.put("message", rest);
		} else if (type.equals("MUL")) {
			map.put("locations", rest);
		} else {
			throw new HesiodException(String.format("Unknown filsys type: %s", type));
		}
		return map;
	}
	
	private class HesiodFilsysSorter implements Comparator<Map<String,String>> {
		public int compare(Map<String,String> o1, Map<String,String> o2) {
			int p1=0, p2=0;
			if (o1.containsKey("priority") && o2.containsKey("priority")) {
				try {
					p1 = Integer.parseInt(o1.get("priority"));
					p2 = Integer.parseInt(o2.get("priority"));
				} catch (NumberFormatException nfe) {
					// ignore
				}
			}
			if (p1 < p2) return -1;
			if (p1 > p2) return 1;
			return 0;
		}
	}

	public String toString() {
		return filsys.toString();
	}
}

