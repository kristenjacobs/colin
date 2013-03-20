import sys
import xml.etree.ElementTree as xml

def getTypeString(typeElmt):
    typeString = typeElmt.tag
    if len(typeElmt.items()) > 0:
        typeString += "("
        first = True
        for (name, value) in typeElmt.items():
            if not first:
                typeString += ", "
            typeString += name + "=\"" + value + "\"" 
            first = False
        typeString += ")"
    return typeString

def handleTypes(typeElmts, arg, mode):
    arg.setMode(mode)
    for child in list(typeElmts.getchildren()):
        arg.addType(getTypeString(child))

def handleCommand(commandElmt, parent, level):
    command = Command( 
        commandElmt.get("name"),
        commandElmt.find("description").text, level)
    parent.addEntry(command)
    argsElmt = commandElmt.find("args")
    if argsElmt != None:
        for argElmt in argsElmt.findall("arg"):
            arg = Arg(argElmt.get("name"))
            command.addArg(arg)
            typeString = ""
            child = argElmt.getchildren()[0]
            if child.tag == "Or":
                handleTypes(child, arg, "or")
            elif child.tag == "And":
                handleTypes(child, arg, "and")
            else:
                arg.setMode("single")
                arg.addType(getTypeString(child))
    outputElmt = commandElmt.find("output")
    if outputElmt != None:
        if len(outputElmt.getchildren()) > 0:
            # Found an explict output element with a type specified.
            command.setRes(Output(getTypeString(outputElmt.getchildren()[0])))
        else: 
            # Found an explict output element with no type specified, default to String.
            command.setRes(Output("String")) 
    else:
        # No output element found, default to String.
        command.setRes(Output("String")) 

def handleContainer(containerElmt, parent, level):
    container = Container( 
        containerElmt.get("name"),
        containerElmt.find("description").text)
    parent.addEntry(container)
    handleCommands(containerElmt, container, level + 1)

def handleTestcase(testcaseElmt, parent):
    testcase = Testcase(
        testcaseElmt.get("name"),
        testcaseElmt.get("description"))
    parent.addEntry(testcase)
    for child in list(testcaseElmt.getchildren()):
        if child.tag == "runCommand":
            entry = ""
            var = child.get("var")            
            if var != None:
                entry += var + "="
            entry += "`" + child.get("name") + "`" 
            testcase.addEntry(entry)

        elif child.tag == "runScript":           
            entry = ""
            var = child.get("var")            
            if var != None:
                entry += var + "="
            entry += "execute(" + child.get("name") + ")"
            testcase.addEntry(entry)

        elif child.tag == "assertEquals":
            var = child.get("var")  
            value = child.find("value").text          
            if value == None:
                value = ""
            testcase.addEntry("assert(" + var + "=\"" + value + "\")")

        elif child.tag == "assertRegex":
            var = child.get("var")            
            value = child.find("value").text   
            if value == None:
                value = ""
            testcase.addEntry("assert(" + var + "~\"" + value + "\")")

        else: 
            print "Error: Unknown element: " + child.tag
            sys.exit(1)

def handleCommands(commandsElmt, container, level):
    for child in list(commandsElmt.getchildren()):
        if child.tag == "command":           
            handleCommand(child, container, level)

        elif child.tag == "container":
            handleContainer(child, container, level)

        elif child.tag == "description":
            # Nothing to do here, as we have already handled the container descriptions
            pass

        else: 
            print "Error: Unknown element: " + child.tag
            sys.exit(1)

def handleTestcases(testcases, colin):
    for child in list(testcases.getchildren()):
        if child.tag == "testcase":     
            handleTestcase(child, colin)

        else: 
            print "Error: Unknown element: " + child.tag
            sys.exit(1)

def parseConfigXml():
    tree = xml.parse("config.xml")

    colinElmt = tree.getroot()
    colin = Colin(
        colinElmt.get("toolname"),
        colinElmt.get("prompt"),
        colinElmt.get("version"))

    for child in list(colinElmt.getchildren()):
        if child.tag == "commands":     
            handleCommands(child, colin, 0)

        elif child.tag == "testcases":
            handleTestcases(child, colin)
         
        else: 
            print "Error: Unknown element: " + child.tag
            sys.exit(1)

    return colin

class Colin:
    def __init__(self, toolname, prompt, version):
        self.toolname = toolname
        self.prompt = prompt
        self.version = version
        self.entries = []
   
    def getEntries(self):
        return self.entries
 
    def addEntry(self, entry):
        self.entries.append(entry)

    def output(self):
        sys.stdout.write(self.toolname + " :: '" + self.prompt + "', " + self.version + "\n")
        for entry in self.entries:
            entry.output() 

class Container:
    def __init__(self, name, description):
        self.name = name
        self.description = description
        self.entries = []

    def getEntries(self):
        return self.entries
 
    def addEntry(self, entry):
        self.entries.append(entry)

    def output(self):
        sys.stdout.write("    " + self.name + " :: " + self.description + "\n")
        for entry in self.entries:
            entry.output() 

class Arg:
    def __init__(self, name):
        self.name = name
        self.types = []
        
    def setMode(self, mode):
        self.mode = mode

    def addType(self, typ):
        self.types.append(typ)

    def output(self):
        sys.stdout.write("[" + self.name + "::")
        if self.mode == "or":
            first = True
            for t in self.types:
                if not first:
                    sys.stdout.write("|")
                sys.stdout.write(t)
                first = False
        elif self.mode == "and":
            first = True
            for t in self.types:
                if not first:
                    sys.stdout.write("&")
                sys.stdout.write(t)
                first = False
        elif self.mode == "single":
            sys.stdout.write(self.types[0])
        else:
            print "Invalid mode: " + mode
            sys.exit(1)
        sys.stdout.write("]") 

class Output:
    def __init__(self, typeString):
        self.typeString = typeString 
        
    def output(self):
        sys.stdout.write("[" + self.typeString + "]") 

class Command:
    def __init__(self, name, description, level):
        self.name = name
        self.description = description
        self.level = level
        self.args = []
        self.res = None

    def addArg(self, arg):
        self.args.append(arg)

    def setRes(self, res):
        self.res = res

    def indent(self, addon=0):
        for i in range(0, self.level + 1 + addon):
            sys.stdout.write("    ")

    def output(self):
        self.indent()
        sys.stdout.write(self.name + " :: " + self.description + "\n")
        first = True
        self.indent(1)
        for arg in self.args:
            if not first:
                sys.stdout.write(", ")
            arg.output()
            first = False
        sys.stdout.write(" -> ")
        self.res.output()
        sys.stdout.write("\n\n")

class Testcase:
    def __init__(self, name, description):
        self.name = name
        self.description = description
        self.entries = []
  
    def addEntry(self, entry):
        self.entries.append(entry)

    def output(self):
        sys.stdout.write("    testcase(" + self.name + ") :: " + self.description + "\n")
        for entry in self.entries:
            sys.stdout.write("        " + entry + "\n")
        sys.stdout.write("\n")

colin = parseConfigXml()
colin.output()

