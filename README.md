# SevenBobcat-Java2CppTranslator

## Features
The translator supports the translation of a restricted version of Java to C++ with the following features:
* Inheritance and Dynamic Dispatch
* Method Overloading
* Auto Memory Management
* Imitated Java Object Initialzation Process
* Array Translation
* Java Package Import

It supports inheritance and dynamic dispatch with virtual class table, method overloading with method name mangling, auto memory management with smart pointers using reference counting, imitated Java object initilization process with translated constructor scheme, array translation with array templates, and Java package import with modified xtc-demo's import parser.

It does not support Java's advanced features like nested classes, anonymous classes, interfaces, enums, annotations, generics, the enhanced for loop, varargs, boxing/unboxing, abstract classes, synchronized methods and statements, strictfp, transient, volatile, lambdas, etc. though.

## Usage
Call sbt from the terminal in the translator directory and run the following code:

&nbsp;&nbsp;&nbsp;`runxtc -runTranslator src/test/java/inputs/testxxx/Testxxx.java`

Replace the xxx in "testxxx" and "Testxxx.java" with the number of the Java class file you want to run. 
The output.h, output.cpp, and main.cpp in the output folder will then be modified accordingly

Run&nbsp;&nbsp;&nbsp;`formatc` to format the C++ files in the output folder to allman style

Run&nbsp;&nbsp;&nbsp;`compilec` to compile the C++ files in the output folder 

Run&nbsp;&nbsp;&nbsp;`execc` to execute the C++ files in the output folder

Run&nbsp;&nbsp;&nbsp;`cleanc` to clean the executable file previously generated in the output folder

All supported commands are 
* printJavaAst - Print Java Ast
* printSimpleJavaAst - Print Simplified Java Ast 
* printJavaCode - Print Java code
* cppFilePrinter - Print example cpp file into output directory
* printJavaImportCode -Print Java code for imports of primary source file
* printSymbolTable - Print symbol table for Java Ast
* printConfig - Output application configuration to screen
* printJavaAstList - Print list of Java Ast 
* printMutatedJavaAstList - Print list of mutated Java Ast
* printHeaderAst - print C++ header AST
* printCppHeader - print C++ header
* printMutatedCppAs - Print mutated C++ Ast
* printMainAst - Print C++ main Ast
* printCppImplementation - Print C++ implementation files
* runTranslator - Run translator                                                           
## Tests
Test Java codes are located in the folder src/test/java/inputs. The translator has full support for all of them.

Unit tests are located in the folder src/test/java/edu/nyu/oop.