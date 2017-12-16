#pragma once

#include "java_lang.h"

using namespace java::lang;

namespace inputs
{

namespace test
{

struct __AA;

struct __AA_VT;

struct __BB;

struct __BB_VT;

struct __A;

struct __A_VT;

struct __AAA;

struct __AAA_VT;

struct __B;

struct __B_VT;

struct __C;

struct __C_VT;

struct __Test;

struct __Test_VT;

typedef __rt::Ptr<__AA> AA;

typedef __rt::Ptr<__BB> BB;

typedef __rt::Ptr<__A> A;

typedef __rt::Ptr<__AAA> AAA;

typedef __rt::Ptr<__B> B;

typedef __rt::Ptr<__C> C;

typedef __rt::Ptr<__Test> Test;

struct __AA
{

    __AA_VT* __vptr;
    int32_t i;
    __AA();

    static       AA __init(AA , int32_t );

    static     int32_t get(AA );

    static   Class __class();

    static __AA_VT __vtable;
};

struct __BB
{

    __BB_VT* __vptr;
    int32_t i;
    __BB();

    static BB __init(BB , int32_t );

    static int32_t get(BB );

    static Class __class();

    static __BB_VT __vtable;
};

struct __A
{

    __A_VT* __vptr;
    A self;
    static int32_t x;
    __A();

    static A __init(A );

    static A __init(A , int32_t );

    static int32_t x_impl();

    static A self_impl(A );

    static void m(A );

    static A m_A(A , A );

    static Class __class();

    static __A_VT __vtable;
};

struct __AAA
{

    __AAA_VT* __vptr;
    __AAA();

    static AAA __init(AAA );

    static int32_t m_byte(AAA , signed char );

    static int32_t m_int(AAA , int32_t );

    static void m_double(AAA , double );

    static Class __class();

    static __AAA_VT __vtable;
};

struct __B
{

    __B_VT* __vptr;
    A self;
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
    A self;
    __C();

    static C __init(C );

    static void m(C );

    static Class __class();

    static __C_VT __vtable;
};

struct __Test
{

    __Test_VT* __vptr;
    __Test();

    static Test __init(Test );

    static void main(__rt::Array<String> );

    static Class __class();

    static __Test_VT __vtable;
};

struct __AA_VT
{

    Class __is_a;
    void (*__delete)(__AA*);
    int32_t (*hashCode)(AA);
    bool (*equals)(AA, Object);
    Class (*getClass)(AA);
    String (*toString)(AA);
    int32_t (*get)(AA);
    __AA_VT()
        :__is_a(__AA::__class()),
         __delete(&__rt::__delete<__AA>),
         hashCode((int32_t (*)(AA)) &__Object::hashCode),
         equals((bool (*)(AA, Object)) &__Object::equals),
         getClass((Class (*)(AA)) &__Object::getClass),
         toString((String (*)(AA)) &__Object::toString),
         get(&__AA::get)
    {
    }

};

struct __BB_VT
{

    Class __is_a;
    void (*__delete)(__BB*);
    int32_t (*hashCode)(BB);
    bool (*equals)(BB, Object);
    Class (*getClass)(BB);
    String (*toString)(BB);
    int32_t (*get)(BB);
    __BB_VT()
        :__is_a(__BB::__class()),
         __delete(&__rt::__delete<__BB>),
         hashCode((int32_t (*)(BB)) &__Object::hashCode),
         equals((bool (*)(BB, Object)) &__Object::equals),
         getClass((Class (*)(BB)) &__Object::getClass),
         toString((String (*)(BB)) &__Object::toString),
         get(&__BB::get)
    {
    }

};

struct __A_VT
{

    Class __is_a;
    void (*__delete)(__A*);
    int32_t (*hashCode)(A);
    bool (*equals)(A, Object);
    Class (*getClass)(A);
    String (*toString)(A);
    A (*self_impl)(A);
    void (*m)(A);
    A (*m_A)(A, A);
    __A_VT()
        :__is_a(__A::__class()),
         __delete(&__rt::__delete<__A>),
         hashCode((int32_t (*)(A)) &__Object::hashCode),
         equals((bool (*)(A, Object)) &__Object::equals),
         getClass((Class (*)(A)) &__Object::getClass),
         toString((String (*)(A)) &__Object::toString),
         self_impl(&__A::self_impl),
         m(&__A::m),
         m_A(&__A::m_A)
    {
    }

};

struct __AAA_VT
{

    Class __is_a;
    void (*__delete)(__AAA*);
    int32_t (*hashCode)(AAA);
    bool (*equals)(AAA, Object);
    Class (*getClass)(AAA);
    String (*toString)(AAA);
    int32_t (*m_byte)(AAA, signed char);
    int32_t (*m_int)(AAA, int32_t);
    void (*m_double)(AAA, double);
    __AAA_VT()
        :__is_a(__AAA::__class()),
         __delete(&__rt::__delete<__AAA>),
         hashCode((int32_t (*)(AAA)) &__Object::hashCode),
         equals((bool (*)(AAA, Object)) &__Object::equals),
         getClass((Class (*)(AAA)) &__Object::getClass),
         toString((String (*)(AAA)) &__Object::toString),
         m_byte(&__AAA::m_byte),
         m_int(&__AAA::m_int),
         m_double(&__AAA::m_double)
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
    A (*self_impl)(B);
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
         self_impl((A (*)(B)) &__A::self_impl),
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
    A (*self_impl)(C);
    void (*m)(C);
    A (*m_A)(C, A);
    C (*m_B)(C, B);
    __C_VT()
        :__is_a(__C::__class()),
         __delete(&__rt::__delete<__C>),
         hashCode((int32_t (*)(C)) &__Object::hashCode),
         equals((bool (*)(C, Object)) &__Object::equals),
         getClass((Class (*)(C)) &__Object::getClass),
         toString((String (*)(C)) &__Object::toString),
         self_impl((A (*)(C)) &__A::self_impl),
         m(&__C::m),
         m_A((A (*)(C, A)) &__B::m_A),
         m_B((C (*)(C, B)) &__B::m_B)
    {
    }

};

struct __Test_VT
{

    Class __is_a;
    void (*__delete)(__Test*);
    int32_t (*hashCode)(Test);
    bool (*equals)(Test, Object);
    Class (*getClass)(Test);
    String (*toString)(Test);
    __Test_VT()
        :__is_a(__Test::__class()),
         __delete(&__rt::__delete<__Test>),
         hashCode((int32_t (*)(Test)) &__Object::hashCode),
         equals((bool (*)(Test, Object)) &__Object::equals),
         getClass((Class (*)(Test)) &__Object::getClass),
         toString((String (*)(Test)) &__Object::toString)
    {
    }

};

}

}

