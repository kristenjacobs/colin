import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.refdb() + " help -exit > bin/output.txt")

