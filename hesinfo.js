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
    var response = h.resolve(arguments[0], arguments[1]);
    var results = response.getResults();
    for (i in results) {
        System.out.print(results[i]+"\n");
    }
} catch (err) {
    System.err.print("error:\n");
    System.err.print(err.message+"\n");
}
