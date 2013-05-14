import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.refdb() + " search title \"Hello\" -exit > bin/output.txt")

