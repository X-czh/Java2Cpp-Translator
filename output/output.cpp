#include <iostream>
#include "output.h"

using namespace java::lang;

namespace inputs
{

namespace test050
{

__A::__A()
    :__vptr(&__vtable)
{
}

Class __A::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test050.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this)
{
    __Object::__init(__this);
    return __this;
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
    static Class k = new __Class(__rt::literal("inputs.test050.B"),__A::__class());
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
    static Class k = new __Class(__rt::literal("inputs.test050.C"),__A::__class());
    return k;
}
__C_VT __C::__vtable;
C __C::__init(C __this)
{
    __A::__init(__this);
    return __this;
}

void __C::m(C __this)
{
    std::cout << __rt::literal("C.m()") << std::endl;
}

__Test050::__Test050()
    :__vptr(&__vtable)
{
}

Class __Test050::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test050.Test050"),__Object::__class());
    return k;
}
__Test050_VT __Test050::__vtable;
Test050 __Test050::__init(Test050 __this)
{
    __Object::__init(__this);
    return __this;
}

void __Test050::main(__rt::Array<String> args)
{
    B b=__B::__init(new __B());
    A a=__A::__init(new __A());
    ( {A temp3=({A temp2=({A temp1=a;
                           __rt::checkNotNull(temp1);
                           temp1->__vptr->m_A(temp1, b);
                          })
                         ;
                 __rt::checkNotNull(temp2);
                 temp2->__vptr->m_A(temp2, ({B temp0=b;
                                            __rt::checkNotNull(temp0);
                                            temp0->__vptr->m_A(temp0, a);
                                            })
                                   );
                })
               ;
       __rt::checkNotNull(temp3);
       temp3->__vptr->m(temp3);
      })
    ;
}

}

}

