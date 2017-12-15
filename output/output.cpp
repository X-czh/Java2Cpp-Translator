#include <iostream>
#include "output.h"

using namespace std;
using namespace java::lang;
namespace inputs {

  namespace test006 {

    __A::__A()
    :__vptr(&__vtable) {
    }

Class __A::__class() {
    static Class k = new __Class(__rt::literal("inputs.test006.A"),__Object::__class());
    return k;
}
__A_VT __A::__vtable;
;
    A __A::__init(A __this){
this->fld=__rt::literal("A");
    }
  
void __A::setFld(A __this,String f){
this->fld=f;
}

void __A::almostSetFld(A __this,String f){
String fld;
;
fld=f;
}

String __A::getFld(A __this){
return this->fld;}

__Test006::__Test006()
:__vptr(&__vtable) {
}

Class __Test006::__class() {
static Class k = new __Class(__rt::literal("inputs.test006.Test006"),__Object::__class());
return k;
}
__Test006_VT __Test006::__vtable;
;
void __Test006::main(__rt::Array<String> args){
A a=__A::__init(new __A());
;
({Aa;
__rt::checkNotNull(temp0);
temp0->__vptr->almostSetFld_String(temp0,__rt::literal("B"));
)}
cout << ({Aa;
__rt::checkNotNull(temp1);
temp1->__vptr->getFld(temp1);
)}
 << endl;
({Aa;
__rt::checkNotNull(temp2);
temp2->__vptr->setFld_String(temp2,__rt::literal("B"));
)}
cout << ({Aa;
__rt::checkNotNull(temp3);
temp3->__vptr->getFld(temp3);
)}
 << endl;
}

}

}

