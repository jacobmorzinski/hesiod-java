#!/usr/bin/env jython

import sys
from edu.mit.hesiod import Hesiod
from javax.naming import NameNotFoundException
h = Hesiod.getInstance()

try:
    print("-Test lookup()-");
    print(h.lookup("jmorzins","passwd"))
    print(h.lookup("sipb","filsys"))
    
    print("-Test lookupFilsys(zacheiss)-");
    ans = h.lookupFilsys("zacheiss")
    for rec in ans.list():
        print(rec)
    
    print("-Test lookupFilsys(dev-sun4sys-94)-");
    ans = h.lookupFilsys("dev-sun4sys-94")
    print(ans.list())
    print(ans.list().get(0))
    print(ans.list().get(0).get("locations"))
except NameNotFoundException:
    print sys.exc_info()[1]
