import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.jcolin() + " version history -exit > bin/output.txt")

