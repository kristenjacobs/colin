import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.jcolin() + " source test.txt > bin/ignored.txt")

