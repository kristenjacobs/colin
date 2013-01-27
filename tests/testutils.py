import os

VERBOSE = False

def jcolin():
    return "java -jar " + os.environ['PRJROOT'] + "/jcolin/jars/jcolin.jar"

def refdb():
    return "java -DSchemaFile=" + os.environ['PRJROOT'] + "/jcolin/colin.rng -DConfigFile=" + os.environ['PRJROOT'] + "/refdb/config.xml -jar " + os.environ['PRJROOT'] + "/refdb/jars/refdb.jar"

def run(command):
    if VERBOSE:
        print command

    os.system(command)

