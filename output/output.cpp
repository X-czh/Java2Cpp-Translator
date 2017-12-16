#include <iostream>
#include "output.h"

using namespace java::lang;

namespace inputs
{

namespace test024
{

__A::__A()
    :__vptr(&__vtable)
{
}

Class __A::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test024.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this, int32_t i)
{
    __Object::__init(__this);
    __this->i=i;
    return __this;
}

int32_t __A::get(A __this)
{
    return __this->i;
}

__Test024::__Test024()
    :__vptr(&__vtable)
{
}

Class __Test024::__class()
{
    static Class k = new __Class(__rt::literal("inputs.test024.Test024"),__Object::__class());
    return k;
}
__Test024_VT __Test024::__vtable;
Test024 __Test024::__init(Test024 __this)
{
    __Object::__init(__this);
    return __this;
}

void __Test024::main(__rt::Array<String> args)
{
    __rt::Array<Object> as= new __rt::__Array<A>(10);
    for(int32_t i=0; i<as->length; i++)
    {
        (*as)[i]=__A::__init(new __A(), i);
    }
    int32_t k=0;
    while (k<10)
    {
        std::cout << ({A temp0=__rt::java_cast<A>((*as)[k]);
                       __rt::checkNotNull(temp0);
                       temp0->__vptr->get(temp);
                      })
                  << std::endl;
        k=k + 1;
    }
}

}

}

