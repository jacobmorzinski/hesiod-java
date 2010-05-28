// jrunscript -classpath . hesinfo.js
// Rhino hesinfo.js

var out = Packages.java.lang.System.out;
var err = Packages.java.lang.System.err;
importClass(Packages.edu.mit.hesiod.Hesiod);

if (arguments.length != 2) {
    out.print("Usage: hesinfo <name> <type>\n");
    quit(1);
}

var h = Hesiod.getInstance();
try {
    var hr = h.resolve(arguments[0], arguments[1]);
    var iter = hr.iterator();
    while (iter.hasNext()) {
        out.print(iter.next()+"\n");
    }
} catch (e) {
    err.print("error:\n");
    err.print(e.message+"\n");
}
