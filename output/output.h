#pragma once

#include "java_lang.h"

using namespace java::lang;

namespace inputs {

  namespace testPrimitive {

    struct __TestPrimitive;

    struct __TestPrimitive_VT;

    typedef __rt::Ptr<__TestPrimitive> TestPrimitive;

    struct __TestPrimitive {

__TestPrimitive_VT* __vptr = ;
      __TestPrimitive( );

      static TestPrimitive __init();

      static void main(__rt::Array<String> );

      static Class __class();

static __TestPrimitive_VT __vtable = ;
    };

    struct __TestPrimitive_VT {

Class __is_a = ;
(*__delete)(__TestPrimitive*) = ;
int32_t (*hashCode)(TestPrimitive) = ;
bool (*equals)(TestPrimitive, Object) = ;
Class (*getClass)(TestPrimitive) = ;
String (*toString)(TestPrimitive) = ;
      __TestPrimitive_VT()
      :__is_a(__TestPrimitive::__class()),
      __delete(&__rt::__delete<__TestPrimitive>),
      hashCode((int32_t (*)(TestPrimitive)) &__Object::hashCode),
      equals((bool (*)(TestPrimitive, Object)) &__Object::equals),
      getClass((Class (*)(TestPrimitive)) &__Object::getClass),
      toString((String (*)(TestPrimitive)) &__Object::toString) {
      }

    };

  }

}

