import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.jcolin() + " source cmds.py arg1 arg2 arg3 -exit > bin/output.txt")

