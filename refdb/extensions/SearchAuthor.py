import sys
import re

targetAuthor = sys.argv[0]
numMatchesFound = 0

refids = refdb.exec(["get", "refids"]).split()
for refid in refids:
    title = refdb.exec(["get", "title", refid])
    authorids = refdb.exec(["get", "authorids", refid]).split()
    for authorid in authorids:
        author = refdb.exec(["get", "author", refid, authorid])
        if re.search(targetAuthor, author):
            print "Found match: Title: " + title + ", Author: " + author
            numMatchesFound += 1

if numMatchesFound == 1:
    print "Found " + str(numMatchesFound) + " match" 

elif numMatchesFound > 1:
    print "Found " + str(numMatchesFound) + " matches"  

else:
    print "No matches found"
