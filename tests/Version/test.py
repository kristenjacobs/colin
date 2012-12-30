import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.refdb() + " version -exit > bin/output.txt")

