package edu.mit.hesiod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.AccessControlException;

/**
 * <p>A HesiodContext contains information about which namespace
 * hesiod searches should be resolved against.
 * 
 * <p>For example, a HesiodContext might contain 
 * lhs=".ns"
 * rhs=".athena.example.edu"
 * 
 * <p>A Hesiod query for a particular name and type will be
 * converted to a DNS query for TXT records for name.type.lhs.rhs.
 * Searching for name="hesiod", type="sloc"
 * in a context with lhs=".ns", rhs=".athena.example.edu"
 * would do a DNS query for "hesiod.sloc.ns.athena.example.edu".
 *
 * <p>The rhs can be overridden on a per-query basis by using 
 * an "@" symbol to specify a particular rhs.
 * Searching for name="hesiod@example.com", type="sloc" would
 * set this query's rhs to "example.com" and do a DNS query for
 * "hesiod.sloc.ns.example.com".
 * 
 * @author jmorzins
 * 
 */
public class HesiodContext {
	private static final String DEFAULT_LHS = ".ns";
	private static final String DEFAULT_RHS = ".athena.mit.edu";
	private static final String hesiod_conf_filename = "hesiod.conf";
	private static final String hesiod_conf_dir = "/usr/local/etc";
	
	private String lhs = null;
	private String rhs = null;

	public String getLhs() {
		return lhs;
	}

	public void setLhs(String lhs) {
		this.lhs = lhs;
	}

	public String getRhs() {
		return rhs;
	}

	public void setRhs(String rhs) {
		this.rhs = rhs;
	}

	/**
	 * Initialize hesiod search context. Throws HesiodException, IOException
	 *  
	 * @param rhs - the RHS of this Hesiod search context.
	 * @throws HesiodException if the configuration file is invalid.
	 * @throws IOException if there was a problem reading the file.
	 */
	public HesiodContext(String rhs) throws HesiodException	{

		File configFile = null;
        String configName = null;
		String hesDomain = null;

		// A web Servlet container like Tomcat can block System.getenv(),
		// and we must catch AccessControlException
		// It's not clear if a java.security.AccessController would help.
		try {
			configName = System.getenv("HESIOD_CONFIG");
			hesDomain = System.getenv("HES_DOMAIN");
		} catch (AccessControlException e) {
			// ignore
		}

		if (configName != null) {
			configFile = new File(configName);
		} else {
			configFile = new File(hesiod_conf_dir, hesiod_conf_filename);
		}
		
		// Read hesiod config file for system rhs + lhs,
		// then possibly over-ride it.
		this.readConfigFile(configFile);
		if (hesDomain != null) {
			this.rhs = hesDomain;
		}
		if (rhs != null) {
			this.rhs = rhs;
		}
	}
	
	/**
	 * Initialize hesiod search context.
	 * 
	 * @see HesiodContext(String rhs)
	 */
	public HesiodContext() throws HesiodException {
		this(null);
	}

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
	
	/**
	 * Try to read {@code rhs} and {@code lhs} from a config file.
	 * Sets the context's {@code rhs} and {@code lhs} based on the config file contents.
	 * 
	 * Throws {@code HesiodException} if the file does not specify {@code rhs}.
	 * 
	 * @param configFile
	 * @throws HesiodException
	 */
	private void readConfigFile(File configFile)
			throws HesiodException {
		boolean isFileBad = false;
		try {
			String line, key, value;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(configFile)));
			while ((line = br.readLine()) != null) {
				if ((line.length() != 0) && (line.charAt(0) != '#')) {
					String[] fields = line.split("=");
					if (fields.length == 2) {
						key = fields[0].trim();
						value = fields[1].trim();
						if (key.equalsIgnoreCase("lhs")) {
							this.lhs = value;
						} else if (key.equalsIgnoreCase("rhs")) {
							this.rhs = value;
						}
					}
				}
			}
			// If we succeeded in reading the file but the file did not set RHS,
			// that is an error.  Prepare to throw exception.
			if (this.rhs == null || this.rhs.length() == 0) {
				isFileBad = true;
			}
		} catch (IOException e) {
			// FileNotFoundException or generic IOException
			// are both OK, we fall back to default values.
			this.lhs = DEFAULT_LHS;
			this.rhs = DEFAULT_RHS;
		} catch (AccessControlException e) {
			// A web Servlet container like Tomcat can block new FileInputStream(),
			// and we must catch AccessControlException
			// It's not clear if a java.security.AccessController would help.
			this.lhs = DEFAULT_LHS;
			this.rhs = DEFAULT_RHS;
		}

		if (isFileBad) {
			String message = 
				String.format("Invalid hesiod config file (%s) did not set rhs.",
					configFile);
			throw new HesiodException(message);
		}
	}
}
