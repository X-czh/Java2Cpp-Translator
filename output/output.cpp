#include <iostream>
#include "output.h"

using namespace std;
using namespace java::lang;
namespace inputs {

  namespace test000 {

    __Test000::__Test000()
    :__vptr(&__vtable) {
    }

Class __Test000::__class() {
    static Class k = new __Class(__rt::literal("inputs.test000.Test000"),__Object::__class());
    return k;
}
    __Test000_VT __Test000::__vtable;

    void __Test000::main(__rt::Array<String> args){
cout << __rt::literal("Hello.") << endl;
}

  }

}

