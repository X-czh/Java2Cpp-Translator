#pragma once

#include "java_lang.h"

using namespace java::lang;

namespace inputs
{

namespace test050
{

struct __A;

struct __A_VT;

struct __B;

struct __B_VT;

struct __C;

struct __C_VT;

struct __Test050;

struct __Test050_VT;

typedef __rt::Ptr<__A> A;

typedef __rt::Ptr<__B> B;

typedef __rt::Ptr<__C> C;

typedef __rt::Ptr<__Test050> Test050;

struct __A
{

    __A_VT* __vptr;
    __A();

    static       A __init(A );

    static     void m(A );

    static   A m_A(A , A );

    static Class __class();

    static __A_VT __vtable;
};

struct __B
{

    __B_VT* __vptr;
    __B();

    static B __init(B );

    static void m(B );

    static C m_B(B , B );

    static A m_A(B , A );

    static Class __class();

    static __B_VT __vtable;
};

struct __C
{

    __C_VT* __vptr;
    __C();

    static C __init(C );

    static void m(C );

    static Class __class();

    static __C_VT __vtable;
};

struct __Test050
{

    __Test050_VT* __vptr;
    __Test050();

    static Test050 __init(Test050 );

    static void main(__rt::Array<String> );

    static Class __class();

    static __Test050_VT __vtable;
};

struct __A_VT
{

    Class __is_a;
    void (*__delete)(__A*);
    int32_t (*hashCode)(A);
    bool (*equals)(A, Object);
    Class (*getClass)(A);
    String (*toString)(A);
    void (*m)(A);
    A (*m_A)(A, A);
    __A_VT()
        :__is_a(__A::__class()),
         __delete(&__rt::__delete<__A>),
         hashCode((int32_t (*)(A)) &__Object::hashCode),
         equals((bool (*)(A, Object)) &__Object::equals),
         getClass((Class (*)(A)) &__Object::getClass),
         toString((String (*)(A)) &__Object::toString),
         m(&__A::m),
         m_A(&__A::m_A)
    {
    }

};

struct __B_VT
{

    Class __is_a;
    void (*__delete)(__B*);
    int32_t (*hashCode)(B);
    bool (*equals)(B, Object);
    Class (*getClass)(B);
    String (*toString)(B);
    void (*m)(B);
    A (*m_A)(B, A);
    C (*m_B)(B, B);
    __B_VT()
        :__is_a(__B::__class()),
         __delete(&__rt::__delete<__B>),
         hashCode((int32_t (*)(B)) &__Object::hashCode),
         equals((bool (*)(B, Object)) &__Object::equals),
         getClass((Class (*)(B)) &__Object::getClass),
         toString((String (*)(B)) &__Object::toString),
         m(&__B::m),
         m_A(&__B::m_A),
         m_B(&__B::m_B)
    {
    }

};

struct __C_VT
{

    Class __is_a;
    void (*__delete)(__C*);
    int32_t (*hashCode)(C);
    bool (*equals)(C, Object);
    Class (*getClass)(C);
    String (*toString)(C);
    void (*m)(C);
    A (*m_A)(C, A);
    __C_VT()
        :__is_a(__C::__class()),
         __delete(&__rt::__delete<__C>),
         hashCode((int32_t (*)(C)) &__Object::hashCode),
         equals((bool (*)(C, Object)) &__Object::equals),
         getClass((Class (*)(C)) &__Object::getClass),
         toString((String (*)(C)) &__Object::toString),
         m(&__C::m),
         m_A((A (*)(C, A)) &__A::m_A)
    {
    }

};

struct __Test050_VT
{

    Class __is_a;
    void (*__delete)(__Test050*);
    int32_t (*hashCode)(Test050);
    bool (*equals)(Test050, Object);
    Class (*getClass)(Test050);
    String (*toString)(Test050);
    __Test050_VT()
        :__is_a(__Test050::__class()),
         __delete(&__rt::__delete<__Test050>),
         hashCode((int32_t (*)(Test050)) &__Object::hashCode),
         equals((bool (*)(Test050, Object)) &__Object::equals),
         getClass((Class (*)(Test050)) &__Object::getClass),
         toString((String (*)(Test050)) &__Object::toString)
    {
    }

};

}

}

