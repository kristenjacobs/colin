import sys

targetAuthor = sys.argv[0]

refids = refdb.exec(["get", "refids"]).split()
for refid in refids:
    title = refdb.exec(["get", "title", refid])
    date  = refdb.exec(["get", "date", refid])
    isbn  = refdb.exec(["get", "isbn", refid])

    authorids = refdb.exec(["get", "authorids", refid]).split()
    infoids   = refdb.exec(["get", "infoids", refid]).split()

    print "===="
    print refid
    print title
    print date
    print isbn
    
    for authorid in authorids:
        author = refdb.exec(["get", "author", refid, authorid])
        print "Author: " + author 

    for infoid in infoids:
        info = refdb.exec(["get", "info", refid, infoid])
        print "Info: " + info 
