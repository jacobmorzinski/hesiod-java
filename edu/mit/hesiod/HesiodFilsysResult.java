package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodFilsysResult extends HesiodResult {
	List<Map<String,String>> filsys = new ArrayList<Map<String,String>>();
	
	public List<Map<String,String>> getFilsys() {
		return filsys;
	}

	public void setFilsys(List<Map<String,String>> arg) {
		this.filsys = arg;
	}

	public HesiodFilsysResult(String[] results) throws HesiodException {
		// Store bare results array, then parse into a list of filsys's.
		super(results);
		parseFilsys();
	}

	public HesiodFilsysResult(HesiodResult hr) throws HesiodException {
		// Store bare results array, then parse into a list of filsys's.
		super(hr.getResults());
		parseFilsys();
	}

	protected void parseFilsys() throws HesiodException {
		// Iterate over the array of raw results,
		// mostly splitting on whitespace and using those words
        // as values in a map (whose keys depend on the FS type).
		
		// TODO Protect against bad input
		// (Can hit exceptions if iter.next() fails
        //  while parsing an abnormally short hesiod string).
		for (String s : this.results) {
			Map<String,String> map = new LinkedHashMap<String,String>();
			List<String> parts = Arrays.asList(s.split("\\s"));
			ListIterator<String> iter = parts.listIterator();
			String type = iter.next();
			map.put("type", type);
			// Modern hesiod DCM creates: NFS, RVD, AFS, ERR, MUL
			if (type.matches("^(NFS|RVD)$")) {
				if (iter.hasNext()) map.put("location",   iter.next());
				if (iter.hasNext()) map.put("server",     iter.next());
				if (iter.hasNext()) map.put("mode",       iter.next());
				if (iter.hasNext()) map.put("mountpoint", iter.next());
				if (iter.hasNext()) map.put("priority",   iter.next());
			} else if (type.matches("^(AFS|UFS|LOC)$") ) {
				if (iter.hasNext()) map.put("location",   iter.next());
				if (iter.hasNext()) map.put("mode",       iter.next());
				if (iter.hasNext()) map.put("mountpoint", iter.next());
				if (iter.hasNext()) map.put("priority",   iter.next());
			} else if (type.equals("ERR")) {
				// The words are a text message, should not have been split.
				// Instead of joining iterated parts, just re-split s with a limit.
				String message = (s.split("\\s",2))[1];
				map.put("message", message);
			} else if (type.equals("MUL")) {
				String locations = (s.split("\\s",2))[1];
				map.put("locations", locations);
			} else {
				throw new HesiodException(String.format("Unknown filsys type: %s", type));
			}
			filsys.add(map); // Accumulate this map in filesys list.
		}
		// Filesystems can have an optional priority.  Sort:
		Collections.sort(filsys, new HesiodFilsysSorter());
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

}

