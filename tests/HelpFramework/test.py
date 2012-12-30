import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.jcolin() + " help -exit > bin/output.txt")
