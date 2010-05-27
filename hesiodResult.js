// jrunscript -classpath . hesinfo.js
// Rhino hesinfo.js

importPackage(Packages.java.lang);
importClass(Packages.edu.mit.hesiod.Hesiod);
importClass(Packages.edu.mit.hesiod.HesiodResult);
// importClass(Packages.edu.mit.hesiod.HesiodFilsysResult);

System.out.print("-Test an epxlicitly-created HesiodResult-\n");
var hr = new HesiodResult(["a","b"]);
System.out.print(hr + "\n");

System.out.print("-Test a generic hesiod lookup(sipb,pcap)-\n");
var h = Hesiod.getInstance();
var hr = h.resolve("sipb","pcap");
System.out.print(hr + "\n");

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
System.out.print(hfr.getFilsys().get(0) + "\n");
System.out.print(hfr.getFilsys().get(0).get("mountpoint") + "\n");

System.out.print("-Test filsysLookup(dev-sun4sys-94)-\n");
var hfr = h.filsysLookup("dev-sun4sys-94");
System.out.print(hfr.getFilsys() + "\n");
System.out.print(hfr.getFilsys().get(0).get("locations") + "\n");

System.out.print("-Test passwdLookup(jmorzins)-\n");
var hpr = h.passwdLookup("jmorzins");
System.out.print(hpr.getPasswd() + "\n");
System.out.print(hpr.getPasswd().get("gecos") + "\n");

System.out.print("-Test poboxLookup(jmorzins)-\n");
var hpr = h.poboxLookup("jmorzins");
System.out.print(hpr.getPobox() + "\n");
System.out.print(hpr.getPobox().get("host") + "\n");

System.out.print("-Test groupLookup(gaccounts)-\n");
var hgr = h.groupLookup("gaccounts");
System.out.print(hgr.getGroup() + "\n");
System.out.print(hgr.getGroup().get("gid") + "\n");

System.out.print("-Test clusterLookup(...)-\n");
var hcr = h.clusterLookup("mark-the-great-print");
System.out.print(hcr.getCluster() + "\n");
var hcr = h.clusterLookup("early-linux");
System.out.print(hcr.getCluster() + "\n");
var hcr = h.clusterLookup("horobi.mit.edu");
System.out.print(hcr.getCluster() + "\n");
System.out.print(hcr.getCluster().get(0) + "\n");
System.out.print(hcr.getCluster().get(0).get(0) + "\n");

System.out.print("-Test pcapLookup(...)-\n");
var hpr = h.pcapLookup("mark-the-great");
System.out.print(hpr.getPcap() + "\n");
var hpr = h.pcapLookup("ashdown");
System.out.print(hpr.getPcap() + "\n");
System.out.print(hpr.getPcap().get("name") + "\n");

System.out.print("-Test serviceLookup(...)-\n");
var hsr = h.serviceLookup("moira_db");
System.out.print(hsr.getService() + "\n");
var hsr = h.serviceLookup("echo");
System.out.print(hsr.getService() + "\n");
System.out.print(hsr.getService().get(0).get("name") + "\n");
