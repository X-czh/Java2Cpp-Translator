#include <iostream>
#include "output.h"

using namespace std;
using namespace java::lang;
namespace inputs {

  namespace test010 {

    __A::__A()
    :__vptr(&__vtable) {
    }

Class __A::__class() {
    static Class k = new __Class(__rt::literal("inputs.test010.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
    void __A::setA(A __this,String x){
this->a=x;
    }
  
void __A::printOther(A __this,A other){
cout << ({ auto temp0 = other;
  __rt::checkNotNull(temp0);
  temp0->__vptr->toString(temp0);
})
 << endl;
}

String __A::toString(A __this){
return this->a;}

__B1::__B1()
:__vptr(&__vtable) {
}

Class __B1::__class() {
static Class k = new __Class(__rt::literal("inputs.test010.B1"),__A::__class());
return k;
}
__B1_VT __B1::__vtable;
__B2::__B2()
:__vptr(&__vtable) {
}

Class __B2::__class() {
static Class k = new __Class(__rt::literal("inputs.test010.B2"),__A::__class());
return k;
}
__B2_VT __B2::__vtable;
__C::__C()
:__vptr(&__vtable) {
}

Class __C::__class() {
static Class k = new __Class(__rt::literal("inputs.test010.C"),__B1::__class());
return k;
}
__C_VT __C::__vtable;
__Test010::__Test010()
:__vptr(&__vtable) {
}

Class __Test010::__class() {
static Class k = new __Class(__rt::literal("inputs.test010.Test010"),__Object::__class());
return k;
}
__Test010_VT __Test010::__vtable;
void __Test010::main(__rt::Array<String> args){
A a=__A::__init(new __A());
({ auto temp1 = a;
__rt::checkNotNull(temp1);
temp1->__vptr->setA_String(temp1,__rt::literal("A"));
})
B1 b1=__B1::__init(new __B1());
({ auto temp2 = b1;
__rt::checkNotNull(temp2);
temp2->__vptr->setA_String(temp2,__rt::literal("B1"));
})
B2 b2=__B2::__init(new __B2());
({ auto temp3 = b2;
__rt::checkNotNull(temp3);
temp3->__vptr->setA_String(temp3,__rt::literal("B2"));
})
C c=__C::__init(new __C());
({ auto temp4 = c;
__rt::checkNotNull(temp4);
temp4->__vptr->setA_String(temp4,__rt::literal("C"));
})
({ auto temp5 = a;
__rt::checkNotNull(temp5);
temp5->__vptr->printOther_A(temp5,a);
})
({ auto temp6 = a;
__rt::checkNotNull(temp6);
temp6->__vptr->printOther_A(temp6,b1);
})
({ auto temp7 = a;
__rt::checkNotNull(temp7);
temp7->__vptr->printOther_A(temp7,b2);
})
({ auto temp8 = a;
__rt::checkNotNull(temp8);
temp8->__vptr->printOther_A(temp8,c);
})
}

}

}

