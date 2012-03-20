// jrunscript -classpath . hesinfo.js
// Rhino hesinfo.js

var out = Packages.java.lang.System.out;
var err = Packages.java.lang.System.err;
importClass(Packages.edu.mit.hesiod.Hesiod);

if (arguments.length != 1) {
    out.print("Usage: hesinfo <service>\n");
    quit(1);
}

var h = Hesiod.getInstance();
try {
    var hsr = h.lookupService(arguments[0]);
    out.print(hsr + "\n");
    var service = hsr.list();
    out.print(service + "\n");
    var service0 = hsr.list().get(0);
    out.print(service0 + "\n");
    var proto = hsr.list().get(0).get("proto");
    var port  = hsr.list().get(0).get("port");
    out.print(proto + " " + port + "\n");
} catch (e) {
    err.print("error:\n");
    err.print(e.message+"\n");
}
