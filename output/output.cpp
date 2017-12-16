#include <iostream>
#include "output.h"

using namespace java::lang;

namespace inputs {

  namespace test026 {

    __A::__A()
    :__vptr(&__vtable) {
    }

Class __A::__class() {
    static Class k = new __Class(__rt::literal("inputs.test026.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
    A __A::__init(A __this, int32_t i){
__Object::__init(__this);
__this->i=i;
return __this;    }
  
int32_t __A::get(A __this){
return __this->i;}

__B::__B()
:__vptr(&__vtable) {
}

Class __B::__class() {
static Class k = new __Class(__rt::literal("inputs.test026.B"),__A::__class());
return k;
}
__B_VT __B::__vtable;
B __B::__init(B __this, int32_t i){
__A::__init(__this, i);
return __this;}

int32_t __B::get(B __this){
return 10 - __this->i;}

__Test026::__Test026()
:__vptr(&__vtable) {
}

Class __Test026::__class() {
static Class k = new __Class(__rt::literal("inputs.test026.Test026"),__Object::__class());
return k;
}
__Test026_VT __Test026::__vtable;
Test026 __Test026::__init(Test026 __this){
__Object::__init(__this);
return __this;}

void __Test026::main(__rt::Array<String> args){
__rt::Array<A> as=({__rt::Array<B> temp0=new __rt::__Array<B>(10);
temp0;})
;
for(int32_t i=0; i<as->length; i++){
({__rt::checkStore(as, __A::__init(new __A(), i));
(*as)[i]=__A::__init(new __A(), i);
})
;}
int32_t k=0;
while (k<10){
std::cout << ({A temp1=__rt::java_cast<A>((*as)[k]);
__rt::checkNotNull(temp1);
temp1->__vptr->get(temp1);
})
 << std::endl;
k=k + 1;
}
}

}

}

