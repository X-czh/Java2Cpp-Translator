#pragma once

#include "java_lang.h"

using namespace java::lang;

namespace inputs {
  namespace javalang {

    struct __A;

    struct __A_VT;

    struct __B;

    struct __B_VT;

    struct __Input;

    struct __Input_VT;

    typedef __A* A;


    typedef __B* B;


    typedef __Input* Input;

    Struct __A{

      __A_VT* __vptr;

      __A();

      int32_t method(A );

      String toString(A );

      static Class __class(A );

      static __A_VT __vtable;

    };

    Struct __B{

      __B_VT* __vptr;

      __B();

      String toString(B );

      static Class __class(B );

      static __B_VT __vtable;

    };

    Struct __Input{

      __Input_VT* __vptr;

      B b;

      A a1;

      A a2;

      Class ca1;

      Class ca2;

      __Input();

      static void main(Input , String );

      static Class __class(Input );

      static __Input_VT __vtable;

    };

    Struct __A_VT{

      Class __is_a;

      int32_t (*method)(A);

      String (*toString)(A);

      int32_t (*hashCode)(A);

      Class (*getClass)(A);

      bool (*equals)(A, Object);

      __A_VT()
      :__is_a(__A::__class()),
      method(&__A::method),
      toString(&__A::toString),
      hashCode((int32_t (*)(A)) &__Object::hashCode),
      getClass((Class (*)(A)) &__Object::getClass),
      equals((bool (*)(A, Object)) &__Object::equals){
      }

    };

    Struct __B_VT{

      Class __is_a;

      String (*toString)(B);

      int32_t (*method)(B);

      int32_t (*hashCode)(B);

      Class (*getClass)(B);

      bool (*equals)(B, Object);

      __B_VT()
      :__is_a(__B::__class()),
      toString(&__B::toString),
      method((int32_t (*)(B)) &__A::method),
      hashCode((int32_t (*)(B)) &__Object::hashCode),
      getClass((Class (*)(B)) &__Object::getClass),
      equals((bool (*)(B, Object)) &__Object::equals){
      }

    };

    Struct __Input_VT{

      Class __is_a;

      String (*toString)(Input);

      int32_t (*hashCode)(Input);

      Class (*getClass)(Input);

      bool (*equals)(Input, Object);

      __Input_VT()
      :__is_a(__Input::__class()),
      toString((String (*)(Input)) &__Object::toString),
      hashCode((int32_t (*)(Input)) &__Object::hashCode),
      getClass((Class (*)(Input)) &__Object::getClass),
      equals((bool (*)(Input, Object)) &__Object::equals){
      }

    };

  }
}
