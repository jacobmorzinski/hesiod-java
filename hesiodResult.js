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
var hr = h.lookup("sipb","pcap");
System.out.print(hr + "\n");

System.out.print("-Test lookupFilsys(steini)-\n");
var hfr = h.lookupFilsys("steini");
System.out.print(hfr.getFilsys()+"\n");

System.out.print("-Test lookupFilsys(sipb)-\n");
var hfr = h.lookupFilsys("sipb");
// System.out.print(hfr + "\n");
System.out.print(hfr.getFilsys() + "\n");

System.out.print("-Test lookupFilsys(zacheiss)-\n");
var hfr = h.lookupFilsys("zacheiss");
// System.out.print(hfr + "\n");
System.out.print(hfr.getFilsys() + "\n");
System.out.print(hfr.getFilsys().get(0) + "\n");
System.out.print(hfr.getFilsys().get(0).get("mountpoint") + "\n");

System.out.print("-Test lookupFilsys(dev-sun4sys-94)-\n");
var hfr = h.lookupFilsys("dev-sun4sys-94");
System.out.print(hfr.getFilsys() + "\n");
System.out.print(hfr.getFilsys().get(0).get("locations") + "\n");

System.out.print("-Test lookupPasswd(jmorzins)-\n");
var hpr = h.lookupPasswd("jmorzins");
System.out.print(hpr.getPasswd() + "\n");
System.out.print(hpr.getPasswd().get("gecos") + "\n");

System.out.print("-Test lookupPobox(jmorzins)-\n");
var hpr = h.lookupPobox("jmorzins");
System.out.print(hpr.getPobox() + "\n");
System.out.print(hpr.getPobox().get("host") + "\n");

System.out.print("-Test lookupGroup(gaccounts)-\n");
var hgr = h.lookupGroup("gaccounts");
System.out.print(hgr.getGroup() + "\n");
System.out.print(hgr.getGroup().get("gid") + "\n");

System.out.print("-Test lookupCluster(...)-\n");
var hcr = h.lookupCluster("mark-the-great-print");
System.out.print(hcr.getCluster() + "\n");
var hcr = h.lookupCluster("early-linux");
System.out.print(hcr.getCluster() + "\n");
var hcr = h.lookupCluster("horobi.mit.edu");
System.out.print(hcr.getCluster() + "\n");
System.out.print(hcr.getCluster().get("syscontrol") + "\n");
System.out.print(hcr.getCluster().get("syscontrol").get(0) + "\n");

System.out.print("-Test lookupPcap(...)-\n");
var hpr = h.lookupPcap("sipb");
System.out.print(hpr.getPcap() + "\n");
var hpr = h.lookupPcap("mark-the-great");
System.out.print(hpr.getPcap() + "\n");
var hpr = h.lookupPcap("ashdown");
System.out.print(hpr.getPcap() + "\n");
System.out.print(hpr.getPcap().get("name") + "\n");

System.out.print("-Test lookupService(...)-\n");
var hsr = h.lookupService("moira_db");
System.out.print(hsr.getService() + "\n");
var hsr = h.lookupService("echo");
System.out.print(hsr.getService() + "\n");
System.out.print(hsr.getService().get(0).get("name") + "\n");
