import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.refdb() + " testcase list -exit > bin/output.txt")

