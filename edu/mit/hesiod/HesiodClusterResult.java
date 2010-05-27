package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HesiodClusterResult extends HesiodResult {
	Map<String,List<String>> cluster = new HashMap<String,List<String>>();
	
	public Map<String, List<String>> getCluster() {
		return cluster;
	}

	public void setCluster(Map<String, List<String>> arg) {
		this.cluster = arg;
	}

	public HesiodClusterResult(String[] results) throws HesiodException {
		// Store bare results array, then parse.
		super(results);
		parseCluster();
	}

	public HesiodClusterResult(HesiodResult hr) throws HesiodException {
		// Store bare results array, then parse.
		super(hr.getResults());
		parseCluster();
	}

	protected void parseCluster() throws HesiodException {
		for (String s : this.results) {
			String[] parts = s.split("\\s", 2);
			if (cluster.containsKey(parts[0])) {
				List<String> l = cluster.get(parts[0]);
				l.add(parts[1]);
			} else {
				List<String> l = new ArrayList<String>();
				cluster.put(parts[0], l);
				l.add(parts[1]);
			}
		}
	}
}

