all:
	g++ -std=c++11 -c main.cpp -o main.o
	g++ -std=c++11 -c output.cpp -o output.o
	g++ -std=c++11 -c java_lang.cpp -o java_lang.o
	g++ main.o output.o java_lang.o -o main

clean:
	rm -rf *.o
	rm -rf main
