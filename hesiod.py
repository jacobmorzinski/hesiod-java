#!/usr/bin/env jython

import sys

from javax.naming import NameNotFoundException
from edu.mit.hesiod import Hesiod
h = Hesiod.getInstance()

def lookup(hname=None, htype=None):
    """Perform a hesiod lookup for hname, htype"""
    return h.lookup(hname, htype)
    

def main(argv=None):
    if argv is None:
        argv = sys.argv
    hname = argv[1]
    htype = argv[2]
    try:
        print lookup(hname, htype)
    except NameNotFoundException:
        print sys.exc_info()[1]
    except:
        print sys.exc_info()[1]


if __name__ == '__main__':
    sys.exit(main())
