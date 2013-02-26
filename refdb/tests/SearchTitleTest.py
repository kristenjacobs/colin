import re

refdb.exec(["create", "TestBook"])

# This test checks the case where the title is found.
result = refdb.exec(["search", "title", "Book"])
if not re.search("Found 1 match", result):
    print "Failed (found)"

# This checks the case where the title is not found.
result = refdb.exec(["search", "title", "Crap"])
if not re.search("No matches found", result):
    print "Failed (not found)"

print "Passed"
