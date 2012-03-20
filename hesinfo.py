#!/usr/bin/env jython

import sys
from edu.mit.hesiod import Hesiod
from javax.naming import NameNotFoundException

def main(argv=None):
    if argv is None:
        argv = sys.argv[1:]
    if (len(argv)==0 or len(argv) > 2):
        print >>sys.stderr, 'usage: hesinfo <name> [ <type> ]'
        return 1

    types = [ "passwd", "filsys", "pobox", "gid", "uid",
              "grplist", "sloc", "cluster", "group", "pcap",
              "service" ]
    type = "all"
    h = Hesiod.getInstance()
    name = argv[0]

    if (len(argv) == 2):
        type = argv[1]
    if (type != "all"):
        try:
            ans = h.lookup(argv[0],argv[1])
            for s in ans:
                print(s)
        except:
            print sys.exc_info()[1]
    else:
        for t in types:
            try:
                ans = h.lookup(argv[0],t)
                for s in ans:
                    print "%10s:" % t.upper(),s
            except NameNotFoundException:
                pass
            except:
                print sys.exc_info()[1]


    

if __name__ == '__main__':
    sys.exit(main())
