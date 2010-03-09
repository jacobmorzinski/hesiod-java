// jrunscript -classpath . hesinfo.js
// Rhino hesinfo.js

importPackage(Packages.java.lang);
importClass(Packages.edu.mit.hesiod.HesiodResult);

var hr = new HesiodResult();
System.out.print(hr+"\n");

var hr = new HesiodResult([]);
System.out.print(hr+"\n");

var hr = new HesiodResult(["a"]);
System.out.print(hr+"\n");

var hr = new HesiodResult(["a","b"]);
System.out.print(hr+"\n");

var hr = new HesiodResult(["'pine'","\"tar,feather"]);
System.out.print(hr+"\n");
