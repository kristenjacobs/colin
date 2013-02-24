import os

VERBOSE = False

def setup():
    os.environ['PYTHON_HOME'] = os.getcwd() + "/../../jcolin/jython" 
    os.environ['PYTHON_VERBOSE'] = 'error' 

def setupRefDb():
    setup()
    os.environ['JCOLIN_EXT_PATH']  = os.getcwd() + "/../../refdb/extensions" 
    os.environ['JCOLIN_TEST_PATH'] = os.getcwd() + "/../../refdb/tests" 

def setupJColin():
    setup()

def jcolin():
    setupJColin()
    return "java -jar " + os.environ['PRJROOT'] + "/jcolin/jars/jcolin.jar"

def refdb():
    setupRefDb()
    return "java -DSchemaFile=" + os.environ['PRJROOT'] + "/jcolin/colin.rng -DConfigFile=" + os.environ['PRJROOT'] + "/refdb/config.xml -jar " + os.environ['PRJROOT'] + "/refdb/jars/refdb.jar"

def run(command):
    if VERBOSE:
        print command

    os.system(command)

