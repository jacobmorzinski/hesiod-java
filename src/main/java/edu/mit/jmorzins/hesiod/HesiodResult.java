package edu.mit.jmorzins.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
public class HesiodResult implements Iterable<String> {
	List<String> results;

	/* Constructors */
	public HesiodResult() {
		results = new ArrayList<String>();
	}
	
	public HesiodResult(String[] result) {
		results = new ArrayList<String>();
		results.addAll(Arrays.asList(result));
	}
	
	public HesiodResult(List<String> result) {
		results = new ArrayList<String>();
		results.addAll(result);
	}

	/* Java bean indexed property methods:
	 * 	
	 * Methods to access individual values
     *  public PropertyElement getPropertyName(int index)
	 *  public void setPropertyName(int index, PropertyElement element)
	 * Methods to access the entire indexed property array
     *  public PropertyElement[] getPropertyName()
	 *  public void setPropertyName(PropertyElement element[])
	 *
	 */
	public String getResults(int index) {
		return results.get(index);
	}
	
	public String[] getResults() {
		return results.toArray(new String[0]);
	}

	public void setResults(int index, String result) {
		results.set(index, result);
	}

	public void setResults(String[] result) {
		results = new ArrayList<String>();
		results.addAll(Arrays.asList(result));
	}
		
	/* Implement the Iterable interface */
	public Iterator<String> iterator() {
		return results.iterator();
	}

	public String toString() {
		return results.toString();
	}
}
