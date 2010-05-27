package edu.mit.hesiod;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodPasswdResult extends HesiodResult {
	Map<String,String> passwd = new LinkedHashMap<String,String>();
	
	public Map<String,String> getPasswd() {
		return passwd;
	}

	public void setPasswd(Map<String, String> arg) {
		this.passwd = arg;
	}

	public HesiodPasswdResult(String[] results) throws HesiodException {
		// Store bare results array, then parse.
		super(results);
		parsePasswd();
	}

	public HesiodPasswdResult(HesiodResult hr) throws HesiodException {
		// Store bare results array, then parse.
		super(hr.getResults());
		parsePasswd();
	}

	protected void parsePasswd() throws HesiodException {
		// Assume there is only one passwd.
		String s = this.getResults(0);

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
		
		this.passwd = map;
	}
}

