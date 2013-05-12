import sys

refids = refdb.exec(["get", "refids"]).split()
for refid in refids:
    title = refdb.exec(["get", "title", refid])
    date  = refdb.exec(["get", "date", refid])
    isbn  = refdb.exec(["get", "isbn", refid])

    authorids = refdb.exec(["get", "authorids", refid]).split()
    infoids   = refdb.exec(["get", "infoids", refid]).split()

    print "<h3>" + title + "</h3>"
    if date != "":
        print "Publication Date: " + date
        print "<p>"

    if isbn != "":
        print "ISBN: " + isbn
        print "<p>"
    
    if len(authorids) > 0:
        print "Authors:",
        for authorid in authorids:
            author = refdb.exec(["get", "author", refid, authorid])
            print author + ",", 
        print "\n<p>"    

    if len(infoids) > 0:
        for infoid in infoids:
            info = refdb.exec(["get", "info", refid, infoid])
            print info  + ",", 
        print    

    print
        
