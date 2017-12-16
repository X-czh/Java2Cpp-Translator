#pragma once

#include "java_lang.h"

using namespace java::lang;

namespace inputs {

  namespace test039 {

    struct __A;

    struct __A_VT;

    struct __B;

    struct __B_VT;

    struct __Test039;

    struct __Test039_VT;

    typedef __rt::Ptr<__A> A;

    typedef __rt::Ptr<__B> B;

    typedef __rt::Ptr<__Test039> Test039;

    struct __A {

__A_VT* __vptr;
      __A();

static       A __init(A );
      
static     void m_Object_Object(A , Object , Object );
    
static   void m_A_Object(A , A , Object );
  
static void m_Object_A(A , Object , A );

static Class __class();

static __A_VT __vtable;
};

struct __B {

__B_VT* __vptr;
__B();

static B __init(B );

static void m_Object_Object(B , Object , Object );

static void m_B_Object(B , B , Object );

static void m_Object_B(B , Object , B );

static Class __class();

static __B_VT __vtable;
};

struct __Test039 {

__Test039_VT* __vptr;
__Test039();

static Test039 __init(Test039 );

static void main(__rt::Array<String> );

static Class __class();

static __Test039_VT __vtable;
};

struct __A_VT {

Class __is_a;
void (*__delete)(__A*);
int32_t (*hashCode)(A);
bool (*equals)(A, Object);
Class (*getClass)(A);
String (*toString)(A);
void (*m_Object_Object)(A, Object, Object);
void (*m_A_Object)(A, A, Object);
void (*m_Object_A)(A, Object, A);
__A_VT()
:__is_a(__A::__class()),
__delete(&__rt::__delete<__A>),
hashCode((int32_t (*)(A)) &__Object::hashCode),
equals((bool (*)(A, Object)) &__Object::equals),
getClass((Class (*)(A)) &__Object::getClass),
toString((String (*)(A)) &__Object::toString),
m_Object_Object(&__A::m_Object_Object),
m_A_Object(&__A::m_A_Object),
m_Object_A(&__A::m_Object_A) {
}

};

struct __B_VT {

Class __is_a;
void (*__delete)(__B*);
int32_t (*hashCode)(B);
bool (*equals)(B, Object);
Class (*getClass)(B);
String (*toString)(B);
void (*m_Object_Object)(B, Object, Object);
void (*m_A_Object)(B, A, Object);
void (*m_Object_A)(B, Object, A);
void (*m_B_Object)(B, B, Object);
void (*m_Object_B)(B, Object, B);
__B_VT()
:__is_a(__B::__class()),
__delete(&__rt::__delete<__B>),
hashCode((int32_t (*)(B)) &__Object::hashCode),
equals((bool (*)(B, Object)) &__Object::equals),
getClass((Class (*)(B)) &__Object::getClass),
toString((String (*)(B)) &__Object::toString),
m_Object_Object(&__B::m_Object_Object),
m_A_Object((void (*)(B, A, Object)) &__A::m_A_Object),
m_Object_A((void (*)(B, Object, A)) &__A::m_Object_A),
m_B_Object(&__B::m_B_Object),
m_Object_B(&__B::m_Object_B) {
}

};

struct __Test039_VT {

Class __is_a;
void (*__delete)(__Test039*);
int32_t (*hashCode)(Test039);
bool (*equals)(Test039, Object);
Class (*getClass)(Test039);
String (*toString)(Test039);
__Test039_VT()
:__is_a(__Test039::__class()),
__delete(&__rt::__delete<__Test039>),
hashCode((int32_t (*)(Test039)) &__Object::hashCode),
equals((bool (*)(Test039, Object)) &__Object::equals),
getClass((Class (*)(Test039)) &__Object::getClass),
toString((String (*)(Test039)) &__Object::toString) {
}

};

}

}

