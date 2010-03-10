package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodFilsysResult extends HesiodResult {
	List<Map<String,String>> filsys = new ArrayList<Map<String,String>>();
	
	public List<Map<String,String>> getFilsys() {
		return filsys;
	}

	public void setFilsys(List<Map<String,String>> filsys) {
		this.filsys = filsys;
	}

	public HesiodFilsysResult(HesiodResult hr) throws HesiodException {
		super(hr.getResults());
		parseFilsys();
	}

	public HesiodFilsysResult(String[] results) throws HesiodException {
		super(results);
		parseFilsys();
	}

	protected void parseFilsys() throws HesiodException {
		// TODO Protect against bad hesiod strings
		// (Can hit exceptions if hesiod string is abnormally short).
		for (String s : this.results) {
			Map<String,String> map = new HashMap<String,String>();
			List<String> parts = Arrays.asList(s.split("\\s"));
			ListIterator<String> iter = parts.listIterator();
			String type = iter.next();
			map.put("type", type);
			// Types: NFS, RVD, AFS, ERR, MUL
			if (type.matches("^(AFS|UFS|LOC)$") ) {
				map.put("location",   iter.next());
				map.put("mode",       iter.next());
				map.put("mountpoint", iter.next());
				map.put("priority",   iter.hasNext() ? iter.next() : "0");
			} else if (type.matches("^(NFS|RVD)$")) {
				map.put("location",   iter.next());
				map.put("server",     iter.next());
				map.put("mode",       iter.next());
				map.put("mountpoint", iter.next());
				map.put("priority",   iter.hasNext() ? iter.next() : "0");
			} else if (type.equals("ERR")) {
				// Instead of joining iterated parts, just re-split s with a limit.
				String message = (s.split("\\s",2))[1];
				map.put("message", message);
			} else if (type.equals("MUL")) {
				String locations = (s.split("\\s",2))[1];
				map.put("locations", locations);
			} else {
				throw new HesiodException(String.format("Unknown filsys type: %s", type));
			}
			filsys.add(map); // Accumulate map in filesys list.
		}
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
