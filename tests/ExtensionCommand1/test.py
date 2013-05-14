import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.refdb() + " search author \"Fred\" -exit > bin/output.txt")

