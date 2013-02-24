import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.refdb() + " testcase list Start.* -exit > bin/output.txt")

