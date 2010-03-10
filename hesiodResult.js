// jrunscript -classpath . hesinfo.js
// Rhino hesinfo.js

importPackage(Packages.java.lang);
importClass(Packages.edu.mit.hesiod.Hesiod);
importClass(Packages.edu.mit.hesiod.HesiodResult);

System.out.print("-Test an epxlicitly-created HesiodResult-\n");
var hr = new HesiodResult(["a","b"]);
System.out.print(hr + "\n");

System.out.print("-Test a generic hesiod lookup(sipb,pcap)-\n");
var h = Hesiod.getInstance();
var result = h.lookup("sipb","pcap");
System.out.print(result + "\n");

System.out.print("-Test filsysLookup(steini)-\n");
var hfr = h.filsysLookup("steini");
System.out.print(hfr.getFilsys()+"\n");

System.out.print("-Test filsysLookup(sipb)-\n");
var hfr = h.filsysLookup("sipb");
// System.out.print(hfr + "\n");
System.out.print(hfr.getFilsys() + "\n");

System.out.print("-Test filsysLookup(zacheiss)-\n");
var hfr = h.filsysLookup("zacheiss");
// System.out.print(hfr + "\n");
System.out.print(hfr.getFilsys() + "\n");

System.out.print("-Test filsysLookup(dev-sun4sys-94)-\n");
var hfr = h.filsysLookup("dev-sun4sys-94");
System.out.print(hfr.getFilsys() + "\n");
