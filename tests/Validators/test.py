import sys
import os
sys.path.append("../")
import testutils

testutils.run("ant > bin/build.txt 2>&1")

testutils.run("echo \"===== Boolean tests =====\" >> bin/output.txt")
testutils.run("java -jar test.jar testBoolean true -exit >> bin/output.txt")
testutils.run("java -jar test.jar testBoolean false -exit >> bin/output.txt")
testutils.run("java -jar test.jar testBoolean invalid -exit >> bin/output.txt")

