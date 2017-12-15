#include <iostream>
#include "output.h"

using namespace std;
using namespace java::lang;
namespace inputs {

  namespace test042 {

    __A::__A()
    :__vptr(&__vtable) {
    }

Class __A::__class() {
    static Class k = new __Class(__rt::literal("inputs.test042.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
    void __A::m(A __this){
cout << __rt::literal("A.m()") << endl;
    }
  
A __A::m(A __this, A a){
cout << __rt::literal("A.m(A)") << endl;
return a;}

__B::__B()
:__vptr(&__vtable) {
}

Class __B::__class() {
static Class k = new __Class(__rt::literal("inputs.test042.B"),__A::__class());
return k;
}
__B_VT __B::__vtable;
void __B::m(B __this){
cout << __rt::literal("B.m()") << endl;
}

B __B::m(B __this, B b){
cout << __rt::literal("B.m(B)") << endl;
return b;}

A __B::m(B __this, A a){
cout << __rt::literal("B.m(A)") << endl;
return a;}

__Test042::__Test042()
:__vptr(&__vtable) {
}

Class __Test042::__class() {
static Class k = new __Class(__rt::literal("inputs.test042.Test042"),__Object::__class());
return k;
}
__Test042_VT __Test042::__vtable;
void __Test042::main(__rt::Array<String> args){
A a=__A::__init(new __A());
({A temp1=({A temp0=a;
__rt::checkNotNull(temp0);
temp0->__vptr->m_A(temp0, a);
})
;
__rt::checkNotNull(temp1);
temp1->__vptr->m(temp1);
})
B b=__B::__init(new __B());
({B temp3=({B temp2=b;
__rt::checkNotNull(temp2);
temp2->__vptr->m_B(temp2, b);
})
;
__rt::checkNotNull(temp3);
temp3->__vptr->m(temp3);
})
({A temp5=({B temp4=b;
__rt::checkNotNull(temp4);
temp4->__vptr->m_A(temp4, __rt::java_cast<A>(b));
})
;
__rt::checkNotNull(temp5);
temp5->__vptr->m(temp5);
})
}

}

}

