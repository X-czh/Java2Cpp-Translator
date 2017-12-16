# SevenBobcat-Java2CppTranslator

## Features
By far, the translator supports the translation of Java with inheritance and dynamic dispatch, Java package imports, 
standard Java field and method declarations to C++ using Virtual Class Table without inheritance.  However, it does 
NOT support the generation of actually implementation file yet, only the header file. We will keep updating it 
to support more advanced features during the rest of the semester.

## Usage
Call sbt from the terminal in the translator directory and run the following code:

&nbsp;&nbsp;&nbsp;`runxtc -runTranslator src/test/java/inputs/testxxx/Testxxx.java`

Replace the xxx in "testxxx" and "Testxxx.java" with the number of the Java class file you want to run. 
The output.h, output.cpp, and main.cpp in the output folder will then be modified accordingly.
Run
&nbsp;&nbsp;&nbsp;`formatc`
to format C++ files
&nbsp;&nbsp;&nbsp;`compilec`
&nbsp;&nbsp;&nbsp;`execc`
&nbsp;&nbsp;&nbsp;`cleanc`
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
To run tests, in sbt do any of the following:

```
 runxtc -printJavaAstList src/test/java/inputs/testxxx/Testxxx.java
 runxtc -printHeaderAst src/test/java/inputs/testxxx/Testxxx.java
 runxtc -printCppHeader src/test/java/inputs/testxxx/Testxxx.java
```

In order, the tests are for Phase 1, Phase 2, and Phase 3.

