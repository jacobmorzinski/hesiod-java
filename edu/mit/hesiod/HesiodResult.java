package edu.mit.hesiod;

import java.util.Arrays;

/**
 * Represents the response from a Hesiod lookup.
 * Has a javabean-style member field, results, with getters and setters.
 * 
 * <p>
 * <em>Usage:</em><br>
 * Hesiod h = Hesiod.getInstance();<br>
 * HesiodResult result = h.resolve("hesiodName", "hesiodType");<br>
 * String[] results = result.getResults();
 * 
 * @author jmorzins
 *
 */
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
	
	public HesiodResult() {
	}
	
	public HesiodResult(String[] results) {
		this.results = results;
	}
	
	public String toString() {
		return Arrays.asList(results).toString();
	}
}
