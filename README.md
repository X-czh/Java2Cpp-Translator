# SevenBobcat-Java2CppTranslator
The translator supports the translation of a restricted version of Java to C++.

The source language is a restricted version of Java without advanced features like nested classes, anonymous classes, interfaces, enums, annotations, generics, the enhanced for loop, varargs, boxing/unboxing, abstract classes, synchronized methods and statements, strictfp, transient, volatile, lambdas, etc. though.
The target language is a restricted version of C++ without virtual methods, inheritance, templates, lambda abstractions, auto, decltype, etc.

The translator is primarily interested in modeling basic translation and support key features of OOP like dynamic dispatch and method overloading. It does not make use of C++'s inheritance to mimic compiler's implementation of inheretance.

## Features
* Inheritance and Dynamic Dispatch
* Method Overloading
* Auto Memory Management
* Imitated Java Object Initialzation Process
* Array Translation
* Java Package Import

## Usage
Call sbt from the terminal in the translator directory and run the following code:

&nbsp;&nbsp;&nbsp;`runxtc -runTranslator src/test/java/inputs/testxxx/Testxxx.java`

Replace the xxx in "testxxx" and "Testxxx.java" with the number of the Java class file you want to run. 
The output.h, output.cpp, and main.cpp in the output folder will then be modified accordingly

Run&nbsp;&nbsp;&nbsp;`formatc` to format the C++ files in the output folder to allman style

Run&nbsp;&nbsp;&nbsp;`compilec` to compile the C++ files in the output folder 

Run&nbsp;&nbsp;&nbsp;`execc` to execute the C++ files in the output folder (does NOT support command line options, need to manually execuate output/a.out if command line options are needed)

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
Test Java codes are located in the folder src/test/java/inputs. A collection of 50 standard test cases named test000-test050 are provided. The translator has full support for all of them except test028-test031 due to lack of support to array class method calls and multi-dimension arrays. A single test named test is included to show the most advanced features that the translator supports.

Unit tests are located in the folder src/test/java/edu/nyu/oop.

## Design and Implementation
### Implementation
It supports inheritance and dynamic dispatch with virtual class table, method overloading with method name mangling, auto memory management with smart pointers using reference counting, imitated Java object initilization process with translated constructor scheme, array translation with custom array templates, and Java package import with modified xtc-demo's import parser.

### Phrased Approach
* Phase 1: Load all sources as Java AST
* Phase 2: Generate AST for inheritance hierarchy
* Phase 3: Write C++ header with inheritance hierarchy
* Phase 4: Mutate/Decorate Java AST to C++ AST
* Phase 5: Write C++ implementation file

### Design Patterns
Design patterns are intensively used throughout the poject. A few representatives are:
* Visitor Pattern

It is intensively used for extensible operations to carry our translation. Tree nodes in ASTs are dynamicly dispatched according to their types, and different operations are carried on accordingly.

* Delegation Pattern

For example, Translator.java provides an interface of using the translator, and delegrates actul work to the other constituting parts of the translator, including import parser, Java AST mutater, inheritance relation resolver, C++ printer and others.

* Chain of Responsibility

It is used naturally by our phrased approach and each phrase is implementated by a few files which take the corresponding responsibility.

## Team
Our team consists of Zhanghao Chen, Yiqin Qiu, Hannah Kelly, Zishi Deng, and Pyay Aung San.