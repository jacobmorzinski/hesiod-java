package edu.mit.hesiod;

import java.util.Arrays;

public class HesiodResult {
	protected String[] results = {};

	public HesiodResult() {
	}
	
	public HesiodResult(String[] results) {
		this.results = results;
	}
	
	public String[] getResults() {
		return results;
	}

	public void setResults(String[] result) {
		this.results = result;
	}
	
	public String toString() {
		return Arrays.asList(results).toString();
	}
}
