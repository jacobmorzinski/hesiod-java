// jrunscript -classpath . hesinfo.js
// Rhino hesinfo.js

var out = Packages.java.lang.System.out;
var err = Packages.java.lang.System.err;
importClass(Packages.edu.mit.hesiod.Hesiod);
// importClass(Packages.edu.mit.hesiod.HesiodResult);
// importClass(Packages.edu.mit.hesiod.HesiodFilsysResult);

// out.print("-Test an epxlicitly-created HesiodResult-\n");
// var hr = new HesiodResult(["a","b"]);
// out.print(hr + "\n");

out.print("-Test a generic hesiod lookup(sipb,filsys)-\n");
var h = Hesiod.getInstance();
var hr = h.lookup("sipb","filsys");
out.print(hr + "\n");
for (var hri=hr.iterator(); hri.hasNext(); ) {
    out.print(hri.next() + "\n");
}

out.print("-Test lookupFilsys(steini)-\n");
var hfr = h.lookupFilsys("steini");
out.print(hfr + "\n");

out.print("-Test lookupFilsys(sipb)-\n");
var hfr = h.lookupFilsys("sipb");
out.print(hfr + "\n");

out.print("-Test lookupFilsys(zacheiss)-\n");
var hfr = h.lookupFilsys("zacheiss");
var iter = hfr.iterator();
if (iter.hasNext()) {
	var r = iter.next();
	out.print(r.get("mountpoint") + "\n");
	out.print(r+"\n");
}
while (iter.hasNext()) {
	out.print(iter.next() + "\n");
}

out.print("-Test lookupFilsys(dev-sun4sys-94)-\n");
var hfr = h.lookupFilsys("dev-sun4sys-94");
out.print(hfr + "\n");
out.print(hfr.iterator().next().get("locations") + "\n");

out.print("-Test lookupPasswd(jmorzins)-\n");
var hpr = h.lookupPasswd("jmorzins").iterator().next();
out.print(hpr + "\n");
out.print(hpr.get("gecos") + "\n");

out.print("-Test lookupPobox(jmorzins)-\n");
var hpr = h.lookupPobox("jmorzins").iterator().next();
out.print(hpr + "\n");
out.print(hpr.get("host") + "\n");

out.print("-Test lookupGroup(gaccounts)-\n");
var hgr = h.lookupGroup("gaccounts").iterator().next();
out.print(hgr + "\n");
out.print(hgr.get("gid") + "\n");

out.print("-Test lookupCluster(...)-\n");
var hcr = h.lookupCluster("mark-the-great-print");
out.print(hcr + "\n");
var hcr = h.lookupCluster("early-linux");
out.print(hcr + "\n");
var hcr = h.lookupCluster("horobi.mit.edu");
for (var hcri = hcr.iterator(); hcri.hasNext(); ) {
    var r = hcri.next();
    out.print(r + "\n");
}
for (var hcri = hcr.iterator(); hcri.hasNext(); ) {
    var r = hcri.next();
    if (r.containsKey("syscontrol")) {
	out.print(r.get("syscontrol") + "\n");
    }
}

out.print("-Test lookupPcap(...)-\n");
var hpr = h.lookupPcap("sipb");
out.print(hpr + "\n");
var hpr = h.lookupPcap("mark-the-great");
out.print(hpr + "\n");
var hpr = h.lookupPcap("ashdown").iterator().next();
out.print(hpr + "\n");
out.print(hpr.get("name") + "\n");

quit();

out.print("-Test lookupService(...)-\n");
var hsr = h.lookupService("moira_db");
out.print(hsr.getService() + "\n");
var hsr = h.lookupService("echo");
out.print(hsr.getService() + "\n");
out.print(hsr.getService().get(0).get("name") + "\n");
