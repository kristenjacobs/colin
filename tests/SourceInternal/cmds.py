import sys
import java

print "Source test start"

# Displays the script arguments
print "Script Args: ",
for arg in sys.argv:
    print arg,
print    

# Valid command
try:
    output = jcolin.exec(["version"])
    print "Command Output: " + output

except java.lang.Exception, e:
    print e.getMessage()

# Invalid command
try:
    jcolin.exec(["a", "b", "c", "d"])

except java.lang.Exception, e:
    print e.getMessage()

print "Source test complete"

