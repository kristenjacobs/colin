import sys
import re

targetTitle = sys.argv[0]
numMatchesFound = 0

refids = refdb.exec(["get", "refids"]).split()
for refid in refids:
    title = refdb.exec(["get", "title", refid])
    if re.search(targetTitle, title):
        print "Found match: Title: " + title
        numMatchesFound += 1

if numMatchesFound == 1:
    print "Found " + str(numMatchesFound) + " match" 

elif numMatchesFound > 1:
    print "Found " + str(numMatchesFound) + " matches"  

else:
    print "No matches found"
