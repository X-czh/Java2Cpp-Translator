#pragma once

#include "java_lang.h"

using namespace java::lang;

namespace inputs {

  namespace test026 {

    struct __A;

    struct __A_VT;

    struct __B;

    struct __B_VT;

    struct __Test026;

    struct __Test026_VT;

    typedef __rt::Ptr<__A> A;

    typedef __rt::Ptr<__B> B;

    typedef __rt::Ptr<__Test026> Test026;

    struct __A {

__A_VT* __vptr;
int32_t i;
      __A();

static       A __init(A , int32_t );
      
static     int32_t get(A );
    
static   Class __class();
  
static __A_VT __vtable;
};

struct __B {

__B_VT* __vptr;
int32_t i;
__B();

static B __init(B , int32_t );

static int32_t get(B );

static Class __class();

static __B_VT __vtable;
};

struct __Test026 {

__Test026_VT* __vptr;
__Test026();

static Test026 __init(Test026 );

static void main(__rt::Array<String> );

static Class __class();

static __Test026_VT __vtable;
};

struct __A_VT {

Class __is_a;
void (*__delete)(__A*);
int32_t (*hashCode)(A);
bool (*equals)(A, Object);
Class (*getClass)(A);
String (*toString)(A);
int32_t (*get)(A);
__A_VT()
:__is_a(__A::__class()),
__delete(&__rt::__delete<__A>),
hashCode((int32_t (*)(A)) &__Object::hashCode),
equals((bool (*)(A, Object)) &__Object::equals),
getClass((Class (*)(A)) &__Object::getClass),
toString((String (*)(A)) &__Object::toString),
get(&__A::get) {
}

};

struct __B_VT {

Class __is_a;
void (*__delete)(__B*);
int32_t (*hashCode)(B);
bool (*equals)(B, Object);
Class (*getClass)(B);
String (*toString)(B);
int32_t (*get)(B);
__B_VT()
:__is_a(__B::__class()),
__delete(&__rt::__delete<__B>),
hashCode((int32_t (*)(B)) &__Object::hashCode),
equals((bool (*)(B, Object)) &__Object::equals),
getClass((Class (*)(B)) &__Object::getClass),
toString((String (*)(B)) &__Object::toString),
get(&__B::get) {
}

};

struct __Test026_VT {

Class __is_a;
void (*__delete)(__Test026*);
int32_t (*hashCode)(Test026);
bool (*equals)(Test026, Object);
Class (*getClass)(Test026);
String (*toString)(Test026);
__Test026_VT()
:__is_a(__Test026::__class()),
__delete(&__rt::__delete<__Test026>),
hashCode((int32_t (*)(Test026)) &__Object::hashCode),
equals((bool (*)(Test026, Object)) &__Object::equals),
getClass((Class (*)(Test026)) &__Object::getClass),
toString((String (*)(Test026)) &__Object::toString) {
}

};

}

}

