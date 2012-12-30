all:
	cd jcolin ; ant ; cd ../
	cd refdb ; ant ; cd ../

clean:
	cd jcolin ; ant clean ; cd ../
	cd refdb ; ant clean ; cd ../

