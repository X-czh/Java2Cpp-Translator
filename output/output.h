#pragma once

#include "java_lang.h"

using namespace java::lang;

namespace inputs {

  namespace test010 {

    struct __B2;

    struct __B2_VT;

    struct __A;

    struct __A_VT;

    struct __C;

    struct __C_VT;

    struct __Test010;

    struct __Test010_VT;

    struct __B1;

    struct __B1_VT;

    typedef __rt::Ptr<__B2> B2;

    typedef __rt::Ptr<__A> A;

    typedef __rt::Ptr<__C> C;

    typedef __rt::Ptr<__Test010> Test010;

    typedef __rt::Ptr<__B1> B1;

    struct __B2 {

__B2_VT* __vptr;
String a;
String b;
      __B2( );

static       B2 __init();
      
static     Class __class();
    
static __B2_VT __vtable;
};

struct __A {

__A_VT* __vptr;
String a;
  __A( );

static   A __init();
  
static void setA(A ,String );

static void printOther(A ,A );

static String toString(A );

static Class __class();

static __A_VT __vtable;
};

struct __C {

__C_VT* __vptr;
String a;
String b;
String c;
__C( );

static C __init();

static Class __class();

static __C_VT __vtable;
};

struct __Test010 {

__Test010_VT* __vptr;
__Test010( );

static Test010 __init();

static void main(__rt::Array<String> );

static Class __class();

static __Test010_VT __vtable;
};

struct __B1 {

__B1_VT* __vptr;
String a;
String b;
__B1( );

static B1 __init();

static Class __class();

static __B1_VT __vtable;
};

struct __B2_VT {

Class __is_a;
(*__delete)(__B2*);
int32_t (*hashCode)(B2);
bool (*equals)(B2, Object);
Class (*getClass)(B2);
String (*toString)(B2);
(*setA)(B2, String);
(*printOther)(B2, A);
__B2_VT()
:__is_a(__B2::__class()),
__delete(&__rt::__delete<__B2>),
hashCode((int32_t (*)(B2)) &__Object::hashCode),
equals((bool (*)(B2, Object)) &__Object::equals),
getClass((Class (*)(B2)) &__Object::getClass),
toString((String (*)(B2)) &__A::toString),
setA((void (*)(B2, String)) &__A::setA),
printOther((void (*)(B2, A)) &__A::printOther) {
}

};

struct __A_VT {

Class __is_a;
(*__delete)(__A*);
int32_t (*hashCode)(A);
bool (*equals)(A, Object);
Class (*getClass)(A);
String (*toString)(A);
(*setA)(A, String);
(*printOther)(A, A);
__A_VT()
:__is_a(__A::__class()),
__delete(&__rt::__delete<__A>),
hashCode((int32_t (*)(A)) &__Object::hashCode),
equals((bool (*)(A, Object)) &__Object::equals),
getClass((Class (*)(A)) &__Object::getClass),
toString(&__A::toString),
setA(&__A::setA),
printOther(&__A::printOther) {
}

};

struct __C_VT {

Class __is_a;
(*__delete)(__C*);
int32_t (*hashCode)(C);
bool (*equals)(C, Object);
Class (*getClass)(C);
String (*toString)(C);
(*setA)(C, String);
(*printOther)(C, A);
__C_VT()
:__is_a(__C::__class()),
__delete(&__rt::__delete<__C>),
hashCode((int32_t (*)(C)) &__Object::hashCode),
equals((bool (*)(C, Object)) &__Object::equals),
getClass((Class (*)(C)) &__Object::getClass),
toString((String (*)(C)) &__A::toString),
setA((void (*)(C, String)) &__A::setA),
printOther((void (*)(C, A)) &__A::printOther) {
}

};

struct __Test010_VT {

Class __is_a;
(*__delete)(__Test010*);
int32_t (*hashCode)(Test010);
bool (*equals)(Test010, Object);
Class (*getClass)(Test010);
String (*toString)(Test010);
__Test010_VT()
:__is_a(__Test010::__class()),
__delete(&__rt::__delete<__Test010>),
hashCode((int32_t (*)(Test010)) &__Object::hashCode),
equals((bool (*)(Test010, Object)) &__Object::equals),
getClass((Class (*)(Test010)) &__Object::getClass),
toString((String (*)(Test010)) &__Object::toString) {
}

};

struct __B1_VT {

Class __is_a;
(*__delete)(__B1*);
int32_t (*hashCode)(B1);
bool (*equals)(B1, Object);
Class (*getClass)(B1);
String (*toString)(B1);
(*setA)(B1, String);
(*printOther)(B1, A);
__B1_VT()
:__is_a(__B1::__class()),
__delete(&__rt::__delete<__B1>),
hashCode((int32_t (*)(B1)) &__Object::hashCode),
equals((bool (*)(B1, Object)) &__Object::equals),
getClass((Class (*)(B1)) &__Object::getClass),
toString((String (*)(B1)) &__A::toString),
setA((void (*)(B1, String)) &__A::setA),
printOther((void (*)(B1, A)) &__A::printOther) {
}

};

}

}

