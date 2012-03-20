package edu.mit.hesiod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;


/**
 * A class for looking up information from Hesiod.
 * 
 * <p>
 * Don't instantiate directly - instead use getInstance() to get
 * a reference to the singleton object.
 * 
 * <p>
 * <em>Usage:</em><br>
 * Hesiod h = Hesiod.getInstance();<br>
 * HesiodResult result = h.resolve("hesiodName", "hesiodType");<br>
 * String[] results = result.getResults();<br>
 * <br>
 * String[] results = Hesiod.hesinfo("hesiodName", "hesiodType");<br>
 * 
 * @author jmorzins
 */
public class Hesiod {


	HesiodContext hesiodContext = null;
	DirContext dnsContext = null;

	/**
	 * A registry of instances of the Hesiod objects.
	 */
	static private Map<String, Hesiod> _registry = new HashMap<String, Hesiod>();

	
	/**
	 * @see {@link #Hesiod(String)}
	 */
	protected Hesiod() throws HesiodException {
		this(null);
	}
	
	/**
	 * Don't instantiate directly - use getInstance().
	 * 
	 * <p>
	 * The initialization code will set <code>lhs</code> and <code>rhs</code>
	 * for the Hesiod search context, and set DNS as the search provider for the
	 * JNDI search context.
	 *
	 * @see {@link #getInstance()}
	 * @throws IOException
	 * @throws HesiodException
	 * @throws NamingException
	 */
	protected Hesiod(String rhs) throws HesiodException {

		hesiodContext = new HesiodContext(rhs);
		// Initialize DNS search context. Throws NamingException
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.dns.DnsContextFactory");
		try {
			dnsContext = new InitialDirContext(env);
		} catch (NamingException e) {
			throw new HesiodException(e);
		}
	}

	/**
	 * Get instance of Hesiod singleton.
	 * 
	 * <p>
	 * If the environment variable HESIOD_CONFIG exists, the initialization
	 * function will try to open that filename to read hesiod search context
	 * (<code>lhs</code>, <code>rhs</code>) from the file.
	 * 
	 * <p>
	 * If the environment variable HES_DOMAIN exists, it will override the
	 * <code>rhs</code> domain.  (Note that <code>rhs</code> can be set on
	 * a per-query basis by appending "@domain" to a query.)
	 */
	/*
	 * In rare situations (typically race conditions with threads) two Hesiod
	 * instances may be exist. This is OK, it wastes some memory to hold the
	 * object and CPU to initialize it, but doesn't hurt anything else.
	 */
	public static Hesiod getInstance(String rhs) throws HesiodException {
		Hesiod h;
		if (_registry.containsKey(rhs)) {
			h = _registry.get(rhs);
		} else {
			h = new Hesiod(rhs);
			_registry.put(rhs, h);
		}
		return h;
	}
	
	public static Hesiod getInstance() throws HesiodException {
		return getInstance(null);
	}

	/**
	 * Convenience method to perform a hesiod information lookup.
	 * 
	 * @param hesiodName
	 * @param hesiodType
	 * @return Array of result strings, or empty array.
	 * @throws HesiodException
	 * @throws NamingException
	 */
	static public String[] hesinfo(String hesiodName, String hesiodType)
			throws HesiodException, NamingException {
		Hesiod h;
		String[] results = new String[] {};
		try {
			h = getInstance();
			results = h.resolve(hesiodName, hesiodType).getResults();
		} catch (HesiodException e) {
			; // Ignore trouble reading config file
		}
		return results;
	}
	
	public HesiodPasswdResult lookupPasswd(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "passwd");
		HesiodPasswdResult hfr = new HesiodPasswdResult(hr);
		return hfr;
	}

	public HesiodFilsysResult lookupFilsys(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "filsys");
		HesiodFilsysResult hfr = new HesiodFilsysResult(hr);
		return hfr;
	}

	public HesiodPoboxResult lookupPobox(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "pobox");
		HesiodPoboxResult hpr = new HesiodPoboxResult(hr);
		return hpr;
	}

	public HesiodGroupResult lookupGID(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "gid");
		HesiodGroupResult hgr = new HesiodGroupResult(hr);
		return hgr;
	}

	public HesiodPasswdResult lookupUID(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "uid");
		HesiodPasswdResult hfr = new HesiodPasswdResult(hr);
		return hfr;
	}
	
	public HesiodResult lookupGrplist(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "grplist");
		HesiodGrplistResult hglr = new HesiodGrplistResult(hr);
		return hglr;
	}

	public HesiodSlocResult lookupSloc(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "sloc");
		HesiodSlocResult hsr = new HesiodSlocResult(hr);
		return hsr;
	}

	public HesiodClusterResult lookupCluster(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "cluster");
		HesiodClusterResult hcr = new HesiodClusterResult(hr);
		return hcr;
	}

	public HesiodGroupResult lookupGroup(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "group");
		HesiodGroupResult hgr = new HesiodGroupResult(hr);
		return hgr;
	}

	public HesiodPcapResult lookupPcap(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "pcap");
		HesiodPcapResult hpr = new HesiodPcapResult(hr);
		return hpr;
	}
	
	public HesiodServiceResult lookupService(String name)
			throws NamingException, HesiodException {
		HesiodResult hr = resolve(name, "service");
		HesiodServiceResult hsr = new HesiodServiceResult(hr);
		return hsr;
	}


	/**
	 * Alternate name for the resolve() method.
	 * 
	 * @see Hesiod#method(String hesiodName, String hesiodType)
	 * 
	 * @param hesiodName
	 * @param hesiodType
	 * @throws NamingException
	 */
	public HesiodResult lookup(String hesiodName, String hesiodType)
			throws NamingException {
		return resolve(hesiodName, hesiodType);
	}

	/**
	 * Look up Hesiod information.
	 * 
	 * <p>
	 * Returns an array of query results.
	 * 
	 * @param hesiodName
	 * @param hesiodType
	 * @throws NamingException
	 */
	public HesiodResult resolve(String hesiodName, String hesiodType)
			throws NamingException {
		String dnsName = toDNSName(hesiodName, hesiodType);
		String dnsType = "txt";
		ArrayList<String> resultList = new ArrayList<String>();

		// Get a DNS answer and unwrap its layers.
		// The DNS answer might include multiple types, of which we only want TXT.
		// The TXT records might have multiple entries.
		Attributes dnsAnswer = dnsContext.getAttributes(dnsName, new String[] { dnsType });
		NamingEnumeration<? extends Attribute> dnsAnswerTypes = dnsAnswer.getAll();
		while (dnsAnswerTypes.hasMore()) {
			Attribute thisType = (Attribute) dnsAnswerTypes.next();
			if (thisType.getID().equalsIgnoreCase(dnsType)) { // "TXT" records
				NamingEnumeration<?> records = thisType.getAll();
				while (records.hasMore()) {
					String record = (String) records.next();
					if (record.startsWith("\"") && record.endsWith("\"")) {
						record = record.substring(1, record.length() - 1);
					}
					resultList.add(record);
				}
			}
		}

		// String[0] just tells toArray() how to cast the result.
		// String[] result = resultList.toArray(new String[0]);
		return new HesiodResult(resultList);
	}

	/**
	 * Using hesiodName, hesiodType, and hesiodContext, construct a name
	 * suitable for a DNS query.
	 * 
	 * @param hesiodName
	 * @param hesiodType
	 * @throws NamingException
	 */
	String toDNSName(String hesiodName, String hesiodType)
			throws NamingException {
		StringBuffer dnsName = new StringBuffer(80);
		String rhs, lhs;
		int i = hesiodName.indexOf("@");
		if (i >= 0) {
			// If there is an "@", we are explicitly searching
			// a different rhs. Extract rhs and trim name.
			rhs = hesiodName.substring(i + 1);
			hesiodName = hesiodName.substring(0, i);
			if (!rhs.contains(".")) {
				String[] rhs_list = resolve(rhs, "rhs-extension").getResults();
				if (rhs_list.length > 0) {
					rhs = rhs_list[0];
				}
			}
		} else {
			// No "@" in name, search our own context's rhs.
			rhs = hesiodContext.getRhs();
		}
		lhs = hesiodContext.getLhs();

		dnsName.append(hesiodName);
		dnsName.append(".");
		dnsName.append(hesiodType);
		if (lhs != null) {
			if (!lhs.startsWith("."))
				dnsName.append(".");
			dnsName.append(lhs);
		}
		if (rhs != null) {
			if (!rhs.startsWith("."))
				dnsName.append(".");
			dnsName.append(rhs);
		}
		return dnsName.toString();
	}

	/**
	 * Driver for interactive use.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String hesiodName = "";
		String hesiodType = "all";

		String[] hesiodTypes = new String[] { "passwd", "filsys", "pobox",
				"gid", "uid", "grplist", "sloc", "cluster", "group", "pcap",
				"service" };

		if ((args.length == 0) || (args.length > 2)) {
			System.err.format("Usage:  java %s <name> [ <type> ]\n",
					Hesiod.class.getName());
			System.exit(1);
		}

		hesiodName = args[0];
		if (args.length > 1) {
			hesiodType = args[1];
		}

		Hesiod hesiodInstance = null;
		try {
			hesiodInstance = Hesiod.getInstance();
		} catch (HesiodException e) {
			System.err.println("Hesiod error during initialization.");
			System.err.println(e.getMessage());
			System.exit(1);
		}

		if (hesiodType.equalsIgnoreCase("all")) {
			for (String thisType : hesiodTypes) {
				try {
					HesiodResult hr = hesiodInstance.resolve(hesiodName, thisType);
					for (String s : hr.getResults()) {
						System.out.format("%10s: %s\n", thisType.toUpperCase(), s);
					}
				} catch (NameNotFoundException e) {
					;
				} catch (NamingException e) {
					System.err.println("Hesiod error: Miscellaneous lookup error.");
					System.err.println(e);
				}
			}
		} else {
			try {
				HesiodResult hr = hesiodInstance.resolve(hesiodName, hesiodType);
				for (String s : hr.getResults()) {
					System.out.println(s);
				}
			} catch (NameNotFoundException e) {
				System.err.println("Hesiod name not found.");
			} catch (NamingException e) {
				System.err.println("Hesiod error: Miscellaneous lookup error.");
				System.err.println(e);
			}
		}

		return;
	}

}