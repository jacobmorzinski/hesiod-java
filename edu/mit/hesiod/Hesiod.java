package edu.mit.hesiod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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
 * String[] results = h.resolve("hesiodName", "hesiodType");<br>
 * 
 * @author jmorzins
 */
public class Hesiod {

	static String default_lhs = ".ns";
	static String default_rhs = ".athena.mit.edu";

	static String hesiod_conf_filename = "hesiod.conf";
	static String hesiod_conf_dir = "/usr/local/etc";
//	static String hesiod_conf_dir = "/etc/athena";
//	static String hesiod_conf_dir = "/etc";
//	static String hesiod_conf_dir = "/Users/jmorzins/Documents/workspace/Hesinfo";
//	static String hesiod_conf_dir = "/afs/athena.mit.edu/user/j/m/jmorzins/workspace/Hesinfo";

	HesiodContext hesiodContext = null;
	DirContext dnsContext = null;

	/**
	 * A registry of instances of the Hesiod objects.
	 */
	static private HashMap<String, Hesiod> _registry = new HashMap<String, Hesiod>();

	
	/**
	 * @see Hesiod#Hesiod(String)
	 */
	protected Hesiod() throws HesiodException, IOException, NamingException {
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
	 * @see Hesiod#getInstance()
	 * @throws IOException
	 * @throws HesiodException
	 * @throws NamingException
	 */
	protected Hesiod(String rhs) throws HesiodException, IOException, NamingException {

		// Initialize hesiod search context. Throws HesiodException, IOException
		final File configFile;
		String configName = System.getenv("HESIOD_CONFIG");
		if (configName != null) {
			configFile = new File(configName);
		} else {
			configFile = new File(hesiod_conf_dir, hesiod_conf_filename);
		}
		// TODO wrap in a java.security.PrivelegedAction
		// AccessController.doPrivileged(PrivelegedAction( run() {readConfigFile();} ))
		hesiodContext = readConfigFile(configFile);
		String hes_domain = System.getenv("HES_DOMAIN");
		if (hes_domain != null) {
			hesiodContext.rhs = hes_domain;
		}

		// Initialize DNS search context. Throws NamingException
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.dns.DnsContextFactory");
		dnsContext = new InitialDirContext(env);

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
	public static Hesiod getInstance(String rhs)
			throws HesiodException, IOException, NamingException {
		Hesiod h;
		if (_registry.containsKey(rhs)) {
			h = _registry.get(rhs);
		} else {
			h = new Hesiod(rhs);
			_registry.put(rhs, h);
		}
		return h;
	}
	
	public static Hesiod getInstance()
			throws HesiodException, IOException, NamingException {
		return getInstance(null);
	}

	/**
	 * Try to read <code>rhs</code> and <code>lhs</code> from a config file.
	 * Throws <code>HesiodException</code> if the file does not specify <code>rhs</code>.
	 * 
	 * @param configFile
	 * @throws HesiodException
	 * @throws IOException
	 */
	private HesiodContext readConfigFile(File configFile)
			throws HesiodException, IOException {
		HesiodContext result = new HesiodContext();

		try {
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(configFile)));
			while ((line = br.readLine()) != null) {
				if ((line.length() != 0) && (line.charAt(0) != '#')) {
					String[] fields = line.split("=");
					if (fields.length == 2) {
						String key = fields[0].trim();
						String value = fields[1].trim();
						if (key.equalsIgnoreCase("lhs")) {
							result.lhs = value;
						} else if (key.equalsIgnoreCase("rhs")) {
							result.rhs = value;
						}
					}
				}
			}
			if (result.rhs == null) {
				throw new HesiodException("Invalid Hesiod configuration file.");
			}
		} catch (FileNotFoundException e) {
			// use internal defaults
			result.lhs = default_lhs;
			result.rhs = default_rhs;
		}
		return result;
	}
	
	static public String[] doLookup(String hesiodName, String hesiodType)
			throws NamingException {
		Hesiod h;
		try {
			h = getInstance();
			return h.resolve(hesiodName, hesiodType);
		} catch (IOException e) {
			; // Ignore trouble reading config file
		} catch (HesiodException e) {
			; // Ignore trouble reading config file
		}
		return new String[] {};
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
	public String[] lookup(String hesiodName, String hesiodType)
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
	public String[] resolve(String hesiodName, String hesiodType)
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
		String[] result = resultList.toArray(new String[0]);
		return result;
	}

	/**
	 * Using hesiodName, hesiodType, and hesiodContext, construct a name
	 * suitable for a DNS query.
	 * 
	 * @param hesiodName
	 * @param hesiodType
	 * @throws NamingException
	 */
	public String toDNSName(String hesiodName, String hesiodType)
			throws NamingException {
		StringBuffer dnsName = new StringBuffer(80);
		String rhs, lhs;
		int i = hesiodName.indexOf("@");
		if (i >= 0) {
			// If there is an "@", we are explicitly searching
			// a different domain's rhs. Extract rhs and trim name.
			rhs = hesiodName.substring(i + 1);
			hesiodName = hesiodName.substring(0, i);
			if (!rhs.contains(".")) {
				String[] rhs_list = resolve(rhs, "rhs-extension");
				if (rhs_list.length > 0) {
					rhs = rhs_list[0];
				}
			}
		} else {
			// No "@" in name, search our own domain's rhs.
			rhs = hesiodContext.rhs;
		}
		lhs = hesiodContext.lhs;

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
			System.err.println("Hesiod error: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Hesiod error: Miscellaneous error reading configuration file.");
			System.err.println(e);
		} catch (NamingException e) {
			System.err.println("Hesiod error: Could not prepare for DNS lookups.");
			System.err.println(e);
		}

		if (hesiodType.equalsIgnoreCase("all")) {
			for (String thisType : hesiodTypes) {
				try {
					String[] result = hesiodInstance.resolve(hesiodName, thisType);
					for (String s : result) {
						System.out.format("%10s: %s\n", thisType.toUpperCase(), s);
					}
				} catch (NameNotFoundException e) {
					;
				} catch (NamingException e) {
					System.err.println("Hesiod error: Miscellaneous DNS error.");
					System.err.println(e);
				}
			}
		} else {
			try {
				String[] result = hesiodInstance.resolve(hesiodName, hesiodType);
				for (String s : result) {
					System.out.println(s);
				}
			} catch (NameNotFoundException e) {
				System.err.println("Hesiod error: Hesiod name not found.");
			} catch (NamingException e) {
				System.err.println("Hesiod error: Miscellaneous DNS error.");
				System.err.println(e);
			}
		}

		return;
	}

}

@SuppressWarnings("serial")
class HesiodException extends Exception {
	HesiodException(String message) {
		super(message);
	}
	HesiodException() {
		super();
	}
}

class HesiodContext {
	String lhs = null;
	String rhs = null;
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		if (lhs != null) {
			if (!lhs.startsWith("."))
				sb.append(".");
			sb.append(lhs);
		}
		if (rhs != null) {
			if (!rhs.startsWith("."))
				sb.append(".");
			sb.append(rhs);
		}
		return sb.toString();
	}
}
