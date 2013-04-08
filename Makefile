all: clean build rnc cln test

build:
	cd jcolin ; ant ; cd ../
	cd refdb ; ant ; cd ../
	cd hello ; ant ; cd ../

clean:
	cd jcolin ; ant clean ; cd ../
	cd refdb ; ant clean ; cd ../
	cd hello ; ant clean ; cd ../
	rm -rf jcolin/colin.rnc
	rm -rf refdb/config.cln
	rm -rf hello/config.cln

test:
	cd tests ; python test.py ; cd ../

rnc:
	cd jcolin ; trang -I rng -O rnc colin.rng colin.rnc ; cd ../

cln:
	cd refdb ; python ../scripts/convert.py > config.cln ; cd ../
	cd hello ; python ../scripts/convert.py > config.cln ; cd ../
