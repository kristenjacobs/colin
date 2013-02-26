#!/usr/bin/python

import os
import shutil
import difflib

def testPassed():
    output = open("bin/output.txt")
    expected = open("expected.txt")
    val = output.read() == expected.read()
    output.close()
    expected.close()
    return val

def traverseTestDirs(action):
    for dir in sorted(os.listdir(".")):
        if os.path.isdir(dir):
            if os.path.exists(dir + "/test.py"):
                os.chdir(dir)
                action(dir)
                os.chdir("../")

def clean(dir):
    shutil.rmtree("bin", ignore_errors=True)
     
def run(dir):
    print "%-25s" % dir,
    os.mkdir("bin")
    os.system("python test.py")
    if testPassed():
        print "... PASSED"
    else:    
        print "***** FAILED *****"

traverseTestDirs(clean)
traverseTestDirs(run)

