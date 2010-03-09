package edu.mit.hesiod;

import java.util.HashMap;

public class HesiodFilsysResult extends HesiodResult {
	HashMap<String,String>[] filsys = null;
	boolean multiRecords = false;
	
	public void parseRecords() {
		super.parseRecords();
		multiRecords = (this.results.length > 1);
		for (String s : this.results) {
			int priority = 0;
			if (multiRecords) {
				
			}
		}
	}
}
