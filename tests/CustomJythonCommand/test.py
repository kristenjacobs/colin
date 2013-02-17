import sys
import os
sys.path.append("../")
import testutils

testutils.run("ant > bin/build.txt 2>&1")
testutils.setup()
testutils.run("java -DSchemaFile=../../jcolin/colin.rng -jar bin/test.jar repeatsayhello 10 -exit >> bin/output.txt")

