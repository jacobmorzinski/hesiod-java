package edu.mit.jmorzins.hesiod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodPasswdResult extends HesiodResult {
	List<Map<String,String>> passwds;
	
	public HesiodPasswdResult() {
		passwds = new ArrayList<Map<String,String>>();
	}

	public HesiodPasswdResult(HesiodResult hr) {
		passwds = new ArrayList<Map<String,String>>();
		for (String s : hr) {
			passwds.add(parse(s));
		}
	}
	
	public List<Map<String,String>> list() {
		return passwds;
	}

	static Map<String,String> parse(String s) {

		// Split it into fields to store in a map.
		// Hesiod line has: 
		//  login:*:uid:101:fullname,nn,oa,op,hp:homedir:shell
		// These are traditionally named:
		//  name:passwd:uid:gid:gecos:dir:shell
		Map<String,String> map = new LinkedHashMap<String,String>();
		List<String> parts = Arrays.asList(s.split(":"));
		ListIterator<String> iter = parts.listIterator();

		if (iter.hasNext()) map.put("name",   iter.next());
		if (iter.hasNext()) map.put("passwd", iter.next());
		if (iter.hasNext()) map.put("uid",    iter.next());
		if (iter.hasNext()) map.put("gid",    iter.next());
		if (iter.hasNext()) map.put("gecos",  iter.next());
		if (iter.hasNext()) map.put("dir",    iter.next());
		if (iter.hasNext()) map.put("shell",  iter.next());
		
		return map;
	}

	public String toString() {
		return passwds.toString();
	}
}

