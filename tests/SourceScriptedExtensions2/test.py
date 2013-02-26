import sys
import os
sys.path.append("../")
import testutils

testutils.run(testutils.refdb() + " source ../../refdb/tests/SearchTitleTest.py -exit > bin/output.txt")

