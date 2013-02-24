import re

refdb.exec(["create", "TestBook"])
refdb.exec(["add", "author", "-", "Fred"])

# This test checks the case where the author is found.
result = refdb.exec(["search", "author", "Fred"])
if not re.search("Found 1 match", result):
    print "Failed (found)"

# This checks the case where the author is not found.
result = refdb.exec(["search", "author", "Bob"])
if not re.search("No matches found", result):
    print "Failed (not found)"

print "Passed"
