import sys
import os
sys.path.append("../")
import testutils

os.environ['PYTHON_HOME'] = os.getcwd() + "/../../Implemantation/jcolin/jython" 
os.environ['PYTHON_VERBOSE'] = 'error' 

testutils.run(testutils.jcolin() + " source cmds.py arg1 arg2 arg3 -exit > bin/output.txt")

