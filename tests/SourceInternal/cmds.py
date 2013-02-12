import sys
import java

print "Source test start"

# Displays the script arguments
print "Script Args: ",
for arg in sys.argv:
    print arg,
print    

# Valid commands
try:
    output = jcolin.exec(["version"])
    print "Command Output [version]>> " + output + "<<"
    output = jcolin.exec(["version"])
    print "Command Output [help]>> " + output + "<<"

except java.lang.Exception, e:
    print e.getMessage()

# Invalid command
try:
    jcolin.exec(["a", "b", "c", "d"])

except java.lang.Exception, e:
    print e.getMessage()

print "Source test complete"

