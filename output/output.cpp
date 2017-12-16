#include <iostream>
#include "output.h"

using namespace java::lang;
namespace inputs {

  namespace test017 {

    __A::__A()
    :__vptr(&__vtable) {
    }

Class __A::__class() {
    static Class k = new __Class(__rt::literal("inputs.test017.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
    A __A::__init(A __this, int x){
__this->self=__this;
    }
  
A __A::self(A __this){
return __this->self;}

__Test017::__Test017()
:__vptr(&__vtable) {
}

Class __Test017::__class() {
static Class k = new __Class(__rt::literal("inputs.test017.Test017"),__Object::__class());
return k;
}
__Test017_VT __Test017::__vtable;
Test017 __Test017::__init(Test017 __this){
}

void __Test017::main(__rt::Array<String> args){
A a=__A::__init(new __A(), (5));
std::cout << ({A temp1=({A temp0=a;
__rt::checkNotNull(temp0);
temp0->__vptr->self(temp0);
})
;
__rt::checkNotNull(temp1);
temp1->__vptr->toString(temp1);
})
 << std::endl;
}

}

}

