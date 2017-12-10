cmake_minimum_required(VERSION 3.9)
project(output)

set(CMAKE_CXX_STANDARD 11)

add_executable(output
        java_lang.cpp
        java_lang.h
        main.cpp
        output.cpp
        output.h
        ptr.h)
