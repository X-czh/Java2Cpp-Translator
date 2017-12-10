#pragma once

#include "java_lang.h"

using namespace java::lang;

namespace inputs {

  namespace test000 {

    struct __Test000;

    struct __Test000_VT;

    typedef __rt::Ptr<__Test000> Test000;

    struct __Test000 {

      __Test000_VT* __vptr;

      __Test000( ){}

      static Test000 __init()
;
      static void main(__rt::Array<String> );
      static Class __class()
;
      static __Test000_VT __vtable;

    };

    struct __Test000_VT {

      Class __is_a;

      void (*__delete)(__Test000*);

      int32_t (*hashCode)(Test000);

      bool (*equals)(Test000, Object);

      Class (*getClass)(Test000);

      String (*toString)(Test000);

      __Test000_VT()

      :__is_a(__Test000::__class()),
      __delete(&__rt::__delete<__Test000>),
      hashCode((int32_t (*)(Test000)) &__Object::hashCode),
      equals((bool (*)(Test000, Object)) &__Object::equals),
      getClass((Class (*)(Test000)) &__Object::getClass),
      toString((String (*)(Test000)) &__Object::toString){}

    };

  }

}

