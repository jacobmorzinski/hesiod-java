package edu.mit.hesiod;

import java.util.Arrays;

public class HesiodResult {
	String[] results = {};

	public String getResults(int index) {
		return this.results[index];
	}
	
	public String[] getResults() {
		return this.results;
	}

	public void setResults(int index, String result) {
		this.results[index] = result;
	}

	public void setResults(String[] result) {
		this.results = result;
	}
	
	public HesiodResult(String[] results) {
		this.results = results;
	}
	
	public String toString() {
		return Arrays.asList(results).toString();
	}
}
