// jrunscript -classpath . hesinfo.js
// Rhino hesinfo.js

var out = Packages.java.lang.System.out;
var err = Packages.java.lang.System.err;
importClass(Packages.edu.mit.hesiod.Hesiod);

var hesiodTypes = [ "passwd", "filsys", "pobox", "gid", "uid", "grplist",
		"sloc", "cluster", "group", "pcap", "service" ];
var hesiodType = "all";
var hesiodName;

if ((arguments.length == 0) || (arguments.length > 2)) {
    out.print("Usage: hesinfo <name> [ <type> ]\n");
    quit(1);
}

hesiodName = arguments[0];
if (arguments.length == 2) {
	hesiodType = arguments[1];
}

var h = Hesiod.getInstance();

if (hesiodType == "all") {
	for (var i in hesiodTypes) {
		try {
			var hr = h.resolve(hesiodName, hesiodTypes[i]);
			var iter = hr.iterator();
			while (iter.hasNext()) {
				var s = "", t = hesiodTypes[i].toUpperCase();
				for ( var i = t.length; i < 10; i++) {
					s += " ";
				}
				out.print(s + t + ": ");
				out.print(iter.next() + "\n");
			}
		} catch (e) {
			if ((typeof(e) == "object") && ("javaException" in e)) {
				e = e.javaException;
				if (e.getClass().getName() == "javax.naming.NameNotFoundException") {
					;
				} else {
					err.println("Hesiod error: Miscellaneous lookup error.");
					err.println("" + e);
				}
			} else {
				err.print(e+"\n");
			}
		}
	}
} else {
	try {
		var hr = h.resolve(hesiodName, hesiodType);
		var iter = hr.iterator();
		while (iter.hasNext()) {
			out.print(iter.next() + "\n");
		}
	} catch (e) {
		if ((typeof(e) == "object") && ("javaException" in e)) {
			e = e.javaException;
			if (e.getClass().getName() == "javax.naming.NameNotFoundException") {
				err.println("Hesiod name not found.");
			} else {
				err.println("Hesiod error: Miscellaneous lookup error.");
				err.println("" + e);
			}
		} else {
			err.println("" + e);
		}
	}
}
