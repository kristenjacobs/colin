import sys
import os
sys.path.append("../")
import testutils

testutils.run("ant > bin/build.txt 2>&1")

command = "java -DSchemaFile=../../jcolin/colin.rng -jar bin/test.jar "

testutils.run("echo \"===== Boolean tests =====\" >> bin/output.txt")
testutils.run(command + "testBooleanPass -exit >> bin/output.txt")
testutils.run(command + "testBooleanFail -exit >> bin/output.txt")

testutils.run("echo \"===== Integer tests =====\" >> bin/output.txt")
testutils.run(command + "testIntegerPass -exit >> bin/output.txt")
testutils.run(command + "testIntegerFail1 -exit >> bin/output.txt")
testutils.run(command + "testIntegerFail2 -exit >> bin/output.txt")

testutils.run("echo \"===== Double tests =====\" >> bin/output.txt")
testutils.run(command + "testDoublePass -exit >> bin/output.txt")
testutils.run(command + "testDoubleFail1 -exit >> bin/output.txt")
testutils.run(command + "testDoubleFail2 -exit >> bin/output.txt")

testutils.run("echo \"===== String tests =====\" >> bin/output.txt")
testutils.run(command + "testStringPass -exit >> bin/output.txt")
testutils.run(command + "testStringFail1 -exit >> bin/output.txt")
testutils.run(command + "testStringFail2 -exit >> bin/output.txt")

testutils.run("echo \"===== File tests =====\" >> bin/output.txt")
testutils.run(command + "testFilePass -exit >> bin/output.txt")
testutils.run(command + "testFileFail1 -exit >> bin/output.txt")
testutils.run(command + "testFileFail2 -exit >> bin/output.txt")
testutils.run(command + "testFileFail3 -exit >> bin/output.txt")

