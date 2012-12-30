import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.jcolin() + " echo \"Hello There\" -exit > bin/output.txt")

