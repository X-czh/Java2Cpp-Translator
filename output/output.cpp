#include <iostream>
#include "output.h"

using namespace java::lang;

namespace inputs {

  namespace test039 {

    __A::__A()
    :__vptr(&__vtable) {
    }

Class __A::__class() {
    static Class k = new __Class(__rt::literal("inputs.test039.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
    A __A::__init(A __this){
__Object::__init(__this);
;
return __this;;
    }
  
void __A::m_Object_Object(A __this, Object o1, Object o2){
std::cout << __rt::literal("A.m(Object, Object)") << std::endl;
;
}

void __A::m_A_Object(A __this, A a1, Object o2){
std::cout << __rt::literal("A.m(A, Object)") << std::endl;
;
}

void __A::m_Object_A(A __this, Object o1, A a2){
std::cout << __rt::literal("A.m(Object, A)") << std::endl;
;
}

__B::__B()
:__vptr(&__vtable) {
}

Class __B::__class() {
static Class k = new __Class(__rt::literal("inputs.test039.B"),__A::__class());
return k;
}
__B_VT __B::__vtable;
B __B::__init(B __this){
__A::__init(__this);
;
return __this;;
}

void __B::m_Object_Object(B __this, Object o1, Object o2){
std::cout << __rt::literal("B.m(Object, Object)") << std::endl;
;
}

void __B::m_B_Object(B __this, B a1, Object o2){
std::cout << __rt::literal("B.m(B, Object)") << std::endl;
;
}

void __B::m_Object_B(B __this, Object o1, B a2){
std::cout << __rt::literal("B.m(Object, B)") << std::endl;
;
}

__Test039::__Test039()
:__vptr(&__vtable) {
}

Class __Test039::__class() {
static Class k = new __Class(__rt::literal("inputs.test039.Test039"),__Object::__class());
return k;
}
__Test039_VT __Test039::__vtable;
Test039 __Test039::__init(Test039 __this){
__Object::__init(__this);
;
return __this;;
}

void __Test039::main(__rt::Array<String> args){
B b=__B::__init(new __B());
;
({B temp0=b;
__rt::checkNotNull(temp0);
temp0->__vptr->m_Object_Object(temp0, __A::__init(new __A()), __rt::java_cast<Object>(b));
})
;
({B temp1=b;
__rt::checkNotNull(temp1);
temp1->__vptr->m_B_Object(temp1, b, __Object::__init(new __Object()));
})
;
({B temp2=b;
__rt::checkNotNull(temp2);
temp2->__vptr->m_Object_B(temp2, __rt::java_cast<Object>(b), b);
})
;
}

}

}

