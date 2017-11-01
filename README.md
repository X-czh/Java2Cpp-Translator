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
Currently, the translator only supports the generation of C++ header file output.h.

The other possible commands are 
* printJavaAst
* printSimpleJavaAst 
* printJavaCode
* printJavaImportCode 
* printConfig
* printJavaAstList
* printHeaderAst
* printCppHeaderFile
* runTranslator                                                                  

To run tests, in sbt do any of the following:

```
 runxtc -printJavaAstList src/test/java/inputs/testxxx/Testxxx.java
 runxtc -printHeaderAst src/test/java/inputs/testxxx/Testxxx.java
 runxtc -printCppHeader src/test/java/inputs/testxxx/Testxxx.java
```

In order, the tests are for Phase 1, Phase 2, and Phase 3.

