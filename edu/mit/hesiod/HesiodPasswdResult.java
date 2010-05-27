package edu.mit.hesiod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class HesiodPasswdResult extends HesiodResult {
	Map<String,String> passwd = new HashMap<String,String>();
	
	public Map<String,String> getPasswd() {
		return passwd;
	}

	public void setPasswd(Map<String, String> arg) {
		this.passwd = arg;
	}

	public HesiodPasswdResult(String[] results) throws HesiodException {
		// Store bare results array, then parse into passwd info.
		super(results);
		parsePasswd();
	}

	public HesiodPasswdResult(HesiodResult hr) throws HesiodException {
		// Store bare results array, then parse into passwd info.
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
		Map<String,String> map = new HashMap<String,String>();
		List<String> parts = Arrays.asList(s.split(":"));
		ListIterator<String> iter = parts.listIterator();
		map.put("name",   iter.next());
		map.put("passwd", iter.next());
		map.put("uid",    iter.next());
		map.put("gid",    iter.next());
		map.put("gecos",  iter.next());
		map.put("dir",    iter.next());
		map.put("shell",  iter.next());
		
		this.passwd = map;
	}
}

