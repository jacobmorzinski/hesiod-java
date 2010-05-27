package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HesiodClusterResult extends HesiodResult {
	List<List<String>> cluster = new ArrayList<List<String>>();
	
	public List<List<String>> getCluster() {
		return cluster;
	}

	public void setPasswd(List<List<String>> arg) {
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
			List<String> parts = Arrays.asList(s.split("\\s"));
			cluster.add(parts);
		}
	}
}

