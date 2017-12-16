#include <iostream>
#include "output.h"

using namespace java::lang;

namespace inputs
{

namespace test
{

__A::__A()
    :__vptr(&__vtable)
{
}

Class __A::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
int32_t __A::x=3;
A __A::__init(A __this)
{
    __Object::__init(__this);
    return __this;
}

A __A::__init(A __this, int32_t x)
{
    __Object::__init(__this);
    __this->self=__this;
    return __this;
}

int32_t __A::x_impl()
{
    return 4;
}

A __A::self_impl(A __this)
{
    return __this->self;
}

void __A::m(A __this)
{
    std::cout << __rt::literal("A.m()") << std::endl;
}

A __A::m_A(A __this, A a)
{
    std::cout << __rt::literal("A.m(A)") << std::endl;
    return a;
}

__B::__B()
    :__vptr(&__vtable)
{
}

Class __B::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test.B"),__A::__class());
    return k;
}
__B_VT __B::__vtable;
B __B::__init(B __this)
{
    __A::__init(__this);
    return __this;
}

void __B::m(B __this)
{
    std::cout << __rt::literal("B.m()") << std::endl;
}

C __B::m_B(B __this, B b)
{
    std::cout << __rt::literal("B.m(B)") << std::endl;
    return __C::__init(new __C());
}

A __B::m_A(B __this, A a)
{
    std::cout << __rt::literal("B.m(A)") << std::endl;
    return a;
}

__C::__C()
    :__vptr(&__vtable)
{
}

Class __C::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test.C"),__B::__class());
    return k;
}
__C_VT __C::__vtable;
C __C::__init(C __this)
{
    __B::__init(__this);
    return __this;
}

void __C::m(C __this)
{
    std::cout << __rt::literal("C.m()") << std::endl;
}

__AA::__AA()
    :__vptr(&__vtable)
{
}

Class __AA::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test.AA"),__Object::__class());
    return k;
}
__AA_VT __AA::__vtable;
AA __AA::__init(AA __this, int32_t i)
{
    __Object::__init(__this);
    __this->i=i;
    return __this;
}

int32_t __AA::get(AA __this)
{
    return __this->i;
}

__BB::__BB()
    :__vptr(&__vtable)
{
}

Class __BB::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test.BB"),__AA::__class());
    return k;
}
__BB_VT __BB::__vtable;
BB __BB::__init(BB __this, int32_t i)
{
    __AA::__init(__this, i);
    return __this;
}

int32_t __BB::get(BB __this)
{
    return 10 - __this->i;
}

__AAA::__AAA()
    :__vptr(&__vtable)
{
}

Class __AAA::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test.AAA"),__Object::__class());
    return k;
}
__AAA_VT __AAA::__vtable;
AAA __AAA::__init(AAA __this)
{
    __Object::__init(__this);
    return __this;
}

int32_t __AAA::m_byte(AAA __this, signed char b)
{
    std::cout << __rt::literal("AAA.m(byte)") << std::endl;
    return b;
}

int32_t __AAA::m_int(AAA __this, int32_t i)
{
    std::cout << __rt::literal("AAA.m(int)") << std::endl;
    return i;
}

void __AAA::m_double(AAA __this, double d)
{
    std::cout << __rt::literal("AAA.m(double)") << std::endl;
}

__Test::__Test()
    :__vptr(&__vtable)
{
}

Class __Test::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test.Test"),__Object::__class());
    return k;
}
__Test_VT __Test::__vtable;
Test __Test::__init(Test __this)
{
    __Object::__init(__this);
    return __this;
}

void __Test::main(__rt::Array<String> args)
{
    AAA aaa=__AAA::__init(new __AAA());
    signed char bbb=1;
    double ddd=1.0;
    ( {AAA temp0=aaa;
       __rt::checkNotNull(temp0);
       temp0->__vptr->m_byte(temp0, bbb);
      })
    ;
    ( {AAA temp1=aaa;
       __rt::checkNotNull(temp1);
       temp1->__vptr->m_int(temp1, bbb + bbb);
      })
    ;
    ( {AAA temp2=aaa;
       __rt::checkNotNull(temp2);
       temp2->__vptr->m_double(temp2, ddd);
      })
    ;
    B b=__B::__init(new __B());
    A a=__A::__init(new __A());
    ( {A temp6=({A temp5=({B temp4=b;
                           __rt::checkNotNull(temp4);
                           temp4->__vptr->m_A(temp4, __rt::java_cast<A>(b));
                          })
                         ;
                 __rt::checkNotNull(temp5);
                 temp5->__vptr->m_A(temp5, __rt::java_cast<B>(({B temp3=b;
                                    __rt::checkNotNull(temp3);
                                    temp3->__vptr->m_B(temp3, b);
                                                               })
                                                             ));
                })
               ;
       __rt::checkNotNull(temp6);
       temp6->__vptr->m(temp6);
      })
    ;
    int32_t x;
    x=__A::x;
    std::cout << x << std::endl;
    std::cout << ( {__A::x_impl();
                   })
              << std::endl;
    A a1=__A::__init(new __A(), 5);
    std::cout << ( {A temp9=({A temp8=a1;
                              __rt::checkNotNull(temp8);
                              temp8->__vptr->self_impl(temp8);
                             })
                            ;
                    __rt::checkNotNull(temp9);
                    temp9->__vptr->toString(temp9);
                   })
              << std::endl;
    __rt::Array<Object> as=( {__rt::Array<AA> temp10=new __rt::__Array<AA>(5);
                              temp10;
                             })
                           ;
    for(int32_t i=0; i<as->length; i++)
    {
        ( {__rt::checkStore(as, __BB::__init(new __BB(), i));
           (*as)[i]=__BB::__init(new __BB(), i);
          })
        ;
    }
    int32_t k=0;
    while (k<5)
    {
        std::cout << ( {AA temp11=__rt::java_cast<AA>((*as)[k]);
                        __rt::checkNotNull(temp11);
                        temp11->__vptr->get(temp11);
                       })
                  << std::endl;
        k=k + 1;
    }
    __rt::Array<AA> a2=( {__rt::Array<BB> temp12=new __rt::__Array<BB>(10);
                          temp12;
                         })
                       ;
    for(int32_t i=0; i<as->length; i++)
    {
        ( {__rt::checkStore(a2, __AA::__init(new __AA(), i));
           (*a2)[i]=__AA::__init(new __AA(), i);
          })
        ;
    }
}

}

}

