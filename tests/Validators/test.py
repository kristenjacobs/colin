import sys
import os
sys.path.append("../")
import testutils

testutils.run("ant > bin/build.txt 2>&1")

command = "java -DSchemaFile=../../jcolin/colin.rng -jar bin/test.jar "

testutils.run("echo \"===== Boolean tests =====\" >> bin/output.txt")
testutils.run(command + "testBoolean true -exit >> bin/output.txt")
testutils.run(command + "testBoolean false -exit >> bin/output.txt")
testutils.run(command + "testBoolean invalid -exit >> bin/output.txt")

testutils.run("echo \"===== Integer tests =====\" >> bin/output.txt")
testutils.run(command + "testInteger 10 -exit >> bin/output.txt")
testutils.run(command + "testInteger 11 -exit >> bin/output.txt")
testutils.run(command + "testInteger 29 -exit >> bin/output.txt")
testutils.run(command + "testInteger 30 -exit >> bin/output.txt")

