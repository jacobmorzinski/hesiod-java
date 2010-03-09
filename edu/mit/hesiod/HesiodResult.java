package edu.mit.hesiod;

import java.util.ArrayList;
import java.util.Arrays;

public class HesiodResult {
	protected ArrayList<String> results = new ArrayList<String>();

	public HesiodResult() {
	}
	
	public HesiodResult(ArrayList<String> results) {
		this.results = results;
	}

	public HesiodResult(String[] results) {
		this.results = new ArrayList<String>(Arrays.asList(results));
	}
	
	public ArrayList<String> getResult() {
		return results;
	}

	public void setResult(ArrayList<String> result) {
		this.results = result;
	}
	
	public String toString() {
		return results.toString();
	}
}
