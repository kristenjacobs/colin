import sys
import os
sys.path.append("../")
import testutils

testutils.run("ant > bin/build.txt 2>&1")
testutils.run("java -jar test.jar testBoolean 12 -exit > bin/output.txt")

