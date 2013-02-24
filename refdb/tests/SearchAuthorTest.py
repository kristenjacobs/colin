
refdb.exec(["create", "TestBook"])
refdb.exec(["add", "author", "-", "Fred"])
refdb.exec(["search", "author", "Fred"])

print "Passed"
