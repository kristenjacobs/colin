all: clean build test

build:
	cd jcolin ; ant ; cd ../
	cd refdb ; ant ; cd ../

clean:
	cd jcolin ; ant clean ; cd ../
	cd refdb ; ant clean ; cd ../

test:
	cd tests ; python test.py ; cd ../
    
