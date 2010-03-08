// jrunscript -classpath . hesinfo.js
// Rhino hesinfo.js

importPackage(Packages.java.lang);
importClass(Packages.edu.mit.hesiod.Hesiod);

if (arguments.length != 2) {
    System.out.print("Usage: hesinfo <name> <type>\n");
    System.exit(1);
}

var h = Hesiod.getInstance();
try {
    var results = h.lookup(arguments[0], arguments[1]);
    for (i in results) {
        System.out.print(results[i]+"\n");
    }
} catch (err) {
    System.err.print("got err\n");
    System.err.print(err.message+"\n");
}
