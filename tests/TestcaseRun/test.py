import sys
import os
sys.path.append("../")
import testutils

testutils.setupRefDb()
testutils.run(testutils.refdb() + " testcase run -exit > bin/output.txt")

